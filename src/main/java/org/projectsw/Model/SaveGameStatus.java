package org.projectsw.Model;


import java.io.*;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.projectsw.Model.CommonGoal.CommonGoal;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * This class represents a status for saving a game, including the game itself and the file path
 * where the game should be saved. It provides methods to serialize game data to JSON format and save
 * it to a file.
 */
public class SaveGameStatus {
    private final Game game; //The game object to be saved.
    private final String filePath; //The file path where the game should be saved.

    /**
     * Constructs a new SaveGameStatus object with the specified game and filePath.
     * @param game the game object to be saved
     * @param filePath the file path where the game should be saved
     */
    public SaveGameStatus(Game game, String filePath) {
        this.game = game;
        this.filePath = filePath;
    }

    /**
     * Serializes the game class into a json string.
     * @return json string
     */
    public String gameToJson() {
        Gson gson = new Gson();
        return gson.toJson(game);
    }

    /**
     * Saves the serialized game state into a file.
     */
    public void saveGame() {

        String json = gameToJson();

        try {
            File file = new File(filePath);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
            fw.close();
            System.out.println("The string was written successfully on file.");
        } catch (IOException e) {
            System.out.println("Error while writing on file: " + e.getMessage());
        }
    }

    /**
     * retrieve game status data of the latest save from file and return the game object
     * updated to the last turn
     * @return game object updated to the last turn
     */
    public Game retrieveGame() {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            Gson gson = new Gson();
            Game savedGame = gson.fromJson(br, Game.class);
            return savedGame;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}