package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class ConfirmTilePlacement extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public ConfirmTilePlacement(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        engine.placeTiles(input.getAlphanumericID(),input.getInteger());
    }
}
