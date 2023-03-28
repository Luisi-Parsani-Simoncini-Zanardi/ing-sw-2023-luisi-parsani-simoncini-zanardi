package org.projectsw.Model;

import java.util.ArrayList;

/**
 * this class represent a single message object with a single sender, the content of the message and a
 * list of all the recipients that needs to receive the message
 */
public class Message {

    private final String sender;
    private final String content;
    private ArrayList<String> recipients;

    /**
     * construct a message object with the sender.
     * @param sender
     */
    public Message(String sender, String content) {
        this.sender = sender;
        this.recipients = new ArrayList<>();
        this.content = content;
    }

    /**
     * set the list of recipients of the message
     * @param recipients
     */
    public void setRecipients (ArrayList<String> recipients) { this.recipients = recipients; }

    /**
     * return the content of the message
     * @return message content
     */
    public String getContent() { return this.content; }

    /**
     * return the list of the recipients of the message
     * @return recipients list
     */
    public ArrayList<String> getRecipients() { return this.recipients; }

    public String getSender() { return this.sender; }
}
