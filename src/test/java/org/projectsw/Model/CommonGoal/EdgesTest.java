package org.projectsw.Model.CommonGoal;
import org.projectsw.Model.Shelf;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class EdgesTest {

    //test that the edges are of the same TileEnum type
    @Test
    void checkTrue() {
        CommonGoal commonGoal = new Shape(new Edges());
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 4);
        }catch(Exception e){}
        assertTrue(commonGoal.check(shelf));
    }

    //test that the edges are of a different TileEnum type
    @Test
    void checkDifferentType(){
        CommonGoal commonGoal = new Shape(new Edges());
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0), 0, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 4);
        }catch(Exception e){}
        assertFalse(commonGoal.check(shelf));
    }

    //test that the edges are only of TilesEnum allowed types
    @Test
    void checkAllowed(){
        CommonGoal commonGoal = new Shape(new Edges());
        Shelf shelf = new Shelf();
        try {
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 0, 0);
            shelf.insertTiles(new Tile(TilesEnum.EMPTY,0), 0, 4);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 0);
            shelf.insertTiles(new Tile(TilesEnum.CATS,0), 5, 4);
        }catch(Exception e){}
        assertFalse(commonGoal.check(shelf));
    }
}