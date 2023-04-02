package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class DifferentRow extends TileControl {

    /**
     * @param shelf is the player shelf
     * @return true if there are at least 2 rows with at most 5 different tile types that aren't UNUSED or EMPTY tiles, returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf){
        ArrayList<TilesEnum> tiles = new ArrayList<TilesEnum>();
        int rightRows=0;
        for(int i=5; i>-1; i--) {
            for (int j = 0; j < 5; j++) {
                tiles.add(shelf.getTileShelf(i, j).getTile());
            }
            if(this.differentTiles(tiles)==-1)
                return false;
            else if(this.differentTiles(tiles)>4)
                rightRows++;
            tiles.clear();
            if(rightRows == 2)
                return true;
        }
        return false;
    }
}
