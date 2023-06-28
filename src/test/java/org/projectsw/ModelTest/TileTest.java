package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.Enums.TilesEnum;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    /**
     * Tests the getters methods of the class.
     */
    @Test
    void gettersTest(){
        Tile  tile = new Tile(TilesEnum.CATS,0);
        assertEquals(TilesEnum.CATS,tile.getTile());
        assertEquals(0,tile.getImageNumber());
    }

    /**
     * Tests if the IndexOutOfBoundsException is correctly thrown for a imageNumber higher than expected.
     */
    @Test
    void testInvalidImageNumberLower() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tile tile = new Tile(TilesEnum.CATS,-1);
            assertEquals(tile, tile);
        });
    }

    /**
     * Tests if the IndexOutOfBoundsException is correctly thrown for a imageNumber lower than expected.
     */
    @Test
    void testInvalidPlayersNumberHigher() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tile tile = new Tile(TilesEnum.CATS,3);
            assertEquals(tile, tile);
        });
    }

    /**
     * Test toString method from Tile class
     */
    @Test
    void toStringTest() {
        Tile tile = new Tile(TilesEnum.GAMES, 0);
        assertEquals(tile.toString(), "GAMES");
    }
}
