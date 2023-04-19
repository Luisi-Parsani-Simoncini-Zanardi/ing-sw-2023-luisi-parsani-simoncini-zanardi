package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.Cross;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class CrossTest {


    /**
     * Tests if the algorithm successfully checks that the shelf has at least one cross pattern formed by equal tiles
     */
    @Test
    void checkTrue() {
        CommonGoalStrategy strategy = new Cross(10);
        CommonGoal cross = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 2, 3);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 3, 2);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 1);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 4, 3);
        }catch(Exception ignored){}
        assertTrue(cross.checkRequirements(shelf));
    }

    /**
     * Tests that the algorithm returns false when the shelf has no cross pattern inside
     */
    @Test
    void noCross() {
        CommonGoalStrategy strategy = new Cross(10);
        CommonGoal cross = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<5; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception ignored){}
        assertFalse(cross.checkRequirements(shelf));
    }
}