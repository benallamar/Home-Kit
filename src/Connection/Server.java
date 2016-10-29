package Connection;

import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.*;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
    LinkedList<String> errors = new LinkedList<String>();

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            maCle = new PaireClesRSA(1024);
            name = "server";
            response = new SocketBody(name);

        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                socket = server.accept();
                request = read(false);
                //Open the sessions
                openSession(request);
                //Set the response of our header
                response.setHeader(request);
                //Set the public Key to decipher the code
                response.setPublicKey(maCle);
                //We switch from one user to another.
                print("You have new connection with the device" + request.getSourceName());
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

        } catch (OperatorCreationException e)

        {
            System.out.println("Error 3" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (CertException e)

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
        return false;
    }


    public void refuseConnection(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {

        //Set the option
        response.setOption(2);
        //Instantiate the response body
        response.setFailed();
        //Set the response
        write(response, false);
    }

    public void getCA(SocketBody request, SocketBody response) {
        //TODO: To send the list of the DA list
        response.setOption(3);
        for (Object[] cert_aut : CA.values()) {
            response.setKey("cert", cert_aut[1]);
        }
        response.setSuccess();
    }

    public boolean checkCode(SocketBody request) {
        //TODO: Check the code
        print("check the code");
        String token = (String) request.getKey("token");
        String code = (String) request.getKey("Code");
        return true;
    }

    public void generateCode(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {
        //Generate the code and the token
        String token = genSecCode(13);
        String code = genSecCode(13);

        //Save the information about the generated token
        tokens.put(token, code);

        //Set the request body
        response.setOption(1);

        //Set new Body
        response.setNewBody();

        //Fill the body of the response
        response.setKey("token", token);

        //Set Success
        response.setSuccess();
        //Set the reponse
        write(response, false);
    }


    public void handleConnectedEquipement(SocketBody request, SocketBody response) throws IOException {
        switch (request.getOption()) {
            case 1:
                print("Ask for an option of the availabl@es options");
                break;
            default:
                print("No known value");
                close();
        }
    }

    public void handleNotConnectEquipement(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException, CertException, OperatorCreationException, InvalidKeySpecException, NoSuchAlgorithmException {
        generateCode(request, response);
        request = read(true);
        if (checkCode(request)) {
            print("connect");
            connect(request, response);
            print("connect");
            request = read(true);
            print("connect");
            acceptConnection(request, response);
        } else {
            unauthorized(response);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(3000);
        server.run();
    }

    //Check if the neighberhood
    public boolean doYouKnow(SocketBody request) throws OperatorCreationException, CertException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (CA.containsKey(request.getFromPort())) {
            PublicKey key = request.getPubKey();
            Certificat cert = (Certificat) CA.get(request.getFromPort())[1];
            if (cert.verifiCerif(key))
                return true;
        }
        return false;
    }

    public void doYouTrust(SocketBody request, SocketBody response) throws OperatorCreationException, CertException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (isExpired(request)) {

        } else {
            if (doYouKnow(request)) {
                //We should return true
            } else {
                Set<Integer> ports = CA.keySet();
                for (int port : ports) {
                    if (doYouTrustClientMode(port, request)) {

                    }

                }
            }
        }

    }

    public boolean doYouTrustClientMode(int port, SocketBody request) {
        return true;
    }
}
