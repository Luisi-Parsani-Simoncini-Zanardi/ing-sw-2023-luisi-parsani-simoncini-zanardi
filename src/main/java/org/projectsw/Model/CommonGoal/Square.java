package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class Square extends ShapeBehavior {
    public Square(){
        super();
        this.commonGoalCode = 1;
    }
    //TODO: manca l'implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
