package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Config;
import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.UnusedTilesException;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.projectsw.Model.TilesEnum.GAMES;

class ShelfTest {

    /**
     * Tests if the empty constructor of shelf actually creates a 6x5 matrix of empty tiles.
     */
    @Test
    void integrityShelfTest(){
        Shelf shelf = new Shelf();
        assertEquals(Config.shelfLength,shelf.getShelf().length);
        assertEquals(Config.shelfHeight,shelf.getShelf()[0].length);
        for(int i=0; i<Config.shelfLength; i++){
            for(int j=0; j<Config.shelfHeight; j++){
                assertEquals(TilesEnum.EMPTY,shelf.getShelf()[i][j].getTile());
            }
        }
    }

    /**
     * Tests if the constructor that copies a shelf in another does it correctly.
     */
    @Test
    void rightShelfCopy() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        Tile tile1 = new Tile(TilesEnum.CATS,0);
        Tile tile2 = new Tile(TilesEnum.PLANTS,0);
        shelf.insertTiles(tile1,0,0);
        shelf.insertTiles(tile2,4,4);
        Shelf shelfClone = new Shelf(shelf);
        for(int i=0; i<Config.shelfLength; i++){
            for(int j=0; j<Config.shelfHeight; j++){
                assertEquals(shelf.getShelf()[i][j].getTile(),shelfClone.getShelf()[i][j].getTile());
            }
        }
        assertEquals(TilesEnum.CATS,shelfClone.getTileShelf(0,0).getTile());
        assertEquals(TilesEnum.PLANTS,shelfClone.getTileShelf(4,4).getTile());

    }

    /**
     * Tests if getters and setters of shelf works correctly.
     */
    @Test
    void getAndSetShelfTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,0);
        shelf.insertTiles(new Tile(TilesEnum.PLANTS,0),4,4);
        Tile[][] shelfGet = shelf.getShelf();
        for(int i=0; i<Config.shelfLength; i++){
            for(int j=0; j<Config.shelfHeight; j++){
                assertEquals(shelf.getShelf()[i][j],shelfGet[i][j]);
            }
        }
        shelfGet[3][3] = new Tile(TilesEnum.GAMES,0);
        shelf.setShelf(shelfGet);
        for(int i=0; i<Config.shelfLength; i++){
            for(int j=0; j<Config.shelfHeight; j++){
                assertEquals(shelf.getShelf()[i][j],shelfGet[i][j]);
            }
        }
    }

    /**
     * Tests if the setShelf() method throws an IllegalArgumentException when
     * given an array of tiles with incorrect dimensions.
     */
    @Test
    public void testSetShelfInvalidInput() {
        Shelf shelf = new Shelf();
        //Both rows and columns wrong
        final Tile[][] invalidShelf0 = new Tile[6][5];
        assertThrows(IllegalArgumentException.class, () -> shelf.setShelf(invalidShelf0));

        //Only columns wrong
        final Tile[][] invalidShelf1 = new Tile[6][6];
        assertThrows(IllegalArgumentException.class, () -> shelf.setShelf(invalidShelf1));

        //Only rows wrong
        final Tile[][] invalidShelf2 = new Tile[5][7];
        assertThrows(IllegalArgumentException.class, () -> shelf.setShelf(invalidShelf2));
    }

    /**
     * Tests if the get tile method returns the correct tile.
     */
    @Test
    void getTileShelfTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        Tile tile = new Tile(TilesEnum.CATS,0);
        shelf.insertTiles(tile,0,0);
        assertEquals(tile,shelf.getTileShelf(0,0));
    }

    /**
     * Tests if the getTileShelf method throws an IndexOutOfBoundsException
     * when the row argument is too big.
     */
    @Test
    void getTileShelfExceptionWhenRowIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(0, 6));
    }

    /**
     * Tests if the getTileShelf method throws an IndexOutOfBoundsException
     * when the column argument is too big.
     */
    @Test
    void getTileShelfExceptionWhenColumnIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(5, 0));
    }

    /**
     * Tests the correct insertion of a tile in the shelf,
     * also test if the rest of the shelf remains like iit was before the insertion.
     */

    @Test
    void insertTilesTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        Tile tile = new Tile(TilesEnum.CATS,0);
        shelf.insertTiles(tile,0,0);
        assertEquals(tile,shelf.getTileShelf(0,0));
        for(int i=0; i<Config.shelfLength; i++) {
            for (int j = 0; j < Config.shelfHeight; j++) {
                if (i != 0 || j != 0){
                    assertEquals(TilesEnum.EMPTY,shelf.getShelf()[i][j].getTile());
                }
            }
        }
    }

    /**
     * Tests if the insertTiles method throws an IndexOutOfBoundsException
     * when the row argument is too big.
     */
    @Test
    void insertExceptionWhenRowIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,6));
    }

    /**
     * Tests if the insertTiles method throws an IndexOutOfBoundsException
     * when the column argument is too big.
     */
    @Test
    void insertExceptionWhenColumnIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.CATS,0),5,0));
    }
    /**
     * Tests if the insertTiles method throws an EmptyTilesException when try to insert empty.
     */
    @Test
    void insertExceptionWhenInsertEmpty() {
        Shelf shelf = new Shelf();
        assertThrows(EmptyTilesException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0),0,0));
    }

    /**
     * Tests if the insertTiles method throws an UnusedTilesException when try to insert unused.
     */
    @Test
    void insertExceptionWhenInsertUnused() {
        Shelf shelf = new Shelf();
        assertThrows(UnusedTilesException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.UNUSED,0),0,0));
    }

    /**
     * Tests if the Tile functions are well integrated with Shelf class by calling them from the shelf object.
     */
    @Test
    void correctIntegrationWithTile() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        Tile tile = new Tile(TilesEnum.CATS,0);
        shelf.insertTiles(tile,0,0);
        assertEquals(tile.getTile(),shelf.getShelf()[0][0].getTile());
        assertEquals(tile.getImageNumber(),shelf.getShelf()[0][0].getImageNumber());
    }

    @Test
    void getSelectableColumnsTest() throws EmptyTilesException, UnusedTilesException {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(GAMES,0),0,5);
        shelf.insertTiles(new Tile(GAMES,0),1,5);
        shelf.insertTiles(new Tile(GAMES,0),2,5);
        shelf.insertTiles(new Tile(GAMES,0),3,5);
        shelf.insertTiles(new Tile(GAMES,0),4,5);
        shelf.insertTiles(new Tile(GAMES,0),0,4);
        shelf.insertTiles(new Tile(GAMES,0),1,4);
        shelf.insertTiles(new Tile(GAMES,0),2,4);
        shelf.insertTiles(new Tile(GAMES,0),4,4);
        shelf.insertTiles(new Tile(GAMES,0),2,3);
        shelf.insertTiles(new Tile(GAMES,0),4,3);
        shelf.insertTiles(new Tile(GAMES,0),2,2);
        shelf.insertTiles(new Tile(GAMES,0),4,2);
        shelf.insertTiles(new Tile(GAMES,0),4,1);
        shelf.insertTiles(new Tile(GAMES,0),3,4);
        ArrayList<Integer> selectableColumns = shelf.getSelectableColumns(5);
        System.out.println(selectableColumns.toString());
    }
}