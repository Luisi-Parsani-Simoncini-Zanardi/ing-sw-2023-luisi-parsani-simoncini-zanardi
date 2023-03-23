package org.projectsw.Model.CommonGoal;
import org.projectsw.Model.Shelf;

public class Row extends CommonGoal{
    RowBehavior rb;

    public Row(RowBehavior rb){
        super();
        this.rb = rb;
    }

    @Override
    public Boolean check(Shelf shelf){
        return rb.check(shelf);
    }
}