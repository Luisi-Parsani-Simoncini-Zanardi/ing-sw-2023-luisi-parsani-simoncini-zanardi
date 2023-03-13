package org.example.Model;

public class CommonGoal{
    //va aggiunto il costruttore
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
