package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class Diagonal extends ShapeBehavior {
    public Diagonal(){
        super();
        this.commonGoalCode = 11;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
