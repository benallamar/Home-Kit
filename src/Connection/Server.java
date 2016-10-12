package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Server extends IOOperation {
    private boolean isServerMode = false;
    protected int port = 12345;
    protected ServerSocket server = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);

        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                socket = server.accept();
                System.out.println(read());
                write("Thank you for your solution");
                System.out.println(read());
                write("Thank you for your solution");
                socket.close();
            }

        } catch (IOException e) {
            System.out.println("Error 2" + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Error 3" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(3000);
        //while (true) {
        server.run();
        //}
    }
}
