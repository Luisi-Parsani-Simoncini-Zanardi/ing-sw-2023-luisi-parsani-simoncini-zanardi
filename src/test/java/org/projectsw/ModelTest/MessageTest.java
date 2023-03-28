package org.projectsw.ModelTest;

import org.junit.Test;
import org.projectsw.Model.Message;

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

        Message message = new Message(" ", "test Content");

        ArrayList<String> recipients = new ArrayList<>();
        String recipient1 = "Pippo";
        String recipient2 = "Mimmo";
        String recipient3 = "Giacobbe";

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
        Message message = new Message(" ", "test content");
        String contentTest = "test content";
        String contentAssert = message.getContent();
        assertEquals(contentTest, contentAssert);
    }
}
