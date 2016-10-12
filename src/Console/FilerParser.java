package Console;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by marouanebenalla on 12/10/2016.
 */
public class FilerParser {
    private String file_path;

    FilerParser(String file_path) {

        try {
            File file = new File(file_path);
            BufferedReader reader = new BufferedReader(file);
            String line = null;
            byte[] byt = new byte[(int) file.length];
            System.out.println(reader.readLine());
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
