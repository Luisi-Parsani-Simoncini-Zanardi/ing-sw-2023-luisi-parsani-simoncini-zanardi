package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.UnusedTilesException;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;
import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    /**
     * Tests that the empty constructor of shelf actually creates a 6x5 matrix of empty tiles
     */
    @Test
    void integrityShelfTest(){
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(TilesEnum.EMPTY,shelf.getShelf()[i][j].getTile());
            }
        }
    }

    /**
     * Tests if the constructor that copies a shelf in another do it correctly
     */
    @Test
    void rightShelfCopy() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        Tile tile1 = new Tile(TilesEnum.CATS,0);
        Tile tile2 = new Tile(TilesEnum.PLANTS,0);
        shelf.insertTiles(tile1,0,0);
        shelf.insertTiles(tile2,4,4);
        Shelf shelfClone = new Shelf(shelf);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getShelf()[i][j].getTile(),shelfClone.getShelf()[i][j].getTile());
            }
        }
        //the following lines are important
        assertEquals(TilesEnum.CATS,shelfClone.getTileShelf(0,0).getTile());
        assertEquals(TilesEnum.PLANTS,shelfClone.getTileShelf(4,4).getTile());

    }

    /**
     * Tests the getters and the setters of the shelf
     */
    @Test
    void getSetShelfTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,0);
        shelf.insertTiles(new Tile(TilesEnum.PLANTS,0),4,4);
        Tile[][] shelfGetted = shelf.getShelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getShelf()[i][j].getTile(),shelfGetted[i][j].getTile());
            }
        }
        shelfGetted[3][3] = new Tile(TilesEnum.GAMES,0);
        shelf.setShelf(shelfGetted);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getShelf()[i][j].getTile(),shelfGetted[i][j].getTile());
            }
        }
    }

    /**
     * Tests that the get tile method returns the correct tile
     */
    @Test
    void getTileShelfTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,0);
        assertEquals(TilesEnum.CATS,shelf.getTileShelf(0,0).getTile());
    }

    /**
     * Tests that the getTileShelf method throws an IndexOutOfBoundsException when the row argument is negative.
     */
    @Test
    void getTileShelfgExceptionWhenRowIsNegative() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(-1, 0));
    }

    /**
     * Tests that the getTileShelf method throws an IndexOutOfBoundsException when the row argument is too large.
     */
    @Test
    void getTileShelfgExceptionWhenRowIsTooLarge() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(6, 0));
    }

    /**
     * Tests that the getTileShelf method throws an IndexOutOfBoundsException when the column argument is negative.
     */
    @Test
    void getTileShelfgExceptionWhenColumnIsNegative() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(0, -1));
    }

    /**
     * Tests that the getTileShelf method throws an IndexOutOfBoundsException when the column argument is too large.
     */
    @Test
    void getTileShelfgExceptionWhenColumnIsTooLarge() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(0, 5));
    }

    /**
     * Tests the sorrect insertion of a tile in the shelf,
     * also test if the rest of the shelf remains like iit was before the insertion
     */

    @Test
    void insertTilesTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,0);
        assertEquals(TilesEnum.CATS,shelf.getTileShelf(0,0).getTile());
        for(int i=0; i<6; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != 0 || j != 0){
                    assertEquals(TilesEnum.EMPTY, shelf.getTileShelf(i, j).getTile());
                }
            }
        }
    }

    /**
     * Tests that the insertTiles method throws an IndexOutOfBoundsException when the row argument is negative.
     */
    @Test
    void insertExceptionWhenRowIsNegative() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.insertTiles(new Tile(TilesEnum.CATS,0),-1,0));
    }

    /**
     * Tests that the insertTiles method throws an IndexOutOfBoundsException when the row argument is too big.
     */
    @Test
    void insertExceptionWhenRowIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.insertTiles(new Tile(TilesEnum.CATS,0),6,0));
    }

    /**
     * Tests that the insertTiles method throws an IndexOutOfBoundsException when the column argument is negative.
     */
    @Test
    void insertExceptionWhenColumnIsNegative() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,-1));
    }

    /**
     * Tests that the insertTiles method throws an IndexOutOfBoundsException when the column argument is negative.
     */
    @Test
    void insertExceptionWhenColumnIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,5));
    }

    /**
     * Tests that the insertTiles method throws an IndexOutOfBoundsException when try to insert empty.
     */
    @Test
    void insertExceptionWhenInsertEmpty() {
        Shelf shelf = new Shelf();
        assertThrows(EmptyTilesException.class, () -> shelf.insertTiles(new Tile(TilesEnum.EMPTY,0),0,0));
    }

    /**
     * Tests that the insertTiles method throws an IndexOutOfBoundsException when try to insert unused.
     */
    @Test
    void insertExceptionWhenInsertUnused() {
        Shelf shelf = new Shelf();
        assertThrows(UnusedTilesException.class, () -> shelf.insertTiles(new Tile(TilesEnum.UNUSED,0),0,0));
    }
}