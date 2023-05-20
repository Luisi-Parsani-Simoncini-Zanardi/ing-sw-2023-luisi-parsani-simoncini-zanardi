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
    private final Tile[][] gameBoard;
    private final Tile[][] playerShelf;
    private final String playerName;
    private final Message message;
    private final ArrayList<Message> chat;
    private final Integer clientID;
    private final ArrayList<Point> selectablePoints;
    private final ArrayList<Point> temporaryPoints;
    private final ArrayList<Tile> temporaryTiles;
    private final Tile[][] playerPersonalGoal;
    private final HashMap<String, Integer> results;
    private final HashMap<String, String> nameColors;
    private final HashMap<String, Tile[][]> allShelves;
    private final ArrayList<String> commonGoalDesc;

    public SerializableGame(int clientID, Message message){
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = message;
    }
    public SerializableGame(int clientID){
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
    }

    public SerializableGame(String nickname){
        this.gameBoard = null;
        this.playerShelf = null;
        this.playerName = nickname;
        this.chat = null;
        this.clientID = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
    }

    public SerializableGame(int clientID, String nickname){
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = nickname;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
    }

    public SerializableGame(Game model, String scope){
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = scope;
        this.chat = model.getChat().getMessages();
        this.clientID = model.getClientID();
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
    }

    public SerializableGame(Game model){
        this.gameBoard =  model.getBoard().getBoard();
        this.playerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.playerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getMessages();
        this.clientID = model.getClientID();
        this.selectablePoints = model.getBoard().getSelectablePoints();
        this.temporaryPoints = model.getBoard().getTemporaryPoints();
        this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
        this.playerPersonalGoal = personalGoalToTile(model.getCurrentPlayer().getPersonalGoal().getPersonalGoal());
        this.results = new HashMap<>();
        for (Player p : model.getPlayers())
        {
            this.results.put(p.getNickname(), p.getPoints());
        }
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = new ArrayList<>();
        this.commonGoalDesc.add(model.getCommonGoals().get(0).getStrategy().getDescription());
        this.commonGoalDesc.add(model.getCommonGoals().get(1).getStrategy().getDescription());
        this.message = null;
    }

    public SerializableGame(int broadcastID, Game model){
        this.gameBoard =  model.getBoard().getBoard();
        this.playerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.playerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getMessages();
        this.clientID = broadcastID;
        this.selectablePoints = model.getBoard().getSelectablePoints();
        this.temporaryPoints = model.getBoard().getTemporaryPoints();
        this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
        this.playerPersonalGoal = personalGoalToTile(model.getCurrentPlayer().getPersonalGoal().getPersonalGoal());
        this.results = new HashMap<>();
        for (Player p : model.getPlayers())
        {
            this.results.put(p.getNickname(), p.getPoints());
        }
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
    }

    public SerializableGame(int clientID, String nickname, Shelf shelf){
        this.gameBoard =  null;
        this.playerShelf = shelf.getShelf();
        this.playerName = nickname;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = null;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
    }

    public SerializableGame(int clientID, ArrayList<Player> players){
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientID = clientID;
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
    }

    public SerializableGame(int clientID, HashMap<String, String> nameColors){
        this.gameBoard =  null;
        this.playerShelf = null;
        this.playerName = null;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.playerPersonalGoal = null;
        this.results = null;
        this.nameColors = nameColors;
        this.allShelves = null;
        this.commonGoalDesc = null;
        this.message = null;
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

    public Tile[][] getGameBoard(){return this.gameBoard;}
    public Tile[][] getPlayerShelf(){return this.playerShelf;}
    public String getPlayerName(){return this.playerName;}
    public ArrayList<Message> getChat(){return this.chat;}
    public int getClientID(){return this.clientID;}
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
