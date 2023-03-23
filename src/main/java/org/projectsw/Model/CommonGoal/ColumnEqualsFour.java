package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class ColumnEqualsFour extends ColumnBehavior {
    public ColumnEqualsFour(){
        super();
        this.commonGoalCode = 3;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
