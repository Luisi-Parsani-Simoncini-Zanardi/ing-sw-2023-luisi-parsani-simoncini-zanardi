package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Tests if the method sets firstPlayer correctly.
     */
    @Test
    void testSetFirstPlayer() {
        Game game = new Game();
        Player john = new Player("John", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        game.setFirstPlayer(john);
        assertEquals(john, game.getFirstPlayer());
        game.setFirstPlayer(elizabeth);
        assertEquals(elizabeth,game.getFirstPlayer());
    }

    /**
     * Tests if the method returns firstPlayer correctly.
     */
    @Test
    void testGetFirstPlayer() {
        Game game = new Game();
        Player john = new Player("John", 1);
        game.setFirstPlayer(john);
        assertEquals(john, game.getFirstPlayer());
    }

    /**
     * Tests if the method sets correctly the current player.
     */
    @Test
    void testSetCurrentPlayer() {
        Game game = new Game();
        Player elizabeth = new Player("Elizabeth", 1);
        Player enzo = new Player("Enzo", 2);
        game.setCurrentPlayer(elizabeth);
        assertEquals(elizabeth, game.getCurrentPlayer());
        game.setCurrentPlayer(enzo);
        assertEquals(enzo, game.getCurrentPlayer());
    }

    /**
     * Tests if the method returns the current player correctly.
     */
    @Test
    void testGetCurrentPlayer() {
        Game game = new Game();
        Player elizabeth = new Player("Elizabeth", 2);
        game.setCurrentPlayer(elizabeth);
        assertEquals(elizabeth, game.getCurrentPlayer());
    }

    /**
     * Tests if the method sets the players correctly.
     */
    @Test
    void testSetPlayers() {
        Game game = new Game();
        Player john = new Player("John", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        Player ronald = new Player("Ronald", 3);
        Player enzo = new Player("Enzo", 4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(john);
        players.add(elizabeth);
        players.add(ronald);
        players.add(enzo);
        game.setPlayers(players);
        assertEquals(players,game.getPlayers());
    }

    /**
     * Tests if the method returns the right players.
     */
    @Test
    void testGetPlayers() {
        Game game = new Game();
        Player john = new Player("John", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        Player ronald = new Player("Ronald", 3);
        Player enzo = new Player("Enzo", 4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(john);
        players.add(elizabeth);
        players.add(ronald);
        players.add(enzo);
        game.setPlayers(players);
        assertEquals(players,game.getPlayers());
    }

    /**
     * Tests if the method updates the board correctly.
     */
    @Test
    void testSetBoard(){
        Game game = new Game();
        Board board = new Board();
        game.setBoard(board);
        assertEquals(board, game.getBoard());

        board.updateBoard(new Tile(TilesEnum.CATS, 0),4,4);
        game.setBoard(board);
        assertEquals(board,game.getBoard());
    }

    /**
     * Tests if the method returns the board correctly.
     */
    @Test
    void testGetBoard(){
        Game game = new Game();
        Board board = new Board();
        board.updateBoard(new Tile(TilesEnum.CATS, 0),4,4);
        game.setBoard(board);
        assertEquals(board, game.getBoard());
    }

    /**
     * Tests if the method correctly generates a random commonGoal.
     */
    @Test
    void testRandomCommonGoal(){
        Game game = new Game();
        ArrayList<CommonGoal> test= new ArrayList<>();
        try{
            test = game.randomCommonGoals();
        } catch(Exception ignore){}
        game.setCommonGoals(test);
        assertNotNull(game.getCommonGoals().get(0).getStrategy());
        assertNotNull(game.getCommonGoals().get(1).getStrategy());
    }

    /**
     * Tests if the method correctly returns the chat.
     */
    @Test
    void testSetGetChat(){
        Game game = new Game();
        Chat chat = new Chat();
        game.setChat(chat);
        assertEquals(chat, game.getChat());
    }

    /**
     * Tests if the method correctly adds players to the game.
     */
    @Test
    void testAddPlayer(){
        Game game = new Game();
        Player james = new Player("James", 1);
        Player kirk = new Player("Kirk", 2);
        Player cliff = new Player("Cliff", 3);
        Player lars = new Player("Lars", 4);
        Player jason = new Player("Jason", 5);
        Player cliff2 = new Player("Cliff", 5);
        try {
            game.addPlayer(james);
            game.addPlayer(kirk);
            game.addPlayer(cliff);
            game.addPlayer(lars);
        } catch (InvalidNameException ignore) {
        }
        assertEquals(james, game.getPlayers().get(0));
        assertEquals(kirk, game.getPlayers().get(1));
        assertEquals(cliff, game.getPlayers().get(2));
        assertEquals(lars, game.getPlayers().get(3));
        assertThrows(InvalidNameException.class, () -> game.addPlayer(cliff2));
    }
}