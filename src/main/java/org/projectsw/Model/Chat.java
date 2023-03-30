package org.projectsw.Model;

import java.util.ArrayList;

/**
 * The Chat class represents a chat log.
 */
public class Chat {
    private final ArrayList<String> chat;

    /**
     * Constructs a Chat object with an empty chat log.
     */
    public Chat (){
        chat = new ArrayList<>();
    }

    /**
     * Adds a message to the chat log.
     * @param message the message to add to the chat log
     */
    public void addChatLog (String message){
        chat.add(message);
    }

    /**
     * Returns the list of messages in the chat log.
     * @return the ArrayList of messages in the chat log
     */
    public ArrayList<String> getChat(){
        return chat;
    }
}
