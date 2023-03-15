package org.projectsw.Model;

public class CommonGoal{

    private final int goalCode;
    private int redeemedNumber;

    public CommonGoal(int code){

        this.redeemedNumber = 0;
        this.goalCode = code;
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
