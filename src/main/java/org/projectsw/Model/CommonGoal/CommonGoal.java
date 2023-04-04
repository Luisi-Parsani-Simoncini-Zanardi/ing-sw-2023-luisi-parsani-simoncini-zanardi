package org.projectsw.Model.CommonGoal;
import org.projectsw.Exceptions.MaximumRedeemedPointsException;
import org.projectsw.Model.Shelf;

public class CommonGoal{
    private int redeemedNumber;
    private final CommonGoalStrategy strategy;

    public CommonGoal(CommonGoalStrategy strategy){
        this.redeemedNumber = 0;
        this.strategy = strategy;
    }

    /**
     * Method that returns the control strategy chosen for the commonGoal
     * @return the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     */
    public CommonGoalStrategy getStrategy(){return this.strategy;}

    /**
     * Method that returns the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     * @return the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     */
    public int getRedeemedNumber(){
        return this.redeemedNumber;
    }

    /**
     * Increase the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     * @throws MaximumRedeemedPointsException when there are no more points to redeem on this CommonGoal
     */
    public void increaseRedeemedNumber() throws MaximumRedeemedPointsException{
        if(getRedeemedNumber()<4)
            redeemedNumber++;
        else throw  new MaximumRedeemedPointsException("There are no more points to redeem");
    }

    /**
     * Method that checks if the commonGoal requirements are met by the player library
     * @param shelf is the player's shelf
     * @return true if the implementation of the check method of the chosen strategy is verified
     */
    public boolean checkRequirements(Shelf shelf){
        return this.strategy.check(shelf);
    }
}