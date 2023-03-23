package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;

public class Shape extends CommonGoal{
    ShapeBehavior rb;

    public Shape(ShapeBehavior rb){
        super();
        this.rb = rb;
    }

    @Override
    public Boolean check(Shelf shelf){
        return rb.check(shelf);
    }
}
