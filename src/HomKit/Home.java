package HomKit;

import Component.Equipement;
import Console.FilerParser;
import Console.JSONParser;
import Interfaces.IHMHome.IHMHome;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
public class Home {
    private static HashMap<Integer, Equipement> equipements = new HashMap<Integer, Equipement>();
    private static IHMHome homeInterface = null;

    public static void checkComponent() {
        //TODO: Check if there been any new file
        equipements.put(2000, new Equipement("Client-1", 2002));
        equipements.put(3000, new Equipement("Client-2", 2003));
        equipements.put(4000, new Equipement("Client-3", 2004));
        equipements.put(5000, new Equipement("Client-4", 2005));
        equipements.put(6000, new Equipement("Client-5", 2006));
        equipements.put(7000, new Equipement("Client-6", 2007));
        equipements.put(8000, new Equipement("Client-7", 2008));
        equipements.put(9000, new Equipement("Client-8", 2009));
        HashSet<String> files = new HashSet<String>();
        //for (String newComName : files) {
        //if (!hasComponent(newComName)) {


        //}
    }

    public static boolean hasComponent(String compName) {
        //for (Equipement com : systemComponent) {
        //    if (com.isEqual(compName))
        //        return true;
        //}
        return false;

    }

    public static void run() {
        //TO DO : Run the system
        try {
            checkComponent();
            homeInterface = new IHMHome(equipements);
        } catch (InterruptedException e) {

        }
    }
}
