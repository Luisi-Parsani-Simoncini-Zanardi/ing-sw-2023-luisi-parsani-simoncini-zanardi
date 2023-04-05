package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public class Cross extends CommonGoalStrategy{

    /**
     * Creates a new instance of the Cross class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Cross(Integer strategyCode){super(strategyCode);}

    /**
     * Checks that there is at least 1 cross pattern in the shelf
     * @param shelf is the player shelf
     * @return true if there is at least one cross pattern formed by equal tiles
     */
    @Override
    public boolean check(Shelf shelf) {
        ArrayList<TilesEnum> tile = new ArrayList<>();
        for(int i=1; i<5; i++){
            for(int j=1; j<4; j++) {
                tile.add(shelf.getTileShelf(i, j).getTile());
                tile.add(shelf.getTileShelf(i-1, j-1).getTile());
                tile.add(shelf.getTileShelf(i+1, j+1).getTile());
                tile.add(shelf.getTileShelf(i+1, j-1).getTile());
                tile.add(shelf.getTileShelf(i-1, j+1).getTile());
                if(this.differentTiles(tile)==1)
                    return true;
                tile.clear();
            }
        }
        return false;
    }
}
