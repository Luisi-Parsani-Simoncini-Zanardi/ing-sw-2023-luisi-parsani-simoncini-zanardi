package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.DifferentColumn;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class DifferentColumnTest {

    /**
     * Tests if the algorithm successfully checks that the shelf has at least 2 columns with all different tiles
     */
    @Test
    void checkTrue() {
        CommonGoalStrategy strategy = new DifferentColumn();
        CommonGoal differentColumn = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<2; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception ignored){}
        assertTrue(differentColumn.checkRequirements(shelf));
    }

    /**
     * Tests that the algorithm returns false when the shelf has few types in the columns
     */
    @Test
    void fewTypes() {
        CommonGoalStrategy strategy = new DifferentColumn();
        CommonGoal differentColumn = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<5; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception ignored){}
        assertFalse(differentColumn.checkRequirements(shelf));
    }

    /**
     * Tests if the algorithm successfully checks that the shelf used not allowed types
     */
    @Test
    void emptyTiles(){
        CommonGoalStrategy strategy = new DifferentColumn();
        CommonGoal differentColumn = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<5; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception ignored){}
        assertFalse(differentColumn.checkRequirements(shelf));
    }

    /**
     * Tests if the algorithm successfully checks that the shelf has too few columns of the right kind
     */
    @Test
    void fewRows(){
        CommonGoalStrategy strategy = new DifferentColumn();
        CommonGoal differentColumn = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        for(int i=0; i<1; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception ignored){}
        assertFalse(differentColumn.checkRequirements(shelf));
    }
}