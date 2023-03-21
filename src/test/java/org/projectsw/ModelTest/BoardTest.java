package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Board;
import org.projectsw.Model.TilesEnum;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    //test that the method unpdate the board correctly
    @Test
    void updateBoard() throws IOException {
        Board board = new Board();
        assertEquals(TilesEnum.EMPTY, board.getBoard()[5][5]);
        board.updateBoard(TilesEnum.CATS, 5,5);
        assertEquals(TilesEnum.CATS, board.getBoard()[5][5]);
    }

    //test that the board is correct before and after an update
    @Test
    void getBoard() throws IOException {
        Board board = new Board();
        board.updateBoard(TilesEnum.CATS, 4,0);
        board.updateBoard(TilesEnum.FRAMES, 4,1);
        board.updateBoard(TilesEnum.PLANTS, 4,2);
        board.updateBoard(TilesEnum.GAMES, 4,3);
        assertEquals(TilesEnum.CATS, board.getBoard()[4][0]);
        assertEquals(TilesEnum.FRAMES, board.getBoard()[4][1]);
        assertEquals(TilesEnum.PLANTS, board.getBoard()[4][2]);
        assertEquals(TilesEnum.GAMES, board.getBoard()[4][3]);

    }

    //test that the method takes the tile from the board, setting it as EMPTY on the board
    //test that the returned tile is of the right type
    @Test
    void getTileFromBoard() throws IOException {
        Board board = new Board();
        TilesEnum temp;
        board.updateBoard(TilesEnum.CATS, 4,0);
        assertEquals(TilesEnum.CATS, board.getBoard()[4][0]);
        temp = board.getTileFromBoard(4, 0);
        assertEquals(TilesEnum.EMPTY, board.getBoard()[4][0]);
        assertEquals(TilesEnum.CATS,temp);
    }

    //test that it sets the board to the endGame
    @Test
    void setEndGame() throws IOException {
        Board board = new Board();
        board.setEndGame(false);
        assertFalse(board.isEndGame());
        board.setEndGame(true);
        assertTrue(board.isEndGame());
    }

    //test that the value of endGame is the right one
    @Test
    void isEndGame() throws IOException {
        Board board = new Board();
        board.setEndGame(false);
        assertFalse(board.isEndGame());
        board.setEndGame(true);
        assertTrue(board.isEndGame());
    }

    //test that the constructor returns a correct and fully inizialized maxtrix of tiles
    @Test
    void integrityTest() throws IOException {
        Board board = new Board();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                TilesEnum tile = board.getTile(i,j);
                assertTrue(tile.equals(TilesEnum.EMPTY)||
                            tile.equals(TilesEnum.UNUSED));
            }
        }
    }

    //test that getTile returns che correct tile and doesn't change the board
    @Test
    void getTile() throws IOException {
        Board board = new Board();
        board.updateBoard(TilesEnum.CATS,0,0);
        board.getTile(0,0);
        assertEquals(TilesEnum.CATS,board.getTile(0,0));
        assertEquals(TilesEnum.CATS,board.getBoard()[0][0]);
    }
}