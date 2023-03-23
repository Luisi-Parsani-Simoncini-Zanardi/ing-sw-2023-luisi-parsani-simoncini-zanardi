package org.projectsw.Model.CommonGoal;
import org.projectsw.Model.Tile;

public abstract class ShapeBehavior extends CommonGoal{
    public ShapeBehavior(){
        super();
    }

    /**
     * Compares two Tiles
     * @param a is the first Tile to compare
     * @param b is the second Tile to compare
     * @return true if the TileEnum type of the Tiles is equal, false otherwise
     */
    protected boolean equalTiles(Tile a, Tile b){
        if(a.getTile() == b.getTile())
            return true;
        return false;
    }
}
