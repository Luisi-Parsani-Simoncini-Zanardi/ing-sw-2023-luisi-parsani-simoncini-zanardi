package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class DeleteModelObserver extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public DeleteModelObserver(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        engine.removeObserver(engine.getClients().getKey(input.getClientNickname()));
    }
}
