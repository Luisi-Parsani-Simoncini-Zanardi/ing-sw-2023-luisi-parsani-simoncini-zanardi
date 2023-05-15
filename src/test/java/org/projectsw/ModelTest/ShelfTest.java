package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Exceptions.UnselectableColumnException;
import org.projectsw.Util.Config;
import org.projectsw.Exceptions.MaxTemporaryTilesExceededException;
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
        
        Shelf shelf2 = new Shelf();
        assertEquals(6,shelf2.getShelf().length);
        assertEquals(5,shelf2.getShelf()[0].length);
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(TilesEnum.EMPTY,shelf2.getShelf()[i][j].getTile());
            }
        }
        assertNull(shelf2.getSelectedColumn());
        assertNull(shelf2.getSelectableColumns());
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
     * Tests if the getTileShelf method throws correctly an IndexOutOfBoundsException
     * when the row or the column argument is too big.
     */
    @Test
    void getTileShelfExceptionWhenInputIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTileShelf(6, 0));
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
     * when the row or the column argument is too big.
     */
    @Test
    void insertExceptionWhenInputIsTooBig() {
        Shelf shelf = new Shelf();
        assertThrows(IndexOutOfBoundsException.class, () ->
                shelf.insertTiles(new Tile(CATS,0),6,0));
        assertThrows(IndexOutOfBoundsException.class, () ->
                shelf.insertTiles(new Tile(CATS,0),0,5));
    }

    /**
     * Tests if the insertTiles method throws an IllegalArgumentException when try to insert an unusable tile, EMPTY or UNUSED.
     */
    @Test
    void insertExceptionWhenInsertUnusable() {
        Shelf shelf = new Shelf();
        assertThrows(IllegalArgumentException.class, () ->
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0),0,0));
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

    /**
     * Tests if the setter, getter and cleaner of selectableColumns ArrayList works correctly.
     */
    @Test
    void setGetAndCleanSelectableColumnsTest() {
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
     * Tests if the setter, getter and cleaner of selectedColumn attribute works correctly.
     */
    @Test
    void setGetAndCleanSelectedColumnTest() throws UnselectableColumnException {
        Shelf shelf = new Shelf();
        Integer selectedColumn = 0;
        assertNull(shelf.getSelectedColumn());
        shelf.setSelectedColumn(selectedColumn);
        assertEquals(0,shelf.getSelectedColumn());
        shelf.cleanSelectedColumn();
        assertNull(shelf.getSelectedColumn());
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

    /**
     * Try for every possible length (form 0 to Config.maximumTilesPickable) of temporaryTiles to fill the board from [0][0], to [0][4], to [5][4] and
     * calling updateSelectableColumns after every insertion, checking if the result is equal to the expected (for both length and elements contained).
     * If the description isn't enough clear try to run the test with the commented lines and check what the function prints.
     */
    @Test
    void updateSelectableColumnsTest() throws MaxTemporaryTilesExceededException, UpdatingOnWrongPlayerException {
        Player player = new Player("Davide",0);
        Shelf shelf = player.getShelf();
        for(int numberOfTiles = 0; numberOfTiles < Config.maximumTilesPickable + 1; numberOfTiles++){
            for(int n = 0; n < numberOfTiles; n++){
                player.addTemporaryTile(new Tile(CATS,0));
            }
            //System.out.println("Testing for number of tiles = "+numberOfTiles+"\nConfirmed number of tiles = "+player.getTemporaryTiles().size());
            for(int i=0;i<Config.shelfHeight;i++){
                for(int j=0;j<Config.shelfLength;j++){
                    shelf.insertTiles(new Tile(CATS,0),i,j);
                    shelf.updateSelectableColumns(player);
                    //System.out.println("Insert in ["+i+"]["+j+"]");
                    ArrayList<Integer> nowSelectable = shelf.getSelectableColumns();
                    if(i > (Config.shelfHeight - numberOfTiles)){
                        //System.out.println("b1,vector: "+nowSelectable.toString());
                        assertEquals(0,nowSelectable.size());
                    } else if(i == (Config.shelfHeight - numberOfTiles)){
                        //System.out.println("b2,vector: "+nowSelectable.toString());
                        assertEquals((Config.shelfLength - 1 - j),nowSelectable.size());
                        for(int h=j+1;h<Config.shelfLength;h++){
                            assertTrue(nowSelectable.contains(h));
                        }
                    } else if (i == (Config.shelfHeight - 1)){
                        //System.out.println("b3,vector: "+nowSelectable.toString());
                        assertEquals((Config.shelfLength - 1 - j),nowSelectable.size());
                        for(int h=j+1;h<Config.shelfLength;h++){
                            assertTrue(nowSelectable.contains(h));
                        }
                    } else {
                        //System.out.println("b4,vector: "+nowSelectable.toString());
                        assertEquals(5,nowSelectable.size());
                        for(int h=0;h<Config.shelfLength;h++){
                            assertTrue(nowSelectable.contains(h));
                        }
                    }
                    //System.out.println("Correct");
                }
            }
            shelf = new Shelf();
            player.clearTemporaryTiles();
        }
    }
}