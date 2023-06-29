package org.projectsw.Distributed;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * The implementation of the client interface.
 */
public class ClientImplementation extends UnicastRemoteObject implements Client, Serializable {
    private TextualUI tui;
    private GuiManager gui;
    private Observer<TextualUI, InputMessage> tuiObserver;
    private Observer<GuiManager, InputMessage> guiObserver;

    /**
     * Constructs a new ClientImplementation instance with the specified server.
     * @param server the server to register the client with
     * @throws RemoteException if a remote communication error occurs
     */
    public ClientImplementation(Server server) throws RemoteException{
        super();
        initialize(server);
    }

    /**
     * Constructs a new ClientImplementation instance with the specified port and server.
     * @param port   the port on which to export the remote object
     * @param server the server to register the client with
     * @throws RemoteException if a remote communication error occurs
     */
    public ClientImplementation(int port, Server server) throws RemoteException {
        super(port);
        initialize(server);
    }

    /**
     * Constructs a new ClientImplementation instance with the specified port, client socket factory, server socket factory, and server.
     * @param port   the port on which to export the remote object
     * @param csf    the client socket factory for creating client sockets
     * @param ssf    the server socket factory for creating server sockets
     * @param server the server to register the client with
     * @throws RemoteException if a remote communication error occurs
     */
    public ClientImplementation(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, Server server) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }

    /**
     * Initializes the client by registering it with the specified server.
     * @param server the server to register the client with
     * @throws RemoteException if a remote communication error occurs
     */
    private void initialize(Server server) throws RemoteException{
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RemoteException("An error while registering client on server" + e.getCause());
        }
    }

    /**
     * Sets the textual user interface (TUI) for the client.
     * @param server the server to update with user input
     */
    public void setTui (Server server){
        gui = null;
        tui = new TextualUI(this);
        tuiObserver = (o, input) -> {
            try {
                server.update(this,input);
            }catch(RemoteException e){
                throw new RuntimeException("A network error occurred: " + e.getMessage());
            }
        };
        tui.addObserver(tuiObserver);
        tui.run();
    }

    /**
     * Sets the graphic user interface (GUI) for the client.
     * @param server the server to update with user input
     */
    public void setGui (Server server) {
        gui = new GuiManager(this);
        tui = null;
        gui.addObserver((o, input) -> {
            try {
                server.update(this,input);
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred: " + e.getMessage());
            }
        });
        gui.run();
    }

    /**
     * Closes the client application.
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void kill() throws RemoteException{
        if(tui != null)
            tui.deleteObserver(tuiObserver);
        else
            gui.deleteObserver(guiObserver);
        System.exit(0);
    }

    /**
     * Updates the user interface (either TextualUI or GraphicalUI) with the given ResponseMessage.
     * If the TextualUI is available, it is updated with the response; otherwise, the GraphicalUI is updated.
     * @param response the ResponseMessage to update the user interface with
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void update(ResponseMessage response) throws RemoteException {
        if(tui != null)
            tui.update(response);
        else
            gui.update(response);
    }

    /**
     * empty method used for checking the client connection status
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void ping() throws RemoteException {
    }


}
