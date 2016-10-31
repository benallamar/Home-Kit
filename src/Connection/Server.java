package Connection;

import Interfaces.IHMConnexion;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.*;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
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
public class Server extends Client {
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
        print("server on port : " + port + " is runing");
        switchMode();
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
                IHMConnexion serverDisplay = new IHMConnexion("Server : " + name, s.getSourceName(), true);
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


    public boolean isConnected(SocketHandler s) throws IOException, OperatorCreationException, CertException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (s.hasCertificat()) {
            for (Object[] obj : CA.values()) {
                if (s.getCertificat().verifiCerif((PublicKey) obj[0])) {
                    return true;
                }
            }
        }
        if (s.hasPubKey()) {
            for (Object[] obj : CA.values()) {
                if (s.getPubKey().equals(obj[0])) {
                    print("connected");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCode(SocketHandler s) {
        return s.isSuccess();
    }

    public String[] generateCode(SocketHandler s, IHMConnexion serverDisplay) throws IOException, ClassNotFoundException {
        //Generate the code and the token
        String[] oauth2 = genKeyToken(maCle.pubKey());

        //Set the request body
        s.setOption(1);

        //Set new Body
        s.setNewBody();

        //Fill the body of the response
        s.setKey("token", oauth2[0]);
        s.setKey("code", oauth2[1]);
        //Display the secure message
        serverDisplay.securityMessage(oauth2[2]);
        //Set Success
        s.setSuccess();
        //Set the reponse
        write(s, false);
        return oauth2;
    }


    public void connectedEquipement(SocketHandler s) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException, OperatorCreationException, CertException {
        s.debug();
        switch (s.getOption()) {
            case 3:
                //Wait for the connection
                establishConnection(s);
                //s.debug();
                read(s, true);
                acceptConnection(s);
                break;
            default:
                print("No known value");
                close(s);
        }
    }


    public void notConnectedEquipement(SocketHandler s, IHMConnexion serverDisplay) throws IOException, ClassNotFoundException, CertException, OperatorCreationException, InvalidKeySpecException, NoSuchAlgorithmException {

        if (serverDisplay.doYouAccept(s.getSourceName())) {
            generateCode(s, serverDisplay);
            read(s, true);
            if (checkCode(s)) {
                serverDisplay.waitForConnection();
                establishConnection(s);
                read(s, true);
                acceptConnection(s);
                serverDisplay.dispose();
                equipmentToSynchronizeWith(s);//We send the equipement to synchronize with
                read(s, true);
                startSynchronization(s);
            } else {
                unauthorized(s);
                write(s, false);
                serverDisplay.refused();
            }
        }

    }

    public void equipmentToSynchronizeWith(SocketHandler s) throws IOException, ClassNotFoundException {
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
