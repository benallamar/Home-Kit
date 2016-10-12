package Console;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * Created by marouanebenalla on 12/10/2016.
 */
public class JSONParser {
    JSONParser() {
        Gson g = new Gson();
        JsonReader json_file = new JsonReader();
        g.fromJson(new JsonReader("src/CompData/x_5.json"));
    }
}
