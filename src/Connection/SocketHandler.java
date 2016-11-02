package Connection;

import HomKit.Home;
import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

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

    public SocketHandler(String host, int port, int serverPort, String name) throws IOException {
        this.s = new Socket(host, serverPort);
        response = new SocketBody(name);
        setPorts(port, serverPort);
    }

    public void write(PrivateKey key, boolean encrypt) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (Home.DEBUG_MODE)
            response.debug();
        os = s.getOutputStream();
        oos = new ObjectOutputStream(os);
        String encryptedMessage = JSONParser.serialize(response);
        if (encrypt) {

            //oos.writeObject(PGPMessage.encryptRSA(key, encryptedMessage.getBytes()));
        } else {
            oos.writeObject(encryptedMessage);
        }
        oos.flush();
    }

    public void read(PublicKey key, boolean decrypt) throws IOException, ClassNotFoundException {
        is = s.getInputStream();
        ois = new ObjectInputStream(is);
        //We have to decypher the gotten message
        String final_message = null;
        if (decrypt) {
            //final_message = PGPMessage.decrypt(key, (byte[]) ois.readObject());
        } else {
            final_message = (String) ois.readObject();
        }
        request = JSONParser.deserialize(final_message);
        setHeader();
    }

    public void read() throws IOException, ClassNotFoundException {
        is = s.getInputStream();
        ois = new ObjectInputStream(is);
        request = JSONParser.deserialize((String) ois.readObject());
    }

    public void close() throws IOException {
        oos.close();
        ois.close();
        is.close();
        os.close();
        s.close();
    }

    public void setPublicKey(PaireClesRSA key) throws IOException {
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

    public void setFromPort(int port) {
        response.setFromPort(port);
    }

    public void setToPort(int port) {
        response.setToPort(port);
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
        return request.getKey(key);
    }

    public boolean hasKey(String key) {
        return request.hasKkey(key);
    }

    public void setCertificat(Certificat certificat) throws IOException {
        response.setCertificat(certificat);
    }

    public void setPorts(int portSource, int portDestination) {
        response.setFromPort(portSource);
        response.setToPort(portDestination);
    }

    public Certificat getCertificat() throws IOException {
        return request.getCertificat();
    }

    public void debug() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        response.debug();
    }

    public boolean hasPubKey() {
        return request.hasPubKey();
    }

    public boolean hasCertificat() {
        return request.hasCertificat();
    }

    public void setSourceName(String s) {
        response.setSourceName(s);
    }
}
