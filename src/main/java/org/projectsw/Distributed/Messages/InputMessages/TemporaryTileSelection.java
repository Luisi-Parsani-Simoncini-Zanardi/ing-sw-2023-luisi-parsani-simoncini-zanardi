package org.projectsw.Distributed.Messages.InputMessages;
import org.projectsw.Controller.Engine;
import org.projectsw.Model.InputController;

import java.io.Serial;
import java.io.Serializable;

public class TemporaryTileSelection extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public TemporaryTileSelection(InputController input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        engine.placeTiles(input.getIndex());
    }
}
