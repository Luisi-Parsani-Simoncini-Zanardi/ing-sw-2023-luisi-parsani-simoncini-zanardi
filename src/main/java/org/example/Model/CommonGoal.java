package org.example.Model;

public class CommonGoal{
    //manca il costruttore, una volta fatto il costruttore mettere final a goalCode
    private int goalCode;
    private int redeemedNumber;

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
