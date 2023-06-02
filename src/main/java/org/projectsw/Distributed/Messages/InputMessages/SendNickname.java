package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class SendNickname extends InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public SendNickname(Client client, SerializableInput input) {
        super(client,input);
    }

    @Override
    public void execute(Engine engine) throws RemoteException{
        try {
            engine.takeNick(getClient(), getInput());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
