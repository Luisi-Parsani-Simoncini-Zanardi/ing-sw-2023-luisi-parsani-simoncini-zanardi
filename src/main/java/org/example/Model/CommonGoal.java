package org.example.Model;

public class CommonGoal{

    private final int goalCode;
    private int redeemedNumber;

    //costruttore
    public CommonGoal(int code){

        this.redeemedNumber = 0;
        this.goalCode = code;
    }
    public CommonGoal getCommonGoal(){
        return this;
    }

    public int getGoalCode() {
        return goalCode;
    }

    public void increaseRedeemedNumber() {
        redeemedNumber++;
    }

    public int getRedeemedNumber() {
        return redeemedNumber;
    }

}
