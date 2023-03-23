package org.projectsw.Model.CommonGoal;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public abstract class RowBehavior extends CommonGoal {

    public RowBehavior(){
        super();
    }

    /**
     * Returns the number of different Type of tiles in the row and checks that there aren't UNUSED or EMPTY Tiles
     * @param rowTiles the ArrayList of Tiles to check
     * @return the number of different tiles types in the arrayList and checks that there aren't UNUSED or EMPTY Tiles
     */
    protected int differentTiles(ArrayList<Tile> rowTiles){
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
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.EMPTY))
            counter = -1;
        return counter;
    }
}