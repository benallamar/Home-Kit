package Component;

import Connection.Server;
import Security.Certificat;
import Security.PaireClesRSA;

import java.security.PublicKey;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
public class Equipement {
    private PaireClesRSA maCle;
    private Certificat monCert;
    private String monNom;
    private Equipement parent = null;
    private HashSet<Equipement> childs = new HashSet<Equipement>();
    private int port;

    public Equipement(String name, int port) {
        // Define the component
        monNom = name;
        this.port = port;
        maCle = new PaireClesRSA(1024);
    }

    public void affichage_da() {
        for (Equipement child : childs) {
            child.affichage();

        }
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

    public Equipement setCertificateForChild(Equipement childComponent) {
        childComponent
                .setMonCert(new Certificat(monNom, maCle, 365)) //Create the certificate
                .setParent(this);//Add to Parent
        this.addChildComp(childComponent);
        return this;
    }

    public Equipement addChildComp(Equipement childComponent) {
        childs.add(childComponent);
        return this;
    }

    public Equipement setParent(Equipement parentComponent) {
        parent = parentComponent;
        return this;
    }

    public Equipement setMonCert(Certificat cert) {
        monCert = cert;
        return this;
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
