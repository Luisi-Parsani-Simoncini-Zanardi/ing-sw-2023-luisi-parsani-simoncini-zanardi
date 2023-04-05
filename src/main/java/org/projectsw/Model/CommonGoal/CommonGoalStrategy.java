package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public abstract class CommonGoalStrategy {
    protected int numObjects;
    protected int StrategyCode;

    public CommonGoalStrategy(int code){
        this.StrategyCode= code;
    }

    /**
     * Checks that the player's shelf meets the strategy requirements
     * @param shelf is the player shelf
     * @return true if the shelf meets the requirements or false if it doesn't
     */
    public boolean check(Shelf shelf){return false;}

    /**
     * Returns the number of different Type of tiles in the row and checks that there aren't UNUSED or EMPTY Tiles
     * @param tiles the ArrayList of Tiles to check
     * @return the number of different tiles types in the arrayList and checks that there aren't UNUSED or EMPTY Tiles
     */
    protected int differentTiles(ArrayList<TilesEnum> tiles){
        int count = 0;

        if(tiles.contains(TilesEnum.CATS))
            count++;
        if(tiles.contains(TilesEnum.BOOKS))
            count++;
        if(tiles.contains(TilesEnum.FRAMES))
            count++;
        if(tiles.contains(TilesEnum.GAMES))
            count++;
        if(tiles.contains(TilesEnum.PLANTS))
            count++;
        if(tiles.contains(TilesEnum.TROPHIES))
            count++;
        if(tiles.contains(TilesEnum.EMPTY))
            count = -1;

        return count;
    }
}
