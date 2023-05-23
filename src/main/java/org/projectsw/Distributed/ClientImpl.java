package org.projectsw.Distributed;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Observer;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;


public class ClientImpl extends UnicastRemoteObject implements Client{
    private TextualUI tui;
    private GraphicalUI gui;
    private Observer<TextualUI, InputMessage> tuiObserver;
    private Observer<GraphicalUI, InputMessage> guiObserver;

    public ClientImpl(Server server) throws RemoteException{
        super();
        initialize(server);
    }
    public ClientImpl(int port, Server server) throws RemoteException {
        super(port);
        initialize(server);
    }
    public ClientImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf, Server server) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }
    private void initialize(Server server) throws RemoteException{
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RemoteException("Cannot register client on server" + e.getCause());
        }
    }

    public void setTui (Server server){
        gui = null;
        tui = new TextualUI(this);
        tuiObserver = (o, input) -> {
            try {
                server.update(this, input);
            }catch(RemoteException e){
                throw new RuntimeException("Network error occurred: "+e.getMessage());
            }
        };
        tui.addObserver(tuiObserver);
        tui.run();
    }
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
     * close the client
     */
    @Override
    public void kill(SerializableGame game) throws RemoteException{
        tui.deleteObserver(tuiObserver);
        tui.kill(game.getInteger());
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
    public void setID(SerializableGame model){
        tui.setID(model.getClientID());
    }

    @Override
    public int getID(){return tui.getClientUID();}

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
    public void setCorrectResponse(boolean response){
        tui.setIsNotCorrect(response);
    }

}
