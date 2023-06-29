package org.projectsw.Model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.projectsw.Model.Enums.GameState;

import java.io.*;
import java.util.stream.Collectors;

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
     * Saves the current game state to a file in JSON format.
     * The game state is converted to a JSON string using the {@link #gameToJson()} method,
     * and then written to the specified file path.
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
     * Retrieves a saved game state from the specified file path and returns it as a `Game` object.
     * The game state is read from the file in JSON format and then converted back into a `Game` object using Gson.
     * @param path the file path from which to retrieve the game state
     * @return the retrieved `Game` object, or null if an error occurs during the retrieval process
     */
    public Game retrieveGame(String path) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String json = br.lines().collect(Collectors.joining());

            Gson gson = new Gson();
            String substring = json.substring(json.indexOf("commonGoals") - 2);
            String newJson = json.replace(substring, "}");
            char[] charArray = substring.toCharArray();
            charArray[0] = '{';
            String commonGoalString = String.valueOf(charArray);

            Game data = gson.fromJson(newJson, Game.class);
            JsonElement commonGoalJson = gson.fromJson(commonGoalString, JsonElement.class);

            int strategyCode1 = commonGoalJson.getAsJsonObject().get("commonGoals")
                    .getAsJsonArray().get(0).getAsJsonObject().get("strategy").getAsJsonObject()
                    .get("strategyCode").getAsInt();
            int strategyCode2 = commonGoalJson.getAsJsonObject().get("commonGoals")
                    .getAsJsonArray().get(1).getAsJsonObject().get("strategy").getAsJsonObject()
                    .get("strategyCode").getAsInt();
            int redeemedNumber1 = commonGoalJson.getAsJsonObject().get("commonGoals")
                    .getAsJsonArray().get(0).getAsJsonObject().get("redeemedNumber").getAsInt();
            int redeemedNumber2 = commonGoalJson.getAsJsonObject().get("commonGoals")
                    .getAsJsonArray().get(1).getAsJsonObject().get("redeemedNumber").getAsInt();

            data.setCommonGoals(data.commonGoalByIndex(new int[]{strategyCode1, strategyCode2}));
            data.getCommonGoals().get(0).setRedeemedNumber(redeemedNumber1);
            data.getCommonGoals().get(1).setRedeemedNumber(redeemedNumber2);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a saved game state from the default file path and returns it as a `Game` object.
     * The game state is read from the file in JSON format and then converted back into a `Game` object using Gson.
     * @return the retrieved `Game` object, or null if an error occurs during the retrieval process
     */
    public Game retrieveGame() {
        return retrieveGame(filePath);
    }

    /**
     * Deletes the save file at the specified path.
     * @param path the path of the save file to be deleted
     */
    public void deleteSaveFile(String path){
        File fileDaEliminare = new File(path);
        fileDaEliminare.delete();
    }

    /**
     * Deletes the save file at the filePath.
     */
    public void deleteSaveFile(){
        File fileDaEliminare = new File(filePath);
        if (fileDaEliminare.exists()) {
            fileDaEliminare.delete();
        } else {
            System.out.println("Il file non esiste.");
        }
    }

    /**
     * Checks if a save file exists at the specified path.
     * @param path the path of the save file to be checked
     * @return true if the save file exists, false otherwise
     */
    public boolean checkExistingSaveFile(String path) {
        File file =new File(path);
        if(file.exists())
            return true;
        return false;
    }

    /**
     * Checks if a save file exists at the filePath.
     * @return true if the save file exists, false otherwise
     */
    public boolean checkExistingSaveFile() {
        return checkExistingSaveFile(filePath);
    }

}