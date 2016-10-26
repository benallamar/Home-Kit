package Connection;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import Console.JSONParser;
import Security.Certificat;
import Security.PaireClesRSA;

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
    protected HashMap<Integer, String> DA = new HashMap<Integer, String>();
    protected HashMap<Integer, Object[]> CA = new HashMap<Integer, Object[]>();
    protected InputStream is = null;
    protected ObjectInputStream ois = null;
    protected HashMap<Integer, PublicKey> sessions = HashMap < Integer, PublicKey>();
    protected SocketBody response;
    protected SocketBody request;
    protected int port;
    protected LinkedList<String> errors = new LinkedList<String>();

    // @over
    public void write(SocketBody response, boolean encrypt) throws IOException, ClassNotFoundException {
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        //We have to cypher the message before we send it.
        //TODO: Add the PGP Protocol
        if (encrypt) {
            String
        } else {
            String encryptedMessage = JSONParser.serialize(response);
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
        System.out.println(JSONParser.deserialize(decryptedMessage).toString());
        return JSONParser.deserialize(decryptedMessage);
    }

    public void print(String string) {
        System.out.println(string);
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

    public void openSession(SocketBody request) {
        //Open the session by adding the information to the sessions base
        sessions.put(request.getFromPort(), (PublicKey) request.getKey("public_key"))
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
        close();
    }

    public void acceptConnection(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {
        //we set the option to get the write from the server
        response.setOption(1);

        //Set the response body
        response.setNewBody();

        //Set the body of the connections

        //We update the CA file.
        PublicKey pubKey = getSession(request);
        Certificat cert = Certificat.deserialize((String) request.getKey("cert"));
        CA.put((Integer) response.getKey("port"),[pubKey, cert]);

        //We want tell the client the operation has been well
        response.setSuccess();

        //Set the response
        write(response, true);
    }

    public void connect(SocketBody request, SocketBody response) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //Set the header of the destination
        response.setHeader(request);
        //Set to the next option of the operation
        response.setOption(1);
        //Instantiate the body of the response
        response.setNewBody();

        //We generate the certificate after accepting the connection
        PublicKey pubKey = getSession(request);
        Certificat certificat = new Certificat(name, pubKey, maCle.privKey(), 356);

        //We serialize the certificat and we put it on the body
        response.setKey("cert", Certificat.serialize(certificat));


        //Set the status for the response
        response.setSuccess();
    }
}
