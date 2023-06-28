package org.projectsw.Model;


import java.util.ArrayList;

/**
 * The Chat class represents a chat log.
 */
public class Chat {

    private final ArrayList<Message> messages;

    /**
     * Constructs a Chat object with an empty chat log.
     */
    public Chat (){
        messages = new ArrayList<>();
    }

    /**
     * Adds a message to the chat log.
     * @param message the message to add to the chat log
     */
    public void addChatLog (Message message){
        messages.add(message);
    }

    /**
     * Returns the list of messages in the chat log.
     * @return the ArrayList of messages in the chat log
     */
    public ArrayList<Message> getMessages(){
        return messages;
    }
}
