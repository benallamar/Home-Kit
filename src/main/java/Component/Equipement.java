package Component;

import Connection.Server;
import HomeSecurityLayer.Certificat;
import Interfaces.IHMEquipement;
import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
public class Equipement extends Server {

  private IHMEquipement display = null;

  public Equipement(String name, int port) {
    // Define the component
    super(name, port);
  }

  public boolean isEqual(String name) {
    return this.name == name;
  }

  public boolean isEqual(Equipement equipement) {
    return name() == equipement.name();
  }


  public String affichage() {
    String message = "Component: " + name;
    message += "\nPort:      " + port;
    message += "\nPublic Key:      " + maCle.pubKey().toString().substring(0, 30) + "...";
    return message;
  }

  public String affichageCA(int port) {
    Object[] ca = CA.get(port);
    return ca.toString();
  }

  public String name() {

    return "Equi: " + name.toUpperCase() + "\n Port: " + port;
  }

  public PublicKey maClePub() {
    // Return the publicKe
    return monCert.getPublicKey();
  }

  public Equipement setCertificateForChild(Equipement childComponent) {
    return this;
  }

  public Equipement addChildComp(Equipement childComponent) {
    return this;
  }


  public Equipement setMonCert(Certificat cert) {
    monCert = cert;
    return this;
  }

  public HashSet<Equipement> getChilds() {
    return new HashSet<Equipement>();
  }

  public Certificat maCertif() {
    return monCert;
  }

  public String toString() {
    String description = "";
    description += "";
    return description;
  }

  public void run(HashSet<Equipement> systemComponent) {
    //Here We have to establish the communication with the other component

    super.run();

  }

  public boolean connectedWith(Equipement equi) {
    return CA.containsKey(equi.port);
  }

  public void update() {
    super.update();
    if (display != null) {
      display.repaint();
    }
  }

  public HashMap<Integer, Object[]> getCA() {
    return CA;
  }

  public void display() {
    display = new IHMEquipement(this);
  }

  public String[] CAArray() {
    String[] array = new String[CA.size()];
    int i = 0;
    for (Object[] obj : CA.values()) {
      array[i] = ((Certificat) obj[1]).getIssuer();
      i++;
    }
    return array;
  }

  public String getCertCA(int index) throws IOException {
    int i = 0;
    for (Object[] obj : CA.values()) {
      if (index == i) {
        return ((Certificat) obj[1]).display();
      }
      i++;
    }
    return "No certificate with the given index";
  }

  public static boolean equiExist(String name, int port, HashSet<Equipement> equipements) {
    for (Equipement equipement : equipements) {
      if (equipement.port == port) {
        return true;
      }
    }
    return false;
  }
}
