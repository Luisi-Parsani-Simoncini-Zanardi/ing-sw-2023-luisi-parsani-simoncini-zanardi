package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

public class NextPlayerTurn extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public NextPlayerTurn(SerializableGame model) {
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
            tui.setTileSelectionPossible(true);
            tui.setTemporaryTilesHold(true);
        }
    }

    @Override
    public void execute(GuiManager guiManager) {
        if (guiManager.getEndState().equals(UIEndState.LOBBY)) {
            guiManager.setEndState(UIEndState.RUNNING);
            guiManager.notifyResponse1();
        }
        if (model.getPlayerName().equals(guiManager.getNickname())) {
            guiManager.setGameMainFrameState(UITurnState.YOUR_TURN_SELECTION);
            guiManager.setTileSelectionPossible(true);
            guiManager.setTemporaryTilesHold(true);
        } else {
            guiManager.setGameMainFrameState(UITurnState.OPPONENT_TURN);
        }
        guiManager.notifyGameMainFrameTurnLock();
    }
}
