package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    /**
     * tests if the method sets firstPlayer correctly
     */
    @Test
    void testSetFirstPlayer() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        game.setFirstPlayer(jhon);
        assertEquals(jhon, game.getFirstPlayer());
        game.setFirstPlayer(elizabeth);
        assertEquals(elizabeth,game.getFirstPlayer());
    }

    /**
     * tests if the method returns firstPlayer correctly
     */
    @Test
    void testGetFirstPlayer() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        game.setFirstPlayer(jhon);
        assertEquals(jhon, game.getFirstPlayer());
    }

    /**
     * tests if the method sets correctly the current player
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
     * tests if the method returns the current player correctly
     */
    @Test
    void testGetCurrentPlayer() {
        Game game = new Game();
        Player elizabeth = new Player("Elizabeth", 2);
        game.setCurrentPlayer(elizabeth);
        assertEquals(elizabeth, game.getCurrentPlayer());
    }

    /**
     * tests if the method sets the players correctly
     */
    @Test
    void testSetPlayers() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        Player ronald = new Player("Ronald", 3);
        Player enzo = new Player("Enzo", 4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(jhon);
        players.add(elizabeth);
        players.add(ronald);
        players.add(enzo);
        game.setPlayers(players);
        assertEquals(players,game.getPlayers());
    }

    /**
     * tests if the method returns the right players of the game
     */
    @Test
    void testGetPlayers() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        Player ronald = new Player("Ronald", 3);
        Player enzo = new Player("Enzo", 4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(jhon);
        players.add(elizabeth);
        players.add(ronald);
        players.add(enzo);
        game.setPlayers(players);
        assertEquals(players,game.getPlayers());
    }

    /**
     * tests if the method updates the board correctly
     */
    @Test
    void testSetBoard() throws IOException {
        Game game = new Game();
        Board board = new Board();
        game.setBoard(board);
        assertEquals(board, game.getBoard());

        board.updateBoard(new Tile(TilesEnum.CATS, 0),4,4);
        game.setBoard(board);
        assertEquals(board,game.getBoard());
    }

    /**
     * tests if the method returns the board correctly
     */
    @Test
    void testGetBoard() throws IOException {
        Game game = new Game();
        Board board = new Board();
        board.updateBoard(new Tile(TilesEnum.CATS, 0),4,4);
        game.setBoard(board);
        assertEquals(board, game.getBoard());
    }

    /**
     * tests if the method correctly generates a random commonGoal
     */
    @Test
    void testRandomCommonGoal(){
        Game game = new Game();
        ArrayList<CommonGoal> test= new ArrayList<>();
        try{
            test = game.randomCommonGoals();
        } catch(Exception e){}
        game.setCommonGoals(test);
        assertTrue(game.getCommonGoals().get(0) instanceof CommonGoal);
        assertTrue(game.getCommonGoals().get(1) instanceof CommonGoal);
    }
}