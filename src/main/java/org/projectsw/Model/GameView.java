package org.projectsw.Model;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Tile[][] gameBoard;
    private final Tile[][] currentPlayerShelf;
    private final String currentPlayerName;
    private final ArrayList<Message> chat;
    private final Integer clientID;
    private final ArrayList<Point> selectablePoints;
    private final ArrayList<Point> temporaryPoints;
    private final ArrayList<Tile> temporaryTiles;
    private final Tile[][] currentPlayerPersonalGoal;
    private final Boolean correct;
    private final Integer numberOfPlayers;
    private final HashMap<String, Integer> results;

    public GameView(int clientID, boolean correct){
        this.gameBoard =  null;
        this.currentPlayerShelf = null;
        this.currentPlayerName = null;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.numberOfPlayers = null;
        this.currentPlayerPersonalGoal = null;
        this.correct=correct;
        this.results = null;
    }
    public GameView(int clientID){
        this.gameBoard =  null;
        this.currentPlayerShelf = null;
        this.currentPlayerName = null;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.numberOfPlayers = null;
        this.currentPlayerPersonalGoal = null;
        this.correct=null;
        this.results = null;
    }

    public GameView(String nickname){
        this.gameBoard = null;
        this.currentPlayerShelf = null;
        this.currentPlayerName = nickname;
        this.chat = null;
        this.clientID = null;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.numberOfPlayers = null;
        this.currentPlayerPersonalGoal = null;
        this.correct=null;
        this.results = null;
    }

    public GameView(int clientID, String nickname){
        this.gameBoard =  null;
        this.currentPlayerShelf = null;
        this.currentPlayerName = nickname;
        this.chat = null;
        this.clientID = clientID;
        this.selectablePoints = null;
        this.temporaryPoints = null;
        this.temporaryTiles = null;
        this.numberOfPlayers = null;
        this.currentPlayerPersonalGoal = null;
        this.correct=null;
        this.results = null;
    }

    public GameView(Game model){
        this.gameBoard =  model.getBoard().getBoard();
        this.currentPlayerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.currentPlayerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getMessages();
        this.clientID = model.getClientID();
        this.selectablePoints = model.getBoard().getSelectablePoints();
        this.temporaryPoints = model.getBoard().getTemporaryPoints();
        this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
        this.numberOfPlayers = model.getNumberOfPlayers();
        this.currentPlayerPersonalGoal= personalGoalToTile(model.getCurrentPlayer().getPersonalGoal().getPersonalGoal());
        this.correct=null;
        this.results = new HashMap<>();
        for (Player p : model.getPlayers())
        {
            this.results.put(p.getNickname(), p.getPoints());
        }
    }

    public GameView(int broadcastID,Game model){
        this.gameBoard =  model.getBoard().getBoard();
        this.currentPlayerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.currentPlayerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getMessages();
        this.clientID = broadcastID;
        this.selectablePoints = model.getBoard().getSelectablePoints();
        this.temporaryPoints = model.getBoard().getTemporaryPoints();
        this.temporaryTiles = model.getCurrentPlayer().getTemporaryTiles();
        this.numberOfPlayers = model.getNumberOfPlayers();
        this.currentPlayerPersonalGoal= personalGoalToTile(model.getCurrentPlayer().getPersonalGoal().getPersonalGoal());
        this.correct=null;
        this.results = new HashMap<>();
        for (Player p : model.getPlayers())
        {
            this.results.put(p.getNickname(), p.getPoints());
        }
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

    public Boolean getCorrect(){return this.correct;}
    public int getNumberOfPlayers(){return this.numberOfPlayers;}
    public Tile[][] getGameBoard(){return this.gameBoard;}
    public Tile[][] getCurrentPlayerShelf(){return this.currentPlayerShelf;}
    public String getCurrentPlayerName(){return this.currentPlayerName;}
    public ArrayList<Message> getChat(){return this.chat;}
    public int getClientID(){return this.clientID;}
    public ArrayList<Point> getSelectablePoints() {return this.selectablePoints; }
    public ArrayList<Point> getTemporaryPoints() {return this.temporaryPoints; }
    public ArrayList<Tile> getTemporaryTiles() {return this.temporaryTiles; }
    public Tile[][] getCurrentPlayerPersonalGoal() {return this.currentPlayerPersonalGoal; }
    public HashMap<String, Integer> getResults() {return this.results;}
}
