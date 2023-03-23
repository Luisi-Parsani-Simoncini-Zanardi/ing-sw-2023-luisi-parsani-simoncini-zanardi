package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class RowGroup implements RowBehavior{

    /**
     *
     * @param shelf is the player shelf
     * @return true if there are at least 4 rows with at most 3 different tile types, returns false otherwise
     */
    @Override
    public boolean check(Shelf shelf){
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        int rightRows=0;
        for(int i=0; i<6; i++) {
            for (int j = 0; j < 5; j++) {
                tiles.add(shelf.getTileShelf(i, j));
            }
            if(this.differentTiles(tiles)<4&&this.differentTiles(tiles)>0)
                rightRows++;
            tiles.clear();
        }
        if(rightRows>3)
            return true;
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
        return counter;
    }
}
