package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Bag;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    //test that the Tiles returned from the pop() method are of the right type
    //test that if there are no more tiles in the bag it returns Tiles.EMPTY
    @Test
    void pop() {
        TilesEnum tile;
        Bag bag = new Bag();
        for(int i=0; i < 132; i++){
            tile = bag.pop();
            assertTrue(tile.equals(TilesEnum.CATS)||tile.equals(TilesEnum.FRAMES)||tile.equals(TilesEnum.BOOKS)||tile.equals(TilesEnum.GAMES)||tile.equals(TilesEnum.TROPHIES)||tile.equals(TilesEnum.PLANTS));
        }
        tile = bag.pop();
        assertEquals(TilesEnum.EMPTY,tile);
    }
}