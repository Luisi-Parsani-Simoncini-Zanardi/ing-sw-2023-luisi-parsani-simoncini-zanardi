package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tiles;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    @Test
    void getShelf() {
    }

    //test that the method don't insert tiles out of bounds
    //test that the method don't insert EMPTY or UNUSED tiles
    //test that the method insert the correct tile in the correct position in the shelf
    @Test
    void insertTiles() {
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(Tiles.CATS, 5,5);
        }catch(Exception e){
            assertEquals("Out of bounds", e.getMessage());
        }

        try {
            shelf.insertTiles(Tiles.UNUSED, 5,4);
        }catch(Exception e){
            assertEquals("You can't add an UNUSED tile to the shelf", e.getMessage());
        }

        try {
            shelf.insertTiles(Tiles.EMPTY, 5,4);
        }catch(Exception e){
            assertEquals("You can't add an EMPTY tile to the shelf", e.getMessage());
        }

        assertEquals(Tiles.EMPTY, shelf.getShelf()[5][4]);
        try {
            shelf.insertTiles(Tiles.CATS, 5,4);
        }catch(Exception e){
        }
        assertEquals(Tiles.CATS, shelf.getShelf()[5][4]);
    }
}