package console;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by marouanebenalla on 12/10/2016.
 */
public class FilerParser {
    private String file_path;

    FilerParser(String file_path) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_path));
        } catch (FileNotFoundException x) {
            System.err.format("FileNotFoundException: %s%n", x);
        } catch (IOException x) {
            System.err.format("FileNotFoundException: %s%n", x);
        }
    }

    public static void main(String[] args) {
        new FilerParser("src/data/welcome_message.txt");
    }
}
