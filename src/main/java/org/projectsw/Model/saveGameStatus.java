package org.projectsw.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

    // la funzione createSaveFile va chiamata appena dopo la creazione del game nel controller mentre savegameState va
    // implementata nella logica dell'endTurn
public class saveGameStatus {
    private Gson gson;
    private String saveFilePath;

    public saveGameStatus(String saveFilePath) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.saveFilePath = saveFilePath;
    }

    public void saveGameState(Game game) throws IOException {
        // convert game status to JSON, it should automatically take the status of bag from board even if not directly connected etc...
        String gameStateJson = gson.toJson(game);

        // open JSON file in write mode
        FileWriter fileWriter = new FileWriter(saveFilePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // write the JSON object on the file
        bufferedWriter.write(gameStateJson);

        // close file
        bufferedWriter.close();
        fileWriter.close();
    }

}
