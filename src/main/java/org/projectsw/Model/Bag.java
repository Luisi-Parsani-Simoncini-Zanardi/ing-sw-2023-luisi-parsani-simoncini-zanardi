package org.projectsw.Model;

import java.util.ArrayList;
import java.util.Collections;
public class Bag {

    private ArrayList<Tiles> tiles;

    public Bag() {
        tiles = new ArrayList<Tiles>();
        for (int i = 0; i < 22; i++) {
            tiles.add(Tiles.CATS);
            tiles.add(Tiles.TROPHIES);
            tiles.add(Tiles.BOOKS);
            tiles.add(Tiles.FRAMES);
            tiles.add(Tiles.PLANTS);
            tiles.add(Tiles.GAMES);
        }
        Collections.shuffle(tiles);
    }

    public Tiles pop(){
        Tiles tile;
        if(tiles.isEmpty()){
            return Tiles.EMPTY;
        }
        tile = tiles.get(0);
        tiles.remove(0);
        return tile;
    }

}
