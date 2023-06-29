package org.projectsw.Model;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class to serialize the game model
 */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and a game model.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param model The game model to be serialized.
     */
public SerializableGame(String alphanumericID, Game model) {
    this.alphanumericID = alphanumericID;
    this.gameBoard = model.getBoard().getBoard();
    this.playerShelf = model.getCurrentPlayer().getShelf().getShelf();
    this.playerName = model.getCurrentPlayer().getNickname();
    this.chat = model.getChat().getMessages();
    this.clientNickname = null;
    this.selectablePoints = model.getBoard().getSelectablePoints();
    this.temporaryPoints = model.getBoard().getTemporaryPoints();
    this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
    this.playerPersonalGoal = null;
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID, a game model and clientNickname.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param model The game model to be serialized.
     * @param clientNickname The clientNickname to be serialized.
     */
    public SerializableGame(String alphanumericID, Game model, String clientNickname) {
        this.alphanumericID = alphanumericID;
        this.gameBoard = model.getBoard().getBoard();
        this.playerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.playerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getMessages();
        this.clientNickname = null;
        this.selectablePoints = model.getBoard().getSelectablePoints();
        this.temporaryPoints = model.getBoard().getTemporaryPoints();
        this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
        this.playerPersonalGoal = personalGoalToTile(model.getPlayers().get(model.getPositionByNick(clientNickname)).getPersonalGoal().getPersonalGoal());
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID, a game model and clientNickname.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param model The game model to be serialized.
     * @param forced The integer to be serialized.
     */
    public SerializableGame(String alphanumericID, Game model, int forced) {
        this.alphanumericID = alphanumericID;
        this.gameBoard = model.getBoard().getBoard();
        this.playerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.playerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getMessages();
        this.clientNickname = null;
        this.selectablePoints = model.getBoard().getSelectablePoints();
        this.temporaryPoints = model.getBoard().getTemporaryPoints();
        this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
        this.playerPersonalGoal = null;
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
        this.integer = forced;
        this.bool = null;
    }

    /**
     * Constructs a SerializableGame object with an alphanumeric ID.
     * @param alphanumericID The alphanumeric ID of the game.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and string.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param string The string to be serialized.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and message.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param message The message to be serialized.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID, a nickname and shelf.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param nickname The nickname to be serialized.
     * @param shelf The shelf to be serialized.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and players.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param players The players to be serialized.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and num.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param num The num to be serialized.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and bool.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param bool The bool to be serialized.
     */
    public SerializableGame(String alphanumericID, boolean bool){
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
        this.integer = null;
        this.bool = bool;
    }

    /**
     * Constructs a SerializableGame object with an alphanumeric ID and nameColors.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param nameColors The nameColors to be serialized.
     */
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

    /**
     * Constructs a SerializableGame object with an alphanumeric ID, a nickname and chat.
     * @param alphanumericID The alphanumeric ID of the game.
     * @param nickname The nickname to be serialized.
     * @param chat The chat to be serialized.
     */
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

    /**
     * Converts a 2D array of TilesEnum representing a personal goal to a 2D array of Tile objects.
     * @param personalGoal The 2D array of TilesEnum representing the personal goal.
     * @return The converted 2D array of Tile objects.
     */
    private Tile[][] personalGoalToTile(TilesEnum[][] personalGoal) {
        Tile[][] goal = new Tile[Config.shelfHeight][Config.shelfLength];
        for (int i = 0; i < Config.shelfHeight; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                goal[i][j] = new Tile(personalGoal[i][j], 0);
            }
        }
        return goal;
    }

    /**
     * Retrieves the alphanumeric ID of the SerializableGame.
     * @return The alphanumeric ID.
     */
    public String getAlphanumericID(){return this.alphanumericID;}

    /**
     * Retrieves the bool of the SerializableGame.
     * @return The bool.
     */
    public Boolean getBool(){return this.bool;}

    /**
     * Retrieves the integer of the SerializableGame.
     * @return The integer.
     */
    public Integer getInteger(){return this.integer;}

    /**
     * Retrieves the gameBoard of the SerializableGame.
     * @return The gameBoard.
     */
    public Tile[][] getGameBoard(){return this.gameBoard;}

    /**
     * Retrieves the playerShelf of the SerializableGame.
     * @return The playerShelf.
     */
    public Tile[][] getPlayerShelf(){return this.playerShelf;}

    /**
     * Retrieves the playerName of the SerializableGame.
     * @return The playerName.
     */
    public String getPlayerName(){return this.playerName;}

    /**
     * Retrieves the chat of the SerializableGame.
     * @return The chat.
     */
    public ArrayList<Message> getChat(){return this.chat;}

    /**
     * Retrieves the clientNickname of the SerializableGame.
     * @return The clientNickname.
     */
    public String getClientNickname(){return this.clientNickname;}

    /**
     * Retrieves the selectablePoints of the SerializableGame.
     * @return The selectablePoints.
     */
    public ArrayList<Point> getSelectablePoints() {return this.selectablePoints; }

    /**
     * Retrieves the temporaryPoints of the SerializableGame.
     * @return The temporaryPoints.
     */
    public ArrayList<Point> getTemporaryPoints() {return this.temporaryPoints; }

    /**
     * Retrieves the temporaryTiles of the SerializableGame.
     * @return The temporaryTiles.
     */
    public ArrayList<Tile> getTemporaryTiles() {return this.temporaryTiles; }

    /**
     * Retrieves the playerPersonalGoal of the SerializableGame.
     * @return The playerPersonalGoal.
     */
    public Tile[][] getPlayerPersonalGoal() {return this.playerPersonalGoal; }

    /**
     * Retrieves the results of the SerializableGame.
     * @return The results.
     */
    public HashMap<String, Integer> getResults() {return this.results;}

    /**
     * Retrieves the nameColors of the SerializableGame.
     * @return The nameColors.
     */
    public HashMap<String, String> getNameColors() {return this.nameColors; }

    /**
     * Retrieves the allShelves of the SerializableGame.
     * @return The allShelves.
     */
    public HashMap<String, Tile[][]> getAllShelves() {return this.allShelves; }

    /**
     * Retrieves the commonGoalDesc of the SerializableGame.
     * @return The commonGoalDesc.
     */
    public ArrayList<String> getCommonGoalDesc() {return this.commonGoalDesc; }

    /**
     * Retrieves the message of the SerializableGame.
     * @return The message.
     */
    public Message getMessage() {return this.message; }
}
