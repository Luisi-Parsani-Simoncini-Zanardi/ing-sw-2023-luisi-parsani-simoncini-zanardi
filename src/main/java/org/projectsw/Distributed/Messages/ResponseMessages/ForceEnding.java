package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ForceEnding extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ForceEnding(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        tui.setFlag(false);
        tui.setWaitResult(false);
        System.out.println("The game is ended press any key to wait for results...");
    }
}
