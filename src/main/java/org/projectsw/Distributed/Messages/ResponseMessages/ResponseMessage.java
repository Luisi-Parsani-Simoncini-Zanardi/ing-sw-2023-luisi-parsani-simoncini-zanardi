package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

/**
 * The abstract class ResponseMessage represents a response message that can be sent
 * between components in a system. It is serializable to allow for network communication.
 * ResponseMessage provides a common structure and behavior for response messages.
 */
public abstract class ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final SerializableGame model;

    /**
     * Constructs a new ResponseMessage with the specified SerializableGame model.
     * @param model the SerializableGame model associated with the response message
     */
    public ResponseMessage(SerializableGame model){
        this.model=model;
    }

    /**
     * Returns the SerializableGame model associated with the response message.
     * @return the SerializableGame model
     */
    public SerializableGame getModel(){
        return model;
    }

    /**
     * Executes the action associated with this response message on the specified TextualUI.
     * This method should be overridden in subclasses to define the specific behavior.
     * @param tui the TextualUI on which to execute the action
     */
    public void execute(TextualUI tui){}

    /**
     * Executes the action associated with this response message on the specified GraphicalUI.
     * This method should be overridden in subclasses to define the specific behavior.
     * @param gui the GraphicalUI on which to execute the action
     */
    public void execute(GuiManager gui){}
}
