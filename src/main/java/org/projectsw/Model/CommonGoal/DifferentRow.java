package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class DifferentRow implements RowBehavior {
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }

    /**
     * Returns the number of different Type of tiles in the row
     * @param rowTiles the ArrayList of Tiles to check
     * @return the number of different tiles types in the arrayList
     */
    private int differentTiles(ArrayList<Tile> rowTiles){
        int counter=0;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.CATS))
            counter++;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.BOOKS))
            counter++;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.FRAMES))
            counter++;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.GAMES))
            counter++;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.PLANTS))
            counter++;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.TROPHIES))
            counter++;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.UNUSED))
            counter = -1;
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.EMPTY))
            counter = -1;
        return counter;
    }
}
