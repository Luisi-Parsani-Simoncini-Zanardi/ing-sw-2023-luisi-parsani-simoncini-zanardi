package org.projectsw.Model;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SerializableGame implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String alphanumericID;
    private final Tile[][] gameBoard;
    private final Tile[][] playerShelf;
    private final String playerName;
    private final Message message;
    private final ArrayList<Message> chat;
    private final String clientNickname;
    private final Integer integer;
    private final Boolean bool;
    private final ArrayList<Point> selectablePoints;
    private final ArrayList<Point> temporaryPoints;
    private final ArrayList<Tile> temporaryTiles;
    private final Tile[][] playerPersonalGoal;
    private final HashMap<String, Integer> results;
    private final HashMap<String, String> nameColors;
    private final HashMap<String, Tile[][]> allShelves;
    private final ArrayList<String> commonGoalDesc;
/*
    public SerializableGame(String alphanumericID, int number){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = number;
        this.bool = null;
    }

    public SerializableGame(String alphanumericID, String clientNickname, Message message){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = clientNickname;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = message;
        this.integer = null;
        this.bool = null;
    }

    public SerializableGame(String alphanumericID, String clientNickname){
        this.alphanumericID = alphanumericID;
        this.gameBoard = null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = clientNickname;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }

    public SerializableGame(String alphanumericID, String clientNickname, String nickname){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = nickname;
        this.chat = null;
        this.clientNickname = clientNickname;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }

    public SerializableGame(String alphanumericID, Game model, String scope){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = scope;
        this.chat = model.getChat().getMessages();
        this.clientNickname = model.getCurrentPlayerNickname();
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }










    public SerializableGame(String alphanumericID, String clientNickname, boolean bool){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = clientNickname;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = bool;
    }
*/
public SerializableGame(String alphanumericID, Game model) {
    this.alphanumericID = alphanumericID;
    this.gameBoard = model.getBoard().getBoard();
    this.playerShelf = model.getCurrentPlayer().getShelf().getShelf();
    this.playerName = model.getCurrentPlayer().getNickname();
    this.chat = model.getChat().getMessages();
    this.clientNickname = model.getCurrentPlayerNickname();
    this.selectablePoints = model.getBoard().getSelectablePoints();
    this.temporaryPoints = model.getBoard().getTemporaryPoints();
    this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
    this.playerPersonalGoal = personalGoalToTile(model.getPlayers().get(model.getPositionByNick(model.getCurrentPlayerNickname())).getPersonalGoal().getPersonalGoal());
    this.results = new HashMap<>();
    for (Player p : model.getPlayers()) {
        this.results.put(p.getNickname(), p.getPoints());
    }
    this.nameColors = null;
    this.allShelves = null;
    this.commonGoalDesc = new ArrayList<>();
    this.commonGoalDesc.add(model.getCommonGoals().get(0).getStrategy().getDescription());
    this.commonGoalDesc.add(model.getCommonGoals().get(1).getStrategy().getDescription());
    this.message = null;
    this.integer = null;
    this.bool = null;
}
    public SerializableGame(String alphanumericID) {
        this.alphanumericID = alphanumericID;
        this.gameBoard = null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }
    public SerializableGame(String alphanumericID, String string){
        this.alphanumericID = alphanumericID;
        this.gameBoard = null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = string;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }
    public SerializableGame(String alphanumericID, Message message){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = message;
        this.integer = null;
        this.bool = null;
    }
    public SerializableGame(String alphanumericID, String nickname, Shelf shelf){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = shelf.getShelf();
        this.playerName = nickname;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }

    public SerializableGame(String alphanumericID, ArrayList<Player> players){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = new HashMap<>();
        for (Player p : players)
        {
            this.allShelves.put(p.getNickname(), p.getShelf().getShelf());
        }
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }
    public SerializableGame(String alphanumericID, int num){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = num;
        this.bool = null;
    }
    public SerializableGame(String alphanumericID, HashMap<String, String> nameColors){
        this.alphanumericID = alphanumericID;
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientNickname = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = nameColors;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }

    public SerializableGame(String alphanumericID,Chat chat, String nickname) {
        this.alphanumericID = alphanumericID;
        this.gameBoard = null;
        this.playerShelf =null;
        this.playerName = null;
        this.chat = chat.getMessages();
        this.clientNickname = nickname;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
        this.integer = null;
        this.bool = null;
    }

    private Tile[][] personalGoalToTile(TilesEnum[][] personalGoal) {
        Tile[][] goal = new Tile[Config.shelfHeight][Config.shelfLength];
        for (int i = 0; i < Config.shelfHeight; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                goal[i][j] = new Tile(personalGoal[i][j], 0);
            }
        }
        return goal;
    }

    public String getAlphanumericID(){return this.alphanumericID;}
    public Boolean getBool(){return this.bool;}
    public Integer getInteger(){return this.integer;}
    public Tile[][] getGameBoard(){return this.gameBoard;}
    public Tile[][] getPlayerShelf(){return this.playerShelf;}
    public String getPlayerName(){return this.playerName;}
    public ArrayList<Message> getChat(){return this.chat;}
    public String getClientNickname(){return this.clientNickname;}
    public ArrayList<Point> getSelectablePoints() {return this.selectablePoints; }
    public ArrayList<Point> getTemporaryPoints() {return this.temporaryPoints; }
    public ArrayList<Tile> getTemporaryTiles() {return this.temporaryTiles; }
    public Tile[][] getPlayerPersonalGoal() {return this.playerPersonalGoal; }
    public HashMap<String, Integer> getResults() {return this.results;}
    public HashMap<String, String> getNameColors() {return this.nameColors; }
    public HashMap<String, Tile[][]> getAllShelves() {return this.allShelves; }
    public ArrayList<String> getCommonGoalDesc() {return this.commonGoalDesc; }
    public Message getMessage() {return this.message; }
}
