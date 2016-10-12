package HomKit;

import Component.Equipement;
import Console.FilerParser;
import Console.JSONParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
public class Home {
    private static HashSet<Equipement> systemComponent = new HashSet<Equipement>();

    public static void checkComponent() {
        //TODO: Check if there been any new file
        HashSet<String> files = new HashSet<String>();
        for (String newComName : files) {
            if (!hasComponent(newComName)) {
                try {
                    Equipement newCom = JSONParser.genrateEquipement(newComName);
                    systemComponent.add(newCom);
                    newCom.run(systemComponent);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }


        }
    }

    public static boolean hasComponent(String compName) {
        for (Equipement com : systemComponent) {
            if (com.isEqual(compName))
                return true;
        }
        return false;
    }

    public static void run() {
        //TO DO : Run the system
        checkComponent();
    }
}
