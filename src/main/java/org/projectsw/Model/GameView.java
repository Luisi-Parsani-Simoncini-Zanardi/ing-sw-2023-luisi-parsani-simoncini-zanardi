package org.projectsw.Model;

import java.io.Serializable;

public class GameView implements Serializable {
    static final long serialVersionUID = 1L;

    private final Board gameBoard;

    private final Shelf currentPlayerShelf;

    public GameView(Game model){
        this.gameBoard =  model.getBoard();
        this.currentPlayerShelf = model.getCurrentPlayer().getShelf();
    }

    public Board getGameBoard(){return this.gameBoard;}

    public Shelf getCurrentPlayerShelf(){return this.currentPlayerShelf;}
}
