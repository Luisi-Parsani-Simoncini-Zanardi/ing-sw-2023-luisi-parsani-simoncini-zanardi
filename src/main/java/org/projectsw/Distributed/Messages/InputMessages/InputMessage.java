package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final SerializableInput input;
    protected final Client client;
    public InputMessage(SerializableInput input){
        this.input=input;
        this.client=null;
    }
    public InputMessage(Client client, SerializableInput input){
        this.input=input;
        this.client=client;
    }
    public void execute(Engine engine) throws RemoteException {}
    public void execute(Client client, Engine engine) throws RemoteException {}
    public SerializableInput getInput(){return this.input;}
    public Client getClient(){return this.client;}
}
