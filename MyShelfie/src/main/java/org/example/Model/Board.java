package org.example.Model;

public class Board{
    //manca costruttore normale
    private Tiles[][] board;
    private boolean endGame;
    private Bag bag;

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
