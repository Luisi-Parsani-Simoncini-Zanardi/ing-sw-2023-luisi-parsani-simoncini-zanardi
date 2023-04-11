package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.MaximumTilesException;
import org.projectsw.Exceptions.UnusedTilesException;
import org.projectsw.Model.*;
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
    void getAndSetTemporaryTilesTest(){
        ArrayList<Tile> list = new ArrayList<>();
        list.add(new Tile(TilesEnum.CATS,0));
        list.add(new Tile(TilesEnum.BOOKS,0));
        Player player = new Player("Riccardo", 3);
        player.setTemporaryTiles(list);
        assertEquals(list, player.getTemporaryTiles());
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

    /*
     * Tests if the getter and setter of personaGoalRedeemed attribute works correctly

    @Test
    void setGetPersonalGoalRedeemed(){
        Player player = new Player("Pietro",3);
        player.setPersonalGoalRedeemed(true);                   attribute removed
        assertTrue(player.isPersonalGoalRedeemed());
        player.setPersonalGoalRedeemed(false);
        assertFalse(player.isPersonalGoalRedeemed());
    }     */

    /**
     * Tests if the method actually adds a tiles in the array temporaryTiles;
     * also tests if the getter method works correctly.
     */
    @Test
    void addValidTileTest() throws EmptyTilesException, UnusedTilesException, MaximumTilesException {
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
     * Tests if the method addTiles throws correctly the EmptyTilesException then adding empty tiles to the array.
     */
    @Test
    void addTileExceptionWhenTileIsEmpty() {
        Player player = new Player("Davide",0);
        assertThrows(EmptyTilesException.class, () ->
                player.addTemporaryTile(new Tile(TilesEnum.EMPTY,0)));
    }

    /**
     * Tests if the method addTiles throws correctly the UnusedTilesException then adding unused tiles to the array.
     */
    @Test
    void addTileExceptionWhenTileIsUnused() {
        Player player = new Player("Davide",0);
        assertThrows(UnusedTilesException.class, () ->
                player.addTemporaryTile(new Tile(TilesEnum.UNUSED,0)));
    }

    /**
     * Tests if the method addTiles throws correctly the MaximumTilesException when the array already has 3 elements
     */
    @Test
    void addTileExceptionWhenArrayIsFull() throws EmptyTilesException, UnusedTilesException, MaximumTilesException{
        Player player = new Player("Davide",0);
        player.addTemporaryTile(new Tile(TilesEnum.CATS,0));
        player.addTemporaryTile(new Tile(TilesEnum.BOOKS,0));
        player.addTemporaryTile(new Tile(TilesEnum.GAMES,0));
        assertThrows(MaximumTilesException.class, () ->
                player.addTemporaryTile(new Tile(TilesEnum.UNUSED,0)));
    }


    /**
     * Tests if the selectTile methods removes correctly the elements form the list, leaving unchanged the
     * other tiles, reducing the size of the list and returning the correct element.
     */
    @Test
    void selectTileTest() throws EmptyTilesException, UnusedTilesException, MaximumTilesException {
        Player player = new Player("Davide",0);
        Tile tile0 = new Tile(TilesEnum.CATS,0);
        Tile tile1 = new Tile(TilesEnum.BOOKS,0);
        Tile tile2 = new Tile(TilesEnum.FRAMES,0);
        assertEquals(0,player.getTemporaryTiles().size());
        player.addTemporaryTile(tile0);
        player.addTemporaryTile(tile1);
        player.addTemporaryTile(tile2);
        //check if elements remains in the correct order and if the size reduces correctly
        //after removing from the head of the list and from the end. Then it removes the last one
        assertEquals(3,player.getTemporaryTiles().size());
        assertEquals(tile0,player.selectTile(0));
        assertEquals(2,player.getTemporaryTiles().size());
        assertEquals(tile1,player.getTemporaryTiles().get(0));
        assertEquals(tile2,player.selectTile(1));
        assertEquals(1,player.getTemporaryTiles().size());
        assertEquals(tile1,player.getTemporaryTiles().get(0));
        assertEquals(tile1,player.selectTile(0));
        assertEquals(0,player.getTemporaryTiles().size());
        player.addTemporaryTile(tile0);
        player.addTemporaryTile(tile1);
        player.addTemporaryTile(tile2);
        //check if elements remains in the correct order after removing from the middle
        assertEquals(tile1,player.selectTile(1));
        assertEquals(tile2,player.getTemporaryTiles().get(1));
        assertEquals(tile0,player.getTemporaryTiles().get(0));
    }

    @Test
    void selectTilesExceptionWhenIndexIsNegative() {
        Player player = new Player("Davide",0);
        assertThrows(IndexOutOfBoundsException.class, () ->
                player.selectTile(-1));
    }

    @Test
    void selectTilesExceptionWhenIndexIsTooBig() {
        Player player = new Player("Davide",0);
        assertThrows(IndexOutOfBoundsException.class, () ->
                player.selectTile(3));
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