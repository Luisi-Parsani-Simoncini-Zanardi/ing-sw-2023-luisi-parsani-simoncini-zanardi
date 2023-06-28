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
     * Tests if the method addChatLog correctly add a Message to the ArrayList chat.
     */
    @Test
    void testChat(){
        Chat chat = new Chat();
        Message message1 = new Message("A","B","Test1");
        chat.addChatLog(message1);
        assertEquals(1,chat.getMessages().size());
        assertEquals("Test1",chat.getMessages().get(0).getPayload());
        assertEquals("A",chat.getMessages().get(0).getSender());
        assertEquals("B",chat.getMessages().get(0).getScope());
        Message message2 = new Message("B","A","Test2");
        chat.addChatLog(message2);
        assertEquals(2,chat.getMessages().size());
        assertEquals("Test2",chat.getMessages().get(1).getPayload());
        assertEquals("B",chat.getMessages().get(1).getSender());
        assertEquals("A",chat.getMessages().get(1).getScope());
    }

    /**
     * Tests if the method actually returns an ArrayList containing the right values.
     */
    @Test
    void testGetChat() {
        Chat chat = new Chat();
        Message message1 = new Message("A","B","Test1");
        Message message2 = new Message("B","A","Test2");
        chat.addChatLog(message1);
        chat.addChatLog(message2);
        assertEquals(2, chat.getMessages().size());
        assertEquals("Test1",chat.getMessages().get(0).getPayload());
        assertEquals("A",chat.getMessages().get(0).getSender());
        assertEquals("B",chat.getMessages().get(0).getScope());
        assertEquals("Test2",chat.getMessages().get(1).getPayload());
        assertEquals("B",chat.getMessages().get(1).getSender());
        assertEquals("A",chat.getMessages().get(1).getScope());
    }
}