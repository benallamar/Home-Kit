package Connection;


import HomeSecurityLayer.PaireClesRSA;
import Interfaces.IHMConnexion;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OperatorException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Client extends IOOperation implements Runnable {
    private int serverPort = 0;
    private int option = 0;

    public Client(String hostName, int port) {
        maCle = new PaireClesRSA(1024);
        name = hostName;
        this.port = port;
    }

    public void runClient() {
        //Display the message of waiting connection
        try {
            //Initiate the socket
            SocketHandler s = new SocketHandler("localhost", port, name);
            IHMConnexion serverDisplay = new IHMConnexion("Client" + getName(), "" + serverPort);
            switch (option) {
                case 1:
                    connect(s, serverDisplay);
                    break;
                case 2:
                    synchronize(s);
                    break;
                default:
                    print("Unkonw option");
                    close(s);
            }
        } catch (IOException e)

        {
            System.out.println("Error 5" + e.getLocalizedMessage());
            errors.add(e.getLocalizedMessage());

        } catch (ClassNotFoundException e)

        {
            System.out.println("Error 6" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (OperatorCreationException e)

        {
            System.out.println("Error 7" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (CertException e)

        {
            System.out.println("Error 8" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (InvalidKeySpecException e)

        {
            System.out.println("Error 9" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (NoSuchAlgorithmException e)

        {
            System.out.println("Error 10" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (InterruptedException e)

        {
            System.out.println("Error 11" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        } catch (OperatorException e)

        {
            System.out.println("Error 11" + e.getMessage());
            errors.add(e.getLocalizedMessage());

        }
    }

    //Create the session
    public void ack(SocketHandler s) throws IOException, ClassNotFoundException {
        //We have to make an acknowledge to the server
        s.setOption(0);
        //Instantiate the body of the response
        s.setNewBody();
        //Set the response
        write(s, false);
    }

    //Send athentification code
    public void sendCode(SocketHandler s, String[] authen) throws IOException, ClassNotFoundException {
        //Set the option of the response
        s.setOption(6);

        //Create new Body
        s.setNewBody();

        //Fill the body of the response
        s.setKey("token", s.getKey("token"));
        s.setKey("code", 12454);

        //Set that has been succeed
        s.setSuccess();
        //Set the response
        write(s, false);
    }


    public static void main(String[] args) {
        Client client = new Client("localhost", 3000);
        client.setServerPort(3000);
        client.start();
    }

    public void startSynchronization(SocketHandler s) throws IOException {
        //We have to send the key to all the
        int k = 0;
        while (s.hasKey("key_" + k)) {
            setServerPort((Integer) s.getKey("key_" + k));
            setOption(2);
            setClientMode();
            new Thread(this).start();
        }
    }

    public void setClientMode() {
        mode_server = false;
    }

    public void setServerPort(int port) {
        serverPort = port;
    }


    public void connect(SocketHandler s, IHMConnexion serverDisplay) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, OperatorException, CertException, InterruptedException {

        //Set the public Key to decipher the code
        s.setPublicKey(maCle);
        //Check
        serverDisplay.waitForConnection();
        //We have to check if is not connected.
        ack(s);
        //Get the response from the server
        read(s, true);
        //Create the session for that user
        openSession(s);
        //Set the header of the response
        s.setHeader();
        //Handle the response
        serverDisplay.authenticate(s, this);
        //Get the request from the client
        read(s, true);
        //Set the display
        if (s.isSuccess()) {
            acceptConnection(s);
            establishConnection(s);
            read(s, true);
            startSynchronization(s);
            //For display proposal
            serverDisplay.accepted();
            update();
        } else {
            serverDisplay.refused();
        }
        close(s);
    }

    public void synchronize(SocketHandler s) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, OperatorException, CertException, InterruptedException {

        //Set the public Key to decipher the code
        s.setPublicKey(maCle);
        //We have to check if is not connected.
        ack(s);
        //Get the response from the server
        read(s, true);
        //Create the session for that user
        openSession(s);
        //Set the header of the response
        s.setHeader();
        //Get the request from the client
        read(s, true);
        //Set the display
        if (s.isSuccess()) {
            acceptConnection(s);
            establishConnection(s);
            update();
        }
        close(s);
    }

    public void setOption(int i) {
        option = i;
    }
}
