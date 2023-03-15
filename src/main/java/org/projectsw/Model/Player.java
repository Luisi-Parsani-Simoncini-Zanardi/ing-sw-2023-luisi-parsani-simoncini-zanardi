package org.projectsw.Model;

import java.util.ArrayList;

public class Player {
    private final int position;
    private final String nickname;
    private int points;
    private Shelf shelf;
    private PersonalGoal personalGoal;

    private ArrayList<Tiles> temporaryTiles;

    public Player (String nickname, int number) {
        this.position=number;
        this.nickname=nickname;
        this.points=0;
        //shelf e personalGoal will have to be set by their respective set function after calling the constructor
    }

    public int getPosition(){
        return position;
    }

    public String getNickname(){
        return nickname;
    }

    public void setPoints(int points){
        this.points=points;
    }

    public int getPoints(){
        return points;
    }

    public void setShelf(Shelf shelf) {
        this.shelf=shelf;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal=personalGoal;
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public void addTile(Tiles tiles) {
        temporaryTiles.add(tiles);
    }

    public ArrayList<Tiles> getTiles() {
        return temporaryTiles;
    }

    public Tiles selectTile(int num){
        Tiles temp;
        temp = temporaryTiles.get(num);
        temporaryTiles.remove(num);
        return temp;
    }
}
