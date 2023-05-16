package org.projectsw.ModelTest.CommonGoalTest;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.Square;
import org.projectsw.Model.Shelf;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.Enums.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    /**
     * Tests if the method returns false when there is only a square.
     */
    @Test
    void checkOne() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns true when there are two squares with the same type of tiles
     */
    @Test
    void checkBasic() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
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
        }catch(Exception ignored){}
        assertTrue(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when one of the two squares is missing a tile
     */
    @Test
    void checkDeformed() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 3);
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }


    /**
     * Tests if the method returns false when there are two squares with different type of tiles from each other
     */
    @Test
    void checkBasicDiff() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
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
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns true when there are two squares with the same tile type in diagonal
     */
    @Test
    void checkCorner() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
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
        }catch(Exception ignored){}
        assertTrue(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when there are two squares with a shared half side
     */
    @Test
    void checkHalfSide() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
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
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when there are two squares with a shared full side
     */
    @Test
    void checkFullSide() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
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
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns true when there are two squares with a shared full side and a separate valid square
     */
    @Test
    void checkFullSideAndAnother() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
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
        }catch(Exception ignored){}
        assertTrue(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when then boards is full of the same type of tiles
     */
    @Test
    void checkWhole() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                try {
                    shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, j);
                }catch(Exception ignored){}
            }
        }
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when the board is empty
     */
    @Test
    void checkEmpty() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when there are two squares with a common tile
     */
    @Test
    void checkOneInCommon() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when there are two squares with two common tiles
     */
    @Test
    void checkTwoInCommon() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
        }catch(Exception ignored){}
        assertFalse(square.checkRequirements(shelf));
    }


    /**
     * Tests if the method returns true when there are two squares with a common tile and another square
     */
    @Test
    void checkOneInCommonAndAnother() {
        CommonGoalStrategy strategy = new Square(1);
        CommonGoal square = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 3);
        }catch(Exception ignored){}
        assertTrue(square.checkRequirements(shelf));
    }

}