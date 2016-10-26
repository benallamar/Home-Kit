package Connection;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import Console.JSONParser;
import Security.Certificat;
import Security.PaireClesRSA;

import java.util.UUID;

/**
 * Project Name : TL_crypto
 */
public abstract class IOOperation extends Thread {
    public Socket socket = null;
    protected ServerSocket server = null;
    protected PaireClesRSA maCle;
    protected Certificat monCert;
    protected String name;
    protected HashMap<Integer, Certificat> CA = HashMap < Integer, Certificat>();
    protected HashMap<Integer, Certificat> DA = HashMap < Integer, Certificat>();

    // @over
    public void write(SocketBody response) throws IOException, ClassNotFoundException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        //We have to cypher the message before we send it.
        //TODO: Add the PGP Protocol
        String encryptedMessage = JSONParser.serialize(response);
        oos.writeObject(encryptedMessage);
        oos.flush();
    }

    public SocketBody read() throws IOException, ClassNotFoundException {
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        //We have to decypher the gotten message
        //TODO : Add the PGP protocol here
        String decryptedMessage = (String) ois.readObject();
        return JSONParser.deserialize(decryptedMessage);
    }

    public void print(String string) {
        System.out.println(string);
    }

    public String genSecCode(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

}
