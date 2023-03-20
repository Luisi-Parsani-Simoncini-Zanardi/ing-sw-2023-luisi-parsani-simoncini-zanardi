package org.projectsw.Model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Board{
    private Tiles[][] board;
    private boolean endGame;
    private Bag bag;

    public Board() throws IOException{ //initializes the 4-player board, the controller will eventually reduce it

        Gson gson = new Gson();
        String[][] tmpMatrix = gson.fromJson(new FileReader("./src/main/resources/PersonalGoals.json"), String[][].class);

        board = new Tiles[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(tmpMatrix[i][j].equals("UNUSED")){board[i][j] = Tiles.UNUSED;}
                if(tmpMatrix[i][j].equals("EMPTY")){board[i][j] = Tiles.EMPTY;}
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

    public Tiles[][] getBoard(){
        return board;
    }

    public void updateBoard(Tiles tile,int row,int column){
        if(row>8 || column>8)
            throw new IndexOutOfBoundsException();
        else
            board[row][column]=tile;
    }

    public Tiles getTileFromBoard(int row,int column){
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
