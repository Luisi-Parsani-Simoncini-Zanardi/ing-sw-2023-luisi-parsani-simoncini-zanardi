package org.projectsw.Model;

import com.google.gson.Gson;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//TODO x Davide: aggiungere nei commenti il self-updating e sistemare tutti i test (e aggiungerne di nuovi)

/**
 * The class represents the game board as a Tiles matrix, it also has a flag for endGame and a bag reference.
 */
public class Board{
    private Tile[][] board;
    private boolean endGame;
    private Bag bag;
    private ArrayList<Point> temporaryPoints;
    private ArrayList<Point> selectablePoints;

    /**
     * Constructs a Board full of unused tiles.
     */
    public Board(){
        board = new Tile[9][9];
        bag = new Bag();
        endGame = false;
        temporaryPoints = new ArrayList<>();
        selectablePoints = new ArrayList<>();
        updateSelectablePoints();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                board[i][j] = new Tile(TilesEnum.UNUSED,0);
            }
        }
    }

    /**
     * Constructs a Board object from a json file, initializing the board for the number of players requested.
     * @param playersNumber the number of players playing the game
     * @throws IllegalArgumentException if the number of players is lower than 2 or higher than 4
     */
    public Board(int playersNumber) throws IllegalArgumentException{
        if(playersNumber<2 || playersNumber>4) throw new IllegalArgumentException("Number of players not between 2 and 4");
        try{
            Gson gson = new Gson();
            String[][][] tmpMatrix = gson.fromJson(new FileReader("src/main/resources/StartingBoards.json"), String[][][].class);
            board = new Tile[9][9];
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(tmpMatrix[playersNumber-2][i][j].equals("UNUSED")){board[i][j] = new Tile(TilesEnum.UNUSED,0);}
                    if(tmpMatrix[playersNumber-2][i][j].equals("EMPTY")){board[i][j] = new Tile(TilesEnum.EMPTY, 0);}
                }
            }
            endGame = false;
            bag = new Bag();
            temporaryPoints = new ArrayList<>();
            selectablePoints = new ArrayList<>();
            updateSelectablePoints();
        }catch (IOException e){
            System.out.println("Error opening the json"+e.getMessage());
        }
    }

    /**
     * Constructs a Board object with the same state as another Board object.
     * @param board the Board object to copy from
     */
    public Board(Board board){
        this.board = board.getBoard();
        this.endGame = board.isEndGame();
        this.bag = board.getBag();
        this.temporaryPoints = board.getTemporaryPoints();
        this.updateSelectablePoints();
    }

    /**
     * Returns the current state of the board.
     * @return the Tile array representing the board
     */
    public Tile[][] getBoard(){
        return board;
    }

    /**
     * Returns the bag of tiles.
     * @return the Bag object representing the bag of tiles
     */
    public Bag getBag(){
        return bag;
    }

    /**
     * Returns the temporary Points of selected tiles vector.
     * @return the Points vector.
     */
    public ArrayList<Point> getTemporaryPoints() {
        return temporaryPoints;
    }

    /**
     * Returns the selectable Points of the board.
     * @return the selectable Points vector.
     */
    public ArrayList<Point> getSelectablePoints() {
        return selectablePoints;
    }

    /**
     * Returns the flag endGame.
     * @return true if the game has ended, false otherwise
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**
     * Sets the board as the passed matrix of tiles.
     * @param board the board to set.
     */
    public void setBoard(Tile[][] board){
        if(board.length != 9) throw new IllegalArgumentException();
        if(board[0].length != 9) throw new IllegalArgumentException();
        this.board = board;
    }

    /**
     * Sets the end game flag to the specified value.
     * @param endGame the value to set the end game flag to
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Sets the temporaryPoints arrayList as a given arrayList of Points.
     * @param temporaryPoints the arrayList of Points to set.
     */
    public void setTemporaryPoints(ArrayList<Point> temporaryPoints) {
        this.temporaryPoints = temporaryPoints;
        updateSelectablePoints();
    }

    /**
     * Returns the Tile at the given position on the board and replaces it with an empty Tile.
     * @param point the indexes of the tile to remove.
     * @return the Tile at the given position.
     * @throws IndexOutOfBoundsException if the given row or column's index is out of bounds.
     */
    public Tile getTileFromBoard(Point point) throws IndexOutOfBoundsException{
        if(point.getX()>8 || point.getY()>8){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        else {
            Tile tmp = board[(int) point.getX()][(int) point.getY()];
            board[(int) point.getX()][(int) point.getY()] = new Tile(TilesEnum.EMPTY, 0);
            updateSelectablePoints();
            return tmp;
        }
    }

    /**
     * Updates the board by placing the given Tile at the specified position.
     * @param tile the Tile to place on the board
     * @param row the row index of the position to place the Tile at
     * @param column the column index of the position to place the Tile at
     * @throws IndexOutOfBoundsException if the given row or column's index is out of bounds
     */
    public void updateBoard(Tile tile, int row, int column) throws IndexOutOfBoundsException{
        if(row>8 || column>8) throw new IndexOutOfBoundsException("Index out of bounds");
        else board[row][column]=tile;
        updateSelectablePoints();
    }

    /**
     * Adds a new Point object to the temporaryPoints arrayList
     * @param point the Point to add.
     */
    public void addTemporaryPoints(Point point){
        temporaryPoints.add(point);
        updateSelectablePoints();
    }

    /**
     * Remove the point from the temporaryPoints list checking if the remaining points inside the list are adjacent, if they are not
     * the method cleans all the list.
     * @param point the point to remove from the list.
     */
    public void removeTemporaryPoints(Point point){
        temporaryPoints.remove(point);
        if(temporaryPoints.size() == 3){
            if(!areAdjacentPoints(temporaryPoints.get(0),temporaryPoints.get(1))){
                cleanTemporaryPoints();
            }
        }
    }

    /**
     * Cleans the arrayList of temporaryPoints (remove all the elements).
     */
    public void cleanTemporaryPoints() {
        temporaryPoints.clear();
        updateSelectablePoints();
    }

    private void updateSelectablePoints() {
        ArrayList<Point> newSelectablePoints = new ArrayList<>();

        switch (temporaryPoints.size()) {
            case 0 -> newSelectablePoints = getFreeEdgesPoints();
            case 1 -> newSelectablePoints = getAdjacentSelectablePoints();
            case 2 -> newSelectablePoints = getSelectablePointsInStraightLine();

        }
        selectablePoints = newSelectablePoints;
    }

    /**
     * Returns the points adjacent to a given point
     * @param middle the point whose adjacent points are desired
     * @return an ArrayList of points, all adjacent to the given point
     */
    private ArrayList<Point> getAdjacentPoints(Point middle){
        ArrayList<Point> adjacentPoints = new ArrayList<>();
        int middleRow = (int) middle.getX();
        int middleColumn = (int) middle.getY();
        if(middleRow != 0) adjacentPoints.add(new Point(middleRow-1,middleColumn));
        if(middleColumn != 0) adjacentPoints.add(new Point(middleRow,middleColumn-1));
        if(middleColumn != 8) adjacentPoints.add(new Point(middleRow,middleColumn+1));
        if(middleRow != 8) adjacentPoints.add(new Point(middleRow+1,middleColumn));
        return adjacentPoints;
    }

    /**
     * Returns the points of all the game-tiles which have free edges.
     * @return an arrayList containing the points of all the game-tiles which have free edges
     */
    private ArrayList<Point> getFreeEdgesPoints() {
        ArrayList<Point> newSelectablePoints = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Tile currentTile1 = board[i][j];
                if(currentTile1.getTile().equals(TilesEnum.CATS ) || currentTile1.getTile().equals(TilesEnum.BOOKS)
                        || currentTile1.getTile().equals(TilesEnum.FRAMES) || currentTile1.getTile().equals(TilesEnum.GAMES)
                        || currentTile1.getTile().equals(TilesEnum.PLANTS) || currentTile1.getTile().equals(TilesEnum.TROPHIES)) {
                    ArrayList<Point> adjacentPoints = getAdjacentPoints(new Point(i,j));
                    boolean emptyEdgeFound = false;
                    for(Point point : adjacentPoints){
                        Tile currentTile2 = board[(int) point.getX()][(int) point.getY()];
                        if(currentTile2.getTile().equals(TilesEnum.EMPTY) || currentTile2.getTile().equals(TilesEnum.UNUSED)){
                            emptyEdgeFound = true;
                            break;
                        }
                    }
                    if(emptyEdgeFound) newSelectablePoints.add(new Point(i,j));
                }
            }
        }
        return newSelectablePoints;
    }

    /**
     * Returns all the selectable points next to the one present in temporaryPoints.
     * @return an arrayList containing all the selectable points next to the one present in temporaryPoints.
     */
    private ArrayList<Point> getAdjacentSelectablePoints(){
        ArrayList<Point> newSelectablePoints = new ArrayList<>();
        selectablePoints = getFreeEdgesPoints();

        Point selectedPoint = temporaryPoints.get(0);
        ArrayList<Point> adjacentToSelectedPoint = getAdjacentPoints(selectedPoint);
        for (Point point : adjacentToSelectedPoint) {
            if (selectablePoints.contains(point)) newSelectablePoints.add(point);
        }
        return newSelectablePoints;
    }

    /**
     * Returns all the selectable points in straight line with the two present in the temporaryPoints.
     * @return an arrayList containing all the selectable points in straight line with the two present in the temporaryPoints.
     */
    private ArrayList<Point> getSelectablePointsInStraightLine(){
        ArrayList<Point> newSelectablePoints = new ArrayList<>();
        Point selectedPoint0 = temporaryPoints.get(0);
        Point selectedPoint1 = temporaryPoints.get(1);
        selectablePoints = getFreeEdgesPoints();
        if (selectedPoint0.getX() == selectedPoint1.getX() + 1) {
            if(selectablePoints.contains(new Point((int) selectedPoint0.getX()+1,(int) selectedPoint0.getY())))
                newSelectablePoints.add(new Point((int) selectedPoint0.getX()+1,(int) selectedPoint0.getY()));
            if(selectablePoints.contains(new Point((int) selectedPoint1.getX()-1,(int) selectedPoint1.getY())))
                newSelectablePoints.add(new Point((int) selectedPoint1.getX()-1,(int) selectedPoint1.getY()));
        } else if (selectedPoint0.getX() == selectedPoint1.getX() - 1) {
            if(selectablePoints.contains(new Point((int) selectedPoint0.getX()-1,(int) selectedPoint0.getY())))
                newSelectablePoints.add(new Point((int) selectedPoint0.getX()-1,(int) selectedPoint0.getY()));
            if(selectablePoints.contains(new Point((int) selectedPoint1.getX()+1,(int) selectedPoint1.getY())))
                newSelectablePoints.add(new Point((int) selectedPoint1.getX()+1,(int) selectedPoint1.getY()));
        } else if (selectedPoint0.getY() == selectedPoint1.getY() + 1){
            if(selectablePoints.contains(new Point((int) selectedPoint0.getX(),(int) selectedPoint0.getY()+1)))
                newSelectablePoints.add(new Point((int) selectedPoint0.getX(),(int) selectedPoint0.getY()+1));
            if(selectablePoints.contains(new Point((int) selectedPoint1.getX(),(int) selectedPoint1.getY()-1)))
                newSelectablePoints.add(new Point((int) selectedPoint1.getX(),(int) selectedPoint1.getY()-1));
        } else if (selectedPoint0.getY() == selectedPoint1.getY() - 1) {
            if (selectablePoints.contains(new Point((int) selectedPoint0.getX(), (int) selectedPoint0.getY() - 1)))
                newSelectablePoints.add(new Point((int) selectedPoint0.getX(), (int) selectedPoint0.getY() - 1));
            if (selectablePoints.contains(new Point((int) selectedPoint1.getX(), (int) selectedPoint1.getY() + 1)))
                newSelectablePoints.add(new Point((int) selectedPoint1.getX(), (int) selectedPoint1.getY() + 1));
        }
        return  newSelectablePoints;
    }

    /**
     * Returns true if the padded points are adjacent.
     * @param p0 the first point to check.
     * @param p1 the second point to check.
     * @return true if p0 and p1 are adjacent, false if they are not.
     */
    public boolean areAdjacentPoints(Point p0, Point p1){
        ArrayList<Point> adjacentP0 = getAdjacentPoints(p0);
        for(Point adjacentPoint : adjacentP0){
            if(adjacentPoint.equals(p1)) return true;
        }
        return false;
    }

    /**
     * Prints the board.
     */
    public void printboard(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Tile current = board[i][j];
                switch(current.getTile()){
                    case EMPTY -> System.out.print("EMPTY\t");
                    case UNUSED -> System.out.print("UNUSED\t");
                    case CATS -> System.out.print("CATS\t");
                    case TROPHIES -> System.out.print("TROPHIES\t");
                    case PLANTS -> System.out.print("PLANTS\t");
                    case FRAMES -> System.out.print("FRAMES\t");
                    case GAMES -> System.out.print("GAMES\t");
                    case BOOKS -> System.out.print("BOOKS\t");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}

