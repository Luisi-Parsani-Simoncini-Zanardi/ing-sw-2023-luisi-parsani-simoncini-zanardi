package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a response message indicating a chat message response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ChatMessage extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ChatMessage object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ChatMessage(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the ChatMessage message on the specified TextualUI.
     * Updates the TextualUI based on the scope of the message.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui){
        if(model.getMessage().getScope().equals(Config.error))
            System.out.println(model.getMessage().getPayload());
        else if(model.getMessage().getScope().equals(Config.everyone)) {
            if (!Objects.equals(model.getMessage().getSender(), tui.getNickname()))
                tui.addBufferMessage(model.getMessage());
        } else if(model.getMessage().getScope().equals(tui.getNickname()))
            tui.addBufferMessage(model.getMessage());
    }

    @Override
    public void execute(GuiManager gui) {
        gui.updateChat();
    }
}
