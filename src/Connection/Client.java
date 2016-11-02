package Connection;


import HomKit.Home;
import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;
import Interfaces.IHMConnexion;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OperatorException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Client extends IOOperation implements Runnable {
    /*
    As an equipment could be a server and client and the same time, we precise that there are two definition:
        Equipment Client: When the equipment plays client's role.
        Equipement Server: When the equipment plays server's role
     */
    private int serverPort = 0;
    private String host = "localhost";//We set the default host, this is used to connect with external equipement
    private LinkedList<Object[]> nextOperations = new LinkedList<Object[]>();

    public Client(String name, int port) {
        maCle = new PaireClesRSA(2048);
        this.name = name;
        this.port = port;
    }

    //We set the host
    public void setHost(String host) {
        this.host = host;
    }

    public void runClient() {
        try {
            SocketHandler s = null;//Check the class SocketHandler for the purpose of the SocketHandler
            Object[] operation = nextOperations.pop();
            int option = (int) operation[0];
            s = new SocketHandler((String) operation[2], port, (int) operation[1], name);
            switch (option) {
                /*
                The equipement has two functions as client, the first one is
                sending connection request to other equipements, or to
                synchronize the informations about the certifcates and The CAs to whom
                he has trusted.
                RQ: The Programmer has the right to add as many options as he needs, but he has to add
                a switch for case for every one, and configure the server to so that he could handle the request
                with the appropriate functions.
                The two main option for the moment:
                    Option - 1 - : Establish trusted connection with the server.
                    Option - 2 - : Synchronize with other equipments.
                 */
                case 1:
                    //First one is connect with other Equipement(the equipement host:serverPost location)
                    //Display the frame
                    IHMConnexion serverDisplay = new IHMConnexion("Client : " + name, "" + serverPort, false);
                    connect(s, serverDisplay);
                    serverDisplay.dispose();
                    break;
                case 2:
                    //We try to rebuild a trusted connection with other equipements
                    synchronize(s, (int) operation[3]);
                    break;
                default:
                    //here we do nothing
            }
            //We finish by closing the connection
            close(s);
            update();
        } catch (IOException e)

        {
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();


        } catch (ClassNotFoundException e)

        {
            System.out.println("Error 6" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();


        } catch (OperatorCreationException e)

        {
            System.out.println("Error 7" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();

        } catch (CertException e)

        {
            System.out.println("Error 8" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();

        } catch (InvalidKeySpecException e)

        {
            System.out.println("Error 9" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e)

        {
            System.out.println("Error 10" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();

        } catch (InterruptedException e)

        {
            System.out.println("Error 11" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();

        } catch (OperatorException e)

        {
            System.out.println("Error 11" + e.getMessage());
            errors.add(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /*
    This method plays the role of 'ping', it has as purpose, to check if the
    other equipment is connected, and open a session with it.
     */
    public void ack(SocketHandler s) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        //We reset the body of our response to the server
        s.setNewBody();
        //We finish by sending the response to the server
        write(s, false);
    }

    /*
    As said, every equipement is a server and client at the same time.
    After run it as a server, we should activate the client mode.
     */
    public void setClientMode() {
        mode_server = false;
    }

    /*
    We set the Equipment(Port) to wich we want to talk.
     */
    public void setServerPort(int port) {
        serverPort = port;
    }

    /*
    Connect method handle the first connection with a new equipment.
    So it starts by the hand-check to create some kind of sessio:@
     */
    public void connect(SocketHandler s, IHMConnexion serverDisplay) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, OperatorException, CertException, InterruptedException {
        //First we start by sending our key.
        s.setPublicKey(maCle);
        //We display the frame("Waiting for connection")
        serverDisplay.waitForConnection();
        //We have to check if is not connected.
        s.setOption(3);
        //Start the connection
        ack(s);
        //Get the response from the server
        read(s, false);
        //Create the session for that user
        openSession(s);
        //We send an empty message to the server
        write(s, true);
        //We read the information
        read(s, false);
        //Handle the response
        if (serverDisplay.authenticate(s, this)) {
            //Get the request from the client
            s.setSuccess();
            //We sen the information to the server
            s.setSourceName(name);
            //Send the request
            write(s, true);
            //Set that we accept the connection
            read(s, false);
            //Set the display
            if (s.isSuccess()) {
                acceptConnection(s);
                establishConnection(s);//What do we have after that one ?
                read(s, false);
                startSynchronization(s);
                equipmentToSynchronizeWith(s);
                //For display proposal
                serverDisplay.dispose();
            } else {
                //TODO: Check why this is no displaying conneciton refused ?
                serverDisplay.refused();
            }
        }
        serverDisplay.dispose();
        close(s);
    }

    /*
    After every new successful connection, the equipment client should be synchronized with all the other equipments
    that the equipment server trust, so we should send this information to the equipment client
     */
    public void equipmentToSynchronizeWith(SocketHandler s) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {

        s.setNewBody();
        int key = 1;
        for (int portEqui : CA.keySet()) {
            if (portEqui != s.getFromPort()) {
                if (Home.DEBUG_MODE)
                    print("Synchroniztion of the equipement " + s.getFromPort() + " to the one connected on the port: " + portEqui);
                s.setKey("key_" + key, portEqui);
                key++;
            }
        }
        write(s, true);
    }

    public void startSynchronization(SocketHandler s) throws IOException {
        //We have to send the key to all the
        int k = 1;
        while (s.hasKey("key_" + k)) {
            Double desPort = (Double) s.getKey("key_" + k);
            if (!CA.containsKey(desPort.intValue())) {
                setNextOperation(2, desPort.intValue(), "localhost", s.getFromPort());
            }
            k++;
        }
    }

    public void synchronize(SocketHandler s, int parentPort) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, OperatorException, CertException, InterruptedException {
        //We show a message
        if (Home.DEBUG_MODE)
            print("Start the synchronisation of the equipement " + port + " with the equipement " + serverPort);
        //We set the option of the body message
        s.setOption(3);
        //Set the public Key to decipher the code
        s.setPublicKey(maCle);
        //We send the certificat too
        s.setCertificat((Certificat) CA.get(parentPort)[1]);
        //print
        s.debug();
        //We have to check if is not connected.
        ack(s);
        //Get the response from the server
        read(s, false);
        //Create the session for that user
        openSession(s);
        //Set the header of the response
        s.setHeader();
        //Set the display
        if (s.isSuccess()) {
            acceptConnection(s);
            establishConnection(s);
            read(s, false);
            startSynchronization(s);
            equipmentToSynchronizeWith(s);
        }
        close(s);
        print(CA.toString());
    }

    public void setNextOperation(int option, int desPort, String host, int sourcePort) {
        if (Home.DEBUG_MODE)
            switch (option) {
                case 1:
                    print("Asking new connection");
                    break;
                case 2:
                    print("Asking for synchronization");
            }
        nextOperations.push(new Object[]{option, desPort, host, sourcePort});
        //And yeah, we run a thread for every instruction
        new Thread(this).start();
    }

}