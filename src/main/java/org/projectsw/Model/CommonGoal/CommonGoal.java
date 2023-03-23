package org.projectsw.Model.CommonGoal;
import org.projectsw.Exceptions.MaximumRedeemedPointsException;
import org.projectsw.Model.Shelf;

public abstract class CommonGoal{
    private int redeemedNumber;

    public CommonGoal(){
        this.redeemedNumber = 0;
    }

    /**
     * @return the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     */
    public int getRedeemedNumber(){
        return this.redeemedNumber;
    }

    /**
     * increase the redeemedNumber that shows how many times a specific CommonGoal has been redeemed
     * @throws MaximumRedeemedPointsException
     */
    public void increaseRedeemedNumber() throws MaximumRedeemedPointsException{
        if(getRedeemedNumber()<4)
            redeemedNumber++;
        else throw  new MaximumRedeemedPointsException("There are no more points to redeem");
    }

    /**
     * @param shelf is the player's shelf
     * @return true after the override by the subclasses if the player's shelf match the requirements of the
     * specific commonGoal, false otherwise
     */
    public boolean check(Shelf shelf){
        return false;
    }

    //TODO: aggiunta solo per rimuovermi gli errori del gson, --questo metodo è da cancellare quando gson fixato--
    public int getGoalCode(){return 1;}

}