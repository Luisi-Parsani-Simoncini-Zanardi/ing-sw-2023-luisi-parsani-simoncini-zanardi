package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientImpl implements Client{

    private TextualUI tui;
    private GraphicalUI gui;

    public ClientImpl(Server server){
        chooseUI();
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(tui != null)
            tui.addObserver((o, arg) -> {
                try {
                    server.update(this, arg);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        else
            gui.addObserver((o, arg) -> {
                try {
                    server.update(this, arg);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
    }
    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {
        //Passa la GameView alla TextualUI (la GameView viene creata effettivamente nella register in ServerImpl)
        if(tui != null)
            tui.update(o, arg);
        else
            gui.update(o, arg);
    }

    private void chooseUI(){
        System.out.println("which UI do you want to use?\n1: Gui\n2: Tui");
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextInt()==1){
            gui = new GraphicalUI();
            tui = null;
        }else{
            tui = new TextualUI();
            gui = null;
        }
    }
}
