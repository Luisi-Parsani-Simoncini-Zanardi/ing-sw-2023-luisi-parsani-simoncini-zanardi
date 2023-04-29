package org.projectsw.Model;

import org.projectsw.Util.Observable;

import java.util.ArrayList;

/**
 * The Chat class represents a chat log.
 */
public class Chat extends Observable<Game.Event> {

    private final ArrayList<Message> chat;


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
    public void addChatLog (Message message){
        chat.add(message);
    }

    /**
     * Returns the list of messages in the chat log.
     * @return the ArrayList of messages in the chat log
     */
    public ArrayList<Message> getChat(){
        return chat;
    }
}
