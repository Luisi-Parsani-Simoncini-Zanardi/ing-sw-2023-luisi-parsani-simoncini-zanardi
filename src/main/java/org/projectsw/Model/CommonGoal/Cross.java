package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class Cross extends ShapeBehavior {
    public Cross(){
        super();
        this.commonGoalCode = 10;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
