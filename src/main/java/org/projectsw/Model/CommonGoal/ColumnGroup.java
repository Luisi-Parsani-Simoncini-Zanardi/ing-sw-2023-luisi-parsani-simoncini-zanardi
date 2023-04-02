package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class ColumnGroup extends TileControl {

    /**
     * @param shelf is the player shelf
     * @return true if there are at least 3 columns with at most 3 different tile types, returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf) {

        int columnCounter = 0;
        ArrayList<TilesEnum> tiles = new ArrayList<TilesEnum>();

        for(int i=0; i<5; i++){
            for(int j=0; j<6; j++){
                tiles.add(shelf.getTileShelf(j,i).getTile());
            }
            if(this.differentTiles(tiles) < 4 && this.differentTiles(tiles) != -1)
                columnCounter++;
            tiles.clear();
            if(columnCounter == 3)
                return true;
        }
        return false;
    }
}