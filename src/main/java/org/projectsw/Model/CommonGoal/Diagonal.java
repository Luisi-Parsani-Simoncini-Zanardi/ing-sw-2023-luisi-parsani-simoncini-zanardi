package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.Enums.TilesEnum;

public class Diagonal extends CommonGoalStrategy {

    /**
     * Creates a new instance of the Diagonal class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Diagonal(Integer strategyCode){
        super(strategyCode);
        this.setDescription("Five tiles of the same type forming a\n" +
                "diagonal.");
    }

    /**
     * check that there is at least 1 diagonal pattern in the shelf
     * @param shelf is the player shelf
     * @return true if there is a diagonal of Tiles equals to each other, false otherwise
     */
    @Override
    public boolean check(Shelf shelf) {
        if(shelf.getTileShelf(0,0).getTile() != TilesEnum.EMPTY &&
                shelf.getTileShelf(0,0).getTile().equals(shelf.getTileShelf(1,1).getTile())&&
                shelf.getTileShelf(0,0).getTile().equals(shelf.getTileShelf(2,2).getTile())&&
                shelf.getTileShelf(0,0).getTile().equals(shelf.getTileShelf(3,3).getTile())&&
                shelf.getTileShelf(0,0).getTile().equals(shelf.getTileShelf(4,4).getTile()))
            return true;
        else if(shelf.getTileShelf(1,0).getTile() != TilesEnum.EMPTY &&
                shelf.getTileShelf(1,0).getTile().equals(shelf.getTileShelf(2,1).getTile())&&
                shelf.getTileShelf(1,0).getTile().equals(shelf.getTileShelf(3,2).getTile())&&
                shelf.getTileShelf(1,0).getTile().equals(shelf.getTileShelf(4,3).getTile())&&
                shelf.getTileShelf(1,0).getTile().equals(shelf.getTileShelf(5,4).getTile()))
            return true;
        else if(shelf.getTileShelf(0,4).getTile() != TilesEnum.EMPTY &&
                shelf.getTileShelf(0,4).getTile().equals(shelf.getTileShelf(1,3).getTile())&&
                shelf.getTileShelf(0,4).getTile().equals(shelf.getTileShelf(2,2).getTile())&&
                shelf.getTileShelf(0,4).getTile().equals(shelf.getTileShelf(3,1).getTile())&&
                shelf.getTileShelf(0,4).getTile().equals(shelf.getTileShelf(4,0).getTile()))
            return true;
        else return shelf.getTileShelf(1, 4).getTile() != TilesEnum.EMPTY &&
                    shelf.getTileShelf(1, 4).getTile().equals(shelf.getTileShelf(2, 3).getTile()) &&
                    shelf.getTileShelf(1, 4).getTile().equals(shelf.getTileShelf(3, 2).getTile()) &&
                    shelf.getTileShelf(1, 4).getTile().equals(shelf.getTileShelf(4, 1).getTile()) &&
                    shelf.getTileShelf(1, 4).getTile().equals(shelf.getTileShelf(5, 0).getTile());
    }
}
