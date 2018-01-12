package connection;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import security.Certificat;
import security.PaireClesRSA;
import ui.IHMConnexion;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public class Client extends IOOperation implements Runnable {

  private int serverPort = 0;
  private int option = 0;
  private String host = "localhost";
  private LinkedList<int[]> equiToSynWith = new LinkedList<int[]>();

  public Client(String name, int port) {
    maCle = new PaireClesRSA(1024);
    this.name = name;
    this.port = port;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void runClient() {
    try {
      SocketHandler s = null;
      switch (option) {
        case 1:
          s = new SocketHandler(host, port, serverPort, name);
          IHMConnexion serverDisplay = new IHMConnexion("Client : " + name, "" + serverPort, false);
          connect(s, serverDisplay);
          serverDisplay.dispose();
          break;
        case 2:
          int[] conParams = equiToSynWith.pop();
          s = new SocketHandler("localhost", port, conParams[1], name);
          synchronize(s, conParams[0]);
          break;
        default:
          close(s);
      }
    } catch (Exception e) {
      errors.add(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

  /**
   * Initiate the first step in the handshake
   *
   * @param s SocketHandler
   */
  public void ack(SocketHandler s) throws IOException, ClassNotFoundException {
    //Instantiate the body of the response
    s.setNewBody();
    //Set the response
    write(s, false);
  }

  /*
   * Using protobuf
   */
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
    client.setOption(1);
    client.runClient();
  }


  public void setClientMode() {
    mode_server = false;
  }

  public void setServerPort(int port) {
    serverPort = port;
  }


  public void connect(SocketHandler s, IHMConnexion serverDisplay)
      throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException {
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
    if (serverDisplay.authenticate(s, this)) {
      //Get the request from the client
      s.setSuccess();
      //We sen the information to the server
      s.setSourceName(name);
      //Send the request
      write(s, true);
      //Set that we accept the connection
      read(s, true);
      //Set the display
      if (s.isSuccess()) {
        acceptConnection(s);
        establishConnection(s);//What do we have after that one ?
        read(s, true);
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
    write(s, true);
  }

  public synchronized void startSynchronization(SocketHandler s) throws IOException {
    //We have to send the key to all the
    int k = 1;
    while (s.hasKey("key_" + k)) {
      Double desPort = (Double) s.getKey("key_" + k);
      setOption(2);
      if (!CA.containsKey(desPort.intValue())) {
        equiToSynWith.push(new int[]{s.getFromPort(), desPort.intValue()});
        new Thread(this).start();
      }
      k++;
    }
  }

  public void synchronize(SocketHandler s, int parentPort)
      throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException {
    //We show a message
    print("Start the synchronisation of the equipement " + port + " with the equipement " + serverPort);
    //We set the option of the message
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
    read(s, true);
    //Create the session for that user
    openSession(s);
    //Set the header of the response
    s.setHeader();
    //Set the display
    if (s.isSuccess()) {
      acceptConnection(s);
      establishConnection(s);
      read(s, true);
      startSynchronization(s);
      equipmentToSynchronizeWith(s);
    }
    close(s);
    print(CA.toString());
  }

  public void setOption(int i) {
    option = i;
  }

  public void setParentPort(int port) {
    parentPort = port;
  }

}