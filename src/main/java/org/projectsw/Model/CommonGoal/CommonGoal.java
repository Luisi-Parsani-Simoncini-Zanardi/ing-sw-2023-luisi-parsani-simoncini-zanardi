package org.projectsw.Model.CommonGoal;
import org.projectsw.Exceptions.MaximumRedeemedPointsException;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public abstract class CommonGoal{
    private int redeemedNumber;
    protected int commonGoalCode;

    public CommonGoal(){
        this.redeemedNumber = 0;
    }

    /**
     * @return the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     */
    public int getRedeemedNumber(){
        return this.redeemedNumber;
    }

    /**
     * increase the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     * @throws MaximumRedeemedPointsException thrown if there are no more points to redeem on this CommonGoal
     */
    public void increaseRedeemedNumber() throws MaximumRedeemedPointsException{
        if(getRedeemedNumber()<4)
            redeemedNumber++;
        else throw  new MaximumRedeemedPointsException("There are no more points to redeem");
    }

    /**
     * @param shelf is the player's shelf
     * @return true after the override by the subclasses if the player's shelf match the requirements of the
     * specific commonGoal, false otherwise
     */
    public boolean check(Shelf shelf){
        return false;
    }

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

    public int getGoalCode() { return this.commonGoalCode; }
}