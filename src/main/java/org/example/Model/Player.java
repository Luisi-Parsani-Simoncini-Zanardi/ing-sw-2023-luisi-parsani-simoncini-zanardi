package org.example.Model;

import java.util.ArrayList;

public class Player {
    private final int position;
    private int points;
    private Shelf shelf;
    private PersonalGoal personalGoal;

    private ArrayList<Tiles> temporaryTiles;

    public Player (int numero) {
        this.position=numero;
        this.points=0;
        //shelf e personalGoal andranno settate con il loro rispettivo set dopo aver chiamato il costruttore
    }

    public void setPoints(int points){
        this.points=points;
    }

    public void setShelf(Shelf shelf) {
        this.shelf=shelf;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal=personalGoal;
    }

    public void addTile(Tiles tiles) {
        temporaryTiles.add(tiles);
    }

    public ArrayList<Tiles> getTiles() {
        return temporaryTiles;
    }


    public int getPosition(){
        return position;
    }

    public int getPoints(){
        return points;
    }

    public Tiles selectTile(int num){
        Tiles temp;
        temp = temporaryTiles.get(num);
        temporaryTiles.remove(num);
        return temp;
    }
}
