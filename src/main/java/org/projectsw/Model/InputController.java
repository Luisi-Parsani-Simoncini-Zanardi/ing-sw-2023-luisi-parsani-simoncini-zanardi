package org.projectsw.Model;

import java.awt.*;
import java.io.Serializable;

public class InputController implements Serializable{

    private final Point coordinate;

    private final Integer index;

    public InputController(Point point, Integer integer){
        this.coordinate = point;
        this.index = integer;
    }

    public Point getCoordinate(){return this.coordinate;}

    public Integer getIndex(){return this.index;}
}
