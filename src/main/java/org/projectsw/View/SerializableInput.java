package org.projectsw.View;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class SerializableInput implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private final String clientNickname;
    private final Point coordinate;
    private final Integer integer;
    private final String nickname;
    private final String string;

    public SerializableInput(String clientNickname, Point coordinate){
        this.clientNickname = clientNickname;
        this.coordinate = coordinate;
        this.integer = null;
        this.string = null;
        this.nickname = null;
    }

    public SerializableInput(String clientNickname, String string){
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = null;
        this.string = string;
        this.nickname = null;
    }

    public SerializableInput(int number){
        this.clientNickname = null;
        this.coordinate = null;
        this.integer = number;
        this.string = null;
        this.nickname = null;
    }

    public SerializableInput(String clientNickname, int num, String string){
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = num;
        this.string = string;
        this.nickname = null;
    }

    public SerializableInput(String clientNickname){
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
        this.nickname = null;
    }

    public SerializableInput(String clientNickname, int integer){
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = integer;
        this.string = null;
        this.nickname = null;
    }

    public String getClientNickname(){return this.clientNickname;}
    public Point getCoordinate(){return this.coordinate;}
    public Integer getInteger(){return this.integer;}
    public String getString(){return this.string;}
    public String getNickname(){return this.nickname;}

}
