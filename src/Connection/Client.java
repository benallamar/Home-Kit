package Connection;


import HomeSecurityLayer.Certificat;
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
    private int parentPort = 0;

    public Client(String hostName, int port) {
        maCle = new PaireClesRSA(1024);
        name = hostName;
        this.port = port;
    }

    public void runClient() {
        //Display the message of waiting connection
        try {
            //Initiate the socket
            SocketHandler s = new SocketHandler("localhost", port, serverPort, name);
            IHMConnexion serverDisplay = new IHMConnexion("Client" + getName(), "" + serverPort);
            switch (option) {
                case 1:
                    connect(s, serverDisplay);
                    break;
                case 2:
                    print("Synchro");
                    synchronize(s);
                    break;
                default:
                    print("Unkonw option");
                    close(s);
            }
            update();
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
        s.setOption(3);
        //Start the connection
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
            equipmentToSynchronizeWith(s);
            update();
            serverDisplay.dispose();
        } else {
            serverDisplay.refused();
        }
        close(s);
    }


    public void equipmentToSynchronizeWith(SocketHandler s) throws IOException, ClassNotFoundException {
        s.setNewBody();
        int key = 1;
        for (int portEqui : CA.keySet()) {
            if (portEqui != s.getFromPort()) {
                print("Synchroniztion of the equipement " + s.getFromPort() + " to the one connected on the port: " + portEqui);
                s.setKey("key_" + key, portEqui);
                key++;
            }
        }
        print("line" + getPort());
        s.debug();
        write(s, true);
    }

    public void startSynchronization(SocketHandler s) throws IOException {
        //We have to send the key to all the
        int k = 1;
        while (s.hasKey("key_" + k)) {
            Double desPort = (Double) s.getKey("key_" + k);
            print("Synchronize the equipement:" + port + " with the equipement:" + desPort.intValue());
            setServerPort(desPort.intValue());
            setOption(2);
            setParentPort(s.getFromPort());
            new Thread(this).start();
            k++;
        }
    }

    public void synchronize(SocketHandler s) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, OperatorException, CertException, InterruptedException {
        s.setOption(3);
        //Set the public Key to decipher the code
        s.setPublicKey(maCle);
        //We send the certificat too
        s.setCertificat((Certificat) CA.get(parentPort)[1]);
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
        print(s.getCertificat().toString());
        //Set the display
        if (s.isSuccess()) {
            acceptConnection(s);
            establishConnection(s);
        }
        close(s);
        print(CA.toString());
        update();
    }

    public void setOption(int i) {
        option = i;
    }

    public void setParentPort(int port) {
        parentPort = port;
    }
}
