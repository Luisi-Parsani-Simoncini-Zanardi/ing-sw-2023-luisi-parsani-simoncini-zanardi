package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class AskForChat extends InputMessage implements Serializable {

    //TODO: da implementare
    @Serial
    private static final long serialVersionUID = 1L;
    public AskForChat(SerializableInput input) {
        super(input);
    }
}
