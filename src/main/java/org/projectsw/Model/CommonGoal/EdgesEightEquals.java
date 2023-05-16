package org.projectsw.Model.CommonGoal;

import org.projectsw.Util.Config;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Enums.TilesEnum;

import java.util.ArrayList;

public class EdgesEightEquals extends CommonGoalStrategy{

    private final boolean edge;

    /**
     * Creates a new instance of the EdgesEightEquals class using the unique code of the CommonGoal.
     * If strategyCode is equal to 8 the player shelf must have the edges of the same type of tiles
     * If strategyCode is equal to 9 the player shelf must have at least 8 Tiles of the same tYpe
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public EdgesEightEquals(Integer strategyCode){
        super(strategyCode);
        this.edge = this.strategyCode == 8;
    }

    /**
     * Return true if the player's shelf meets the requirements of the commonGoal identified by the unique strategyCode.
     * Returns false otherwise.
     * @param shelf is the player shelf
     * @return true if the player's shelf meets the requirements of the commonGoal identified by the unique strategyCode
     */
    @Override
    public boolean check(Shelf shelf){
        ArrayList<TilesEnum> tiles = new ArrayList<>();
        if(this.edge){
            tiles.add(shelf.getTileShelf(0,0).getTile());
            tiles.add(shelf.getTileShelf(0,4).getTile());
            tiles.add(shelf.getTileShelf(5,0).getTile());
            tiles.add(shelf.getTileShelf(5,4).getTile());
            return this.differentTiles(tiles) == 1;
        }else{
            return this.equalsCounter(shelf);
        }
    }

    /**
     * Returns true if the player's shelf has at least 8 tiles of the same type. Returns false otherwise
     * @param shelf is the player's shelf
     * @return true if the player's shelf has at least 8 tiles of the same type. Return false otherwise
     */
    private boolean equalsCounter(Shelf shelf){
        int cats = 0;
        int frames = 0;
        int books = 0;
        int plants = 0;
        int trophies = 0;
        int games = 0;
        for(int i = 0; i< Config.shelfHeight; i++)
            for(int j=0; j<Config.shelfLength; j++){
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
        return cats > 7 || frames > 7 || books > 7 || plants > 7 || trophies > 7 || games > 7;
    }
}
