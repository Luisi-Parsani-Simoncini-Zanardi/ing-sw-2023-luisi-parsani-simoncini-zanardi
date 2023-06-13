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


public class ClientImplementation extends UnicastRemoteObject implements Client, Serializable {
    private TextualUI tui;
    private GuiManager gui;
    private Observer<TextualUI, InputMessage> tuiObserver;
    private Observer<GuiManager, InputMessage> guiObserver;

    private long lastPingTimestamp;

    public ClientImplementation(Server server) throws RemoteException{
        super();
        initialize(server);
    }
    public ClientImplementation(int port, Server server) throws RemoteException {
        super(port);
        initialize(server);
    }
    public ClientImplementation(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, Server server) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }
    private void initialize(Server server) throws RemoteException{
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RemoteException("An error while registering client on server" + e.getCause());
        }
    }

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
    public void setGui (Server server) {
        gui = new GuiManager();
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

    public TextualUI getTui() {return this.tui;}

    /**
     * close the client
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
    public Observer<GuiManager, InputMessage>  getGuiObserver()  throws RemoteException{
        return guiObserver;
    }

    @Override
    public void ping() throws RemoteException {
    }


}
