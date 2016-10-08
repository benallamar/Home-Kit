package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public interface Server {
    private boolean isServerMode = false;
    protected int port = 12345;
    protected String name;

    public void modeServer() throws IOException, ClassNotFoundException {
        //Establish a Socket Connection
        ServerSocket serverSocket = new ServerSocket(port);
        try:{
            while (true) {

            }
        }
        finally{

        }
        Socket newServerSocket = serverSocket.accept();
        InputStream nativeIn = newServerSocket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(nativeIn);
        OutputStream nativeOut = newServerSocket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(nativeOut);
        String res = (String) ois.readObject();
        System.out.println(res);
        System.out.println("nice");
        oos.writeObject("I have nothing to say to you");
        oos.flush();
        ois.close();
        oos.close();
        nativeIn.close();
        nativeOut.close();
        newServerSocket.close();
        serverSocket.close();


        //Write Some things
        //oos.writeObject("I have nothing to say to you");
        //oos.flush();
        //Close all the connections
        //ois.close();
        //oos.close();
        //nativeIn.close();
        //nativeOut.close();
        //newServerSocket.close();
        //serverSocket.close();
    }

    public void modeClient() throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("localhost", port);
        //InputStream nativeInput = clientSocket.getInputStream();
        OutputStream nativeOutput = clientSocket.getOutputStream();
        for (int i = 0; i < 10; i++) {
            ObjectOutputStream oos = new ObjectOutputStream(nativeOutput);
            System.out.println("Write some thing\n");
            oos.writeObject("I have nothing to say to you");
            oos.flush();
        }
    }

    public void run(boolean modeServer) {
        try {
            if (modeServer)
                modeClient();
            else
                modeServer();
        } catch (IOException e) {
            System.out.print("You have get this error message:  " + e.getMessage() + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            System.out.print(e.getMessage());
        }

    }
}
