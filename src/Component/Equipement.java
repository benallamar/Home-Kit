package Component;

import Connection.Server;
import Security.Certificat;
import Security.PaireClesRSA;

import java.security.PublicKey;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
public class Equipement extends Server {
    public Equipement(String name, int port) {
        // Define the component
        super(port);
        this.name = name;
    }

    public boolean isEqual(String name) {
        return this.name == name;
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
        return this;
    }

    public void setParent(Equipement parent) {
        //TODO: Try to fix this one
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

}
