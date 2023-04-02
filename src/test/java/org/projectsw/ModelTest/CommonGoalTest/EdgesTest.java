package org.projectsw.ModelTest.CommonGoalTest;

import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.Edges;
import org.projectsw.Model.Shelf;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class EdgesTest {

    /**
     * Tests if the method returns true when the edges are of the same TileEnum type
     */
    @Test
    void checkTrue() {
        CommonGoalStrategy strategy = new Edges();
        CommonGoal edge = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 4);
        }catch(Exception ignored){}
        assertTrue(edge.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns true when the edges are of a different TileEnum type
     */
    @Test
    void checkDifferentType(){
        CommonGoalStrategy strategy = new Edges();
        CommonGoal edge = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 0, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 4);
        }catch(Exception ignored){}
        assertFalse(edge.checkRequirements(shelf));
    }

    /**
     * Tests if the method returns false when the edges are not of only TilesEnum allowed types
     */
    @Test
    void checkAllowed(){
        CommonGoalStrategy strategy = new Edges();
        CommonGoal edge = new CommonGoal(strategy);
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), 0, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 4);
        }catch(Exception ignored){}
        assertFalse(edge.checkRequirements(shelf));
    }
}