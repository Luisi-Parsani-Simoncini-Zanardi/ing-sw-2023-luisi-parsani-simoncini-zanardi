package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.EdgesEightEquals;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class EdgesEightEqualsTest {


    /**
     * Checks if the edges of the player's shelf are of the same Tile type
     */
    @Test
    void trueEdges() {
        CommonGoalStrategy strategy = new EdgesEightEquals(8);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,4);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if the edges of the player's shelf are of different Tile type
     */
    @Test
    void falseEdges() {
        CommonGoalStrategy strategy = new EdgesEightEquals(8);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,4);
        }catch(Exception ignore){}
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the edges of the player's shelf doesn't contain empty Tiles
     */
    @Test
    void emptyEdges() {
        CommonGoalStrategy strategy = new EdgesEightEquals(8);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,4);
        }catch(Exception ignore){}
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the edges of the player's shelf is empty
     */
    @Test
    void emptyShelfEdges() {
        CommonGoalStrategy strategy = new EdgesEightEquals(8);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf contains 8 Tiles of the same type
     */
    @Test
    void trueEightEquals1() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),4,2);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf contains 8 Tiles of the same type
     */
    @Test
    void trueEightEquals2() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),4,2);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf contains 8 Tiles of the same type
     */
    @Test
    void trueEightEquals3() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),4,2);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }
    /**
     * Checks if the player's shelf contains 8 Tiles of the same type
     */
    @Test
    void trueEightEquals4() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),4,2);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf contains 8 Tiles of the same type
     */
    @Test
    void trueEightEquals5() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),4,2);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf contains 8 Tiles of the same type
     */
    @Test
    void trueEightEquals6() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),4,2);
        }catch(Exception ignore){}
        assertTrue(common.checkRequirements(shelf));
    }


    /**
     * Checks if the player's shelf contains less than 8 Tiles of the same type
     */
    @Test
    void falseEightEquals() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),0,0);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0),0,4);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0),5,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0),5,4);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),2,2);
            shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0),3,3);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0),5,1);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0),4,2);
        }catch(Exception ignore){}
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf is empty
     */
    @Test
    void emptyShelfEightEquals() {
        CommonGoalStrategy strategy = new EdgesEightEquals(9);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }
}