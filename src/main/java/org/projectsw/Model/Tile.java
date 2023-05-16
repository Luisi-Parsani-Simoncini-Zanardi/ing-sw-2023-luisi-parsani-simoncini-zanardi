package org.projectsw.Model;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;

import java.io.Serializable;

/**
 * Class representing a single tile.
 */
public class Tile implements Serializable {
    private final TilesEnum tile;
    private final int imageNumber;

    /**
     * Constructs a new Tile object with the specified type and image number.
     * @param tileType the type of the tile
     * @param imageNumber the image number of the tile (must be 0,1, or 2)
     */
    public Tile (TilesEnum tileType, int imageNumber) throws IllegalArgumentException{
        if(imageNumber < 0 || imageNumber > Config.numberOfImages-1) throw new IllegalArgumentException();
        this.tile = tileType;
        this.imageNumber = imageNumber;
    }

    /**
     * Returns the type of the tile.
     * @return the type of the tile
     */
    public TilesEnum getTile(){
        return tile;
    }

    /**
     * Returns the image number of the tile.
     * @return the image number of the tile
     */
    public int getImageNumber(){
        return imageNumber;
    }

    /**
     * Returns a string rappresenting the kind of tile.
     * @return a string that rappresent the tile.
     */
    @Override
    public String toString() {
        String str;
        switch(this.getTile()){
            case EMPTY -> str = "EMPTY";
            case CATS -> str = "CATS";
            case TROPHIES -> str = "TROPHIES";
            case PLANTS -> str = "PLANTS";
            case FRAMES -> str = "FRAMES";
            case GAMES -> str = "GAMES";
            case BOOKS -> str = "BOOKS";
            default -> str = "UNUSED";
        }
        return str;
    }
}
