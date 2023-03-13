package org.example.Model;

public class PersonalGoal {
    private int[][] personalGoal;

    public PersonalGoal(){
        //da sistemare
    }

    public PersonalGoal(PersonalGoal personalGoal){
        this.personalGoal=personalGoal.personalGoal;
    }

    public PersonalGoal getPersonalGoal(){return this;}
}
