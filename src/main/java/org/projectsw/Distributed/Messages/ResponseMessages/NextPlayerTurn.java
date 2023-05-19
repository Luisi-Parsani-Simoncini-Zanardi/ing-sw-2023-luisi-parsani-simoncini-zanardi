package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.GameView;
import org.projectsw.View.Enums.UIEndState;
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
        if (tui.getEndState() == UIEndState.LOBBY) {
            synchronized (tui) {
                tui.setEndState(UIEndState.RUNNING);
                tui.notifyAll();
            }
        }
        if (model.getPlayerName().equals(tui.getNickname())) {
            tui.setTurnState(UITurnState.YOUR_TURN_SELECTION);
            tui.setNoMoreSelectableTiles(true);
            tui.setNoMoreTemporaryTiles(true);
            System.out.println("   ---YOUR TURN---");
        }
        System.out.println("---CHOOSE AN ACTION---");
        System.out.println("Press 0 to see all possible actions...");
    }
}
