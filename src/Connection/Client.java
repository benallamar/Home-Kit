package Connection;

import Component.Equipement;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Client extends IOOperation implements Runnable {

    public Client(String hostName, int port) {
        try {
            socket = new Socket(hostName, port);
        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        } finally {

        }
    }

    public void run() {
        //TO DO: Implements this class to have the both client and server communicate
        try {
            write("Nice to see you working");
            System.out.println(read());
            closeSocket();
        } catch (IOException e) {
            System.out.println("Error 2" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error 3" + e.getMessage());
        }


    }


    public static void main(String[] args) {
        Client radio = new Client("local_host", 3000);
        radio.run();
    }

    public void closeSocket() throws IOException {
        //TODO: We have to implements this
        socket.close();
    }
}
