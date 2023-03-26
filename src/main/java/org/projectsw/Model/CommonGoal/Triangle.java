package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class Triangle extends ShapeBehavior {
    public Triangle(){
        super();
        this.commonGoalCode = 12;
    }

    /**
     * @param shelf is the player's shelf
     * @return true if the columns are decreasing in height from left to right or from right to left and differ in height by one,
     * returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf) {
        int [] columnHeight = new int[5];

        for(int i=0;i<5;i++)
            for(int j=0; j<6;j++){
                if(shelf.getTileShelf(j,i).getTile() != TilesEnum.EMPTY)
                    columnHeight[i]++;
            }
        if(columnHeight[0] == 6 && columnHeight[1] == 5 && columnHeight[2] == 4 && columnHeight[3] == 3 && columnHeight[4] == 2)
            return true;
        if(columnHeight[4] == 6 && columnHeight[3] == 5 && columnHeight[2] == 4 && columnHeight[1] == 3 && columnHeight[0] == 2)
            return true;
        if(columnHeight[0] == 5 && columnHeight[1] == 4 && columnHeight[2] == 3 && columnHeight[3] == 2 && columnHeight[4] == 1)
            return true;
        if(columnHeight[4] == 5 && columnHeight[3] == 4 && columnHeight[2] == 3 && columnHeight[1] == 2 && columnHeight[0] == 1)
            return true;
        return false;
    }
}
