package Console;

import Component.Equipement;
import Connection.SocketBody;
import HomKit.Home;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by marouanebenalla on 12/10/2016.
 */
public class JSONParser {

  //Set the directory of the comp data
  private static final String COMP_DIRECTORY = "src/data/CompData/";

  public static void genrateEquipement(String compName, HashSet<Equipement> equipements)
      throws IOException {
    JsonReader json_file = new JsonReader(new FileReader(COMP_DIRECTORY + compName));
    HashMap<String, Object> object = new Gson().fromJson(json_file, HashMap.class);
    String name = (String) object.get("name");
    int port = ((Double) object.get("port")).intValue();
    if (!Equipement.equiExist(name, port, equipements)) {
      System.out.println("New Equipement has been detected... Name: " + name + " Port:" + port);
      Home.newCo = true;
      equipements.add(new Equipement(name, port));
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
}
