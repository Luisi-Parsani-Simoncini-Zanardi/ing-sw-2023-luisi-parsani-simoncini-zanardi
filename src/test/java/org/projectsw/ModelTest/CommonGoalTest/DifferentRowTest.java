package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.DifferentColumn;
import org.projectsw.Model.CommonGoal.DifferentRow;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class DifferentRowTest {

    /**
     * tests if the algorithm successfully checks that the shelf meets the requirements of the chosen CommonGoal
     */
    @Test
    void checkTrue() {
        CommonGoalStrategy strategy = new DifferentRow();
        CommonGoal differentRow = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=5; i>3; i--)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertTrue(differentRow.checkRequirements(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf has too much types in the rows
     */
    @Test
    void fewTypes() {
        CommonGoalStrategy strategy = new DifferentRow();
        CommonGoal differentRow = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(differentRow.checkRequirements(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf used not allowed types
     */
    @Test
    void emptyTiles(){
        CommonGoalStrategy strategy = new DifferentRow();
        CommonGoal differentRow = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(differentRow.checkRequirements(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf has few rows of the right kind
     */
    @Test
    void fewRows(){
        CommonGoalStrategy strategy = new DifferentRow();
        CommonGoal differentRow = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=5; i>4; i--)
            try {
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(differentRow.checkRequirements(shelf));
    }
}