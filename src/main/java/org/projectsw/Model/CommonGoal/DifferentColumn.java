package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class DifferentColumn extends ColumnBehavior {
    public DifferentColumn(){
        super();
        this.commonGoalCode = 2;
    }
    //TODO: da testare + javadoc
    @Override
    public boolean check(Shelf shelf) {

        int columnCounter = 0;
        ArrayList<TilesEnum> tiles = new ArrayList<TilesEnum>();

        for(int i=0; i<5; i++){
            for(int j=0; j<6; j++){
                tiles.add(j, shelf.getTileShelf(j,i).getTile());
            }
            if(this.differentTiles(tiles) == 6)
                columnCounter++;
            if(columnCounter == 2)
                return true;
        }
        return false;
    }

}
