package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class EmptyTemporaryPoints extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public EmptyTemporaryPoints(GameView model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        System.out.println(ConsoleColors.RED + "You don't have any tiles selected. Please select any tile..." + ConsoleColors.RESET);
    }
}
