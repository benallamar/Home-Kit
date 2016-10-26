package Connection;

import Security.Certificat;
import Security.PaireClesRSA;

import java.io.*;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Server extends IOOperation {
    protected int port = 12345;
    SocketBody request = new SocketBody();
    SocketBody response = new SocketBody();
    HashMap<String, String> tokens = new HashMap<String, String>();

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
                request = read();
                //We switch from one user to another.
                switch (request.getOption()) {
                    case 1:
                        print("Connection with the component: " + request.getKey("name"));
                        acceptConnection(request, response);
                        print("Connection accepter!");
                        write(response);
                        request = read();
                        connect(request, response);
                        write(response);
                        break;
                    case 2:
                        print("Get the component CA");
                        sendCA(request, response);
                        write(response);
                        break;
                    case 3:
                        print("Get the componenet DA");
                        sendDA(request, response);
                        write(response);
                        break;
                    case 4:
                        doYouTrust(request, response);
                        write(response);
                        /* Other options could */
                    case 5:
                        generateCode(request, response);
                        break;
                    case 6:
                        //On fait du OAuth2 Authentification
                        print("Check the checksum code");
                        checkCode(request, response);
                        write(response);
                    default:
                        print("Unknow option");
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
        response.setNewBody();

        //Set the body of the connections
        response.setKey("public_key", maCle.pubKey().toString());

        //We want tell the client the operation has been well
        response.setSuccess();

        //Set the response to True
        return true;
    }

    public boolean acceptConnection(SocketBody request, SocketBody response) {

        //Set to the next option of the operation
        response.setOption(2);

        //Instantiate the body of the response
        response.setNewBody();

        //We have to instantiate the body fo the response
        response.setKey("certificate", "Some string here to send to the server");

        //Set the status for the response
        response.setSuccess();

        //And return True
        return true;
    }

    public boolean doYouTrust(SocketBody request, SocketBody response) {
        //TODO:Creating the algorithm handling request routing
        return true;
    }

    public void getDA(SocketBody request, SocketBody response) {
        //TODO: Get the list of the CA list
        response.setOption(3);
        for (Certificat cert : DA.values()) {
            response.setKey("cert", cert);
        }
        response.setSuccess();
    }

    public void getCA(SocketBody request, SocketBody response) {
        //TODO: To send the list of the DA list
        response.setOption(3);
        for (Certificat cert : CA.values()) {
            response.setKey("cert", cert);
        }
        response.setSuccess();
    }

    public void checkCode(SocketBody request, SocketBody response) {
        //TODO: Check the code
        String token = (String) request.getKey("token");
        int code = (int) request.getKey("Code");
        if (tokens.get(token) == code) {
            //The code has been validted so we have to move to the next step
            response.setOption(1);
            response.setSuccess();
        } else {
            //The code hasn't been validated
            response.setFailed();
        }
    }

    public void generateCode(SocketBody request, SocketBody response) {
        //Generate the code and the token
        String token = genSecCode(13);
        String code = genSecCode(13);

        //Save the information about the generated token
        tokens.put(token, code);

        //Set the request body
        response.setOption(2);

        //Set new Body
        response.setNewBody();

        //Fill the body of the response
        response.setKey("token", token);

        //Set Success
        response.setSuccess();
    }


    public static void main(String[] args) {
        Server server = new Server(3000);
        server.run();
    }
}
