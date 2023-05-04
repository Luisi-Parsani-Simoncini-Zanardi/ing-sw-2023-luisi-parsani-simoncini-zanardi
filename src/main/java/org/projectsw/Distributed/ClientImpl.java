package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientImpl extends UnicastRemoteObject implements Client{
    private TextualUI tui;
    private GraphicalUI gui;


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
            throw new RuntimeException("cannot register client on server" + e.getMessage());//TODO: gestire esplicitamente
        }
        chooseUI();
        if(tui != null)
            tui.addObserver((o, arg) -> {
                try {
                    server.update(this, arg, new InputController(tui.getPoint(),tui.getIndex(),tui.getString()));
                } catch (RemoteException e) {
                    throw new RuntimeException("cannot send the client input" + e.getMessage());//TODO: gestire esplicitamente
                }
            });
        else
            gui.addObserver((o, arg) -> {
                try {
                    server.update(this, arg, new InputController(tui.getPoint(),tui.getIndex(), tui.getString()));
                } catch (RemoteException e) {
                    throw new RuntimeException("cannot send the client input" + e.getMessage());//TODO: gestire esplicitamente
                }
            });
        runView();
    }

    public TextualUI getTui(){
        return this.tui;
    }
    public GraphicalUI getGui() {
        return this.gui;
    }
    private void runView(){
        if(this.tui != null)
            tui.run();
        else
            gui.run();
    }

    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {
        if(tui != null)
            tui.update(o, arg);
        else
            gui.update(o, arg);
    }

    private void chooseUI(){
        System.out.println("Which UI do you want to use?\n1: Gui\n2: Tui");
        Scanner scanner = new Scanner(System.in);
        int selected;
        try {
             selected = scanner.nextInt();
        } catch (InputMismatchException e) {
            selected = 0;
        }
        if(selected==1){
            tui = null;
            gui = new GraphicalUI();
        }else {
            if ((selected==2)){
            gui = null;
            tui = new TextualUI();
            } else {
                System.out.println("Invalid value. Try again...");
                chooseUI();
            }
        }
    }
}
