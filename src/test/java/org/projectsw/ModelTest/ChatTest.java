package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Chat;
import org.projectsw.Model.Message;
import org.projectsw.Model.Player;
import org.projectsw.TestUtils;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatTest extends TestUtils {

    /**
     * Tests if the method addChatLog correctly add a String to the ArrayList chat.
     */
    @Test
    void testAddChatLog(){
        Player sender = new Player("Pippo", 1);
        Chat chat = new Chat();
        chat.addChatLog(new Message(sender, "Testing"));
        chat.addChatLog(new Message(sender, "class"));
        chat.addChatLog(new Message(sender, "chat"));
        assertEquals("Testing", chat.getChat().get(0).getContent());
        assertEquals("class", chat.getChat().get(1).getContent());
        assertEquals("chat", chat.getChat().get(2).getContent());
    }

    /**
     * Tests if the method actually returns an ArrayList containing the right values.
     */
    @Test
    void testGetChat() {
        Player sender = new Player("Pippo", 1);
        Chat chat = new Chat();
        ArrayList<Message> test = new ArrayList<>();
        chat.addChatLog(new Message(sender, "Hi i'm Lorenzo and im testing the chat class"));
        test.add(new Message(sender, "Hi i'm Lorenzo and im testing the chat class"));
        for (int i=0; i<chat.getChat().size(); i++)
            assertEqualsMessage(test.get(i), chat.getChat().get(i));

    }
}