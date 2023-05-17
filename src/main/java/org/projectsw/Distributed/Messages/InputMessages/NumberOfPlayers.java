package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Model.InputController;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;

public class NumberOfPlayers extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public NumberOfPlayers(InputController input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        engine.setNumberOfPlayers(input.getIndex());
    }
}
