package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class ErrorLobbyClosed extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorLobbyClosed(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        System.out.println(ConsoleColors.RED + "Sorry, the lobby is full. Exiting..." + ConsoleColors.RESET);
        System.exit(0);
    }
    public void execute(GraphicalUI gui){}
}