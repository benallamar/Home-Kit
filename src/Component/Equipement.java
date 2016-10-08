package Component;

import Connection.Server;
import Security.Certificat;
import Security.PaireClesRSA;

import java.security.PublicKey;

/**
 * Project Name : TL_crypto
 */
public class Equipement extends Server {
    private PaireClesRSA maCle;
    private Certificat monCert;
    private String monNom;
    private Equipement parent = null, child = null;
    private Boolean client_mode;

    Equipement(String name, int port) {
        // Define the component
        monNom = name;
        monPort = port;
        monCert = new Certificat();
    }

    public void affichage_da() {
        child.affichage();
    }

    public void affichage_ca() {
        parent.affichage();
    }

    public void affichage() {
        String message = "Component: " + monNom;
        System.out.println(message);
    }

    public String monNom() {
        return monNom;
    }

    public PublicKey maClePub() {
        // Return the publicKe
        return monCert.getPublicKey();
    }

    public Certificat maCertif() {
        return monCert;
    }

    public String toString() {
        String description = "";
        description += "";
        return description;
    }
}
