package org.projectsw.Model;
//TODO: sistemare javadoc message

import java.util.ArrayList;
import java.time.LocalTime;

/**
 * this class represent a single message object with a single sender, the content of the message and a
 * list of all the recipients that needs to receive the message
 */
public class Message {

    private final Player sender;
    private final String content;
    private ArrayList<Player> recipients;

    private final LocalTime time;

    /**
     * construct a message object with the sender.
     * @param sender
     */
    public Message(Player sender, String content, LocalTime time) {
        this.sender = sender;
        this.recipients = new ArrayList<>();
        this.content = content;
        this.time = time;
    }

    /**
     * set the list of recipients of the message
     * @param recipients
     */
    public void setRecipients (ArrayList<Player> recipients) { this.recipients = recipients; }

    /**
     * return the content of the message
     * @return message content
     */
    public String getContent() { return this.content; }

    /**
     * return the list of the recipients of the message
     * @return recipients list
     */
    public ArrayList<Player> getRecipients() { return this.recipients; }

    /**
     * return the sender of the message
     * @return sender
     */
    public Player getSender() { return this.sender; }

    /**
     * return the time of the message
     * @return time
     */
    public LocalTime getTime() { return this.time; }
}
