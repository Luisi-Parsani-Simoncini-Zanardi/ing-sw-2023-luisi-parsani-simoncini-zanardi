package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

public class Edges extends CommonGoalStrategy{

    /**
     * Creates a new instance of the Edges class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Edges(int strategyCode){
        super(strategyCode);
    }

    /**
     * Checks that the corners of the shelf are all the same type
     * @param shelf is the player shelf
     * @return true if the edges of the shelf are the same TileEnum type and not EMPTY type
     */
    @Override
    public boolean check(Shelf shelf){
        return shelf.getTileShelf(0, 0).getTile() == shelf.getTileShelf(0, 4).getTile() &&
                shelf.getTileShelf(0, 0).getTile() == shelf.getTileShelf(5, 0).getTile() &&
                shelf.getTileShelf(0, 0).getTile() == shelf.getTileShelf(5, 4).getTile() &&
                shelf.getTileShelf(0, 0).getTile() != TilesEnum.EMPTY;
    }
}
