package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class Kill extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public Kill(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui) {
        try {
            tui.deleteObserver(tui.getClient().getTuiObserver());
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while removing the observer: " + e);
        }
        tui.kill(model.getInteger());
        System.exit(0);
    }
}