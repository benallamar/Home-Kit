package Connection;

import java.io.*;
import java.net.Socket;

/**
 * Project Name : TL_crypto
 */
public abstract class IOOperation {
    public Socket socket = null;


    public void write(String stringText) throws IOException, ClassNotFoundException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(stringText);
        oos.flush();
        //os.close();
    }

    public String read() throws IOException, ClassNotFoundException {
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String message = (String) ois.readObject();
        //ois.close();
        //is.close();
        return message;
    }
}
