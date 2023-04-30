package org.projectsw.Model;

import java.io.Serial;
import java.io.Serializable;

public class GameView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Board gameBoard;
    private final Shelf currentPlayerShelf;
    private final String currentPlayerName;
    private final Chat chat;

    public GameView(Game model){
        this.gameBoard =  model.getBoard();
        this.currentPlayerShelf = model.getCurrentPlayer().getShelf();
        this.currentPlayerName = model.getCurrentPlayer().getNickname();
        this.chat = model.getChat();
    }

    public Board getGameBoard(){return this.gameBoard;}

    public Shelf getCurrentPlayerShelf(){return this.currentPlayerShelf;}

    public String getCurrentPlayerName(){return this.currentPlayerName;}
    public Chat getChat(){return this.chat;}
}
