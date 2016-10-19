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
                switch (request.getOption()) {
                    case 1:
                        //We skip the part in which we ask the user to confirme the connection with the component.
                        print("Connection...");
                        SocketBody response = new SocketBody();
                        acceptConnection(request, response);
                        print("Connection accepter!");
                        write(response);
                        request = read();
                        acceptConnection(request, response);
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
        response.setSuccess();
        return true;
    }

    public boolean acceptConnection(SocketBody request, SocketBody response) {
        //
        response.setOption(2);
        //Instantiate the body of the response
        HashMap<String, String> body = new HashMap<String, String>()
        //We have to instantiate the body fo the response
        body.put("certificate", "Some string here to send to the server");
        //Set the bddy to the respone
        response.setBody(body);
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
