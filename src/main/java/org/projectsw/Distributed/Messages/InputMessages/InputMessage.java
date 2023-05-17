package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.Model.InputController;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final InputController input;
    public InputMessage(InputController input){
        this.input=input;
    }
    public void execute(Engine engine) throws RemoteException {}
    public void execute(Client client, Engine engine) throws RemoteException {}
    public InputController getInput(){return this.input;}
}
