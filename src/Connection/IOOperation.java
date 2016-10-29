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
    protected Socket socket = null;
    protected ServerSocket server = null;
    protected PaireClesRSA maCle;
    protected Certificat monCert;
    protected String name;
    protected OutputStream os = null;
    protected ObjectOutputStream oos = null;
    protected HashMap<Integer, Object[]> CA = new HashMap<Integer, Object[]>();
    protected InputStream is = null;
    protected ObjectInputStream ois = null;
    protected HashMap<Integer, PublicKey> sessions = new HashMap<Integer, PublicKey>();
    protected SocketBody response;
    protected SocketBody request;
    protected int port;
    protected LinkedList<String> errors = new LinkedList<String>();

    // @over
    public void write(SocketBody response, boolean encrypt) throws IOException, ClassNotFoundException {
        response.debug();
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        //We have to cypher the message before we send it.
        //TODO: Add the PGP Protocol
        String encryptedMessage = JSONParser.serialize(response);
        if (encrypt) {
            //String encryptedMessage = PGPMessage.signEncryptMessage();
        }
        oos.writeObject(encryptedMessage);
        oos.flush();
    }

    public SocketBody read(boolean decrypt) throws IOException, ClassNotFoundException {
        is = socket.getInputStream();
        ois = new ObjectInputStream(is);
        //We have to decypher the gotten message
        //TODO : Add the PGP protocol here
        String decryptedMessage = (String) ois.readObject();
        return JSONParser.deserialize(decryptedMessage);
    }

    public void print(String string) {
        System.out.println(string);
    }

    public void print(int number) {
        System.out.println(number);
    }

    public void close() throws IOException {
        os.close();
        oos.close();
        is.close();
        ois.close();
        socket.close();
    }

    public String genSecCode(int length) {
        return UUID.randomUUID().toString().substring(0, length);

    }

    public void openSession(SocketBody request) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        //Deserialize the key
        PublicKey pub_client = request.getPubKey();
        //Open the session by adding the information to the sessions base
        sessions.put(request.getFromPort(), pub_client);
    }

    public void closeSession(SocketBody request) {
        //Remove the key of the sessions
        sessions.remove(request.getFromPort());
    }

    public PublicKey getSession(SocketBody request) {
        return sessions.get(request.getFromPort());
    }

    public void unauthorized(SocketBody response) throws IOException, ClassNotFoundException {
        //set the option that you have and error and close the connection
        response.setNewBody();
        response.setFailed();
        //Send the response and close the socket after
        write(response, true);
    }

    public void acceptConnection(SocketBody request, SocketBody response) throws OperatorCreationException, IOException, ClassNotFoundException, CertException {
        //we set the option to get the write from the server
        response.setOption(1);

        //Set the response body
        response.setNewBody();

        //Set the body of the connections

        //We update the CA file.
        PublicKey pubKey = getSession(request);
        Certificat cert = request.getCertificat();
        Object[] trusted_certificat = {pubKey, cert};
        System.out.print(request.cert);
        if (cert.verifiCerif(pubKey)) {
            CA.put(response.getFromPort(), trusted_certificat);
            response.setSuccess();
        } else {
            response.setFailed();
        }
    }

    public void connect(SocketBody request, SocketBody response) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
        //Set the header of the destination
        response.setHeader(request);
        //Set to the next option of the operation
        response.setOption(1);
        //Instantiate the body of the response
        response.setNewBody();

        //We generate the certificate after accepting the connection
        PublicKey pubKey = getSession(request);
        response.setCertificat(new Certificat(name, pubKey, maCle.privKey(), 356));
        print(response.getCertificat().toString());
        //Set the status for the response
        response.setSuccess();

        //Set the response
        write(response, false);
    }

    public boolean isExpired(SocketBody request) {
        if (CA.containsKey(request.getFromPort())) {
            String lastLog = (String) CA.get(request.getFromPort())[3];
            if (lastLog.equals(request.getSubject()))
                return true;
        }
        return false;
    }
}
