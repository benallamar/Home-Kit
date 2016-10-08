package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Client implements Runnable {
    private Socket socket;
    private InputStream is = null;
    private ObjectInputStream ois = null;
    private OutputStream os = null;
    private ObjectOutputStream oos = null;

    public Client(String hostName, int port) {
        socket = new Socket(hostName, port);
    }

    public void run() {
        //TO DO: Implements this class to have the both client and server communicate
    }

    public void write(String stringText) throws IOException, ClassNotFoundException {
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        oos.writeObject(stringText);
    }

    public String read(String string) throws IOException, ClassNotFoundException {
        is = socket.getInputStream();
        ois = new ObjectOutputStream(is);
        return (String) ois.readObject();
    }

    public void closeSocket() {
        //TODO: We have to implements this
    }
}
