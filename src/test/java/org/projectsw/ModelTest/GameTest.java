package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.TestUtils;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GameTest extends TestUtils{

    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Tests the correct creation of a game instance
     */
    @Test
    void integrityGameTest(){
        Player firstPlayer = new Player("Davide",0);
        for(int i=2;i<5;i++){
            Game game = new Game();
            game.initializeGame(i);
            assertEquals(GameState.LOBBY,game.getGameState());
            assertEquals(i,game.getNumberOfPlayers());
            assertEqualsBoard(new Board(i),game.getBoard());
            assertEqualsChat(new Chat(),game.getChat());
            ArrayList<Player> fakeList = new ArrayList<>();
            fakeList.add(firstPlayer);
            assertEquals(fakeList,game.getPlayers());
            assertEquals(firstPlayer,game.getFirstPlayer());
            assertEquals(firstPlayer,game.getCurrentPlayer());
        }
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
    void testSetPlayers(){
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
    void testGetPlayers(){
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
     * Tests if the method updates the board correctly for each possible board dimension.
     */
    @Test
    void testSetBoard(){
        Game game = new Game();
        for(int i=2; i<5; i++) {
            Board board = new Board(i);
            game.setBoard(board);
            assertEquals(board, game.getBoard());
            board.updateBoard(new Tile(TilesEnum.CATS, 0),4,4);
            game.setBoard(board);
            assertEquals(board,game.getBoard());
        }
    }

    /**
     * Tests if the method returns the board correctly.
     */
    @Test
    void testGetBoard(){
        Game game = new Game();
        Board board = new Board(4);
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
     * Tests if the method addPlayer correctly adds players to the game.
     */
    @Test
    void testAddPlayer(){
        Game game = new Game();
        assertEquals(0,game.getPlayers().size());
        game.addPlayer(new Player("James", 0));
        assertEquals(1,game.getPlayers().size());
        assertEquals("James",game.getPlayers().get(0).getNickname());
        assertEquals(0,game.getPlayers().get(0).getPosition());
        game.addPlayer(new Player("Kirk", 1));
        assertEquals(2,game.getPlayers().size());
        assertEquals("Kirk",game.getPlayers().get(1).getNickname());
        assertEquals(1,game.getPlayers().get(1).getPosition());
    }

    /**
     * test if correctly retrieve the next player
     */
    @Test
    void getNextPlayerTest(){
        Player current = new Player("Renala", 0);
        Player next1 = new Player("Gravius", 1);
        Player next2 = new Player("Lusat", 2);
        Game game = new Game();
        game.initializeGame(3);
        game.addPlayer(current);
        game.addPlayer(next1);
        game.addPlayer(next2);
        game.setCurrentPlayer(current);
        assertEqualsPlayer(next1, game.getNextPlayer());
        assertEqualsPlayer(current, game.getCurrentPlayer());
    }
}