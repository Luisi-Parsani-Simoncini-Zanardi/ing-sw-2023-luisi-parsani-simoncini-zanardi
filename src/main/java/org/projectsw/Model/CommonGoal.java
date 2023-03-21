package org.projectsw.Model;

import org.projectsw.Exceptions.MaximumRedeemedPointsException;

/**
 * The class represents a common goal.
 */
public class CommonGoal{
    private final int goalCode;
    private int redeemedNumber;

    /**
     * Constructs a CommonGoal object with the given code and zero redeemed points.
     * @param code the code identifying the common goal
     */
    public CommonGoal(int code){
        this.redeemedNumber = 0;
        this.goalCode = code;
    }

    /**
     * @return the code identifying the common goal
     */
    public int getGoalCode() {
        return goalCode;
    }

    /**
     * @return the number of times the goal has been redeemed
     */
    public int getRedeemedNumber() {
        return redeemedNumber;
    }

    /**
     * Increases the number of times the goal has been redeemed by one, up to a maximum of four.
     * @throws MaximumRedeemedPointsException if the number of redeemed points is already at the maximum of four
     */
    public void increaseRedeemedNumber() throws MaximumRedeemedPointsException {
        if(getRedeemedNumber()<4)
            redeemedNumber++;
        else{
            throw new MaximumRedeemedPointsException("There are no more points to redeem");
        }
    }
}
