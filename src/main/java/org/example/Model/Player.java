package org.example.Model;

public class Player {
    private final int position;
    private int points;
    private Shelf shelf;
    private PersonalGoal personalGoal;

    public Player (int numero) {
        this.position=numero;
        this.points=0;
        //shelf e personalGoal andranno settate con il loro rispettivo set dopo aver chiamato il costruttore
    }

    public void setPoints(int[] points){
        this.points=points;
    }

    public void setShelf(Shelf shelf) {
        this.shelf=shelf;
    }

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal=personalGoal;
    }

    public int getPosition(){
        return position;
    }

    public int[] getPoints(){
        return points;
    }

}
