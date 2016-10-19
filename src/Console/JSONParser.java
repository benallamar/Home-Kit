package Console;

import Component.Equipement;
import Connection.SocketBody;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by marouanebenalla on 12/10/2016.
 */
public class JSONParser {
    //Set the directory of the comp data
    private static final String COMP_DIRECTORY = "src/data/CompData/";

    public static Equipement genrateEquipement(String compName) throws FileNotFoundException, IOException {
        JsonReader json_file = new JsonReader(new FileReader(COMP_DIRECTORY + compName + ".json"));
        return new Gson().fromJson(json_file, Equipement.class);

    }

    // Serialize the object to be send to the prod server
    public static String serialize(SocketBody response) {
        return new Gson().toJson(response);
    }

    // Deserializer the object
    public static SocketBody deserialize(String request) {
        return new Gson().fromJson(request, SocketBody.class);
    }
}
