package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class Column extends CommonGoal{
    ColumnBehavior rb;

    public Column(ColumnBehavior rb){
        super();
        this.rb = rb;
    }

    @Override
    public Boolean check(Shelf shelf){
        return rb.check(shelf);
    }
}
