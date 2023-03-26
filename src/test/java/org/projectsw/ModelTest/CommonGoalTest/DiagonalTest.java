package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.Diagonal;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalTest {
    /**
     * tests if the algorithm successfully checks that the shelf meets the requirements of the chosen CommonGoal on the first diagonal
     */
    @Test
    void checkTrue1() {
        CommonGoal diagonal = new Diagonal();
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
                try {
                    shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, i);
                    shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i, i);
                    shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i, i);
                    shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, i);
                    shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, i);
                }catch(Exception e){}
        assertTrue(diagonal.check(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf meets the requirements of the chosen CommonGoal on the second diagonal
     */
    @Test
    void checkTrue2() {
        CommonGoal diagonal = new Diagonal();
        Shelf shelf = new Shelf();
        for(int i=0; i<5; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i+1, i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i+1, i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i+1, i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i+1, i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i+1, i);
            }catch(Exception e){}
        assertTrue(diagonal.check(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the shelf has too much types in the rows
     */
    @Test
    void checkFalse() {
        CommonGoal diagonal = new Diagonal();
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(diagonal.check(shelf));
    }

    /**
     * tests if the algorithm successfully checks that the empty shelf does not satisfy the requirements
     */
    @Test
    void checkEmpty() {
        CommonGoal diagonal = new Diagonal();
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++)
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS,0), i, 0);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i, 1);
                shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), i, 2);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0), i, 3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0), i, 4);
            }catch(Exception e){}
        assertFalse(diagonal.check(shelf));
    }
}