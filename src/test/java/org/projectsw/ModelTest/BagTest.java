package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Bag;
import org.projectsw.Model.Tiles;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    //test that the Tiles returned from the pop() method are of the right type
    //test that if there are no more tiles in the bag it returns Tiles.EMPTY
    @Test
    void pop() {
        Tiles tile;
        Bag bag = new Bag();
        for(int i=0; i < 132; i++){
            tile = bag.pop();
            assertTrue(tile.equals(Tiles.CATS)||tile.equals(Tiles.FRAMES)||tile.equals(Tiles.BOOKS)||tile.equals(Tiles.GAMES)||tile.equals(Tiles.TROPHIES)||tile.equals(Tiles.PLANTS));
        }
        tile = bag.pop();
        assertEquals(Tiles.EMPTY,tile);
    }
}