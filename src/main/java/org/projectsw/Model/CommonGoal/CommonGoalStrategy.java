package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
public abstract class CommonGoalStrategy {
    protected int numObjects;
    protected int GoalCode;
    public CommonGoalStrategy(){}//TODO: rimuovi sta merda
    public CommonGoalStrategy(int code){
        this.GoalCode= code;
    }

    /**
     * Checks that the player's shelf meets the strategy requirements
     * @param shelf is the player shelf
     * @return true if the shelf meets the requirements or false if it doesn't
     */
    public boolean check(Shelf shelf){return false;}
}
