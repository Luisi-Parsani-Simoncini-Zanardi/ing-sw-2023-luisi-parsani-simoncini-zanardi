package org.projectsw.ModelTest;

import org.junit.Test;
import org.projectsw.Model.Message;
import org.projectsw.Model.Player;
import java.time.LocalTime;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test for the Message class methods
 */
public class MessageTest {

    /**
     * test if the setRecipients method correctly set the recipients list
     */
    @Test
    public void setRecipientsTest() {
        LocalTime time = LocalTime.now();
        Player sender = new Player("Pippo", 1);
        Message message = new Message(sender, "test Content", time);

        ArrayList<Player> recipients = new ArrayList<>();
        Player recipient1 = new Player("Nabbus", 2);
        Player recipient2 = new Player("Mimmo", 3);
        Player recipient3 = new Player("Giacobbe", 2);

        recipients.add(recipient1);
        recipients.add(recipient2);
        recipients.add(recipient3);
        message.setRecipients(recipients);

        assertEquals(recipient1, message.getRecipients().get(0));
        assertEquals(recipient2, message.getRecipients().get(1));
        assertEquals(recipient3, message.getRecipients().get(2));

    }

    /**
     * test if the method getContent retrieve the correct content
     */
    @Test
    public void getContentTest() {
        LocalTime time = LocalTime.now();
        Player sender = new Player("Pippo", 1);
        Message message = new Message(sender, "test content", time);
        String contentTest = "test content";
        String contentAssert = message.getContent();
        assertEquals(contentTest, contentAssert);
    }

    /**
     * test if the method getSender retrieve the correct sender
     */
    @Test
    public void getSenderTest() {
        LocalTime time = LocalTime.now();
        Player sender = new Player("Pippo", 1);
        Message message = new Message(sender, "test content", time);
        String senderTest = "Pippo";
        Player senderAssert = message.getSender();
        assertEquals(senderTest, senderAssert.getNickname());
    }

    /**
     * test if the method getTime retrieve the correct time
     */
    @Test
    public void getTimeTest() {
        LocalTime time = LocalTime.now();
        Player sender = new Player("Pippo", 1);
        Message message = new Message(sender, "test content", time);
        assertEquals(time, message.getTime());
    }
}
