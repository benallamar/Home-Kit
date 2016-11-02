package Component;

import Connection.Server;
import HomeSecurityLayer.Certificat;
import Interfaces.IHMEquipement;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;

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

    //Check if an equipement is equal to other
    public boolean isEqual(Equipement equipement) {
        return port == equipement.port;
    }

    //Check if an equipement is equal to an other one
    public boolean isEqual(int port) {
        return this.port == port;
    }

    //Display the information about the equipement
    public String affichage() {
        String message = "Component: " + name;
        message += "\nPort:      " + port;
        message += "\nPublic Key:      " + maCle.pubKey().toString().substring(0, 30) + "...";
        return message;
    }


    public String name() {

        return "Equi: " + name.toUpperCase() + "\n Port: " + port;
    }

    public PublicKey maClePub() {
        return monCert.getPublicKey();
    }


    public Certificat maCertif() {
        return monCert;
    }

    //Check if the equipement is connected with the given in params
    public boolean connectedWith(Equipement equi) {
        return CA.containsKey(equi.port);
    }

    //Update the GUI
    public void update() {
        super.update();
        //if the user has done some operation while the "Information window".
        if (display != null)
            display.repaint();
    }

    public HashMap<Integer, Object[]> getCA() {
        return CA;
    }

    //Open the window of the project.
    public void display() {
        display = new IHMEquipement(this);
    }

    /*
    Transform the CA HashMap to Array of Certificates(We use this to display the Authorities certificate that have signed
     a certificate to the equipement)
     */

    public String[] CAArray() {
        String[] array = new String[CA.size()];
        int i = 0;
        for (Object[] obj : CA.values()) {
            array[i] = ((Certificat) obj[1]).getIssuer();//We set as information the issuer of the certificate
            i++;
        }
        return array;
    }

    /*
    Get the certificat of the Equipement index with the given index in params
     */
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

    /*
    Check if the given
     */
    public static boolean equiExist(int port, HashSet<Equipement> equipements) {
        for (Equipement equipement : equipements) {
            if (equipement.isEqual(port)) {
                return true;
            }
        }
        return false;
    }
}
