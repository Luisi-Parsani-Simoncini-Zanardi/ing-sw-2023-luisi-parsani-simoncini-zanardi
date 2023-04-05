package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class RowColumn extends CommonGoalStrategy{

    private int diffTiles;
    private boolean rowColumn;  //true Row, false Column

    /**
     * Creates a new instance of the RowColumn class using the unique code of the CommonGoal to be created to correctly initialize the parameters
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public RowColumn(int strategyCode){
        super(strategyCode);
        switch (strategyCode) {
            case 2 -> {
                this.numObjects = 2;
                this.diffTiles = 6;
                this.rowColumn = false;
            }
            case 5 -> {
                this.numObjects = 3;
                this.diffTiles = 3;
                this.rowColumn = false;
            }
            case 6 -> {
                this.numObjects = 2;
                this.diffTiles = 5;
                this.rowColumn = true;
            }
            case 7 -> {
                this.numObjects = 4;
                this.diffTiles = 3;
                this.rowColumn = true;
            }
        }
    }

    /**
     * Checks that the player's shelf meets the CommonGoal requirements
     * @param shelf is the player shelf
     * @return true if the player shelf meets the requirements of the CommonGoal
     * associated with strategyCode
     */
    @Override
    public boolean check(Shelf shelf) {
        ArrayList<Integer> numDiffTiles;
        numDiffTiles = this.rowColumnCounter(shelf);
        return shelfVerifier(this.StrategyCode, numDiffTiles);
    }

    /**
     * auxiliary method which, according to the CommonGoal code, checks that the rows or columns satisfy the requirements
     * based on the number of different tiles they contain.
     * @param code is the unique CommonGoal code
     * @param numDiffTiles is an array that contains the number of different tiles in each column or row
     * @return true if there are enough rows or columns that satisfy the requirement on how many types they can contain
     */
    private boolean shelfVerifier(int code, ArrayList<Integer> numDiffTiles){
        int counter=0;

        if(code == 2 || code == 6){
            for (Integer numDiffTile : numDiffTiles)
                if (numDiffTile == this.diffTiles)
                    counter++;
        }
        else{
            for (Integer numDiffTile : numDiffTiles)
                if (numDiffTile <= this.diffTiles)
                    counter++;
        }
        return counter >= this.numObjects;
    }

    /**
     * Auxiliary function for checking CommonGoals 2,5,6,7 which returns the number of different tiles in each row or column
     * @param shelf is the player shelf
     * @return an ArrayList containing the number of different tiles in each row or in each column based on the passed code
     */
    private ArrayList<Integer> rowColumnCounter(Shelf shelf){

        ArrayList<TilesEnum> tiles = new ArrayList<>();
        ArrayList<Integer> tilesCounted = new ArrayList<>();

        if(this.rowColumn){
            for (int i = 5; i > -1; i--) {
                for (int j = 0; j < 5; j++) {
                    tiles.add(shelf.getTileShelf(i, j).getTile());
                }
                if(this.differentTiles(tiles)!=-1)
                    tilesCounted.add(this.differentTiles(tiles));
                tiles.clear();
            }
        }else{
            for (int i = 0; i < 5; i++) {
                for (int j = 5; j > -1; j--) {
                    tiles.add(shelf.getTileShelf(j, i).getTile());
                }
                if(this.differentTiles(tiles)!=-1)
                    tilesCounted.add(this.differentTiles(tiles));
                tiles.clear();
            }
        }
        return tilesCounted;
    }
}
