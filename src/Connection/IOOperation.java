package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import Console.JSONParser;
import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.operator.OperatorCreationException;

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

<<<<<<<HEAD

    public void openSession(SocketBody request) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        //Deserialize the key
        PublicKey pub_client = request.getPubKey();
        //Open the session by adding the information to the sessions base
        sessions.put(request.getFromPort(), pub_client);
=======
        public void openSession (SocketHandler s) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
        {
            //Deserialize the key
            PublicKey pub_client = s.request.getPubKey();
            //Open the session by adding the information to the sessions base
            sessions.put(s.getFromPort(), pub_client);
>>>>>>>7 a6243510962f4572971285ac0623e07e60fb030
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
        s.response.setOption(1);

        //Set the response body
        s.response.setNewBody();

        //Set the body of the connections

        //We update the CA file.
        PublicKey pubKey = getSession(s);
        Certificat cert = s.request.getCertificat();
        Object[] trusted_certificat = {pubKey, cert};
        System.out.print(s.request.cert);
        if (cert.verifiCerif(pubKey)) {
            CA.put(s.response.getFromPort(), trusted_certificat);
            s.response.setSuccess();
        } else {
            s.response.setFailed();
        }
    }

    public void connect(SocketHandler s) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
        //Set the header of the destination
        s.response.setHeader(s.request);
        //Set to the next option of the operation
        s.response.setOption(1);
        //Instantiate the body of the response
        s.response.setNewBody();

        //We generate the certificate after accepting the connection
        PublicKey pubKey = getSession(s);
        s.response.setCertificat(new Certificat(name, pubKey, maCle.privKey(), 356));
        print(s.response.getCertificat().toString());
        //Set the status for the response
        s.response.setSuccess();

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
    }

    public int getPort() {
        return port;
    }
}
