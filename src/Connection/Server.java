package Connection;

import Security.Certificat;
import Security.PaireClesRSA;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
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
    protected SocketBody response;
    protected SocketBody request;
    HashMap<String, String> tokens = new HashMap<String, String>();
    LinkedList<String> errors = new LinkedList<String>();

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
                request = read();
                //We switch from one user to another.
                print("You have new connection with the device" + request.getName());
                if (isConnected(request, response)) {
                    //We check the othe params that his is looking for
                    switch (request.getOption()) {
                        case 2:
                            print("Get the component CA");
                            sendCA(request, response);
                            write(response);
                            break;
                        default:
                            print("Option not recognized");

                    }
                } else {
                    //We have to try to connect it to the server or cancel the connection
                    generateCode(request, response);
                    request = read();
                    if (checkCode(request)) {
                        connect(request, response);
                        acceptConnection(read(), response);
                    } else {
                        unauthorized(response);
                    }

                }
                close();
            }
        } catch (
                IOException e)

        {
            System.out.println("Error 2" + e.getMessage());
            errors.add(e.getLocalizedMessage())

        } catch (
                ClassNotFoundException e)

        {
            System.out.println("Error 3" + e.getMessage());
            errors.add(e.getLocalizedMessage())

        } catch (
                InvalidKeySpecException e)

        {
            System.out.println("Error 4" + e.getMessage());
            errors.add(e.getLocalizedMessage())

        } catch (
                NoSuchAlgorithmException e)

        {
            System.out.println("Error 5" + e.getMessage());
            errors.add(e.getLocalizedMessage())

        }

    }

    public boolean isConnected(SocketBody request) {
        return true;
    }

    public void connect(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {
        //we set the option to get the write from the server
        response.setOption(1);

        //Set the response body
        response.setNewBody();

        //Set the body of the connections
        response.setKey("public_key", maCle.pubKey().toString());

        //We want tell the client the operation has been well
        response.setSuccess();

        //Set the response
        write(response);
    }

    public void acceptConnection(SocketBody request, SocketBody response) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {


        //Set to the next option of the operation
        response.setOption(1);

        //Instantiate the body of the response
        response.setNewBody();

        //We generate the certificate after accepting the connection
        PublicKey pubKey = PaireClesRSA.desrialize((String) request.getKey("public_key"));
        Certificat certificat = new Certificat(name, pubKey, maCle.privKey(), 356);

        //We serialize the certificat and we put it on the body
        response.setKey("certificate", Certificat.serialize(certificat));

        //We update the CA file.
        CA.put((Integer) response.getKey("port"),[pubKey, certificat]);

        //Set the status for the response
        response.setSuccess();

    }

    public void refuseConnection(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {

        //Set the option
        response.setOption(2);

        //Instantiate the response body
        response.setFailed();

        //Add the information to the DA(Not permitted devices
        DA.put(request.getPort(), getName());
    }

    public void trust(SocketBody request, SocketBody response) {
        //And we have to see if we trust any of the following


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

    public void unauthorized(SocketBody response) throws IOException, ClassNotFoundException {
        //set the option that you have and error and close the connection
        response.setNewBody();
        response.setFailed();
        //Send the response and close the socket after
        write(response);
        close();
    }

    public static void main(String[] args) {
        Server server = new Server(3000);
        server.run();
    }
}
