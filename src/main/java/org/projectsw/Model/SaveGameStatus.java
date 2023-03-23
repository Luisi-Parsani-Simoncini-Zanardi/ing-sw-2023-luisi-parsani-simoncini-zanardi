package org.projectsw.Model;

import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class represents a status for saving a game, including the game itself and the file path
 * where the game should be saved. It provides methods to serialize game data to JSON format and save
 * it to a file.
 */
public class SaveGameStatus {
    private final Game game; //The game object to be saved.
    private final String filePath; //The file path where the game should be saved.

    /**
     * Constructs a new `SaveGameStatus` object with the specified `game` and `filePath`.
     * @param game the game object to be saved
     * @param filePath the file path where the game should be saved
     */
    public SaveGameStatus(Game game, String filePath) {
        this.game = game;
        this.filePath = filePath;
    }

    /**
     * Generates a JSON object containing all the information about a given player.
     * @param player the player to generate JSON data for
     * @return a JSON object containing all the information about the given player
     */
    public JsonObject jsonPlayer(Player player) {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("position", new JsonPrimitive(player.getPosition()));
        json.add("nickname", new JsonPrimitive(player.getNickname()));
        json.add("points", new JsonPrimitive(player.getPoints()));
        json.add("shelf", gson.toJsonTree(player.getShelf()));
        json.add("personalGoal", gson.toJsonTree(player.getPersonalGoal()));

        return json;
    }

    /**
     * Generates a JSON object containing all the information about a given board.
     * @param board the board to generate JSON data for
     * @return a JSON object containing all the information about the given board
     */
    public JsonObject jsonBoard(Board board) {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("board", gson.toJsonTree(board.getBoard()));
        json.add("endGame", new JsonPrimitive(board.isEndGame()));
        json.add("bag", gson.toJsonTree(board.getBag()));

        return json;
    }

    /**
     * Generates a JSON object containing all the information about a given common goal.
     * @param commonGoal the common goal to generate JSON data for
     * @return a JSON object containing all the information about the given common goal
     */
    public JsonObject jsonCommonGoal(CommonGoal commonGoal) {
        JsonObject json = new JsonObject();
        json.add("goalCode", new JsonPrimitive(commonGoal.getGoalCode()));
        json.add("redeemedNumber", new JsonPrimitive(commonGoal.getRedeemedNumber()));

        return json;
    }

    /**
     * Generates a JSON object containing all the information about all players in the game.
     * @return a JSON object containing all the information about all players in the game
     */
    public JsonObject jsonPlayerArray() {
        Gson gsonTmp = new Gson();
        JsonObject jsonTmp = new JsonObject();
        for(Player x: game.getPlayers())
            jsonTmp.add(x.getNickname(), gsonTmp.toJsonTree(jsonPlayer(x)));

        return jsonTmp;
    }

    /**
     * Generates a JSON object containing all the information about all common goals in the game.
     * @return a JSON object containing all the information about all common goals in the game
     */
    public JsonObject jsonCommonGoalArray() {
        Gson gsonTmp = new Gson();
        JsonObject jsonTmp = new JsonObject();
        for(CommonGoal x: game.getCommonGoals())
            jsonTmp.add(String.valueOf(x.getGoalCode()), gsonTmp.toJsonTree(jsonCommonGoal(x)));

        return jsonTmp;
    }

    /**
     * Serializes the game data to JSON format and saves it to a file.
     */
    public void saveGame() {
        // creation of the object Gson with indented format
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //JSON serialization of the object
        JsonObject json = new JsonObject();

        json.add("firstPlayer", new JsonPrimitive(game.getFirstPlayer().getNickname()));
        json.add("currentPlayer", new JsonPrimitive(game.getCurrentPlayer().getNickname()));
        json.add("players", gson.toJsonTree(jsonPlayerArray()));
        json.add("board", gson.toJsonTree(jsonBoard(game.getBoard())));
        json.add("commonGoals", gson.toJsonTree(jsonCommonGoalArray()));

        //write the json opn a file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(json, fileWriter);
        } catch (IOException e) {
            System.out.println("Error opening the json file");
        }
    }
}

/*public class saveGameStatusV2 {
    private Gson gson;
    private String saveFilePath;

    public saveGameStatusV2(String saveFilePath) {
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

}*/