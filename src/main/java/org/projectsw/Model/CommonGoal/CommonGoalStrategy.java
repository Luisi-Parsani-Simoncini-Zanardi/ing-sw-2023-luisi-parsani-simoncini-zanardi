package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public abstract class CommonGoalStrategy {
    protected int numObjects;
    protected int strategyCode;

    public CommonGoalStrategy(Integer code){
        this.strategyCode= code;
    }

    /**
     * Returns the numObjects of the commonGoal
     * @return the numObjects of the commonGoal
     */
    public int getNumObjects(){return this.numObjects;}

    /**
     * Returns the strategyCode of the commonGoal
     * @return the strategyCode of the commonGoal
     */
    public int getStrategyCode(){return this.strategyCode;}

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

    /**
     * return the numObjects
     * @return numObjects
     */
    public int getNumObjects() { return numObjects; }

    /**
     * return the strategyCode
     * @return strategyCode
     */
    public int getStrategyCode() { return strategyCode; }
}
