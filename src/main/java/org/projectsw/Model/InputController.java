package org.projectsw.Model;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class InputController implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private final Integer clientID;
    private final Point coordinate;
    private final Integer integer;
    private final String nickname;
    private final String string;

    public InputController(int clientID, Point coordinate){
        this.clientID = clientID;
        this.coordinate = coordinate;
        this.integer = null;
        this.string = null;
        this.nickname = null;
    }

    public InputController(String string){
        this.clientID = null;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
        this.nickname = string;
    }

    public InputController(int clientID, String string){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = null;
        this.string = string;
        this.nickname = null;
    }

    public InputController(int clientID, int num, String string){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = num;
        this.string = string;
        this.nickname = null;
    }

    public InputController(int clientID){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
        this.nickname = null;
    }

    public InputController(int clientID, int integer){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = integer;
        this.string = null;
        this.nickname = null;
    }

    public InputController(String nickname, String string){
        this.clientID = null;
        this.coordinate = null;
        this.integer = null;
        this.string = string;
        this.nickname = nickname;
    }

    public Integer getClientID(){return this.clientID;}
    public Point getCoordinate(){return this.coordinate;}
    public Integer getIndex(){return this.integer;}
    public String getString(){return this.string;}
    public String getNickname(){return this.nickname;}

}
