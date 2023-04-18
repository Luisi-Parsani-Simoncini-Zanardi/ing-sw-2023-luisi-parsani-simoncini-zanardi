package org.projectsw.Model;

import org.projectsw.Config;

/**
 * Class representing a single tile.
 */
public class Tile {
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
}
