package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class Cross extends ShapeBehavior {
    public Cross(){
        super();
        this.commonGoalCode = 10;
    }

    /**
     * @param shelf is the player shelf
     * @return true if there is at least one cross pattern formed by equal tiles
     */
    @Override
    public boolean check(Shelf shelf) {
        ArrayList<TilesEnum> tile = new ArrayList<TilesEnum>();
        for(int i=1; i<5; i++){
            for(int j=1; j<4; j++) {
                tile.add(shelf.getTileShelf(i, j).getTile());
                tile.add(shelf.getTileShelf(i-1, j-1).getTile());
                tile.add(shelf.getTileShelf(i+1, j+1).getTile());
                tile.add(shelf.getTileShelf(i+1, j-1).getTile());
                tile.add(shelf.getTileShelf(i-1, j+1).getTile());
                if(this.differentTiles(tile)==1)
                    return true;
                tile.clear();
            }
        }
        return false;
    }
}
