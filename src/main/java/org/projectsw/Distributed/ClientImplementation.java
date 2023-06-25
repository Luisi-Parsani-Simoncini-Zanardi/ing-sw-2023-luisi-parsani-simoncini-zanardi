package org.projectsw.Distributed;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI;
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
    private GraphicalUI gui;
    private Observer<TextualUI, InputMessage> tuiObserver;
    private Observer<GraphicalUI, InputMessage> guiObserver;

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
        gui = new GraphicalUI();
        tui = null;
        gui.addObserver((o, arg) -> {
                /*try {
                    server.update(new InputController(tui.getClientUID(), tui.getPoint(),tui.getNumber(),tui.getNickname()), arg);
                    cambiare una volta finita GUI
                } catch (RemoteException e) {
                    throw new RuntimeException("Cannot send the client input" + e.getMessage());
                }*/
        });
        gui.run();
    }

    /**
     * Returns the textual user interface (TUI) of the client.
     * @return the textual user interface
     */
    public TextualUI getTui() {return this.tui;}

    /**
     * Closes the client application.
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void kill() throws RemoteException{
        tui.deleteObserver(tuiObserver);
        System.exit(0);
    }

    @Override
    public void update(ResponseMessage response) throws RemoteException {
        if(tui != null)
            tui.update(response);
        else
            gui.update(response);
    }

    @Override
    public String  getNickname() throws RemoteException{
        return tui.getNickname();
    }
    @Override
    public Observer<TextualUI, InputMessage>  getTuiObserver() throws RemoteException{
        return tuiObserver;
    }
    @Override
    public Observer<GraphicalUI, InputMessage>  getGuiObserver()  throws RemoteException{
        return guiObserver;
    }

    @Override
    public void ping() throws RemoteException {
    }


}
