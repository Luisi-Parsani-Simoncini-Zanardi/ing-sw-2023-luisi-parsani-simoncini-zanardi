package org.projectsw.Model.CommonGoal;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class RowTest {

    @Test
    void check() {
        CommonGoal commonRow = new Row(new RowGroup());
        Shelf shelf = new Shelf();
        Tile tile = new Tile(TilesEnum.CATS,0);
        for(int i=0; i<6; i++)
            for(int j=0;j<5;j++)
                try {
                    shelf.insertTiles(tile, i, j);
                }catch(Exception e){}
        assertTrue(commonRow.check(shelf));
    }
}