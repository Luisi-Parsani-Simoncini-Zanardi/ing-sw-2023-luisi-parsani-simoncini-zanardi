package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class ColumnEqualsTwo extends ColumnBehavior {
    public ColumnEqualsTwo(){
        super();
        this.commonGoalCode = 4;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
