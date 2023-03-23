package org.projectsw.Model.CommonGoal;
import org.projectsw.Exceptions.MaximumRedeemedPointsException;
import org.projectsw.Model.Shelf;

public abstract class CommonGoal{
    private int redeemedNumber;

    public CommonGoal(){
        this.redeemedNumber = 0;
    }

    public int getRedeemedNumber(){
        return this.redeemedNumber;
    }

    public void increaseRedeemedNumber() throws MaximumRedeemedPointsException{
        if(getRedeemedNumber()<4)
            redeemedNumber++;
        else throw  new MaximumRedeemedPointsException("There are no more points to redeem");
    }

    public Boolean check(Shelf shelf){
        return null;
    }

}