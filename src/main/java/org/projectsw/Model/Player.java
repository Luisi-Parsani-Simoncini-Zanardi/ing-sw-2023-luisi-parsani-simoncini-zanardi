package org.projectsw.Model;

import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.MaximumTilesException;
import org.projectsw.Exceptions.UnusedTilesException;

import java.util.ArrayList;

public class Player {
    private final int position;
    private final String nickname;
    private int points;
    private Shelf shelf;
    private PersonalGoal personalGoal;

    private ArrayList<Tile> temporaryTiles;

    public Player (String nickname, int number) {
        this.position=number;
        this.nickname=nickname;
        this.points=0;
        this.temporaryTiles = new ArrayList<Tile>();
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

    public void addTile(Tile tiles) throws MaximumTilesException, EmptyTilesException, UnusedTilesException {

        if(temporaryTiles.size()>2){
            throw new MaximumTilesException("Maximum number of tiles reached");
        }else if(tiles.getTile() == TilesEnum.EMPTY){
            throw new EmptyTilesException("You can't add an EMPTY tile");
        }else if(tiles.getTile() == TilesEnum.UNUSED){
            throw new UnusedTilesException("You can't add an UNUSED tile");
        }else{
            temporaryTiles.add(tiles);
        }
    }

    public ArrayList<Tile> getTiles() {
        return temporaryTiles;
    }

    public Tile selectTile(int num){
        Tile temp;
        temp = temporaryTiles.get(num);
        temporaryTiles.remove(num);
        return temp;
    }
}
