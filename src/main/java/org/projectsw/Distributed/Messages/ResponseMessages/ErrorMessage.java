package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ErrorMessage extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ErrorMessage(SerializableGame model) {
        super(model);
    }

    @Override
    public void execute(TextualUI tui) {
        System.err.println(model.getClientNickname());
    }
}
