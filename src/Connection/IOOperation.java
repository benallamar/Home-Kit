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

    public void write(SocketBody response) throws IOException, ClassNotFoundException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(JSONParser.serialize(response));
        oos.flush();
    }

    public SocketBody read() throws IOException, ClassNotFoundException {
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        return JSONParser.deserialize((String) ois.readObject());
    }

    public void print(String string) {
        System.out.println(string);
    }


}
