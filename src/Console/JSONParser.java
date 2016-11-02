package Console;

import Component.Equipement;
import Connection.SocketBody;
import HomKit.Home;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by marouanebenalla on 12/10/2016.
 */
public class JSONParser {

    public static void genrateEquipement(String compName, HashSet<Equipement> equipements) throws IOException {
        //We restore the information of the equipment from the JSON file
        JsonReader json_file = new JsonReader(new FileReader(Home.EQUIPMENT_DIRECTORY + compName));
        HashMap<String, Object> object = new Gson().fromJson(json_file, HashMap.class);
        /*
        Every time you create a new JSON file inside your repository, we check if you have the right configuration
        if it's a good one, we create the equipement, if not, we show juste a small error message
         */
        if (object.containsKey("name") && object.containsKey("port")) {
            String name = (String) object.get("name");
            int port = ((Double) object.get("port")).intValue();
            if (!Equipement.equiExist(port, equipements)) {
                //Display the new information of the new detected equipement
                display(name, port);
                Home.newCo = true;
                //We create the new equipment and we play it
                equipements.add(new Equipement(name, port));
            }
        } else {
            //We display a small message to explain to the user that you have create a file with wrong configurations
            ConsoleDisplay.danger("The file with the name " + compName + " contains incorrect information", true);
        }
    }

    // Serialize the object to be send to the prod server
    public static String serialize(SocketBody response) {
        return new Gson().toJson(response);
    }

    // Deserializer the object
    public static SocketBody deserialize(String request) {
        return new Gson().fromJson(request, SocketBody.class);
    }

    //Display new detection
    public static void display(String name, int port) {
        ConsoleDisplay.warning("New Equipement has been detected...", true);
        ConsoleDisplay.infoTitle("  Name: ", false);
        ConsoleDisplay.infoTitle(name, false);
        ConsoleDisplay.infoTitle("  Port: ", false);
        ConsoleDisplay.infoTitle("" + port, false);
    }
}
