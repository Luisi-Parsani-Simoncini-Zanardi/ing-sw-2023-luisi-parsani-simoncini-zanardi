package org.projectsw.Distributed.Messages.InputMessages;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.View.SerializableInput;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * An abstract class representing an input message.
 * Input messages are used to communicate input data between clients and the engine.
 * They can be executed on the engine to perform specific actions.
 */
public abstract class InputMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final SerializableInput input;
    protected final Client client;

    /**
     * Constructs a new InputMessage with the given input data.
     * @param input the input data for the message
     */
    public InputMessage(SerializableInput input){
        this.input=input;
        this.client=null;
    }

    /**
     * Executes the input message on the specified engine.
     * @param engine the engine on which to execute the message
     * @throws RemoteException if a remote communication error occurs
     */
    public void execute(Engine engine) throws RemoteException {}

    /**
     * Executes the input message on the specified client and engine.
     * @param client the client on which to execute the message
     * @param engine the engine on which to execute the message
     * @throws RemoteException if a remote communication error occurs
     */
    public void execute(Client client, Engine engine) throws RemoteException {}

    /**
     * Returns the input data associated with the message.
     * @return the input data
     */
    public SerializableInput getInput(){return this.input;}

    /**
     * Returns the client associated with the message.
     * @return the client
     */
    public Client getClient(){return this.client;}
}
