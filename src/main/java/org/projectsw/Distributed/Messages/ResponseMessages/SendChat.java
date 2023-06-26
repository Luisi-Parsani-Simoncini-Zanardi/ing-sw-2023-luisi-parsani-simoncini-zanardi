package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.Message;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a response message indicating a chat response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class SendChat extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SendChat object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public SendChat(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the SendChat message on the specified TextualUI.
     * Displays the chat messages based on the scope and participants of the chat.
     * Updates the TextualUI to indicate the chat status.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui) {
        int counter = 0;
        if (model.getClientNickname().equals(Config.everyone)){
            System.out.println("---GLOBAL CHAT---");
            for (Message message : model.getChat()) {
                if (message.getScope().equals(Config.everyone)) {
                    System.out.println(tui.getNameColors().get(message.getSender()) + message.getSender() + ": " + ConsoleColors.RESET + message.getPayload());
                    counter++;
                }
            }
        }
        else {
            System.out.println("---CHAT WITH "+model.getClientNickname()+"---");
            for (Message message : model.getChat()) {
                if ((message.getScope().equals(tui.getNickname()) && message.getSender().equals(model.getClientNickname())) ||
                        (message.getScope().equals(model.getClientNickname()) && message.getSender().equals(tui.getNickname()))) {
                    System.out.println(tui.getNameColors().get(message.getSender()) + message.getSender() + ": " + ConsoleColors.RESET + message.getPayload());
                    counter++;
                }
            }
        }
        if(counter == 0)
            System.err.println("This chat is empty...");
        System.out.print("\n");
    }
}
