package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.awt.*;
import java.util.ArrayList;

public class Groups extends CommonGoalStrategy{

    //TODO: c'è qualcosa di strato nell'iteratore

    private final int groupDim;

    private ArrayList<Point> coordinates;

    /**
     * Creates a new instance of the Groups class using the unique code of the CommonGoal.
     * If strategyCode = 3 the player's shelf must have at least 4 groups of 4 Tiles made up of the same Tile types.
     * If strategyCode = 4 the player's shelf must have at least 6 groups of 2 Tiles made up of the same Tile types
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Groups(int strategyCode){
        super(strategyCode);
        this.coordinates = new ArrayList<>();
        if(this.StrategyCode == 3) {
            this.numObjects = 4;
            this.groupDim = 4;
        }
        else {
            this.numObjects = 6;
            this.groupDim = 2;
        }
    }

    /**
     * Checks that the player's shelf meets the CommonGoal requirements
     * @param shelf is the player shelf
     * @return true if the player shelf meets the requirements of the CommonGoal
     * associated with strategyCode
     */
    @Override
    public boolean check(Shelf shelf){
        boolean [][]matrix = new boolean[6][5];

        int rightGroup=0;
        int dim;

        for(int i=5; i>-1; i--){
            for (int j = 0; j < 5; j++) {
                dim=0;
                if(!matrix[i][j])
                    dim = this.customShelfIterator(shelf, matrix, shelf.getTileShelf(i,j).getTile(), i, j);
                if(dim >= this.groupDim)
                    rightGroup++;
                if(rightGroup == 5)//this.numObjects)
                    return true;
            }
        }
        return false;
    }

    /**
     * Method that given a shelf position and type iterates over all the joint Tiles present, and returns the size of the found group
     * @param shelf is the player's shelf
     * @param matrix is an array of booleans to keep track of the shelf boxes that have already been navigated
     * @param type is the Tile type of the group
     * @param row is the current row in the shelf
     * @param column is the current column in the shelf
     * @return returns the size of the found group
     */
    private int customShelfIterator(Shelf shelf, boolean [][]matrix, TilesEnum type, int row , int column){
        Point nextPoint;
        //sopra
        if(row-1 > -1 && !matrix[row-1][column] && shelf.getTileShelf(row-1,column).getTile()==type)
            this.coordinates.add(new Point(row-1,column));
        //sotto
        if(row+1 < 6 && !matrix[row+1][column] && shelf.getTileShelf(row+1,column).getTile()==type)
            this.coordinates.add(new Point(row+1,column));
        //sinistra
        if(column-1 > -1 && !matrix[row][column-1] && shelf.getTileShelf(row,column-1).getTile()==type)
            this.coordinates.add(new Point(row,column-1));
        //destra
        if(column+1 < 5 && !matrix[row][column+1] && shelf.getTileShelf(row,column + 1).getTile()==type)
            this.coordinates.add(new Point(row,column+1));

        matrix[row][column]=true;
        if(this.coordinates.size()!=0) {
            nextPoint = this.coordinates.get(0);
            this.coordinates.remove(0);
            return 1 + customShelfIterator(shelf, matrix, type, (int) nextPoint.getX(), (int) nextPoint.getY());
        }
        return 1;
    }
}

