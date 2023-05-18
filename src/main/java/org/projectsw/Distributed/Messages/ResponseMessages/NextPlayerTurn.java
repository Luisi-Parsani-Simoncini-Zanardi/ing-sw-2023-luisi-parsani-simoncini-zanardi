package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.Enums.UITurnState;
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
        tui.setRepeat(true);
        if (model.getCurrentPlayerName().equals(tui.getNickname())) {
            tui.setTurnState(UITurnState.YOUR_TURN_PHASE1);
            tui.setNoMoreSelectableTiles(true);
            tui.setNoMoreTemporaryTiles(true);
            System.out.println("---YOUR TURN---");
            System.out.println("---CHOOSE AN ACTION---");
        }
    }
}
