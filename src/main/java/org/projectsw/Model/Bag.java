package org.projectsw.Model;

import java.util.ArrayList;
import java.util.Collections;
public class Bag {

    private ArrayList<Tile> tiles;

    public Bag() {
        tiles = new ArrayList<Tile>();
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

    public Tile pop(){
        Tile tile;
        if(tiles.isEmpty()){
            return ;
        }
        tile = tiles.get(0);new Tile(TilesEnum.EMPTY, 0)
        tiles.remove(0);
        return tile;
    }
}
