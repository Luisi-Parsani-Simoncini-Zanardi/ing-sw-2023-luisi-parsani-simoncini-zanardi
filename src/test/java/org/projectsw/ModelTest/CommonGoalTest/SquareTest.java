package org.projectsw.ModelTest.CommonGoalTest;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.Edges;
import org.projectsw.Model.CommonGoal.Square;
import org.projectsw.Model.Shelf;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

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
}