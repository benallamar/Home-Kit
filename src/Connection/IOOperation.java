package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Console.JSONParser;
import Security.Certificat;
import Security.PaireClesRSA;

/**
 * Project Name : TL_crypto
 */
public abstract class IOOperation extends Thread {
    public Socket socket = null;
    protected ServerSocket server = null;
    protected PaireClesRSA maCle;
    protected Certificat monCert;
    protected String name;
    protected OutputStream os = null;
    protected ObjectOutputStream oos = null;
    InputStream is = null;
    ObjectInputStream ois = null;

    // @over
    public void write(SocketBody response) throws IOException, ClassNotFoundException {
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        //We have to cypher the message before we send it.
        //TODO: Add the PGP Protocol
        String encryptedMessage = JSONParser.serialize(response);
        oos.writeObject(encryptedMessage);
        oos.flush();
    }

    public SocketBody read() throws IOException, ClassNotFoundException {
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

}
