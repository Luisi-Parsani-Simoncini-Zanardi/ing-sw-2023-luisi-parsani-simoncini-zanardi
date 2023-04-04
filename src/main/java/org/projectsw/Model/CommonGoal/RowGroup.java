package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class RowGroup extends TileControl{

    /**
     * Checks that there are at least 4 columns with at most 3 different tile types
     * @param shelf is the player shelf
     * @return true if there are at least 4 rows with at most 3 different tile types, returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf){
        ArrayList<TilesEnum> tiles = new ArrayList<TilesEnum>();
        int rightFind=0;
        for(int i=5; i>-1; i--) {
            for (int j = 0; j < 5; j++) {
                tiles.add(shelf.getTileShelf(i, j).getTile());
            }
            if(this.differentTiles(tiles)==-1)
                return false;
            else if(this.differentTiles(tiles)<4)
                rightFind++;
            tiles.clear();
            if(rightFind == 4)
                return true;
        }
        return false;
    }

}
