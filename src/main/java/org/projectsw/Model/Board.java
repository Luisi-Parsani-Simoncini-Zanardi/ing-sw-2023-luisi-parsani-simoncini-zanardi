package org.projectsw.Model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Board{
    private Tile[][] board;
    private boolean endGame;
    private Bag bag;

    public Board() throws IOException{ //initializes the 4-player board, the controller will eventually reduce it

        Gson gson = new Gson();
        String[][] tmpMatrix = gson.fromJson(new FileReader("src/main/resources/StartingBoardFourPlayers.json"), String[][].class);

        board = new Tile[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(tmpMatrix[i][j].equals("UNUSED")){board[i][j] = new Tile(TilesEnum.UNUSED,0);}
                if(tmpMatrix[i][j].equals("EMPTY")){board[i][j] = new Tile(TilesEnum.EMPTY, 0);}
            }
        }

        endGame = false;
        bag = new Bag();

        endGame = false;
    }

    public Board(Board board){
        this.board=board.board;
        this.endGame=board.endGame;
        this.bag=board.bag;
    }

    public Tile[][] getBoard(){
        return board;
    }

    public Bag getBag(){
        return bag;
    }

    public void updateBoard(Tile tile, int row, int column){
        if(row>8 || column>8)
            throw new IndexOutOfBoundsException();
        else
            board[row][column]=tile;
    }

    public Tile getTileFromBoard(int row, int column){
        Tile tmp = board[row][column];
        board[row][column]= new Tile(TilesEnum.EMPTY, 0);
        return tmp;
    }

    public Tile getTile(int row, int column){
        return board[row][column];
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
}
