package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

public class Diagonal extends ShapeBehavior {
    public Diagonal(){
        super();
        this.commonGoalCode = 11;
    }

    /**
     * @param shelf is the player shelf
     * @return true if there is a diagonal of Tiles equal to each other, false otherwise
     */
    @Override
    public boolean check(Shelf shelf) {
        if(this.equalTiles(shelf.getTileShelf(0,0),shelf.getTileShelf(1,1))&&
           this.equalTiles(shelf.getTileShelf(0,0),shelf.getTileShelf(2,2))&&
           this.equalTiles(shelf.getTileShelf(0,0),shelf.getTileShelf(3,3))&&
           this.equalTiles(shelf.getTileShelf(0,0),shelf.getTileShelf(4,4))&&
           shelf.getTileShelf(0,0).getTile() != TilesEnum.EMPTY)
            return true;
        else if(this.equalTiles(shelf.getTileShelf(1,0),shelf.getTileShelf(2,1))&&
                this.equalTiles(shelf.getTileShelf(1,0),shelf.getTileShelf(3,2))&&
                this.equalTiles(shelf.getTileShelf(1,0),shelf.getTileShelf(4,3))&&
                this.equalTiles(shelf.getTileShelf(1,0),shelf.getTileShelf(5,4))&&
                shelf.getTileShelf(1,0).getTile() != TilesEnum.EMPTY
        )
            return true;
        return false;
    }
}
