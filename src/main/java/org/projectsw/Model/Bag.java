package org.projectsw.Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Bag class represents the bag of tiles that can be drawn from.
 */
public class Bag implements Cloneable{
    private final ArrayList<Tile> tiles;

    /**
     * Constructs a new Bag object with 132 tiles, 22 of each of the 6 types of tiles
     * and shuffles them
     */
    public Bag() {
        tiles = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            tiles.add(new Tile(TilesEnum.CATS, i%3));
            tiles.add(new Tile(TilesEnum.TROPHIES, i%3));
            tiles.add(new Tile(TilesEnum.BOOKS, i%3));
            tiles.add(new Tile(TilesEnum.FRAMES, i%3));
            tiles.add(new Tile(TilesEnum.PLANTS, i%3));
            tiles.add(new Tile(TilesEnum.GAMES, i%3));
        }
        Collections.shuffle(tiles);
    }

    /**
     * Returns the size of the bag
     * @return the size of the bag
     */
    public int getBagSize(){
        return tiles.size();
    }

    /**
     * Removes and returns the first tile from the bag. If the bag is empty, returns a new Tile object with type EMPTY.
     * @return The Tile object that was removed from the bag or a new Tile object with type EMPTY if the bag is empty.
     */
    public Tile pop(){
        Tile tile;
        if(tiles.isEmpty()){
            return new Tile(TilesEnum.EMPTY, 0);
        }
        tile = tiles.get(0);
        tiles.remove(0);
        return tile;
    }

    public ArrayList<Tile> getBag() {
        return (ArrayList<Tile>) tiles.clone();
    }

    /**
     * Clones an object.
     * @return the cloned object
     * @throws CloneNotSupportedException if clone is not supported
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
