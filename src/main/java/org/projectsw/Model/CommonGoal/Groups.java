package org.projectsw.Model.CommonGoal;

import org.projectsw.Config;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.awt.*;
import java.util.ArrayList;

public class Groups extends CommonGoalStrategy{

    private final int groupDim;

    private final ArrayList<Point> coordinates;

    /**
     * Creates a new instance of the Groups class using the unique code of the CommonGoal.
     * If strategyCode = 3 the player's shelf must have at least 4 groups of 4 Tiles made up of the same Tile types.
     * If strategyCode = 4 the player's shelf must have at least 6 groups of 2 Tiles made up of the same Tile types
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Groups(Integer strategyCode){
        super(strategyCode);
        this.coordinates = new ArrayList<>();
        if(this.strategyCode == 3) {
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
        boolean [][]matrix = new boolean[Config.shelfLength][Config.shelfHeight];

        int rightGroup=0;
        int dim;

        for(int i=Config.shelfLength-1; i>-1; i--){
            for (int j = 0; j < Config.shelfHeight-1; j++) {
                if(shelf.getTileShelf(i,j).getTile() != TilesEnum.EMPTY){
                    dim = 0;
                    if (!matrix[i][j])
                        dim = this.customShelfIterator(shelf, matrix, shelf.getTileShelf(i, j).getTile(), i, j);
                    if (dim >= this.groupDim)
                        rightGroup++;
                    if (rightGroup == this.numObjects)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that given a shelf position and type iterates over all the joint Tiles present, and returns the size of the found group
     * @param shelf is the player's shelf
     * @param matrix is an array of booleans to keep track of the shelf boxes that have already been navigated
     * @param type is the Tile type of the group
     * @param i is the current i in the shelf
     * @param j is the current j in the shelf
     * @return returns the size of the found group
     */
    private int customShelfIterator(Shelf shelf, boolean [][]matrix, TilesEnum type, int i , int j){
        Point nextPoint;

        if(i-1 > -1 && !matrix[i-1][j] && shelf.getTileShelf(i-1,j).getTile()==type && !this.coordinates.contains(new Point(i-1,j)))
            this.coordinates.add(new Point(i-1,j));
        if(i+1 < Config.shelfLength && !matrix[i+1][j] && shelf.getTileShelf(i+1,j).getTile()==type && !this.coordinates.contains(new Point(i+1,j)))
            this.coordinates.add(new Point(i+1,j));
        if(j-1 > -1 && !matrix[i][j-1] && shelf.getTileShelf(i,j-1).getTile()==type && !this.coordinates.contains(new Point(i,j-1)))
            this.coordinates.add(new Point(i,j-1));
        if(j+1 < Config.shelfHeight && !matrix[i][j+1] && shelf.getTileShelf(i,j + 1).getTile()==type && !this.coordinates.contains(new Point(i,j+1)))
            this.coordinates.add(new Point(i,j+1));

        matrix[i][j]=true;
        if(this.coordinates.size()!=0) {
            nextPoint = this.coordinates.get(0);
            this.coordinates.remove(0);
            return 1 + customShelfIterator(shelf, matrix, type, (int) nextPoint.getX(), (int) nextPoint.getY());
        }
        return 1;
    }
}

