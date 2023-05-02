package org.projectsw.Model;

import java.awt.*;
import java.io.Serializable;

public class InputController implements Serializable{

    private final Point coordinate;
    private final Integer integer;
    private final String string;

    public InputController(Point point, Integer integer, String string){
        this.coordinate = point;
        this.integer = integer;
        this.string = string;
    }

    public Point getCoordinate(){return this.coordinate;}
    public Integer getIndex(){return this.integer;}
    public String getString(){return this.string;}
}
