package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.EdgesEightEquals;
import org.projectsw.Model.CommonGoal.Square;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.TestUtils;

import java.lang.reflect.InvocationTargetException;
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
     * Tests the correct creation of a game instance for each possible number of players
     */
    @Test
    void integrityGameTest(){
        Player firstPlayer = new Player("Davide",0);
        Player secondPlayer = new Player("Lorenzo",1);
        Player thirdPlayer = new Player("Luca",2);
        Player fourthPlayer = new Player("Silvio",3);
        ArrayList<Player> fakeList = new ArrayList<>();
        fakeList.add(0,firstPlayer);
        fakeList.add(1,secondPlayer);
        fakeList.add(2,thirdPlayer);
        fakeList.add(3,fourthPlayer);
        for(int i=2;i<4;i++){
            Game game = new Game();
            game.initializeGame(i);
            assertEquals(GameState.LOBBY,game.getGameState());
            assertEquals(i,game.getNumberOfPlayers());
            assertEqualsBoard(new Board(i),game.getBoard());
            assertEqualsChat(new Chat(),game.getChat());
            for(int j=0;j<i+1;j++){
                game.getPlayers().add(fakeList.get(j));
            }
            game.setFirstPlayer(game.getPlayers().get(0));
            game.setCurrentPlayer(game.getPlayers().get(0));
            assertEquals(firstPlayer,game.getFirstPlayer());
            for(int j=0; j<i;j++) {
                assertEquals(fakeList.get(j).getNickname(), game.getCurrentPlayer().getNickname());
                assertEquals(fakeList.get(j).getPosition(), game.getCurrentPlayer().getPosition());
                game.setCurrentPlayer(game.getNextPlayer());
            }
        }
    }

    /**
     * Tests if the method sets firstPlayer correctly.
     */
    @Test
    void testSetFirstPlayer() {
        Game game = new Game();
        Player john = new Player("John", 0);
        Player elizabeth = new Player("Elizabeth", 0);
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
        Player john = new Player("John", 0);
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

    /**
     * Test if the game state is updated correctly
     */
    @Test
    void gameStateTest(){
        Game game = new Game();
        assertEquals(GameState.LOBBY,game.getGameState());
        game.setGameState(GameState.RUNNING);
        assertEquals(GameState.RUNNING,game.getGameState());
        game.setGameState(GameState.SILLY);
        assertEquals(GameState.SILLY,game.getGameState());
        game.setGameState(GameState.ENDING);
        assertEquals(GameState.ENDING,game.getGameState());
        game.setGameState(GameState.LOBBY);
        assertEquals(GameState.LOBBY,game.getGameState());
    }

    /**
     * Test if the game returns the players nicknames correctly
     */
    @Test
    void getPlayersNickname(){
        Game game = new Game();
        game.initializeGame(4);
        Player player1 = new Player("Bonnie",0);
        Player player2 = new Player("Loretta",1);
        Player player3 = new Player("Megan",2);
        Player player4 = new Player("Lois",3);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        assertTrue(game.getPlayersNickname().contains("Bonnie"));
        assertTrue(game.getPlayersNickname().contains("Loretta"));
        assertTrue(game.getPlayersNickname().contains("Megan"));
        assertTrue(game.getPlayersNickname().contains("Lois"));
    }

    /**
     * Test if the game returns the correct position passing a nickname as a parameter
     */
    @Test
    void getPositionByNick(){
        Game game = new Game();
        game.initializeGame(4);
        Player player1 = new Player("Bonnie",0);
        Player player2 = new Player("Loretta",1);
        Player player3 = new Player("Megan",2);
        Player player4 = new Player("Lois",3);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        assertEquals(0,game.getPositionByNick("Bonnie"));
        assertEquals(1,game.getPositionByNick("Loretta"));
        assertEquals(2,game.getPositionByNick("Megan"));
        assertEquals(3,game.getPositionByNick("Lois"));
    }

    /**
     * Test if the game returns the right common goals if it takes an array of indexes as a parameter
     */
    @Test
    void commonGoalByIndex(){
        Game game = new Game();
        int []indexes = {1,9};
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        try {
            commonGoals = game.commonGoalByIndex(indexes);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignore){}
        assertEquals(commonGoals.get(0).getStrategy().getClass(), Square.class);
        assertEquals(commonGoals.get(1).getStrategy().getClass(), EdgesEightEquals.class);
    }
}