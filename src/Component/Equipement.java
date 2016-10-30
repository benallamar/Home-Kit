package Component;

import Connection.Server;
import HomeSecurityLayer.Certificat;
import Interfaces.IHMEquipement;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * Project Name : TL_crypto
 */
public class Equipement extends Server {
    private IHMEquipement display;

    public Equipement(String name, int port) {
        // Define the component
        super(name, port);
        display = new IHMEquipement();
    }

    public boolean isEqual(String name) {
        return this.name == name;
    }


    public void affichage() {
        String message = "Component: " + name;
    }

    public String name() {

        return name + "\n\n" + port;
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


    public void update() {
        super.update();
        display.repaint();
    }

    public HashMap<Integer, Object[]> getCA() {
        return CA;
    }

    public void display() {
        display = new IHMEquipement();
        display.setVisible(true);
    }
}
