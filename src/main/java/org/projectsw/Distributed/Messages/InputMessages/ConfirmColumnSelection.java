package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class ConfirmColumnSelection extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ConfirmColumnSelection(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        engine.selectColumn(input.getAlphanumericID(),input.getInteger());
    }
}
