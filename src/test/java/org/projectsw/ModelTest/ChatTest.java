package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Chat;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    //test that the method addChatLog correctly add a String to the ArrayList chat
    @Test
    void addChatLog(){
        Chat chat = new Chat();
        chat.addChatLog("Testing");
        chat.addChatLog("class");
        chat.addChatLog("chat");
        assertEquals("Testing", chat.getChat().get(0));
        assertEquals("class", chat.getChat().get(1));
        assertEquals("chat", chat.getChat().get(2));
    }

    //test that the method actually returns an ArrayList containing the right values
    @Test
    void getChat() {
        Chat chat = new Chat();
        ArrayList<String> prova = new ArrayList<String>();
        chat.addChatLog("Hi i'm Lorenzo and im testing the chat class");
        prova.add("Hi i'm Lorenzo and im testing the chat class");
        assertEquals(prova, chat.getChat());
    }
}