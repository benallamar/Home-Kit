package Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Project Name : TL_crypto
 */
public abstract class IOOperation {
    protected Socket socket = null;

    public void write(String stringText) throws IOException, ClassNotFoundException {
        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        oos.writeObject(stringText);
        oos.flush();
        os.close();
    }

    public String read() throws IOException, ClassNotFoundException {
        is = socket.getInputStream();
        ois = new ObjectInputStream(is);
        String message = (String) ois.readObject();
        ois.close();
        is.close();
        return message;
    }
}
