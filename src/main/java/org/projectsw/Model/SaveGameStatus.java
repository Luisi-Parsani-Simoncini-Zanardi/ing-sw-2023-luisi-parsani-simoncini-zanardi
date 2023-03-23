package org.projectsw.Model;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveGameStatus {
    private Game game;
    private String filePath;

    public SaveGameStatus(Game game, String filePath) {
        this.game = game;
        this.filePath = filePath;
    }

    public JsonObject jsonPlayer(Player player) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        JsonObject json = new JsonObject();
        json.add("position", new JsonPrimitive(player.getPosition()));
        json.add("nickname", new JsonPrimitive(player.getNickname()));
        json.add("points", new JsonPrimitive(player.getPoints()));
        json.add("shelf", gson.toJsonTree(player.getShelf()));
        json.add("personalGoal", gson.toJsonTree(player.getPersonalGoal()));

        return json;
    };

    public String prova(Game game) {
        Gson gson = new Gson();
        String json = gson.toJson(game);
        return json;
    }
    public JsonObject jsonBoard(Board board) {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("board", gson.toJsonTree(board.getBoard()));
        json.add("endGame", new JsonPrimitive(board.isEndGame()));
        json.add("bag", gson.toJsonTree(board.getBag()));

        return json;
    }

    public JsonObject jsonCommonGoal(CommonGoal commonGoal) {
        JsonObject json = new JsonObject();
        json.add("goalCode", new JsonPrimitive(commonGoal.getGoalCode()));
        json.add("redeemedNumber", new JsonPrimitive(commonGoal.getRedeemedNumber()));

        return json;
    }

    public JsonObject jsonPlayerArray() {
        Gson gsonTmp = new Gson();
        JsonObject jsonTmp = new JsonObject();
        for(Player x: game.getPlayers())
            jsonTmp.add(x.getNickname(), gsonTmp.toJsonTree(jsonPlayer(x)));

        return jsonTmp;
    }

    public JsonObject jsonCommonGoalArray() {
        Gson gsonTmp = new Gson();
        JsonObject jsonTmp = new JsonObject();
        for(CommonGoal x: game.getCommonGoals())
            jsonTmp.add(String.valueOf(x.getGoalCode()), gsonTmp.toJsonTree(jsonCommonGoal(x)));

        return jsonTmp;
    }

    public void saveGame() {
        // creazione dell'oggetto Gson con la formattazione indentata
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // serializzazione del modello in formato JSON
        JsonObject json = new JsonObject();

        json.add("firstPlayer", gson.toJsonTree(jsonPlayer(game.getFirstPlayer())));
        json.add("currentPlayer", gson.toJsonTree(jsonPlayer(game.getCurrentPlayer())));
        json.add("players", gson.toJsonTree(jsonPlayerArray()));
        json.add("board", gson.toJsonTree(jsonBoard(game.getBoard())));
        json.add("commonGoals", gson.toJsonTree(jsonCommonGoalArray()));



        // scrittura del JSON su un file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(json, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
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