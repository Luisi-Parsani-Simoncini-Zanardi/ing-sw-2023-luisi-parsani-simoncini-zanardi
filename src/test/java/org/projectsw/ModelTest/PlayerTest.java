package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.*;
import org.projectsw.Model.Enums.TilesEnum;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Tests if the constructor of player works correctly.
     */
    @Test
    void integrityTestPlayer(){
        //Checking the first parameters: nickname, position, points, shelf
        Player player = new Player("Davide",0);
        assertEquals("Davide",player.getNickname());
        assertEquals(0,player.getPosition());
        assertEquals(0,player.getPoints());
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(shelf.getTileShelf(i,j).getTile(),player.getShelf().getTileShelf(i,j).getTile());
            }
        }
        //Checking if the personal goal given to the player actually matches with one of the 12 possible goals
        PersonalGoal.cleanUsedCodes();
        boolean personalGoalFound = false;
        for(int p=0;p<12;p++){
            boolean different = false;
            PersonalGoal personalGoal = new PersonalGoal(p);
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    if(!personalGoal.getPersonalGoal()[i][j].equals(player.getPersonalGoal().getPersonalGoal()[i][j])){
                        different = true;
                    }
                }
            }
            if(!different) personalGoalFound = true;
        }
        assertTrue(personalGoalFound);
        assertFalse(player.isCommonGoalRedeemed(0));
        assertFalse(player.isCommonGoalRedeemed(1));
        assertTrue(player.getTemporaryTiles().isEmpty());
    }

    /**
     * Tests if the getter of position attribute works correctly.
     */
    @Test
    void getPositionTest() {
        Player player = new Player("Lorenzo", 2);
        assertEquals(2,player.getPosition());
    }

    /**
     * Tests if the getter of nickname attribute works correctly.
     */
    @Test
    void getNicknameTest() {
        Player player = new Player("Paolo", 2);
        assertEquals("Paolo",player.getNickname());
    }

    /**
     * Tests if the getter and setter of points work correctly.
     */
    @Test
    void getAndSetPointsTest() {
        Player player = new Player("Davide", 3);
        player.setPoints(57);
        assertEquals(57, player.getPoints());
    }

    /**
     * Tests if the getter and setter of shelf work correctly.
     */
    @Test
    void getAndSetShelfTest() {
        Shelf shelf = new Shelf();
        shelf.insertTiles(new Tile(TilesEnum.CATS,0),1,2);
        Player player = new Player("Attila", 4);
        player.setShelf(shelf);
        assertEquals(shelf, player.getShelf());
    }

    /**
     * Tests if the getter of personalGoal work correctly
     */
    @Test
    void getAndSetPersonalGoalTest(){
        PersonalGoal personalGoal = new PersonalGoal(0);
        PersonalGoal.cleanUsedCodes();
        Player player = new Player("Riccardo", 3);
        player.setPersonalGoal(personalGoal);
        assertEquals(personalGoal, player.getPersonalGoal());
    }

    /**
     * Tests if the getter of temporaryTiles works correctly.
     */
    @Test
    void getAndSetTemporaryTilesTest() throws MaxTemporaryTilesExceededException {
        ArrayList<Tile> list = new ArrayList<>();
        list.add(new Tile(TilesEnum.CATS,0));
        list.add(new Tile(TilesEnum.BOOKS,0));
        Player player = new Player("Riccardo", 3);
        player.setTemporaryTiles(list);
        assertEquals(list, player.getTemporaryTiles());
    }

    @Test
    void setTooBigTemporaryTilesTest(){
        ArrayList<Tile> list = new ArrayList<>();
        list.add(new Tile(TilesEnum.CATS,0));
        list.add(new Tile(TilesEnum.BOOKS,0));
        list.add(new Tile(TilesEnum.CATS,0));
        list.add(new Tile(TilesEnum.BOOKS,0));
        Player player = new Player("Riccardo", 3);
        assertThrows(MaxTemporaryTilesExceededException.class,() -> player.setTemporaryTiles(list));
    }

    /**
     * Tests if the getter and setter of temporaryTiles work correctly
     */
    @Test
    void getAndSetCommonGoalsRedeemedTest(){
        Player player = new Player("Riccardo", 3);
        player.setCommonGoalRedeemed(0, true);
        assertTrue(player.isCommonGoalRedeemed(0));
        player.setCommonGoalRedeemed(1, true);
        assertTrue(player.isCommonGoalRedeemed(1));
    }

    /**
     * Tests if the method actually adds a tiles in the array temporaryTiles;
     * also tests if the getter method works correctly.
     */
    @Test
    void addValidTileTest() throws MaxTemporaryTilesExceededException {
        Player player = new Player("Davide",0);
        Tile tile0 = new Tile(TilesEnum.CATS,0);
        Tile tile1 = new Tile(TilesEnum.BOOKS,0);
        Tile tile2 = new Tile(TilesEnum.FRAMES,0);
        assertEquals(0,player.getTemporaryTiles().size());
        player.addTemporaryTile(tile0);
        assertEquals(1,player.getTemporaryTiles().size());
        player.addTemporaryTile(tile1);
        assertEquals(2,player.getTemporaryTiles().size());
        player.addTemporaryTile(tile2);
        assertEquals(3,player.getTemporaryTiles().size());
        assertEquals(tile0,player.getTemporaryTiles().get(0));
        assertEquals(tile1,player.getTemporaryTiles().get(1));
        assertEquals(tile2,player.getTemporaryTiles().get(2));
    }

    /**
     * Tests if the method addTiles throws correctly the IllegalArgumentException then adding empty or unused tiles to the array.
     */
    @Test
    void addTileExceptionWhenTileIsEmptyOrUnused() {
        Player player = new Player("Davide",0);
        assertThrows(IllegalArgumentException.class, () ->
                player.addTemporaryTile(new Tile(TilesEnum.EMPTY,0)));
        assertThrows(IllegalArgumentException.class, () ->
                player.addTemporaryTile(new Tile(TilesEnum.UNUSED,0)));
    }

    /**
     * Tests if the method addTiles throws correctly the MaxTemporaryTilesExceededException when the array already has 3 elements
     */
    @Test
    void addTileExceptionWhenArrayIsFull() throws MaxTemporaryTilesExceededException {
        Player player = new Player("Davide",0);
        player.addTemporaryTile(new Tile(TilesEnum.CATS,0));
        player.addTemporaryTile(new Tile(TilesEnum.BOOKS,0));
        player.addTemporaryTile(new Tile(TilesEnum.GAMES,0));
        assertThrows(MaxTemporaryTilesExceededException.class, () ->
                player.addTemporaryTile(new Tile(TilesEnum.BOOKS,0)));
    }


    /**
     * Tests if the selectTile methods removes correctly the elements form the list, leaving unchanged the
     * other tiles, reducing the size of the list and returning the correct element.
     */
    @Test
    void selectTileTest() throws MaxTemporaryTilesExceededException {
        Player player = new Player("Davide",0);
        Tile tile0 = new Tile(TilesEnum.CATS,0);
        Tile tile1 = new Tile(TilesEnum.BOOKS,0);
        Tile tile2 = new Tile(TilesEnum.FRAMES,0);
        assertEquals(0,player.getTemporaryTiles().size());

        //check if elements remains in the correct order and if the size reduces correctly
        //after removing from the head of the list and from the end. Then it removes the last one
        player.addTemporaryTile(tile0);
        player.addTemporaryTile(tile1);
        player.addTemporaryTile(tile2);
        assertEquals(3,player.getTemporaryTiles().size());
        assertEquals(tile0,player.selectTemporaryTile(0));
        assertEquals(2,player.getTemporaryTiles().size());
        assertEquals(tile1,player.getTemporaryTiles().get(0));
        assertEquals(tile2,player.selectTemporaryTile(1));
        assertEquals(1,player.getTemporaryTiles().size());
        assertEquals(tile1,player.getTemporaryTiles().get(0));
        assertEquals(tile1,player.selectTemporaryTile(0));
        assertEquals(0,player.getTemporaryTiles().size());

        //check if elements remains in the correct order after removing from the middle
        player.addTemporaryTile(tile0);
        player.addTemporaryTile(tile1);
        player.addTemporaryTile(tile2);
        assertEquals(tile1,player.selectTemporaryTile(1));
        assertEquals(tile2,player.getTemporaryTiles().get(1));
        assertEquals(tile0,player.getTemporaryTiles().get(0));
    }

    @Test
    void selectTilesExceptionWrongIndex() {
        Player player = new Player("Davide",0);
        assertThrows(IndexOutOfBoundsException.class, () ->
                player.selectTemporaryTile(-1));
        assertThrows(IndexOutOfBoundsException.class, () ->
                player.selectTemporaryTile(3));
    }

    /**
     * Tests if the removeTemporaryTile and clearTemporaryTiles works correctly.
     */
    @Test
    void removeAndCleanTemporaryTiles() throws MaxTemporaryTilesExceededException {
        Player player = new Player("Davide", 0);
        Tile tile0 = new Tile(TilesEnum.CATS, 0);
        Tile tile1 = new Tile(TilesEnum.BOOKS, 0);
        Tile tile2 = new Tile(TilesEnum.FRAMES, 0);
        assertEquals(0, player.getTemporaryTiles().size());
        player.addTemporaryTile(tile0);
        player.addTemporaryTile(tile1);
        player.addTemporaryTile(tile2);
        assertEquals(3, player.getTemporaryTiles().size());
        player.removeTemporaryTile(tile1);
        assertEquals(2, player.getTemporaryTiles().size());
        assertEquals(tile0,player.getTemporaryTiles().get(0));
        assertEquals(tile2,player.getTemporaryTiles().get(1));
        player.clearTemporaryTiles();
        assertEquals(0, player.getTemporaryTiles().size());
    }

    /**
     * Tests if the constructor of players always create a player with a different
     * personal goal from the other players already created.
     */
    @Test
    void tryPersonalGoal() {
        Player[] players = new Player[12];
        PersonalGoal[] personalGoals = new PersonalGoal[12];
        int matches = 0;
        for (int i = 0; i < 12; i++) {
            players[i] = new Player("Davide", 0);
            personalGoals[i] = players[i].getPersonalGoal();
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (personalGoals[i].equals(personalGoals[j])) matches++;
            }
        }
        //the matches have to be 12 because every personal goal matches with itself
        assertEquals(12, matches);
    }
}