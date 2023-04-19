package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Config;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.Diagonal;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalTest {

    /**
     * Tests that the algorithm properly checks that the tiles in the first main diagonal are equal
     */
    @Test
    void checkTrue1() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i = 0; i< Config.shelfLength; i++)
                try {
                    shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, i);
                }catch(Exception ignored){}
        assertTrue(diagonal.checkRequirements(shelf));
    }

    /**
     * Tests that the algorithm properly checks that the tiles in the second main diagonal are equal
     */
    @Test
    void checkTrue2() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<Config.shelfLength; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, i+1);
            }catch(Exception ignored){}
        assertTrue(diagonal.checkRequirements(shelf));
    }

    /**
     * Tests that the algorithm properly checks that the tiles in the first secondary diagonal are equal
     */
    @Test
    void checkTrue3() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        int k = 0;
        for(int i=Config.shelfLength-1; i>-1; i--) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i, k);
            } catch (Exception ignored) {
            }
            k++;
        }
        assertTrue(diagonal.checkRequirements(shelf));
    }

    /**
     * Tests that the algorithm properly checks that the tiles in the second secondary diagonal are equal
     */
    @Test
    void checkTrue4() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        int k = 1;
        for(int i=Config.shelfLength-1; i>-1; i--) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i, k);
            } catch (Exception ignored) {
            }
            k++;
        }
        assertTrue(diagonal.checkRequirements(shelf));
    }



    /**
     * Tests if the algorithm successfully checks that the diagonal isn't composed by equal tiles
     */
    @Test
    void checkFalse() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<Config.shelfLength-1; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, i);
            }catch(Exception ignored){}

        try {
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 4, 4);
        }catch(Exception ignored){}
        assertFalse(diagonal.checkRequirements(shelf));
    }

    /**
     * Tests if the algorithm successfully checks that the empty shelf does not satisfy the requirements
     */
    @Test
    void checkEmpty() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        assertFalse(diagonal.checkRequirements(shelf));
    }

    /**
     * Test that in the diagonal there cannot be an empty tile
     */
    @Test
    void checkEmptyTile() {
        CommonGoalStrategy strategy = new Diagonal(11);
        CommonGoal diagonal = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<Config.shelfLength-1; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, i);
            }catch(Exception ignored){}
        assertFalse(diagonal.checkRequirements(shelf));
    }
}