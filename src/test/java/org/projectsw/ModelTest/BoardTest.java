package org.projectsw.ModelTest;

import org.projectsw.Model.Board;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BoardTest {
    /**
     * tests if the method updates the board correctly
     */
    @Test
    void checkUpdateBoard(){
        Board board = new Board();
        assertEquals(TilesEnum.UNUSED, board.getBoard()[5][5].getTile());
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 5,5);
        assertEquals(TilesEnum.CATS, board.getBoard()[5][5].getTile());
    }

    /**
     * tests if the board is correct before and after an update
     */
    @Test
    void checkGetBoard(){
        Board board = new Board();
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 4,0);
        board.updateBoard(new Tile(TilesEnum.FRAMES, 0), 4,1);
        board.updateBoard(new Tile(TilesEnum.PLANTS, 0), 4,2);
        board.updateBoard(new Tile(TilesEnum.GAMES, 0), 4,3);
        assertEquals(TilesEnum.CATS, board.getBoard()[4][0].getTile());
        assertEquals(TilesEnum.FRAMES, board.getBoard()[4][1].getTile());
        assertEquals(TilesEnum.PLANTS, board.getBoard()[4][2].getTile());
        assertEquals(TilesEnum.GAMES, board.getBoard()[4][3].getTile());

    }

    /**
     *  tests if the method takes the tile from the board correctly, setting it as EMPTY on the board, and if the returned tile is of the right type
     */
    @Test
    void checkGetTileFromBoard(){
        Board board = new Board();
        TilesEnum temp;
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 4,0);
        assertEquals(TilesEnum.CATS, board.getBoard()[4][0].getTile());
        temp = board.getTileFromBoard(4, 0).getTile();
        assertEquals(TilesEnum.EMPTY, board.getBoard()[4][0].getTile());
        assertEquals(TilesEnum.CATS,temp);
    }

    /**
     * tests that the method sets correctly the board to the endGame
     */
    @Test
    void checkEndGame(){
        Board board = new Board();
        board.setEndGame(false);
        assertFalse(board.isEndGame());
        board.setEndGame(true);
        assertTrue(board.isEndGame());
    }

    /**
     * tests that the Board constructor returns a correctly initialized matrix of tiles with all tiles set to UNUSED.
     */
    @Test
    void integrityTestUnusedBoard(){
        Board board = new Board();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Tile tile = board.getBoard()[i][j];
                assertEquals(tile.getTile(),TilesEnum.UNUSED);
            }
        }
    }

    /**
     * tests that the Board constructor returns a correctly initialized matrix of tiles for everi king of game,
     * from 2 to 4 players, it also checks if the number of empty and unused tiles is correct
     */
    @Test
    void integrityTestBoards(){
        for(int p=2;p<5;p++) {
            int emptyNumber = 0;
            int unusedNumber = 0;
            Board board = new Board(p);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Tile tile = board.getBoard()[i][j];
                    assertTrue(tile.getTile().equals(TilesEnum.UNUSED) ||
                            tile.getTile().equals(TilesEnum.EMPTY));
                    if(tile.getTile().equals(TilesEnum.UNUSED)) unusedNumber++;
                    if(tile.getTile().equals(TilesEnum.EMPTY)) emptyNumber++;
                }
            }
            if(p == 2){
                assertEquals(52,unusedNumber);
                assertEquals(29,emptyNumber);
            }
            if(p == 3){
                assertEquals(44,unusedNumber);
                assertEquals(37,emptyNumber);
            }
            if(p == 4){
                assertEquals(36,unusedNumber);
                assertEquals(45,emptyNumber);
            }
        }
    }

    /**
     * tests if the IndexOutOfBoundsException is correctly thrown for a number of players higher then expected
     */
    @Test
    void testInvalidPlayersNumber1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Board board = new Board(5);
        });
    }

    /**
     * tests if the IndexOutOfBoundsException is correctly thrown for a number of players lower then expected
     */
    @Test
    void testInvalidPlayersNumber2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Board board = new Board(1);
        });
    }
}