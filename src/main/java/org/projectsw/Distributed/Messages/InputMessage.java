package org.projectsw.Distributed.Messages;

import org.projectsw.Controller.Engine;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.Enums.UIEvent;

public abstract class InputMessage {
    private final InputController input;
    private UIEvent arg;

    public InputMessage(InputController input){
        this.input=input;
    }
    public void execute(Engine engine){
    }
}
