package org.projectsw.Model;

import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Util.Observable;

import java.util.ArrayList;

/**
 * The Chat class represents a chat log.
 */
public class Chat extends Observable<GameEvent> {

    private final ArrayList<Message> messages;
    private static int counter = 0;

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
        messages.add(counter, message);
        counter++;
    }

    /**
     * Returns the list of messages in the chat log.
     * @return the ArrayList of messages in the chat log
     */
    public ArrayList<Message> getMessages(){
        return messages;
    }
}
