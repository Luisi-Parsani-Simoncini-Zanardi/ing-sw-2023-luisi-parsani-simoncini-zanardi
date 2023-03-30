package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Chat;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    /**
     * Tests if the method addChatLog correctly add a String to the ArrayList chat.
     */
    @Test
    void testAddChatLog(){
        Chat chat = new Chat();
        chat.addChatLog("Testing");
        chat.addChatLog("class");
        chat.addChatLog("chat");
        assertEquals("Testing", chat.getChat().get(0));
        assertEquals("class", chat.getChat().get(1));
        assertEquals("chat", chat.getChat().get(2));
    }

    /**
     * Tests if the method actually returns an ArrayList containing the right values.
     */
    @Test
    void testGetChat() {
        Chat chat = new Chat();
        ArrayList<String> test = new ArrayList<>();
        chat.addChatLog("Hi i'm Lorenzo and im testing the chat class");
        test.add("Hi i'm Lorenzo and im testing the chat class");
        assertEquals(test, chat.getChat());
    }
}