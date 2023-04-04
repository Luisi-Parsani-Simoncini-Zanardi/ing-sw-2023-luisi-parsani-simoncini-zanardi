package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;

public class EightEquals extends CommonGoalStrategy {

    /**
     * Creates a new instance of the EightEquals class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public EightEquals(int strategyCode){
        super(strategyCode);
    }

    /**
     * Checks that there are at least 8 identical tiles in the shelf
     * @param shelf is the player shelf
     * @return true if there is at least eight equal tiles in the shelf
     */
    @Override
    public boolean check(Shelf shelf) {
        int cats = 0;
        int frames = 0;
        int books = 0;
        int plants = 0;
        int trophies = 0;
        int games = 0;
        for(int i=0; i<6; i++)
            for(int j=0; j<5; j++){
                if(shelf.getTileShelf(i,j).getTile() == TilesEnum.CATS)
                    cats++;
                if(shelf.getTileShelf(i,j).getTile() == TilesEnum.FRAMES)
                    frames++;
                if(shelf.getTileShelf(i,j).getTile() == TilesEnum.BOOKS)
                    books++;
                if(shelf.getTileShelf(i,j).getTile() == TilesEnum.PLANTS)
                    plants++;
                if(shelf.getTileShelf(i,j).getTile() == TilesEnum.TROPHIES)
                    trophies++;
                if(shelf.getTileShelf(i,j).getTile() == TilesEnum.GAMES)
                    games++;
            }
        if(cats > 7 || frames > 7 || books > 7 || plants > 7 || trophies > 7 || games > 7)
            return true;
        return false;
    }
}
