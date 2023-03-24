package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    /**
     * tests if the method doesn't insert tiles out of bounds, if the method doesn't insert EMPTY or UNUSED tiles and if the method insert the correct tile in the correct position in the shelf
     */
    @Test
    void insertTiles() {
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 5,5);
        }catch(Exception e){
            assertEquals("Out of bounds", e.getMessage());
        }

        try {
            shelf.insertTiles(new Tile(TilesEnum.UNUSED, 0), 5,4);
        }catch(Exception e){
            assertEquals("You can't add an UNUSED tile to the shelf", e.getMessage());
        }

        try {
            shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0), 5,4);
        }catch(Exception e){
            assertEquals("You can't add an EMPTY tile to the shelf", e.getMessage());
        }

        assertEquals(TilesEnum.EMPTY, shelf.getShelf()[5][4].getTile());
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 5,4);
        }catch(Exception e){
        }
        assertEquals(TilesEnum.CATS, shelf.getShelf()[5][4].getTile());
    }
}