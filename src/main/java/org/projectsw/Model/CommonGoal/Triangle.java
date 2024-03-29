package org.projectsw.Model.CommonGoal;

import org.projectsw.Util.Config;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Enums.TilesEnum;

/**
 * The Triangle class represents a game strategy where the goal is to have columns on the bookshelf
 * that either ascend or descend in height from left to right.
 */
public class Triangle extends CommonGoalStrategy{


    /**
     * Creates a new instance of the Triangle class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Triangle(Integer strategyCode){
        super(strategyCode);
        this.setDescription("""
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.""");
    }

    /**
     * Returns true if the player's shelf has columns ascending or descending from left to right
     * @param shelf is the player shelf
     * @return true if the player's shelf has columns ascending or descending from left to right.
     * Returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf){

        int [] height = this.columnsHeight(shelf);
        return this.ascending(height) || this.descending(height);
    }

    /**
     * Auxiliary method that returns a vector with the heights of the columns from left to right or
     * returns -1 in columnHeight[0] to interrupt the algorithm
     * @param shelf is the player shelf
     * @return a vector with the heights of the columns from left to right.
     * If the height of a column is 0 it returns -1 in columnHeight[0] to interrupt the algorithm
     */
    private int[] columnsHeight(Shelf shelf){
        int [] columnHeight = new int[Config.shelfLength];
        for(int i=0;i<Config.shelfLength;i++) {
            for (int j = 0; j < Config.shelfHeight; j++) {
                if (shelf.getTileShelf(j, i).getTile() != TilesEnum.EMPTY)
                    columnHeight[i]++;
            }
            if(columnHeight[i] == 0) {
                columnHeight[0] = -1;
                return columnHeight;
            }
        }
        return columnHeight;
    }

    /**
     * Auxiliary function that returns true
     * if the columns are arranged in ascending order from left to right
     * @param height is the vector containing the heights of the columns
     * @return true if columns are increasing from left to right
     */
    private boolean ascending(int []height){
        int increasing=0;
        for(int i = 0; i<Config.shelfLength-1; i++)
            if (height[i + 1] - height[i] != 1) {
                increasing = 1;
                break;
            }
        return increasing == 0;
    }

    /**
     * Auxiliary function that returns true
     * if the columns are arranged in descending order from left to right
     * @param height is the vector containing the heights of the columns
     * @return true if columns are decreasing from left to right
     */
    private boolean descending(int []height){
        int decreasing=0;
        for(int i = 0; i<Config.shelfLength-1; i++)
            if (height[i] - height[i + 1] != 1) {
                decreasing = 1;
                break;
            }
        return decreasing == 0;
    }

}