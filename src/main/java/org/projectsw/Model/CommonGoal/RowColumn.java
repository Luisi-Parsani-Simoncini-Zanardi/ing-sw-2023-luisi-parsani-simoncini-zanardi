package org.projectsw.Model.CommonGoal;

import org.projectsw.Util.Config;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Enums.TilesEnum;

import java.util.ArrayList;

/**
 * The RowColumn class represents a game strategy where the goal is to have a certain number of rows or columns on the bookshelf
 * that meet specific requirements.
 * If strategyCode is equal to 2, the player's shelf must contain at least 2 columns made up of all different types of Tiles.
 * If strategyCode is equal to 5, the player's shelf must contain at least 3 columns made up of at most 3 types of tiles.
 * If strategyCode is equal to 6, the player's shelf must contain at least 2 rows made up of all different types of Tiles.
 * If strategyCode is equal to 7, the player's shelf must contain at least 4 rows made up of at most 3 types of tiles.
 */
public class RowColumn extends CommonGoalStrategy{

    private int diffTiles;
    private boolean rowColumn;  //true Row, false Column

    /**
     * Creates a new instance of the RowColumn class using the unique code of the CommonGoal.
     * If strategyCode is equal to 2, the player's shelf must contain at least 2 columns made up of all different types of Tiles
     * If strategyCode is equal to 5, the player's shelf must contain at least 3 columns made up of at most 3 types of tiles
     * If strategyCode is equal to 6, the player's shelf must contain at least 2 rows made up of all different types of Tiles
     * If strategyCode is equal to 7, the player's shelf must contain at least 4 columns made up of at most 3 types of tiles
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public RowColumn(Integer strategyCode){
        super(strategyCode);
        switch (strategyCode) {
            case 2 -> {
                this.numObjects = 2;
                this.diffTiles = 6;
                this.rowColumn = false;
                this.setDescription("Two columns each formed by 6\n" +
                        "different types of tiles.");
            }
            case 5 -> {
                this.numObjects = 3;
                this.diffTiles = 3;
                this.rowColumn = false;
                this.setDescription("""
                        Three columns each formed by 6 tiles
                        of maximum three different types. One
                        column can show the same or a different
                        combination of another column.""");
            }
            case 6 -> {
                this.numObjects = 2;
                this.diffTiles = 5;
                this.rowColumn = true;
                this.setDescription("""
                        Two lines each formed by 5 different
                        types of tiles. One line can show the
                        same or a different combination of the
                        other line.""");
            }
            case 7 -> {
                this.numObjects = 4;
                this.diffTiles = 3;
                this.rowColumn = true;
                this.setDescription("""
                        Four lines each formed by 5 tiles of
                        maximum three different types. One
                        line can show the same or a different
                        combination of another line.""");
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
        return shelfVerifier(this.strategyCode, numDiffTiles);
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
            for (int i = Config.shelfHeight-1; i > -1; i--) {
                for (int j = 0; j < Config.shelfLength; j++) {
                    tiles.add(shelf.getTileShelf(i, j).getTile());
                }
                if(this.differentTiles(tiles)!=-1)
                    tilesCounted.add(this.differentTiles(tiles));
                tiles.clear();
            }
        }else{
            for (int i = 0; i < Config.shelfLength; i++) {
                for (int j = Config.shelfHeight-1; j > -1; j--) {
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
