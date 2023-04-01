package org.projectsw.ModelTest.CommonGoalTest;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.Square;
import org.projectsw.Model.Shelf;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    /**
     * Tests if the method returns false when there is only a square.
     */
    @Test
    void checkOne() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
        }catch(Exception e){}
        assertFalse(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns true when there are two squares with the same type of tiles
     */
    @Test
    void checkBasic() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 3);
        }catch(Exception e){}
        assertTrue(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns false when one of the two squares is missing a tile
     */
    @Test
    void checkDeformed() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 3);
        }catch(Exception e){}
        assertFalse(commonGoal.check(shelf));
    }


    /**
     * tests if the method returns false when there are two squares with different type of tiles from each other
     */
    @Test
    void checkBasicDiff() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 4, 4);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 4, 3);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 3, 4);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 3, 3);
        }catch(Exception e){}
        assertFalse(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns true when there are two squares with the same tile type in diagonal
     */
    @Test
    void checkCorner() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 3);
        }catch(Exception e){}
        assertTrue(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns true when there are two squares with a shared half side
     */
    @Test
    void checkHalfSide() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
        }catch(Exception e){}
        assertTrue(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns true when there are two squares with a shared full side
     */
    @Test
    void checkFullSide() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
        }catch(Exception e){}
        assertTrue(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns true when there are two squares with a shared full side and a separate valid square
     */
    @Test
    void checkFullSideAndAnother() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 3);
        }catch(Exception e){}
        assertTrue(commonGoal.check(shelf));
    }

    /**
     * tests if the method returns true when then boards is full of the same type of tiles
     */
    @Test
    void checkWhole() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        for (int i=0; i<6; i++){
            for (int j=0; j<6; j++){
                try {
                    shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, j);
                }catch(Exception e){}
                assertTrue(commonGoal.check(shelf));
            }
        }
    }

    /**
     * tests if the method returns false when the board is empty
     */
    @Test
    void checkEmpty() {
        CommonGoal commonGoal = new Square();
        Shelf shelf = new Shelf();
        assertFalse(commonGoal.check(shelf));
    }

}