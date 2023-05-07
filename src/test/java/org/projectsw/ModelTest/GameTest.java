package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Exceptions.InvalidNumberOfPlayersException;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.TestUtils;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GameTest extends TestUtils{

    //TODO DAVIDE: sistemare i test utilizzando il nuovo schema di costruzione di game
    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Tests the correct creation of a silly game instance
     */
    @Test
    void integritySillyGameTest(){
        Game sillyGame = new Game();
        assertEquals(GameStates.SILLY,sillyGame.getGameState());
        assertEquals(0,sillyGame.getNumberOfPlayers());
        assertEqualsBoard(new Board(),sillyGame.getBoard());
        assertEqualsChat(new Chat(),sillyGame.getChat());
        assertEquals(new ArrayList<>(),sillyGame.getCommonGoals());
        assertEquals(new ArrayList<>(),sillyGame.getPlayers());
        assertNull(sillyGame.getFirstPlayer());
        assertNull(sillyGame.getCurrentPlayer());
    }

    /**
     * Tests the correct creation of a game instance
     */
    @Test
    void integrityGameTest() throws InvalidNumberOfPlayersException {
        Player firstPlayer = new Player("Davide",0);
        for(int i=2;i<5;i++){
            Game game = new Game();
            game.initializeGame(firstPlayer,i);
            assertEquals(GameStates.LOBBY,game.getGameState());
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
     * Tests if the constructor of game correctly throws the IllegalArgumentException when the number of players
     * is too low or too high.
     */
    @Test
    void invalidNumberOfPlayersTest(){
        Game game =  new Game();
        assertThrows(InvalidNumberOfPlayersException.class, () -> game.initializeGame(new Player("Davide",0),1));
        assertThrows(InvalidNumberOfPlayersException.class, () -> game.initializeGame(new Player("Davide",0),5));
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
    void testSetPlayers() throws InvalidNameException {
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
    void testGetPlayers() throws InvalidNameException {
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
     * Tests if the method addPlayer correctly adds players to the game.
     */
    @Test
    void testAddPlayer() throws InvalidNameException{
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
     * Tests if the method addPlayer correctly throws the InvalidNameException when the user chose a duplicated nickname.
     */
    @Test
    void testInvalidNicknameNotUniqueTest() throws InvalidNameException {
        Game game = new Game();
        game.addPlayer(new Player("James", 0));
        assertThrows(InvalidNameException.class, () -> game.addPlayer(new Player("James", 1)));
    }

    /**
     * test if correctly retrieve the next player
     * @throws InvalidNameException duplicate or invalid name
     */
    @Test
    void getNextPlayerTest() throws InvalidNameException, InvalidNumberOfPlayersException {
        Player current = new Player("Renala", 0);
        Player next1 = new Player("Gravius", 1);
        Player next2 = new Player("Lusat", 2);
        Game game = new Game();
        game.initializeGame(current, 3);
        game.addPlayer(next1);
        game.addPlayer(next2);
        game.setCurrentPlayer(current);
        assertEqualsPlayer(next1, game.getNextPlayer());
        assertEqualsPlayer(current, game.getCurrentPlayer());
    }
}