package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.ColumnGroup;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.DifferentColumn;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class ColumnGroupTest {

    /**
     * tests if the algorithm successfully checks that the shelf has at least 3 columns with all different tiles
     */
    @Test
    void checkTrue() {
        CommonGoal columnGroup = new ColumnGroup();
        Shelf shelf = new Shelf();
        for(int i=0; i<3; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 5, i);
            }catch(Exception e){}
        assertTrue(columnGroup.check(shelf));
    }

    /**
     *     tests that the algorithm returns false when the shelf has too much types in the columns
     */
    @Test
    void fewTypes() {
        CommonGoal columnGroup = new ColumnGroup();
        Shelf shelf = new Shelf();
        for(int i=0; i<5; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception e){}
        assertFalse(columnGroup.check(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf used not allowed types
     */
    @Test
    void emptyTiles(){
        CommonGoal columnGroup = new ColumnGroup();
        Shelf shelf = new Shelf();
        for(int i=0; i<5; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), 5, i);
            }catch(Exception e){}
        assertFalse(columnGroup.check(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf has too few columns of the right kind
     */
    @Test
    void fewRows(){
        CommonGoal differentColumn = new DifferentColumn();
        Shelf shelf = new Shelf();
        for(int i=0; i<2; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, i);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), 1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 2, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 3, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 4, i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS,0), 5, i);
            }catch(Exception e){}
        assertFalse(differentColumn.check(shelf));
    }
}