package Connection;

import Security.Certificat;
import Security.PaireClesRSA;

import java.io.*;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
/*
For server there are four main option:
    1- Connect.
    2- Connection Accepted.
    3- Do you trust the equipement with the given id.
 */
public class Server extends IOOperation {
    protected int port = 12345;
    HashMap<String, String> tokens = new HashMap<String, String>();

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            maCle = new PaireClesRSA(1024);
            name = "server";
            response = new SocketBody(name, port);

        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                socket = server.accept();
                request = read(false);
                //We switch from one user to another.
                print("You have new connection with the device" + request.getName());
                if (isConnected(request)) {
                    //We check the othe params that his is looking for
                    handleConnectedEquipement(request, response);

                } else {
                    //We have to try to connect it to the server or cancel the connection
                    handleNotConnectEquipement(request, response);
                }
                close();

            }
        } catch (IOException e)

        {
            System.out.println("Error 2" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (ClassNotFoundException e)

        {
            System.out.println("Error 3" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (InvalidKeySpecException e)

        {
            System.out.println("Error 4" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (NoSuchAlgorithmException e)

        {
            System.out.println("Error 5" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        }

    }

    public boolean isConnected(SocketBody request) {
        return true;
    }


    public void refuseConnection(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {

        //Set the option
        response.setOption(2);

        //Instantiate the response body
        response.setFailed();

        //Add the information to the DA(Not permitted devices
        DA.put(request.getPort(), getName());
    }

    public void getCA(SocketBody request, SocketBody response) {
        //TODO: To send the list of the DA list
        response.setOption(3);
        for (Certificat cert : CA.values()) {
            response.setKey("cert", cert);
        }
        response.setSuccess();
    }

    public boolean checkCode(SocketBody request) {
        //TODO: Check the code
        String token = (String) request.getKey("token");
        String code = (String) request.getKey("Code");
        return tokens.get(token) == code;
    }

    public void generateCode(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {
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
        //Set the response
        write(response);
    }


    public void handleConnectedEquipement(SocketBody request, SocketBody response) throws IOException {
        switch (request.getOption()) {
            case 1:
                print("Ask for an option of the availables options");
                break;
            default:
                print("No known value");
                close();
        }
    }

    public void handleNotConnectEquipement(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        response.setHeader(request);
        generateCode(request, response);
        request = read(true);
        if (checkCode(request)) {
            request = read(true);
            acceptConnection(request, response);
            write(response);
        } else {
            unauthorized(response);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(3000);
        server.run();
    }
}
