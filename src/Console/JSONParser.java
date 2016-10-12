package Console;

import Component.Equipement;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
