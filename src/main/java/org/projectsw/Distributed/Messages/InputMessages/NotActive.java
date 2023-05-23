package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Model.Player;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;

public class NotActive extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public NotActive(SerializableInput input) {
        super(input);
    }
    @Override
    public void execute(Engine engine){
        for(Player player : engine.getGame().getPlayers()){
            if(input.getNickname().equals(player.getNickname()))
                player.setIsActive(false);
        }
    }
}
