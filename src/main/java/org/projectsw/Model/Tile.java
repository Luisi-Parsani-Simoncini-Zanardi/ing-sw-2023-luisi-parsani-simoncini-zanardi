package org.projectsw.Model;

/**
 * Class representing a single tile
 */
public class Tile {
    private final TilesEnum tile;

    private final int imageNumber;

    /**
     * Constructs a new Tile object with the specified type and image number.
     * @param tileType the type of the tile
     * @param imageNumber the image number of the tile
     */
    public Tile (TilesEnum tileType, int imageNumber){
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
