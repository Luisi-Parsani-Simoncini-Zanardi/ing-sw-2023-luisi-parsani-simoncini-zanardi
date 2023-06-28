package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Represents a client skeleton that acts as a remote proxy for a client.
 * Implements the Client interface and Serializable interface.
 */
public class ClientSkeleton implements Client, Serializable {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    /**
     * Constructs a new ClientSkeleton object with the specified socket.
     * @param socket the socket for communication with the client
     * @throws RemoteException if a remote communication error occurs
     */
    public ClientSkeleton(Socket socket) throws RemoteException{
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("An error while creating output stream: "+e.getMessage());
        }
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("An error while creating input stream: "+e.getMessage());
        }
    }

    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    @Override
    public Observer<TextualUI, InputMessage> getTuiObserver() throws RemoteException {
        return null;
    }

    @Override
    public Observer<GuiManager, InputMessage> getGuiObserver() throws RemoteException {
        return null;
    }

    @Override
    public void ping() throws RemoteException {

    }

    @Override
    public TextualUI getTui() throws RemoteException {
        return null;
    }

    /**
     * Sends a response message to the client.
     * @param response the response message to send
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void update(ResponseMessage response) throws RemoteException {
        try {
            oos.writeObject(response);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            throw new RemoteException("An error while sending a response message: ", e);
        }
    }

    @Override
    public void kill() throws RemoteException {
    }

    /**
     * Receives input from the client and updates the server with the input message.
     * @param server the server object to update with the input message
     * @throws RemoteException if a remote communication error occurs
     */
    public void receive(Server server) throws RemoteException{
        InputMessage input;
        try {
            input = (InputMessage) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("An error while receiving choice from client: ", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("An error while deserializing choice from client: ", e);
        }
        server.update(this,input);
    }
}