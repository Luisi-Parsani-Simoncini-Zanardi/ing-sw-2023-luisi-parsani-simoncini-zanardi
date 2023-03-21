package org.projectsw.Model;

public class Tile {
    private TilesEnum tile;

    private int imageNumber;

    public Tile (TilesEnum tileType, int imageNumber){
        this.tile = tileType;
        this.imageNumber = imageNumber;
    }

    public TilesEnum getTile(){
        return tile;
    }

    public int getImageNumber(){
        return imageNumber;
    }
}
