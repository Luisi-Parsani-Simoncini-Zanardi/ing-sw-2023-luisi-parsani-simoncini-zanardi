package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

public class Edges implements CommonGoalStrategy{

    /**
     * @param shelf is the player shelf
     * @return true if the edges of the shelf are the same TileEnum type and not EMPTY type
     */
    @Override
    public boolean check(Shelf shelf){
        if(shelf.getTileShelf(0,0).getTile() == shelf.getTileShelf(0,4).getTile() &&
           shelf.getTileShelf(0,0).getTile() == shelf.getTileShelf(5,0).getTile() &&
           shelf.getTileShelf(0,0).getTile() == shelf.getTileShelf(5,4).getTile() &&
           shelf.getTileShelf(0,0).getTile() != TilesEnum.EMPTY)
            return true;
        return false;
    }
}
