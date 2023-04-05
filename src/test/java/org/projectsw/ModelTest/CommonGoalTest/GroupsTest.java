package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.Groups;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class GroupsTest {

    /**
     * Check if there are at least 4 groups of size 4 of tiles of the same type
     */
    @Test
    void trueFourGroupOfFour() {
        CommonGoalStrategy strategy = new Groups(3);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 0,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,1);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 2,1);

            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 4,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 5,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 5,1);

            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 0,4);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 1,4);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 1,3);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 2,3);

            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 3,4);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 4,4);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 5,4);
            shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 5,3);

            for(int i=0; i<6; i++)
                for(int j=0; j<5; j++)
                    if(shelf.getTileShelf(i,j).getTile()==TilesEnum.EMPTY)
                        shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), i, j);
        }
        catch(Exception ignore){}

        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks that groups composed of empty tiles are not counted
     */
    @Test
    void emptyFourTilesShelf(){
        CommonGoalStrategy strategy = new Groups(3);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 0,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,0);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,1);
            shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 2,1);

            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 4,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 5,0);
            shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 5,1);

            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 0,4);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 1,4);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 1,3);
            shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 2,3);
        }
        catch(Exception ignore){}

        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks that the empty shelf does not contain any groups
     */
    @Test
    void emptyFourShelf(){
        CommonGoalStrategy strategy = new Groups(3);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks that the shelf does not contain at least 4 groups made up of 4 tiles of the same type
     */
    @Test
    void falseFourShelf(){
        CommonGoalStrategy strategy = new Groups(3);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        try{
            for(int i=0; i<2; i++)
                for(int j=0; j<5; j++)
                    shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), i,j);

            for(int i=2; i<4; i++)
                for(int j=0; j<5; j++)
                    shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,j);

            for(int i=4; i<6; i++)
                for(int j=0; j<5; j++)
                    shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,j);
        }
        catch(Exception ignore){}

        assertFalse(common.checkRequirements(shelf));
    }
}