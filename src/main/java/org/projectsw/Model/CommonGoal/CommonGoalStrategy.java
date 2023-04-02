package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
public interface CommonGoalStrategy {

    /**
     * Checks that the player's shelf meets the strategy requirements
     * @param shelf is the player shelf
     * @return true if the shelf meets the requirements or false if it doesn't
     */
    public boolean check(Shelf shelf);
}
