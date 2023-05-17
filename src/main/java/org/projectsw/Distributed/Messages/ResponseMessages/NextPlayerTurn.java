package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UIState;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

public class NextPlayerTurn extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public NextPlayerTurn(GameView model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui){
        if (model.getCurrentPlayerName().equals(tui.getNickname())) {
            tui.setState(UIState.YOUR_TURN);
            tui.setNoMoreSelectableTiles(true);
            tui.setNoMoreTemporaryTiles(true);
            System.out.println(ConsoleColors.PURPLE_BOLD+"---YOUR TURN---"+ConsoleColors.RESET);
        }
    }
}
