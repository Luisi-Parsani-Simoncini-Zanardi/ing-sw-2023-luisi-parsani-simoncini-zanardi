package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.RowGroup;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class RowGroupTest {

    //tests that the algorithm successfully checks that the shelf meets the requirements of the chosen CommonGoal
    @Test
    void checkTrue() {
        CommonGoal commonRow = new RowGroup();
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertTrue(commonRow.check(shelf));
    }

    //tests that the algorithm successfully checks that the shelf has too much types in the rows
    @Test
    void tooMuchTypes() {
        CommonGoal commonRow = new RowGroup();
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(commonRow.check(shelf));
    }

    //tests that the algorithm successfully checks that the shelf used not allowed types
    @Test
    void unusedTiles(){
        CommonGoal commonRow = new RowGroup();
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(commonRow.check(shelf));
    }

    //tests that the algorithm successfully checks that the shelf has few rows of the right kind
    @Test
    void fewRows(){
        CommonGoal commonRow = new RowGroup();
        Shelf shelf = new Shelf();
        for(int i=2; i>-1; i--)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(commonRow.check(shelf));
    }
}