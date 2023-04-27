package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Config;
import org.projectsw.Model.*;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.projectsw.Model.TilesEnum.*;

class ShelfTest {

    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Tests if constructors Shelf() and Shelf(Player) actually creates two correct shelf.
     */
    @Test
    void integrityShelfTest(){
        Shelf shelf1 = new Shelf();
        assertEquals(6,shelf1.getShelf().length);
        assertEquals(5,shelf1.getShelf()[0].length);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(TilesEnum.EMPTY,shelf1.getShelf()[i][j].getTile());
            }
        }
        assertNull(shelf1.getSelectedColumn());
        assertNull(shelf1.getSelectableColumns());
        assertNull(shelf1.getPlayer());

        Player player = new Player("Davide",0);
        Shelf shelf2 = new Shelf(player);
        assertEquals(6,shelf2.getShelf().length);
        assertEquals(5,shelf2.getShelf()[0].length);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(TilesEnum.EMPTY,shelf2.getShelf()[i][j].getTile());
            }
        }
        assertNull(shelf2.getSelectedColumn());
        assertNull(shelf2.getSelectableColumns());
        assertEquals(player,shelf2.getPlayer());
    }

    /**
     * Tests if the constructor that copies a shelf in another does it correctly.
     */
    @Test
    void rightShelfCopy() {
        Shelf shelf = new Shelf();
        Tile tile1 = new Tile(CATS,0);
        Tile tile2 = new Tile(TilesEnum.PLANTS,0);
        shelf.insertTiles(tile1,0,0);
        shelf.insertTiles(tile2,4,4);
        Shelf shelfClone = new Shelf(shelf);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getShelf()[i][j].getTile(),shelfClone.getShelf()[i][j].getTile());
            }
        }
        assertEquals(CATS,shelfClone.getTileShelf(0,0).getTile());
        assertEquals(TilesEnum.PLANTS,shelfClone.getTileShelf(4,4).getTile());

    }

    /**
     * Tests if getters and setters of shelf works correctly.
     */
    @Test
    void getAndSetShelfTest() {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(CATS,0),0,0);
        shelf.insertTiles(new Tile(TilesEnum.PLANTS,0),4,4);
        Tile[][] shelfGetted = shelf.getShelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getShelf()[i][j],shelfGetted[i][j]);
            }
        }
        shelfGetted[3][3] = new Tile(TilesEnum.GAMES,0);
        shelf.setShelf(shelfGetted);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getShelf()[i][j],shelfGetted[i][j]);
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
        final Tile[][] invalidShelf0 = new Tile[5][6];
        assertThrows(IllegalArgumentException.class, () -> shelf.setShelf(invalidShelf0));

        //Only columns wrong
        final Tile[][] invalidShelf1 = new Tile[6][6];
        assertThrows(IllegalArgumentException.class, () -> shelf.setShelf(invalidShelf1));

        //Only rows wrong
        final Tile[][] invalidShelf2 = new Tile[7][5];
        assertThrows(IllegalArgumentException.class, () -> shelf.setShelf(invalidShelf2));
    }

    /**
     * Tests if the get tile method returns the correct tile.
     */
    @Test
    void getTileShelfTest() {
        Shelf shelf = new Shelf();
        Tile tile = new Tile(CATS,0);
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
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(6, 0));
    }

    /**
     * Tests if the getTileShelf method throws an IndexOutOfBoundsException
     * when the column argument is too big.
     */
    @Test
    void getTileShelfExceptionWhenColumnIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(0, 5));
    }

    /**
     * Tests the correct insertion of a tile in the shelf,
     * also test if the rest of the shelf remains like iit was before the insertion.
     */

    @Test
    void insertTilesTest() {
        Shelf shelf = new Shelf();
        Tile tile = new Tile(CATS,0);
        shelf.insertTiles(tile,0,0);
        assertEquals(tile,shelf.getTileShelf(0,0));
        for(int i=0; i<6; i++) {
            for (int j = 0; j < 5; j++) {
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
                shelf.insertTiles(new Tile(CATS,0),6,0));
    }

    /**
     * Tests if the insertTiles method throws an IndexOutOfBoundsException
     * when the column argument is too big.
     */
    @Test
    void insertExceptionWhenColumnIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () ->
                shelf.insertTiles(new Tile(CATS,0),0,5));
    }
    /**
     * Tests if the insertTiles method throws an IllegalArgumentException when try to insert empty.
     */
    @Test
    void insertExceptionWhenInsertEmpty() {
        Shelf shelf = new Shelf();
        assertThrows(IllegalArgumentException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0),0,0));
    }

    /**
     * Tests if the insertTiles method throws an IllegalArgumentException when try to insert unused.
     */
    @Test
    void insertExceptionWhenInsertUnused() {
        Shelf shelf = new Shelf();
        assertThrows(IllegalArgumentException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.UNUSED,0),0,0));
    }

    /**
     * Tests if the Tile functions are well integrated with Shelf class by calling them from the shelf object.
     */
    @Test
    void correctIntegrationWithTile() {
        Shelf shelf = new Shelf();
        Tile tile = new Tile(CATS,0);
        shelf.insertTiles(tile,0,0);
        assertEquals(tile.getTile(),shelf.getShelf()[0][0].getTile());
        assertEquals(tile.getImageNumber(),shelf.getShelf()[0][0].getImageNumber());
    }

    @Test
    void setAndGetSelectableColumnsTest() {
        Shelf shelf = new Shelf();
        ArrayList<Integer> selectableColumns = new ArrayList<>();
        selectableColumns.add(0);
        selectableColumns.add(1);
        selectableColumns.add(2);
        shelf.setSelectableColumns(selectableColumns);
        assertEquals(3,shelf.getSelectableColumns().size());
        assertEquals(0,shelf.getSelectableColumns().get(0));
        assertEquals(1,shelf.getSelectableColumns().get(1));
        assertEquals(2,shelf.getSelectableColumns().get(2));
        shelf.cleanSelectableColumns();
        assertEquals(0,shelf.getSelectableColumns().size());
    }


    /**
     * Tests if maxFreeColumnSpace always returns the exact expected value of maxFreeColumns space. Tested by filling the
     * shelf from the bottom ad calling maxFreeColumnSpace after every insertion.
     */
    @Test
    void maxFreeColumnsSpaceTest(){
        Shelf shelf = new Shelf();
        for(int i = 0; i< Config.shelfHeight; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                shelf.getShelf()[i][j] = new Tile(CATS,0);
                if(i>=(Config.shelfHeight-Config.maximumTilesPickable)){
                    if(j == Config.shelfLength-1) assertEquals((Config.shelfHeight)-i-1,shelf.maxFreeColumnSpace());
                    else assertEquals((Config.shelfHeight)-i,shelf.maxFreeColumnSpace());
                } else {
                    assertEquals(3, shelf.maxFreeColumnSpace());
                }

            }
        }
    }
}