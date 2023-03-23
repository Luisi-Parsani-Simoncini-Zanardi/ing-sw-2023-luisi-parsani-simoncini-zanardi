package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class DifferentColumn extends ColumnBehavior {
    public DifferentColumn(){
        super();
        this.commonGoalCode = 2;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
