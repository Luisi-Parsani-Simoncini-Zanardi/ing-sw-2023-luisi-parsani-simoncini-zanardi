package org.projectsw.View;

import org.projectsw.Distributed.Client;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.io.StringReader;

/**
 * A Serializable input class representing input data.
 */
public class SerializableInput implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private final String clientNickname;
    private final Point coordinate;
    private final Integer integer;
    private final String string;
    private final String alphanumericID;

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param clientNickname The client's nickname.
     * @param coordinate     The coordinate.
     * @param client         The client object.
     */
    public SerializableInput(String alphanumericID, String clientNickname, Point coordinate, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = coordinate;
        this.integer = null;
        this.string = null;
    }

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param clientNickname The client's nickname.
     * @param string         The string.
     * @param client         The client object.
     */
    public SerializableInput(String alphanumericID, String clientNickname, String string, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = null;
        this.string = string;
    }

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param number         The number.
     * @param client         The client object.
     */
    public SerializableInput(String alphanumericID, int number, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = null;
        this.coordinate = null;
        this.integer = number;
        this.string = null;
    }

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param clientNickname The client's nickname.
     * @param string         The string.
     * @param client         The client object.
     * @param num            The num object.
     */
    public SerializableInput(String alphanumericID, String clientNickname, int num, String string, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = num;
        this.string = string;
    }

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param clientNickname The clientNickname.
     * @param client         The client object.
     */
    public SerializableInput(String alphanumericID, String clientNickname, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
    }

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param clientNickname The client's nickname.
     * @param integer        The integer.
     * @param client         The client object.
     */
    public SerializableInput(String alphanumericID, String clientNickname, int integer, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = clientNickname;
        this.coordinate = null;
        this.integer = integer;
        this.string = null;
    }

    /**
     * Constructs a SerializableInput object with the specified parameters.
     * @param alphanumericID The alphanumeric ID.
     * @param client         The client object.
     */
    public SerializableInput(String alphanumericID, Client client){
        this.alphanumericID = alphanumericID;
        this.clientNickname = null;
        this.coordinate = null;
        this.integer = null;
        this.string = null;
    }

    /**
     * Retrieves the alphanumeric ID of the SerializableInput.
     * @return The alphanumeric ID.
     */
    public String getAlphanumericID(){return this.alphanumericID;}

    /**
     * Retrieves the clientNickname of the SerializableInput.
     * @return The clientNickname.
     */
    public String getClientNickname(){return this.clientNickname;}

    /**
     * Retrieves the coordinate of the SerializableInput.
     * @return The coordinate.
     */
    public Point getCoordinate(){return this.coordinate;}

    /**
     * Retrieves the integer of the SerializableInput.
     * @return The integer.
     */
    public Integer getInteger(){return this.integer;}

    /**
     * Retrieves the string of the SerializableInput.
     * @return The string.
     */
    public String getString(){return this.string;}
}
