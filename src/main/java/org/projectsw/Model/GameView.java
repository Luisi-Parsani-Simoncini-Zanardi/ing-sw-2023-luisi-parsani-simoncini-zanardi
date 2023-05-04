package org.projectsw.Model;

import org.projectsw.Exceptions.ErrorName;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GameView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Tile[][] gameBoard;
    private final Tile[][] currentPlayerShelf;
    private final String currentPlayerName;
    private final ArrayList<Message> chat;
    private final ErrorName error;
    private final int clientID;

    public GameView(int clientID){
        this.gameBoard =  new Tile[1][1];
        gameBoard[0][0] = new Tile(TilesEnum.CATS, 1);
        this.currentPlayerShelf = new Tile[1][1];
        currentPlayerShelf[0][0] = new Tile(TilesEnum.CATS, 1);
        this.currentPlayerName = "a";
        this.chat = new ArrayList<>();
        this.error = ErrorName.NULL;
        this.clientID = clientID;
    }

    public GameView(ErrorName error, int clientID){
        this.gameBoard =  new Tile[1][1];
        gameBoard[0][0] = new Tile(TilesEnum.CATS, 1);
        this.currentPlayerShelf = new Tile[1][1];
        currentPlayerShelf[0][0] = new Tile(TilesEnum.CATS, 1);
        this.currentPlayerName = "a";
        this.chat = new ArrayList<>();
        this.error = error;
        this.clientID = clientID;
    }

    public GameView(Game model){
        this.gameBoard =  model.getBoard().getBoard();
        this.currentPlayerShelf = model.getCurrentPlayer().getShelf().getShelf();
        this.currentPlayerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat().getChat();
        this.error = ErrorName.NULL;
        this.clientID = 0;
    }

    public Tile[][] getGameBoard(){return this.gameBoard;}
    public Tile[][] getCurrentPlayerShelf(){return this.currentPlayerShelf;}
    public String getCurrentPlayerName(){return this.currentPlayerName;}
    public ArrayList<Message> getChat(){return this.chat;}
    public ErrorName getError(){return this.error;}
    public int getClientID(){return this.clientID;}

}
