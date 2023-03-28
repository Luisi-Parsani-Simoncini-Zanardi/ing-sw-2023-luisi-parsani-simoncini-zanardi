package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Chat;
import org.projectsw.Model.Message;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    /**
     * tests if the method addChatLog correctly add a String to the ArrayList chat
     */
    @Test
    void testAddChatLog(){
        Chat chat = new Chat();
        chat.addChatLog(new Message(" ", "Testing"));
        chat.addChatLog(new Message(" ", "class"));
        chat.addChatLog(new Message(" ", "chat"));
        assertEquals("Testing", chat.getChat().get(0).getContent());
        assertEquals("class", chat.getChat().get(1).getContent());
        assertEquals("chat", chat.getChat().get(2).getContent());
    }

    /**
     * tests if the method actually returns an ArrayList containing the right values
     */
    @Test
    void testGetChat() {
        Chat chat = new Chat();
        ArrayList<Message> prova = new ArrayList<>();
        chat.addChatLog(new Message(" ", "Hi i'm Lorenzo and im testing the chat class"));
        prova.add(new Message(" ", "Hi i'm Lorenzo and im testing the chat class"));
        assertEquals(prova, chat.getChat());
    }
}