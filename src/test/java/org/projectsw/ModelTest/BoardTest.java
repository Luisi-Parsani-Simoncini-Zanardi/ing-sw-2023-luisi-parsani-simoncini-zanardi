package org.projectsw.ModelTest;

import org.projectsw.Model.Board;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.*;

class BoardTest {
    /**
     * Tests if the method updates the board correctly.
     */
    @Test
    void testUpdateBoard(){
        Board board = new Board();
        assertEquals(TilesEnum.UNUSED, board.getBoard()[5][5].getTile());
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 5,5);
        assertEquals(TilesEnum.CATS, board.getBoard()[5][5].getTile());
    }

    /**
     * Tests if the board is correct before and after an update.
     */
    @Test
    void testGetBoard(){
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
     * Tests if the method takes the tile from the board correctly,
     * setting it as EMPTY on the board, and if the returned tile is of the right type.
     */
    @Test
    void testGetTileFromBoard(){
        Board board = new Board();
        TilesEnum temp;
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 4,0);
        assertEquals(TilesEnum.CATS, board.getBoard()[4][0].getTile());
        temp = board.getTileFromBoard(new Point(0,4)).getTile();
        assertEquals(TilesEnum.EMPTY, board.getBoard()[4][0].getTile());
        assertEquals(TilesEnum.CATS,temp);
    }

    /**
     * Tests that the method sets correctly the board to the endGame.
     */
    @Test
    void testEndGame(){
        Board board = new Board();
        board.setEndGame(false);
        assertFalse(board.isEndGame());
        board.setEndGame(true);
        assertTrue(board.isEndGame());
    }
    
    /**
     * Tests if the Board constructor returns a correctly initialized matrix of tiles with all tiles set to UNUSED.
     */
    @Test
    void integrityTestUnusedBoard(){
        Board board = new Board();
        assertEquals(9,board.getBoard().length);
        assertEquals(9,board.getBoard()[0].length);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Tile tile = board.getBoard()[i][j];
                assertEquals(tile.getTile(),TilesEnum.UNUSED);
            }
        }
    }

    /**
     * Tests that the Board constructor returns a correctly initialized matrix of tiles for every kind of game,
     * from 2 to 4 players, it also checks if the number of empty and unused tiles is correct.
     */
    @Test
    void integrityTestBoards(){
        for(int p=2;p<5;p++) {
            int emptyNumber = 0;
            int unusedNumber = 0;
            Board board = new Board(p);
            assertEquals(9,board.getBoard()[0].length);
            for (int i = 0; i < 9; i++) {
                assertEquals(9,board.getBoard()[i].length);
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
     * Tests if the constructor of the board throws correctly the IllegalArgumentException for a number
     * of players higher than expected.
     */
    @Test
    void testInvalidPlayersNumberHigher() {
        assertThrows(IllegalArgumentException.class, () -> new Board(5));
    }

    /**
     * Tests if the constructor of the board throws correctly the IllegalArgumentException for a number
     * of players lower than expected.
     */
    @Test
    void testInvalidPlayersNumberLower() {
        assertThrows(IllegalArgumentException.class, () -> new Board(1));
    }

    /**
     * Tests if the constructor that copies a board in another does it correctly.
     */
    @Test
    void integrityTestCopiedBoard(){
        Board board = new Board();
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 0,0);
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 1,1);
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 2,2);
        board.getBag().pop();
        board.getBag().pop();
        board.setEndGame(true);
        Board copiedBoard = new Board(board);
        assertEquals(board.getBag().getBagSize(),copiedBoard.getBag().getBagSize());
        assertEquals(board.isEndGame(),copiedBoard.isEndGame());
        assertEquals(9,board.getBoard()[0].length);
        for (int i = 0; i < 9; i++) {
            assertEquals(9,board.getBoard()[i].length);
            for(int j=0;j<9;j++){
                assertEquals(board.getBoard()[i][j],copiedBoard.getBoard()[i][j]);
            }
        }
    }

    /**
     * Tests if the method copy the tile from the board setting it as EMPTY, if
     * the returned tile is of the right type and if the rest of the board remains as it was.
     */
    @Test
    void getTileFromBoard(){
        Board board1 = new Board();
        board1.updateBoard(new Tile(TilesEnum.CATS, 0), 0,0);
        board1.updateBoard(new Tile(TilesEnum.CATS, 0), 1,1);
        board1.updateBoard(new Tile(TilesEnum.CATS, 0), 2,2);
        Board board2 = new Board(board1);
        TilesEnum temp = board1.getTileFromBoard(new Point(0,0)).getTile();
        assertEquals(TilesEnum.EMPTY, board1.getBoard()[0][0].getTile());
        assertEquals(TilesEnum.CATS, temp);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(i != 0 && j != 0) assertEquals(board1.getBoard()[i][j].getTile(),board2.getBoard()[i][j].getTile());
            }
        }
    }

    /**
     * Tests if the getTileFromBoard() method throws an IndexOutOfBoundsException when given an invalid row index.
     */
    @Test
    public void testGetTileFromBoardInvalidRow() {
        Board board = new Board();
        assertThrows(IndexOutOfBoundsException.class, () -> board.getTileFromBoard(new Point(9,0)));
    }

    /**
     * Tests if the getTileFromBoard method throws an IndexOutOfBoundsException when given an invalid column index.
     */
    @Test
    public void testGetTileFromBoardInvalidColumn() {
        Board board = new Board();
        assertThrows(IndexOutOfBoundsException.class, () -> board.getTileFromBoard(new Point(0,9)));
    }

    /**
     * Tests if isEndGame and setEndGame work correctly.
     */
    @Test
    void inAndSetEndGame(){
        Board board = new Board();
        board.setEndGame(false);
        assertFalse(board.isEndGame());
        board.setEndGame(true);
        assertTrue(board.isEndGame());
    }

    /**
     * Tests if the method updateBoard works correctly, leaving unchanged the rest of the board.
     */
    @Test
    void updateBoardTest(){
        Board board = new Board();
        assertEquals(TilesEnum.UNUSED, board.getBoard()[0][0].getTile());
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 0,0);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(i != 0 && j != 0) assertEquals(TilesEnum.UNUSED,board.getBoard()[i][j].getTile());
            }
        }
        assertEquals(TilesEnum.CATS, board.getBoard()[0][0].getTile());
    }

    /**
     * Tests if the updateBoard method throws an IndexOutOfBoundsException when given an invalid row index.
     */
    @Test
    public void testUpdateBoardInvalidRow() {
        Board board = new Board();
       assertThrows(IndexOutOfBoundsException.class, () -> board.updateBoard(new Tile(TilesEnum.CATS, 1), 9, 0));
    }

    /**
     * Tests if the updateBoard method throws an IndexOutOfBoundsException when given an invalid column index.
     */
    @Test
    public void testUpdateBoardInvalidColumn() {
        Board board = new Board();
        assertThrows(IndexOutOfBoundsException.class, () -> board.updateBoard(new Tile(TilesEnum.CATS, 1), 0, 9));
    }

    /**
     * Tests if the Bag functions are well integrated with Board class by calling them from the shelf object.
     */
    @Test
    public void integrationWithBagTest(){
        Board board = new Board();
        assertEquals(132,board.getBag().getBagSize());
        for(int i=131;i>=0;i--){
            assertNotEquals(TilesEnum.EMPTY,board.getBag().pop().getTile());
            assertEquals(i,board.getBag().getBagSize());
        }
        assertEquals(0,board.getBag().getBagSize());
        assertEquals(TilesEnum.EMPTY,board.getBag().pop().getTile());
    }

    /**
     * Test if the isBoardEmpty method correctly retrieve the board status
     */
    @Test
    public void isBoardEmptyTest(){
        Board board = new Board(3);
        assertTrue(board.isBoardEmpty());
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 0,0);
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 1,1);
        board.updateBoard(new Tile(TilesEnum.CATS, 0), 2,2);
        assertFalse(board.isBoardEmpty());
    }


    @Test
    public void getFirstSelectablePointsTest(){
        Board board = new Board(4);
        board.updateBoard(new Tile(TilesEnum.CATS,0),1,1);
        board.updateBoard(new Tile(TilesEnum.CATS,0),1,2);
        board.updateBoard(new Tile(TilesEnum.CATS,0),1,3);
        board.updateBoard(new Tile(TilesEnum.CATS,0),2,1);
        board.updateBoard(new Tile(TilesEnum.CATS,0),2,2);
        board.updateBoard(new Tile(TilesEnum.CATS,0),2,3);
        board.updateBoard(new Tile(TilesEnum.CATS,0),3,1);
        board.updateBoard(new Tile(TilesEnum.CATS,0),3,2);
        board.updateBoard(new Tile(TilesEnum.CATS,0),3,3);
        System.out.println("\nTest 0:");
        System.out.println(board.getSelectablePoints().size());
        System.out.println("\nTest 1:");
        System.out.println(board.getSelectablePoints().size());
        for(Point point : board.getSelectablePoints()){
            System.out.println(point.toString());
        }
        System.out.println("\nTest 2:");
        board.addTemporaryPoints(new Point( 1,3));
        System.out.println(board.getSelectablePoints().size());
        for(Point point : board.getSelectablePoints()){
            System.out.println(point.toString());
        }
        System.out.println("\nTest 3:");
        board.addTemporaryPoints(new Point( 2,3));
        System.out.println(board.getSelectablePoints().size());
        for(Point point : board.getSelectablePoints()){
            System.out.println(point.toString());
        }
        System.out.println("\nTest 4:");
        board.addTemporaryPoints(new Point( 3,3));
        System.out.println(board.getSelectablePoints().size());

        System.out.println("\nTest 5:");
        board.cleanTemporaryPoints();
        System.out.println(board.getSelectablePoints().size());
    }
}