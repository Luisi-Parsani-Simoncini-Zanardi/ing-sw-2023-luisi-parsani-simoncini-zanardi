package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    //test that the method sets the right firstPlayer of the game
    @Test
    void setFirstPlayer() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        game.setFirstPlayer(jhon);
        assertEquals(jhon, game.getFirstPlayer());
        game.setFirstPlayer(elizabeth);
        assertEquals(elizabeth,game.getFirstPlayer());
    }

    //test that the method returns the right firstPlayer of the game
    @Test
    void getFirstPlayer() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        game.setFirstPlayer(jhon);
        assertEquals(jhon, game.getFirstPlayer());
    }

    //test that the method sets correctly the current player
    @Test
    void setCurrentPlayer() {
        Game game = new Game();
        Player elizabeth = new Player("Elizabeth", 1);
        Player enzo = new Player("Enzo", 2);
        game.setCurrentPlayer(elizabeth);
        assertEquals(elizabeth, game.getCurrentPlayer());
        game.setCurrentPlayer(enzo);
        assertEquals(enzo, game.getCurrentPlayer());
    }

    //test that the method returns the current player
    @Test
    void getCurrentPlayer() {
        Game game = new Game();
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        game.setCurrentPlayer(elizabeth);
        assertEquals(elizabeth, game.getCurrentPlayer());
    }

    //test that the method sets the right players of the game
    @Test
    void setPlayers() {
        Game game = new Game();
        Player [] giocatore = {null,null,null,null};
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        Player ronald = new Player("Ronald", 3);
        Player enzo = new Player("Enzo", 4);
        giocatore[0] = jhon;
        giocatore[1] = elizabeth;
        giocatore[2] = ronald;
        giocatore[3] = enzo;
        game.setPlayers(giocatore);
        assertEquals(giocatore,game.getPlayers());
    }

    //test that the method returns the right players of the game
    @Test
    void getPlayers() {
        Game game = new Game();
        Player [] giocatore = {null,null,null,null};
        Player jhon = new Player("Jhon", 1);
        Player elizabeth = new Player("Elizabeth", 2);
        Player ronald = new Player("Ronald", 3);
        Player enzo = new Player("Enzo", 4);
        giocatore[0] = jhon;
        giocatore[1] = elizabeth;
        giocatore[2] = ronald;
        giocatore[3] = enzo;
        game.setPlayers(giocatore);
        assertEquals(giocatore,game.getPlayers());
    }

    //tests that the method update the board correctly
    @Test
    void setBoard() {
        Game game = new Game();
        Board board = new Board();
        game.setBoard(board);
        assertEquals(board, game.getBoard());

        board.updateBoard(Tiles.CATS,4,4);
        game.setBoard(board);
        assertEquals(board,game.getBoard());
    }

    //tests that the method returns the board
    @Test
    void getBoard() {
        Game game = new Game();
        Board board = new Board();
        board.updateBoard(Tiles.CATS,4,4);
        game.setBoard(board);
        assertEquals(board, game.getBoard());
    }

    //test that the commonGoals are set correctly
    @Test
    void setCommonGoals() {
        Game game = new Game();
        CommonGoal []test = {null, null};
        test[0] = new CommonGoal(4);
        test[1] = new CommonGoal(5);
        game.setCommonGoals(test);
        assertEquals(test,game.getCommonGoals());
    }

    //test that the commonGoals are returned correctly
    @Test
    void getCommonGoals() {
        Game game = new Game();
        CommonGoal []test = {null, null};
        test[0] = new CommonGoal(4);
        test[1] = new CommonGoal(5);
        game.setCommonGoals(test);
        assertEquals(test,game.getCommonGoals());
    }

}