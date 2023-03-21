package org.projectsw.Model;

import org.projectsw.Exceptions.MaximumRedeemedPointsException;

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

    public void increaseRedeemedNumber() throws MaximumRedeemedPointsException {
        if(getRedeemedNumber()<4)
            redeemedNumber++;
        else{
            throw new MaximumRedeemedPointsException("There are no more points to redeem");
        }
    }

    public int getRedeemedNumber() {
        return redeemedNumber;
    }

}
