package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import Console.JSONParser;
import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;
import Interfaces.IHMHome.IHMHome;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.operator.OperatorCreationException;
import sun.security.rsa.RSAPublicKeyImpl;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Project Name : TL_crypto
 */
public abstract class IOOperation extends Thread {
    protected ServerSocket server = null;
    protected PaireClesRSA maCle;
    protected Certificat monCert;
    protected String name;
    protected HashMap<Integer, Object[]> CA = new HashMap<Integer, Object[]>();
    protected HashMap<Integer, PublicKey> sessions = new HashMap<Integer, PublicKey>();
    protected int port;
    protected LinkedList<String> errors = new LinkedList<String>();
    boolean mode_server = true;

    // @over
    public void write(SocketHandler s, boolean encrypt) throws IOException, ClassNotFoundException {
        s.write(maCle, encrypt);
    }

    public void read(SocketHandler s, boolean decrypt) throws IOException, ClassNotFoundException {
        s.read(maCle, decrypt);
    }

    public void print(String string) {
        System.out.println(string);
    }

    public void print(int number) {
        System.out.println(number);
    }

    public void close(SocketHandler s) throws IOException {
        s.close();
    }

    public String genSecCode(int length) {
        return UUID.randomUUID().toString().substring(0, length);

    }

    public void openSession(SocketHandler s) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        //Deserialize the key
        PublicKey pub_client = s.request.getPubKey();
        //Open the session by adding the information to the sessions base
        sessions.put(s.getFromPort(), pub_client);
    }

    public void closeSession(SocketBody request) {
        //Remove the key of the sessions
        sessions.remove(request.getFromPort());
    }

    public PublicKey getSession(SocketHandler s) {
        return sessions.get(s.getFromPort());
    }

    public void unauthorized(SocketHandler s) throws IOException, ClassNotFoundException {
        //set the option that you have and error and close the connection
        s.response.setNewBody();
        s.response.setFailed();
        //Send the response and close the socket after

        write(s, false);
    }

    public void acceptConnection(SocketHandler s) throws OperatorCreationException, IOException, ClassNotFoundException, CertException {
        //we set the option to get the write from the server
        s.setOption(1);

        //Set the response body
        s.setNewBody();

        //Set the body of the connections

        //We update the CA file.
        PublicKey pubKey = getSession(s);
        Certificat cert = s.getCertificat();
        //System.out.print(cert);
        Object[] trusted_certificat = {pubKey, cert};
        if (cert.verifiCerif(pubKey)) {
            //print("Get certificat from " + s.getFromPort() + " Certificat:" + cert.x509.toString());
            CA.put(s.getFromPort(), trusted_certificat);
            s.setSuccess();
        } else {
            s.setFailed();
        }
    }

    //Create the certificat for the user.
    public void establishConnection(SocketHandler s) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
        //Set the header of the destination
        s.setHeader();
        //Instantiate the body of the response
        s.setNewBody();
        //We generate the certificate after accepting the connection
        PublicKey pubKey = getSession(s);
        Certificat cert = new Certificat(name, pubKey, maCle.privKey(), 356);
        s.setCertificat(cert);
        //print(cert.toString());
        //Set the status for the response
        s.setSuccess();

        //Set the response
        write(s, false);
    }

    public boolean isExpired(SocketHandler s) {
        if (CA.containsKey(s.request.getFromPort())) {
            String lastLog = (String) CA.get(s.getFromPort())[3];
            if (lastLog.equals(s.getSubject()))
                return true;
        }
        return false;
    }

    public void update() {
        IHMHome.update();
    }

    public int getPort() {
        return port;
    }

    public void switchMode() {
        mode_server = !mode_server;
    }

    //To avoid the man in the middle
    public static String[] genKeyToken(PublicKey key) {
        RSAPublicKeyImpl pubParams = (RSAPublicKeyImpl) key;
        String modulus = pubParams.getModulus().toString();
        int longueur = modulus.length();
        String token = "", message = "", code = "";
        for (int i = 0; i < 12; i++) {
            int position = ((Double) (Math.random() * longueur)).intValue();
            token += Integer.toHexString(position);
            code += Integer.toHexString(position).length();
            message += modulus.charAt(position);
        }
        return new String[]{token, code, message};
    }

    //Decode the message
    public static String genKeyMessage(PublicKey key, String token, String code) {
        RSAPublicKeyImpl pubParams = (RSAPublicKeyImpl) key;
        String modulus = pubParams.getModulus().toString();
        String message = "";
        int k = 0;
        for (int i = 0; i < code.length(); i++) {
            int length = Integer.parseInt("" + code.charAt(i));
            int pos = Integer.parseInt(token.substring(k, k + length), 16);
            message += modulus.charAt(pos);
            k += length;
        }
        return message;
    }
}
