package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    /**
     * Test for the getters methods of the class
     */
    @Test
    void gettersTest(){
        Tile  tile = new Tile(TilesEnum.CATS,0);
        assertEquals(TilesEnum.CATS,tile.getTile());
        assertEquals(0,tile.getImageNumber());
    }

    /**
     * Tests if the IndexOutOfBoundsException is correctly thrown for a imageNumber higher then expected
     */
    @Test
    void testInvalidImageNumberHigher() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tile tile = new Tile(TilesEnum.CATS,-1);
        });
    }

    /**
     * Tests if the IndexOutOfBoundsException is correctly thrown for a imageNumber lower then expected
     */
    @Test
    void testInvalidPlayersNumber2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tile tile = new Tile(TilesEnum.CATS,3);
        });
    }
}
