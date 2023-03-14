package org.example.Model;

public class Player {
    //vanno aggiunti i costruttori
    private int position;
    private int[] points;
    private Shelf shelf;
    private PersonalGoal personalGoal;

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
