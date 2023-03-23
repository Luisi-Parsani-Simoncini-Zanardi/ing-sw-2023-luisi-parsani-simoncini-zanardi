package org.projectsw.Model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class represents the game board as a Tiles matrix, it also has a flag for endGame and a bag reference.
 */
public class Board{
    private Tile[][] board;
    private boolean endGame;
    private Bag bag;

    /**
     * Constructs a Board object from a json file
     * initializes the board for the numer of players requested
     * @param playersNumber the number of players playing the game
     */
    public Board(int playersNumber) {
        try{
            Gson gson = new Gson();
            String[][][] tmpMatrix = gson.fromJson(new FileReader("src/main/resources/StartingBoards.json"), String[][][].class);
            board = new Tile[9][9];
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(tmpMatrix[playersNumber][i][j].equals("UNUSED")){board[i][j] = new Tile(TilesEnum.UNUSED,0);}
                    if(tmpMatrix[playersNumber][i][j].equals("EMPTY")){board[i][j] = new Tile(TilesEnum.EMPTY, 0);}
                }
            }
            endGame = false;
            bag = new Bag();
        }catch (IOException e){
            System.out.println("Error opening the json"+e.getMessage());
        }
    }

    /**
     * Constructs a Board object with the same state as another Board object.
     * @param board the Board object to copy from
     */
    public Board(Board board){
        this.board=board.board;
        this.endGame=board.endGame;
        this.bag=board.bag;
    }

    /**
     * Returns the current state of the board.
     * @return the Tile array representing the board
     */
    public Tile[][] getBoard(){
        return board;
    }

    /**
     * Returns the Tile at the given position on the board.
     * @param row the row index of the Tile
     * @param column the column index of the Tile
     * @return the Tile at the given position
     */
    public Tile getTile(int row, int column){
        return board[row][column];
    }

    /**
     * Returns the bag of tiles.
     * @return the Bag object representing the bag of tiles
     */
    public Bag getBag(){
        return bag;
    }

    /**
     * Returns the flag endGame.
     * @return true if the game has ended, false otherwise
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**
     * Sets the end game flag to the specified value.
     * @param endGame the value to set the end game flag to
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Updates the board by placing the given Tile at the specified position.
     * @param tile the Tile to place on the board
     * @param row the row index of the position to place the Tile at
     * @param column the column index of the position to place the Tile at
     * @throws IndexOutOfBoundsException if the given row or column index is out of bounds
     */
    public void updateBoard(Tile tile, int row, int column){
        if(row>8 || column>8)
            throw new IndexOutOfBoundsException();
        else
            board[row][column]=tile;
    }

    /**
     * Returns the Tile at the given position on the board and replaces it with an empty Tile.
     * @param row the row index of the Tile
     * @param column the column index of the Tile
     * @return the Tile at the given position
     */
    public Tile getTileFromBoard(int row, int column){
        Tile tmp = board[row][column];
        board[row][column]= new Tile(TilesEnum.EMPTY, 0);
        return tmp;
    }
}
