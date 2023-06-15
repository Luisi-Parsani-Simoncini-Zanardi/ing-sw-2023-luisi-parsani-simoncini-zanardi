package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.Message;
import static org.junit.jupiter.api.Assertions.*;
/**
 * test for the Message class methods
 */
public class MessageTest {

    /**
     * Test if the payload is returned correctly
     */
    @Test
    void getPayload() {
        Message message = new Message("Lorenzo","Luca","This is a test!!!");
        assertEquals("This is a test!!!",message.getPayload());
    }


    /**
     * Test if the Sender is returned correctly
     */
    @Test
    void getSender() {
        Message message = new Message("Lorenzo","Luca","This is a test!!!");
        assertEquals("Lorenzo",message.getSender());
    }

    /**
     * Test if the Scope is returned correctly
     */
    @Test
    void getScope() {
        Message message = new Message("Lorenzo","Luca","This is a test!!!");
        assertEquals("Luca",message.getScope());
    }
}
