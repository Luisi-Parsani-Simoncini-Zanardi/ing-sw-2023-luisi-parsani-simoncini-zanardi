package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Board;
import org.projectsw.Model.Tiles;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    //test that the method unpdate the board correctly
    @Test
    void updateBoard() {
        Board board = new Board();
        assertEquals(Tiles.EMPTY, board.getBoard()[5][5]);
        board.updateBoard(Tiles.CATS, 5,5);
        assertEquals(Tiles.CATS, board.getBoard()[5][5]);
    }

    //test that the board is correct before and after an update
    @Test
    void getBoard() {
        Board board = new Board();
        board.updateBoard(Tiles.CATS, 4,0);
        board.updateBoard(Tiles.FRAMES, 4,1);
        board.updateBoard(Tiles.PLANTS, 4,2);
        board.updateBoard(Tiles.GAMES, 4,3);
        assertEquals(Tiles.CATS, board.getBoard()[4][0]);
        assertEquals(Tiles.FRAMES, board.getBoard()[4][1]);
        assertEquals(Tiles.PLANTS, board.getBoard()[4][2]);
        assertEquals(Tiles.GAMES, board.getBoard()[4][3]);

    }

    //test that the method takes the tile from the board, setting it as EMPTY on the board
    //test that the returned tile is of the right type
    @Test
    void getTileFromBoard() {
    }

    //test that it sets the board to the endGame
    @Test
    void setEndGame() {
    }

    //test that the value of endGame is the right one
    @Test
    void isEndGame() {
    }

}