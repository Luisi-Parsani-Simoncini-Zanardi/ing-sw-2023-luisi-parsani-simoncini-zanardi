package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class Triangle extends ShapeBehavior {
    public Triangle(){
        super();
        this.commonGoalCode = 12;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
