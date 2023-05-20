package org.projectsw.View;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class SerializableInput implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private final Integer clientID;
    private final Point coordinate;
    private final Integer integer;
    private final String nickname;
    private final String string;

    public SerializableInput(int clientID, Point coordinate){
        this.clientID = clientID;
        this.coordinate = coordinate;
        this.integer = null;
        this.string = null;
        this.nickname = null;
    }

    public SerializableInput(String string){
        this.clientID = null;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
        this.nickname = string;
    }

    public SerializableInput(int clientID, String string){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = null;
        this.string = string;
        this.nickname = null;
    }

    public SerializableInput(int clientID, int num, String string){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = num;
        this.string = string;
        this.nickname = null;
    }

    public SerializableInput(int clientID){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
        this.nickname = null;
    }

    public SerializableInput(int clientID, int integer){
        this.clientID = clientID;
        this.coordinate = null;
        this.integer = integer;
        this.string = null;
        this.nickname = null;
    }

    public SerializableInput(int clientID, String nickname, String string){
        this.clientID = clientID;
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
