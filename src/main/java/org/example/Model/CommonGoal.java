package org.example.Model;

public class CommonGoal{

    private int goalCode;
    private int redeemedNumber;

    //costruttore
    public CommonGoal(int code){

        this.redeemedNumber = 0;
        this.goalCode = code;
    }
    public CommonGoal getCommonGoal(){
        return this;
    }

    public void increaseRedeemedNumber() {
        redeemedNumber++;
    }

    public int getGoalCode() { //non c'è in UML
        return goalCode;
    }

    public int getRedeemedNumber() {
        return redeemedNumber;
    }
}
