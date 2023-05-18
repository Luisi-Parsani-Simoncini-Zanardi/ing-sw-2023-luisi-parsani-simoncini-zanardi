package org.projectsw.Model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
            for(Player player : data.getPlayers()){
                player.setIsActive(false);
            }
            data.getCurrentPlayer().setIsActive(false);
            data.getFirstPlayer().setIsActive(false);


            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Game retrieveGame() {
        return retrieveGame(filePath);
    }

    public void deleteSaveFile(){
        File fileDaEliminare = new File(filePath);
        if (fileDaEliminare.exists()) {
            fileDaEliminare.delete();
        } else {
            System.out.println("Il file non esiste.");
        }
    }

    public boolean checkExistingSaveFile(String path) {
        File file =new File(path);
        if(file.exists())
            return true;
        return false;
    }

    public boolean checkExistingSaveFile() {
        return checkExistingSaveFile(filePath);
    }

}