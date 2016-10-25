package Connection;


import Security.PaireClesRSA;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

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
            SocketBody response = new SocketBody();
            //Display the message of waiting connection
            print("Connexion en cours ...");
            connect(response);
            write(response);
            SocketBody request = read();
            if (request.isSuccess()) {
                switch (request.getOption()) {
                    case 1:
                        print("Certificat Delivrance ...");
                        acceptConnection(request, response);
                        write(request);
                        print("connection has been established between the two component");
                        break;
                    default:
                        print("Connection refused");
                }
            } else {
                print("Error on the server");
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Error 2" + e.getMessage());
        } catch (
                ClassNotFoundException e)

        {
            System.out.println("Error 3" + e.getMessage());
        }


    }

    public boolean connect(SocketBody response) {

        //First we set the option of the communication to tell the server the reason of this communication
        response.setOption(1);

        //Instantiate the body of our request.
        response.setBody(new HashMap<String, Object>());

        //We have to send the certificate to the server so he could create for as a kind of certificate
        response.getBody().put("public_key", maCle.serialize());
        response.getBody().put("name", name);

        //Set that the operation has been done
        response.setSuccess();

        //And return True as every thing is write
        return true;
    }


    public boolean acceptConnection(SocketBody request, SocketBody response) {
        //Set the response ...
        response.setOption(2);

        //Instantiate the body
        response.setBody(new HashMap<String, Object>());

        //We generate the certificat for the user ...
        response.getBody().put("certificat", "Some Certificat");

        //Set the response as correct
        response.setSuccess();

        //And return True
        return true;
    }

    public static void main(String[] args) {

        new Client("localhost", 3000).run();
    }
}
