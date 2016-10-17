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
    private String name;
    private Equipement parent = null;
    private HashSet<Equipement> childs = new HashSet<Equipement>();
    private short port;
    private Server server = null;

    public Equipement(String name, short port) {
        // Define the component
        this.name = name;
        this.port = port;
        maCle = new PaireClesRSA(1024);
    }

    public boolean isEqual(String name) {
        return this.name == name;
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
        String message = "Component: " + name;
        System.out.println(message);
    }

    public String name() {
        return name;
    }

    public PublicKey maClePub() {
        // Return the publicKe
        return monCert.getPublicKey();
    }

    public Equipement setCertificateForChild(Equipement childComponent) {
        childComponent
                .setMonCert(new Certificat(name, maCle, 365)) //Create the certificate
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

    public void run(HashSet<Equipement> systemComponent) {
        //Here We have to establish the communication with the other component
        server = new Server(port);


    }
}
