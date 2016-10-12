package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Server extends Thread {
    private boolean isServerMode = false;
    protected int port = 12345;
    protected ServerSocket server = null;

    Server(int port) {
        server = new ServerSocket(port);
    }

    public void run(boolean modeServer) {
        try {
            socket = server.accept();
            read();
        }
    }
}
