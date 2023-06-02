package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class AskForChat extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public AskForChat(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        try {
            engine.sendChat(input.getString(),input.getAlphanumericID());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
