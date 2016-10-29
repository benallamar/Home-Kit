package Connection;


import HomeSecurityLayer.PaireClesRSA;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Client extends IOOperation implements Runnable {

    public Client(String hostName, int port) {
        try {
            socket = new Socket(hostName, port);
            maCle = new PaireClesRSA(1024);
            name = "String";

        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        } finally {

        }
    }

    public void run() {
        //TO DO: Implements this class to have both the client and server communicate
        try {
            SocketBody response = new SocketBody(name);
            //Display the message of waiting connection
            print("Connexion en cours ...");
            //Set the public Key to decipher the code
            response.setPublicKey(maCle);
            //We have to check if is not connected.
            ack(response);
            //Get the response from the server
            request = read(true);
            //Create the session for that user
            openSession(request);
            //Set the header of the response
            response.setHeader(request);
            //Handle the response
            switch (request.getOption()) {
                case 1:
                    sendCode(request, response);
                    request = read(true);
                    if (request.isSuccess()) {
                        print("connect");
                        acceptConnection(request, response);
                        connect(request, response);
                        print("connection finished");
                    } else {
                        close();
                    }
                    break;
                default:
                    print("Unknow request");
                    close();
            }

            close();
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


    public void sendCode(SocketBody request, SocketBody response) throws IOException, ClassNotFoundException {
        //Set the option of the response
        response.setOption(6);

        //Create new Body
        response.setNewBody();

        //Fill the body of the response
        response.setKey("token", request.getKey("token"));
        response.setKey("code", 12454);

        //Set that has been succeed
        response.setSuccess();
        //Set the response
        write(response, false);
    }

    public void ack(SocketBody response) throws IOException, ClassNotFoundException {
        //We have to make an acknowledge to the server
        response.setOption(0);
        //Instantiate the body of the response
        response.setNewBody();
        //Set the response
        write(response, false);
    }

    public static void main(String[] args) {

        new Client("localhost", 3000).run();
    }
}
