package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class ColumnGroup extends ColumnBehavior {
    public ColumnGroup(){
        super();
        this.commonGoalCode = 5;
    }
    //TODO: manca implementazione
    @Override
    public boolean check(Shelf shelf) {
        return false;
    }
}
