package Connection;

import HomeSecurityLayer.PaireClesRSA;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import Console.JSONParser;

/**
 * Project Name : TL_crypto
 */
public class SocketHandler {
    protected Socket s;
    protected OutputStream os = null;
    protected ObjectOutputStream oos = null;
    protected InputStream is = null;
    protected ObjectInputStream ois = null;
    protected SocketBody response;
    protected SocketBody request;

    public SocketHandler(Socket s, String name) {

        this.s = s;
        response = new SocketBody(name);
    }

    public SocketHandler(String host, int port, String name) throws IOException {
        this.s = new Socket(host, port);
        response = new SocketBody(name);
    }

    public void write(PaireClesRSA key, boolean encrypt) throws IOException, ClassNotFoundException {
        response.debug();
        os = s.getOutputStream();
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

    public void read(PaireClesRSA key, boolean decrypt) throws IOException, ClassNotFoundException {
        is = s.getInputStream();
        ois = new ObjectInputStream(is);
        //We have to decypher the gotten message
        //TODO : Add the PGP protocol here
        String decryptedMessage = (String) ois.readObject();
        request = JSONParser.deserialize(decryptedMessage);
    }

    public void close() throws IOException {
        oos.close();
        ois.close();
        is.close();
        os.close();
        s.close();
    }

    public void setPublicKey(PaireClesRSA key) throws IOException {
        System.out.print(key.toString());
        response.setPublicKey(key);
    }

    public void setHeader() {
        response.setHeader(request);
    }

    public boolean isSuccess() {
        return request.isSuccess();
    }

    public String getSourceName() {
        return request.getSourceName();
    }

    public int getFromPort() {
        return request.getFromPort();
    }

    public String getSubject() {
        return request.getSubject();
    }

    public PublicKey getPubKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return request.getPubKey();
    }

    public void setOption(int option) {
        response.setOption(option);
    }

    public void setSuccess() {
        response.setSuccess();
    }

    public void setFailed() {
        response.setFailed();
    }

    public int getOption() {
        return request.getOption();
    }

    public void setNewBody() {
        response.setNewBody();
    }

    public void setKey(String key, Object object) {
        response.setKey(key, object);
    }

    public Object getKey(String key) {
        return response.getKey(key);
    }

}
