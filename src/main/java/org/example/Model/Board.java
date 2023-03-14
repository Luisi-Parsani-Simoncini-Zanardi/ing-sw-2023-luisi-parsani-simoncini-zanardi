package org.example.Model;

import static org.example.Model.Tiles.EMPTY;

public class Board{
    //manca costruttore normale
    private Tiles[][] board;
    private boolean endGame;
    private Bag bag;

    public Board(){ //inizializzo la board a 4 giocatori, se sono meno la ridurrà il controller
        this.board = new Tiles[][]{
            {Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED},
            {Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED},
            {Tiles.UNUSED, Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED, Tiles.UNUSED},
            {Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY},
            {Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY},
            {Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED},
            {Tiles.UNUSED, Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED, Tiles.UNUSED},
            {Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED},
            {Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED, Tiles.EMPTY, Tiles.EMPTY, Tiles.UNUSED, Tiles.UNUSED, Tiles.UNUSED},

        };
    }

    public Board(Board board){
        this.board=board.board;
        this.endGame=board.endGame;
        this.bag=board.bag;
    }

    public Board getBoard(){
        return this;
    }

    public void updateBoard(Tiles tile,int column,int row){
        board[row][column]=tile;
    }
}
