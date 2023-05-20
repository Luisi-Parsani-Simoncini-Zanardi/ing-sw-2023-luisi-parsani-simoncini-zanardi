package org.projectsw.Model;

import java.io.Serializable;

/**
 * This class represent a single message object with a single sender, the payload of the message.
 */
public class Message implements Serializable {

    private final String sender;
    private final String payload;
    private final String scope;

    /**
     * Constructs a message object with the sender and the content.
     * @param sender the player who sent the message
     * @param scope if it's everyone the message will be sent to all players, if it's a nickname only to the interested player
     * @param payload the payload of the message
     */
    public Message(String sender,String scope, String payload) {
        this.sender = sender;
        this.payload = payload;
        this.scope = scope;
    }

    /**
     * Returns the payload of the message.
     * @return the payload of the message
     */
    public String getPayload() { return this.payload; }


    /**
     * Returns the sender of the message.
     * @return the sender of the message
     */
    public String getSender() { return this.sender; }

    public String getScope() { return this.scope; }

}
