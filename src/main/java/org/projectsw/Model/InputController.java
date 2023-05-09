package org.projectsw.Model;

import java.awt.*;
import java.io.Serializable;

public class InputController implements Serializable{

    private final int clientID;
    private final Point coordinate;
    private final Integer integer;
    private final String string;

    public InputController(int clientID, Point coordinate){
        this.clientID=clientID;
        this.coordinate=coordinate;
        this.integer=null;
        this.string=null;
    }

    public InputController(int clientID, String string){
        this.clientID=clientID;
        this.coordinate=null;
        this.integer=null;
        this.string=string;
    }

    public InputController(int clientID, int num, String string){
        this.clientID=clientID;
        this.coordinate=null;
        this.integer=num;
        this.string=string;
    }

    public InputController(int clientID){
        this.clientID=clientID;
        this.coordinate=null;
        this.integer=null;
        this.string=null;
    }

    public InputController(int clientID, int integer){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = integer;
        this.string = null;
    }

    public int getClientID(){return this.clientID;}
    public Point getCoordinate(){return this.coordinate;}
    public Integer getIndex(){return this.integer;}
    public String getString(){return this.string;}

}
