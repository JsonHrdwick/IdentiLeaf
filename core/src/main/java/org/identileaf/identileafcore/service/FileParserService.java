package org.identileaf.identileaf.service;

import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class FileParserService {
    private final String databaseid;
    private final String dbusername;
    private final String dbpassword;
    private final String aikey;

    public FileParserService(String filename) throws IOException {
        String json = fileReader("creds.json");

        JsonReader jsonReading = new JsonReader( new StringReader(json));
        jsonReading.setStrictness(Strictness.LENIENT);
        jsonReading.beginObject();

        jsonReading.nextName();
        this.databaseid = jsonReading.nextString();
        jsonReading.nextName();
        this.dbusername = jsonReading.nextString();
        jsonReading.nextName();
        this.dbpassword = jsonReading.nextString();
        jsonReading.nextName();
        this.aikey = jsonReading.nextString();
    }

    public String getDatabaseid() {
        return databaseid;
    }

    public String getDbusername() {
        return dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public String getAikey() {
        return aikey;
    }

    private String fileReader(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        String output = scanner.next();
        while (scanner.hasNextLine()) {
            output += scanner.nextLine();
        }
        scanner.close();
        return output;
    }
}
