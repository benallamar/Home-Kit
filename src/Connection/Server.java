package Connection;

import HomeSecurityLayer.Certificat;
import HomeSecurityLayer.PaireClesRSA;
import Interfaces.IHMConnexion;
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
public class Server extends Client {
    HashMap<String, String> tokens = new HashMap<String, String>();
    LinkedList<String> errors = new LinkedList<String>();
    public boolean on = false;

    public Server(String name, int port) {
        super(name, port);
        try {
            server = new ServerSocket(port);
            on = true;
        } catch (IOException e) {
            System.out.println("Error 1" + e.getMessage());
        }
    }

    public void runServer() {
        print("server is on");
        while (true) {
            try {

                SocketHandler s = new SocketHandler(server.accept(), name);
                read(s, false);
                //Open the sessions
                openSession(s);
                //Set the response of our header
                s.setHeader();
                //Set the public Key to decipher the code
                s.setPublicKey(maCle);
                //We switch from one user to another.
                IHMConnexion serverDisplay = new IHMConnexion("Server" + getName(), s.getSourceName());
                //Check of is a trusted equipement
                serverDisplay.checkTrustedEquipement();
                if (isConnected(s)) {
                    //If is connected by comparing the given certificat we cold connect it again
                    serverDisplay.dispose();
                    //We handle the
                    connectedEquipement(s);

                } else {
                    //We have to try to connect it to the server or cancel the connection
                    notConnectedEquipement(s, serverDisplay);
                    //We Update the home image
                    update();
                }

                close(s);


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


    }


    public boolean isConnected(SocketHandler s) {
        return false;
    }

    public boolean checkCode(SocketHandler s) {
        //TODO: Check the code
        print("check the code");
        String token = (String) s.getKey("token");
        String code = (String) s.getKey("Code");
        return true;
    }

    public String[] generateCode(SocketHandler s) throws IOException, ClassNotFoundException {
        //Generate the code and the token
        String token = genSecCode(13);
        String code = genSecCode(13);
        String[] oauth2 = {token, code};
        //Save the information about the generated token
        tokens.put(token, code);

        //Set the request body
        s.setOption(1);

        //Set new Body
        s.setNewBody();

        //Fill the body of the response
        s.setKey("token", token);

        //Set Success
        s.setSuccess();
        //Set the reponse
        write(s, false);
        return oauth2;
    }


    public void connectedEquipement(SocketHandler s, IHMConnexion serverDisplay) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException, OperatorCreationException, CertException {
        switch (s.getOption()) {
            case 4:
                //Wait for the connection
                serverDisplay.waitForConnection();
                connect(s);
                read(s, true);
                acceptConnection(s);
                serverDisplay.accepted();
                break;
            default:
                print("No known value");
                close(s);
        }
    }


    public void notConnectedEquipement(SocketHandler s, IHMConnexion serverDisplay) throws IOException, ClassNotFoundException, CertException, OperatorCreationException, InvalidKeySpecException, NoSuchAlgorithmException {

        if (serverDisplay.doYouAccept(s.getSourceName())) {
            String[] oauth2 = generateCode(s);
            serverDisplay.codeDisplay(oauth2[0], oauth2[1]);
            read(s, true);
            if (checkCode(s)) {
                serverDisplay.waitForConnection();
                connect(s);
                read(s, true);
                acceptConnection(s);
                serverDisplay.accepted();
                equipmentToSynchronizeWith(s);//We send the equipement to synchronize with
            } else {
                unauthorized(s);
                serverDisplay.refused();
            }
        }

    }

    public void equipmentToSynchronizeWith(SocketHandler s) throws IOException,ClassNotFoundException {
        s.setNewBody();
        int key = 1;
        for (Integer port : CA.keySet()) {
            s.setKey("key_" + key, port);
            key++;
        }
        write(s, true);
    }

    public static void main(String[] args) {
        Server server = new Server("host", 3000);
        server.run();
    }


    public boolean isOn() {
        return on;
    }

    public void run() {
        if (mode_server) {
            runServer();
        } else {
            runClient();
        }
    }

}
