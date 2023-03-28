package org.projectsw.ModelTest;

import org.junit.Test;
import org.projectsw.Model.Message;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

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
}
