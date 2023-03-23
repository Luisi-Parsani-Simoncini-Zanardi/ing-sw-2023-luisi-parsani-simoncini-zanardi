package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    //test that the method getPosition returns the correct position of the player
    @Test
    void getPosition() {
        Player player = new Player("Lorenzo", 2);
        assertEquals(2,player.getPosition());
    }

    //test that the method getNickname returns the correct nickname of the player
    @Test
    void getNickname() {
        Player player = new Player("Paolo", 2);
        assertEquals("Paolo",player.getNickname());
    }

    //test that the method setPoints actually sets a new value for the attribute points of the object player
    @Test
    void setPoints() {
        Player player = new Player("Luca", 2);
        player.setPoints(999);
        assertEquals(999, player.getPoints());
    }

    //test that the method getPoints returns the correct value of the attribute points
    @Test
    void getPoints() {
        Player player = new Player("Davide", 3);
        player.setPoints(57);
        assertEquals(57, player.getPoints());
    }

    //test that the method actually set a shelf for the player
    @Test
    void setShelf() {
        Shelf shelf1 = new Shelf();
        Player player = new Player("Shelfie", 1);
        player.setShelf(shelf1);
        assertEquals(shelf1, player.getShelf());
    }

    //test that the method returns the shelf of the player correctly
    @Test
    void getShelf() {
        Shelf shelf1 = new Shelf();
        Player player = new Player("Attila", 4);
        player.setShelf(shelf1);
        assertEquals(shelf1, player.getShelf());
    }

    //test that the method sets correctly every possible personalGoal
    @Test
    void setPersonalGoal(){

        Player player = new Player("Morfeo", 1);
        PersonalGoal.cleanUsedCodes();

        PersonalGoal personal1 = new PersonalGoal(0);
        player.setPersonalGoal(personal1);
        assertEquals(personal1, player.getPersonalGoal());

        PersonalGoal personal2 = new PersonalGoal(1);
        player.setPersonalGoal(personal2);
        assertEquals(personal2, player.getPersonalGoal());

        PersonalGoal personal3 = new PersonalGoal(2);
        player.setPersonalGoal(personal3);
        assertEquals(personal3, player.getPersonalGoal());

        PersonalGoal personal4 = new PersonalGoal(3);
        player.setPersonalGoal(personal4);
        assertEquals(personal4, player.getPersonalGoal());

        PersonalGoal personal5 = new PersonalGoal(4);
        player.setPersonalGoal(personal5);
        assertEquals(personal5, player.getPersonalGoal());

        PersonalGoal personal6 = new PersonalGoal(5);
        player.setPersonalGoal(personal6);
        assertEquals(personal6, player.getPersonalGoal());

        PersonalGoal personal7 = new PersonalGoal(6);
        player.setPersonalGoal(personal7);
        assertEquals(personal7, player.getPersonalGoal());

        PersonalGoal personal8 = new PersonalGoal(7);
        player.setPersonalGoal(personal8);
        assertEquals(personal8, player.getPersonalGoal());

        PersonalGoal personal9 = new PersonalGoal(8);
        player.setPersonalGoal(personal9);
        assertEquals(personal9, player.getPersonalGoal());

        PersonalGoal personal10 = new PersonalGoal(9);
        player.setPersonalGoal(personal10);
        assertEquals(personal10, player.getPersonalGoal());

        PersonalGoal personal11 = new PersonalGoal(10);
        player.setPersonalGoal(personal11);
        assertEquals(personal11, player.getPersonalGoal());

        PersonalGoal personal12 = new PersonalGoal(11);
        player.setPersonalGoal(personal12);
        assertEquals(personal12, player.getPersonalGoal());

        PersonalGoal.cleanUsedCodes();
    }

    //test that the method returns the correct value of personal goal
    @Test
    void getPersonalGoal(){

        PersonalGoal personal = new PersonalGoal(4);
        Player player = new Player("Riccardo", 3);
        PersonalGoal.cleanUsedCodes();
        player.setPersonalGoal(personal);
        assertEquals(personal, player.getPersonalGoal());
    }

    //test that the method actually adds a tile in the array temporaryTiles
    //test that temporaryTiles can't have more than 3 elements
    //test that temporaryTiles can't contain EMPTY or UNUSED tile type
    @Test
    void addTile() {
        Player player = new Player("Asia", 3);

        try{
            player.addTile(new Tile(TilesEnum.GAMES, 0));
            assertEquals(1,player.getTiles().size());

        }catch(Exception e){
            return;
        }
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.GAMES));

        try{
            player.addTile(new Tile(TilesEnum.CATS, 0));
            assertEquals(2,player.getTiles().size());
        }catch(Exception e){
            return;
        }
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.GAMES));
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.CATS));
        try{
            player.addTile(new Tile(TilesEnum.PLANTS, 0));
            assertEquals(3,player.getTiles().size());
        }catch(Exception e){
            return;
        }
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.PLANTS));
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.GAMES));
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.CATS));

        try{
            player.addTile(new Tile(TilesEnum.BOOKS, 0));
            assertEquals(3,player.getTiles().size());
        }catch(Exception e){
            assertEquals("Maximum number of tiles reached", e.getMessage());
        }
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.PLANTS));
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.GAMES));
        assertTrue(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.CATS));
        assertFalse(player.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.BOOKS));

        Player player2 = new Player("Roberto", 1);

        try{
            player2.addTile(new Tile(TilesEnum.UNUSED, 0));
        }catch(Exception e){
            assertEquals("You can't add an UNUSED tile", e.getMessage());
        }

        try{
            player2.addTile(new Tile(TilesEnum.EMPTY, 0));
        }catch(Exception e){
            assertEquals("You can't add an EMPTY tile", e.getMessage());
        }
    }

    //test that the method getTiles actually returns the right temporaryTile array
    @Test
    void getTiles() {

        Player test = new Player("Pietro",3);
        assertEquals(0,test.getTiles().size());
        try{
            test.addTile(new Tile(TilesEnum.BOOKS, 0));
        }catch(Exception e){
        }
        assertEquals(1,test.getTiles().size());
        assertTrue(test.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.BOOKS));

        try{
            test.addTile(new Tile(TilesEnum.CATS, 0));
        }catch(Exception e){
        }
        assertEquals(2,test.getTiles().size());
        assertTrue(test.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.BOOKS));
        assertTrue(test.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.CATS));

        try{
            test.addTile(new Tile(TilesEnum.FRAMES, 0));
        }catch(Exception e){
        }
        assertEquals(3,test.getTiles().size());
        assertTrue(test.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.BOOKS));
        assertTrue(test.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.CATS));
        assertTrue(test.getTiles().stream().anyMatch(tile -> tile.getTile() == TilesEnum.FRAMES));
    }

    //test that the method edit the size of the array and returns the right value
    @Test
    void selectTile() {
        Player test = new Player("Pietro",3);
        try {
            test.addTile(new Tile(TilesEnum.BOOKS, 0));
            test.addTile(new Tile(TilesEnum.CATS, 0));
            test.addTile(new Tile(TilesEnum.FRAMES, 0));
        }catch(Exception e){
        }

        assertEquals(TilesEnum.BOOKS,test.selectTile(0).getTile());
        assertEquals(TilesEnum.CATS,test.getTiles().get(0).getTile());
        assertEquals(TilesEnum.FRAMES,test.getTiles().get(1).getTile());
        assertEquals(2,test.getTiles().size());

        assertEquals(TilesEnum.CATS,test.selectTile(0).getTile());
        assertEquals(TilesEnum.FRAMES,test.getTiles().get(0).getTile());
        assertEquals(1,test.getTiles().size());

        assertEquals(TilesEnum.FRAMES,test.selectTile(0).getTile());
        assertEquals(0,test.getTiles().size());
    }

    //tests the set and get methods of pg redeemed
    @Test
    void setGetPersonalGoalRedeemed(){
        Player test = new Player("Pietro",3);
        PersonalGoal.cleanUsedCodes();

        test.setPersonalGoalRedeemed(true);
        assertTrue(test.isPersonalGoalRedeemed());
        test.setPersonalGoalRedeemed(false);
        assertFalse(test.isPersonalGoalRedeemed());
    }

    //tests the set and get methods of cg redeemed
    @Test
    void setGetCommonGoalRedeemed(){
        Player test = new Player("Pietro",3);
        PersonalGoal.cleanUsedCodes();

        test.setCommonGoalRedeemed(true, 0);
        assertTrue(test.isCommonGoalRedeemed(0));
        test.setCommonGoalRedeemed(false, 0);
        assertFalse(test.isCommonGoalRedeemed(0));

        test.setCommonGoalRedeemed(true, 1);
        assertTrue(test.isCommonGoalRedeemed(1));
        test.setCommonGoalRedeemed(false, 1);
        assertFalse(test.isCommonGoalRedeemed(1));
    }

    //tests the IllegalArgumentException catch recalculates the personalGoal
    @Test
    void tryPersonalGoal(){
        Player test = new Player("Pietro",3);
        PersonalGoal.cleanUsedCodes();

        PersonalGoal personal5 = new PersonalGoal(4);

        PersonalGoal personal6 = test.tryPersonalGoal(4);

        assertNotEquals(personal5, personal6);
    }
}