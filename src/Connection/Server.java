package Connection;

import Security.Certificat;
import Security.PaireClesRSA;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Server extends IOOperation {
    protected int port = 12345;


    public Server(int port) {
        try {
            server = new ServerSocket(port);
            maCle = new PaireClesRSA(1024);

        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                socket = server.accept();
                SocketBody request = read();
                //We switch from one user to another.
                switch (request.getOption()) {
                    case 1:
                        print("Connection with the component: " + request.getKey("modulus"));
                        SocketBody response = new SocketBody();
                        acceptConnection(request, response);
                        print("Connection accepter!");
                        write(response);
                        request = read();
                        connect(request, response);
                        write(response);
                        break;
                    case 2:
                        print("Get the component CA");
                        break;
                    case 3:
                        print("Get the componenet DA");
                        break;
                    default:
                        print("Unknow option");
                        socket.close();
                }
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error 2" + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Error 3" + e.getMessage());
        }
    }

    public boolean connect(SocketBody request, SocketBody response) {
        //we set the option to get the write from the server
        response.setOption(1);

        //Set the response body
        response.setBody(new HashMap<String, Object>());

        //Set the body of the connections
        response.getBody().put("public_key", maCle.pubKey().toString());

        //We want tell the client the operation has been well
        response.setSuccess();

        //Set the response to True
        return true;
    }

    public boolean acceptConnection(SocketBody request, SocketBody response) {
        //Set to the next option of the operation
        response.setOption(2);

        //Instantiate the body of the response
        response.setBody(new HashMap<String, Object>());

        //We have to instantiate the body fo the response
        response.getBody().put("certificate", "Some string here to send to the server");

        //Set the status for the response
        response.setSuccess();

        //And return True
        return true;
    }

    public static void main(String[] args) {
        Server server = new Server(3000);
        server.run();
    }
}
