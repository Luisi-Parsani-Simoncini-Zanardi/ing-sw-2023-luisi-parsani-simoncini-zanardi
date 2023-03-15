package org.example.Model;

public class Board{
    private Tiles[][] board;
    private boolean endGame;
    private Bag bag;

    public Board(){ //initializes the 4-player board, the controller will eventually reduce it
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

    public Tiles[][] getBoard(){
        return board;
    }

    public void updateBoard(Tiles tile,int column,int row){
        board[row][column]=tile;
    }

    public Tiles getTileFromBoard(Tiles tile,int column,int row){
        Tiles tmp = board[row][column];
        board[row][column]=Tiles.EMPTY;
        return tmp;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
}
