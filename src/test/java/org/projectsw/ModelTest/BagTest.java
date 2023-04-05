package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Bag;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    /**
     * Tests if the bag is well created,
     * that is formed by 22 tiles of each type and that every imageNumber is used.
     */
    @Test
    void integrityBagTest(){
        Tile tile;
        int cat0 = 0,cat1 = 0,cat2 = 0,games0 = 0,games1 = 0,games2 = 0,trophies0 = 0,trophies1 = 0,trophies2 = 0;
        int frames0 = 0,frames1 = 0,frames2 = 0,plants0 = 0,plants1 = 0,plants2 = 0,books0 = 0,books1 = 0,books2 = 0;
        int cats,games,trophies,frames,plants,books;
        Bag bag = new Bag();
        assertEquals(132,bag.getBagSize());
        for(int i=0; i < 132; i++) {
            tile = bag.pop();
            if(tile.getTile().equals(TilesEnum.CATS) && tile.getImageNumber() == 0) cat0++;
            if(tile.getTile().equals(TilesEnum.CATS) && tile.getImageNumber() == 1) cat1++;
            if(tile.getTile().equals(TilesEnum.CATS) && tile.getImageNumber() == 2) cat2++;
            if(tile.getTile().equals(TilesEnum.GAMES) && tile.getImageNumber() == 0) games0++;
            if(tile.getTile().equals(TilesEnum.GAMES) && tile.getImageNumber() == 1) games1++;
            if(tile.getTile().equals(TilesEnum.GAMES) && tile.getImageNumber() == 2) games2++;
            if(tile.getTile().equals(TilesEnum.TROPHIES) && tile.getImageNumber() == 0) trophies0++;
            if(tile.getTile().equals(TilesEnum.TROPHIES) && tile.getImageNumber() == 1) trophies1++;
            if(tile.getTile().equals(TilesEnum.TROPHIES) && tile.getImageNumber() == 2) trophies2++;
            if(tile.getTile().equals(TilesEnum.FRAMES) && tile.getImageNumber() == 0) frames0++;
            if(tile.getTile().equals(TilesEnum.FRAMES) && tile.getImageNumber() == 1) frames1++;
            if(tile.getTile().equals(TilesEnum.FRAMES) && tile.getImageNumber() == 2) frames2++;
            if(tile.getTile().equals(TilesEnum.PLANTS) && tile.getImageNumber() == 0) plants0++;
            if(tile.getTile().equals(TilesEnum.PLANTS) && tile.getImageNumber() == 1) plants1++;
            if(tile.getTile().equals(TilesEnum.PLANTS) && tile.getImageNumber() == 2) plants2++;
            if(tile.getTile().equals(TilesEnum.BOOKS) && tile.getImageNumber() == 0) books0++;
            if(tile.getTile().equals(TilesEnum.BOOKS) && tile.getImageNumber() == 1) books1++;
            if(tile.getTile().equals(TilesEnum.BOOKS) && tile.getImageNumber() == 2) books2++;
        }
        assertNotEquals(0,cat0); assertNotEquals(0,cat1); assertNotEquals(0,cat2);
        assertNotEquals(0,trophies0); assertNotEquals(0,trophies1); assertNotEquals(0,trophies2);
        assertNotEquals(0,games0); assertNotEquals(0,games1); assertNotEquals(0,games2);
        assertNotEquals(0,frames0); assertNotEquals(0,frames1); assertNotEquals(0,frames2);
        assertNotEquals(0,books0); assertNotEquals(0,books1); assertNotEquals(0,books2);
        assertNotEquals(0,plants0); assertNotEquals(0,plants1); assertNotEquals(0,plants2);
        cats = cat0 + cat1 + cat2;
        games = games0 + games1 + games2;
        trophies = trophies0 + trophies1 + trophies2;
        frames = frames0 + frames1 + frames2;
        books = books0 + books1 + books2;
        plants = plants0 + plants1 + plants2;
        assertEquals(22,cats);
        assertEquals(22,games);
        assertEquals(22,trophies);
        assertEquals(22,frames);
        assertEquals(22,books);
        assertEquals(22,plants);
    }

    /**
     * Tests if getSize always returns the right size of the bag after every pop.
     */
    @Test
    void getSizeTest(){
        Bag bag = new Bag();
        for(int i=132; i > 0; i--) {
            assertEquals(i,bag.getBagSize());
            bag.pop();
        }
    }

    /**
     * Tests if the Tiles returned from the pop method are of the right type;
     * also tests if popping when the bag is empty always returns an EMPTY tile.
     */
    @Test
    void correctPopTest() {
        Tile tile;
        Bag bag = new Bag();
        for(int i=0; i < 132; i++){
            tile = bag.pop();
            assertTrue(tile.getTile().equals(TilesEnum.CATS)||
                        tile.getTile().equals(TilesEnum.FRAMES)||
                        tile.getTile().equals(TilesEnum.BOOKS)||
                        tile.getTile().equals(TilesEnum.GAMES)||
                        tile.getTile().equals(TilesEnum.TROPHIES)||
                        tile.getTile().equals(TilesEnum.PLANTS));
        }
        tile = bag.pop();
        assertEquals(TilesEnum.EMPTY,tile.getTile());
    }

    /**
     * Tests if getBag returns the array correctly.
     */
    @Test
    void testGetBag() {
        Bag bag = new Bag();
        ArrayList<Tile> tmp = bag.getBag();
        for (Tile tile : tmp) {
            assertEquals(tile, bag.pop());
        }
    }
}