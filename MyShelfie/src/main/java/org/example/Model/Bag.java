package org.example.Model;

import java.util.Stack;

public class Bag {
    //manca il costruttore
    private Stack<Tiles> bag;

    public Tiles popTile(){
        return bag.pop();
    }

}
