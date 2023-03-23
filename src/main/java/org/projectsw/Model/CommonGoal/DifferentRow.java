package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class DifferentRow implements RowBehavior {
    /**
     * @param shelf is the player shelf
     * @return true if there are at least 2 rows with at most 5 different tile types that aren't UNUSED or EMPTY tiles, returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf){
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        int rightRows=0;
        for(int i=5; i>-1; i--) {
            for (int j = 0; j < 5; j++) {
                tiles.add(shelf.getTileShelf(i, j));
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

    /**
     * Returns the number of different Type of tiles in the row and checks that there aren't UNUSED or EMPTY Tiles
     * @param rowTiles the ArrayList of Tiles to check
     * @return the number of different tiles types in the arrayList and checks that there aren't UNUSED or EMPTY Tiles
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
        if(rowTiles.stream().anyMatch(tile -> tile.getTile() == TilesEnum.EMPTY))
            counter = -1;
        return counter;
    }
}
