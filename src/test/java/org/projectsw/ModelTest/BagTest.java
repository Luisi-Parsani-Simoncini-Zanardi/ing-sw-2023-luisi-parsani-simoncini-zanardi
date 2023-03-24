package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Bag;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    /**
     * tests if the Tiles returned from the pop() method are of the right type, and that if there are no more tiles in the bag it returns Tiles.EMPTY
     */
    @Test
    void checkPop() {
        Tile tile;
        Bag bag = new Bag();
        for(int i=0; i < 132; i++){
            tile = bag.pop();
            assertTrue(tile.getTile().equals(TilesEnum.CATS)||tile.getTile().equals(TilesEnum.FRAMES)||tile.getTile().equals(TilesEnum.BOOKS)||tile.getTile().equals(TilesEnum.GAMES)||tile.getTile().equals(TilesEnum.TROPHIES)||tile.getTile().equals(TilesEnum.PLANTS));
        }
        tile = bag.pop();
        assertEquals(TilesEnum.EMPTY,tile.getTile());
    }
}