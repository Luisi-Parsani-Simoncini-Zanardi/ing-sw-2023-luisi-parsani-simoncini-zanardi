package org.projectsw.View;

import org.projectsw.Distributed.Client;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.io.StringReader;

public class SerializableInput implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private final String clientNickname;
    private final Point coordinate;
    private final Integer integer;
    private final String string;
    private final String alphanumericID;

    public SerializableInput(String alphanumericID, String clientNickname, Point coordinate, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = coordinate;
        this.integer = null;
        this.string = null;
    }

    public SerializableInput(String alphanumericID, String clientNickname, String string, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = null;
        this.string = string;
    }

    public SerializableInput(String alphanumericID, int number, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = null;
        this.coordinate = null;
        this.integer = number;
        this.string = null;
    }

    public SerializableInput(String alphanumericID, String clientNickname, int num, String string, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = num;
        this.string = string;
    }

    public SerializableInput(String alphanumericID, String clientNickname, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
    }

    public SerializableInput(String alphanumericID, String clientNickname, int integer, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = integer;
        this.string = null;
    }
    public SerializableInput(String alphanumericID, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = null;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
    }

    public String getAlphanumericID(){return this.alphanumericID;}
    public String getClientNickname(){return this.clientNickname;}
    public Point getCoordinate(){return this.coordinate;}
    public Integer getInteger(){return this.integer;}
    public String getString(){return this.string;}
}
