package Connection;

import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;


/**
 * Project Name : TL_crypto
 */
public final class SocketBody {
    private int status = 200;
    private int option = 1;
    private String sourceName;
    private String subject;
    private int from;
    private int to;
    private HashMap<String, Object> body = new HashMap<String, Object>();
    private String public_key = null;
    public String cert = null;

    public SocketBody(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public String getSourceName() {
        return sourceName;
    }

    public int getFromPort() {
        return from;
    }

    public int getToPort() {
        return to;
    }

    public void setFromPort(int port) {
        //We set the port Sender
        from = port;
    }

    public void setToPort(int port) {
        //We set the port of destination
        to = port;
    }

    public boolean isSuccess() {
        return status == 200;
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    public Object getKey(String key) {
        return body.get(key);
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public void setSuccess() {
        this.status = 200;
    }

    public void setFailed() {
        this.status = 500;
    }

    public String toString() {
        return body.toString();
    }

    public void setNewBody() {
        body = new HashMap<String, Object>();
    }

    public void setKey(String key, Object object) {
        body.put(key, object);
    }

    public void setHeader(SocketBody request) {
        to = request.from;
        from = request.to;
    }

    public void debug() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("The body of the connection from " + getFromPort() + " to " + getToPort());
        if (hasCertificat()) {
            System.out.println("Certificat: " + getCertificat().toString());
        }
        if (hasPubKey()) {
            System.out.println("Public Key: " + getPubKey().toString());
        }
        System.out.println(getBody().toString());

    }

    public void setPublicKey(PaireClesRSA key) throws IOException {
        public_key = PaireClesRSA.serialize(key);
    }

    public PublicKey getPubKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return PaireClesRSA.deserialize(public_key);
    }

    public void setCertificat(Certificat certificat) throws IOException {
        cert = Certificat.serialize(certificat);
    }

    public Certificat getCertificat() throws IOException {
        return Certificat.deserialize(cert);
    }

    public boolean hasKkey(String key) {
        return body.containsKey(key);
    }

    public boolean hasCertificat() {
        return cert != null;
    }

    public boolean hasPubKey() {
        return public_key != null;
    }
}
