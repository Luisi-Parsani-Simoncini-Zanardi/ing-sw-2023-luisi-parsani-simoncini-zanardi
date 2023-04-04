package org.projectsw.Model;

import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Exceptions.MaximumPlayerException;

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


    /**
     * Construct a message object with the sender and the content.
     * @param sender player who sent the message
     * @param content message content
     */
    public Message(Player sender, String content) {
        this.sender = sender;
        this.recipients = new ArrayList<>();
        this.content = content;
    }

    /**
     * set the list of recipients of the message
     * @param recipients
     */
    public void setRecipients (ArrayList<Player> recipients) throws InvalidNameException {
        ArrayList<String> recipientName = new ArrayList<>();
        getRecipients().forEach((element) -> recipientName.add(element.getNickname()));
        if (recipientName.contains(sender.getNickname())) {
            throw new InvalidNameException("sender can't be recipients");
        }
        this.recipients = recipients;
    }

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

}
