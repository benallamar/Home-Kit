package Component;

import Security.Certificat;
import Security.PaireClesRSA;

import java.security.PublicKey;

/**
 * Project Name : TL_crypto
 */
public class Equipement {
    private PaireClesRSA maCle;
    private Certificat monCert;
    private String monNom;
    private int monPort;

    Equipement(String name, int port) {
        // Define the component
        monNom = name;
        monPort = port;
    }

    public void affichage_da() {

    }

    public void affichage_ca() {

    }

    public void affichage() {

    }

    public String monNom() {
        return monNom;
    }

    public PublicKey maClePub() {
        // Return the publicKe
        return null;
    }

    public Certificat maCertif() {
        return monCert;
    }
}
