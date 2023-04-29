package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientImpl implements Client{

    private TextualUI tui;
    private GraphicalUI gui;
    private final String nickname;

    public ClientImpl(Server server){
        this.nickname = insertNickname();
        chooseUI();
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(tui != null)
            tui.addObserver((o, arg) -> {
                try {
                    server.update(this, arg, new InputController(tui.getCoordinate(),tui.getIndex()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        else
            gui.addObserver((o, arg) -> {
                try {
                    server.update(this, arg, new InputController(tui.getCoordinate(),tui.getIndex()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        runView();
    }

    @Override
    public String getNickname(){
        return this.nickname;
    }

    public TextualUI getTui(){
        return this.tui;
    }

    public GraphicalUI getGui() {
        return gui;
    }

    private void runView(){
        if(this.tui != null)
            tui.run();
        else
            gui.run();
    }
    private String insertNickname(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("insert your nickname: ");
        return scanner.nextLine();
    }


    @Override
    public void update(GameView o, Game.Event arg) throws RemoteException {
        if(tui != null)
            tui.update(o, arg);
        else
            gui.update(o, arg);
    }

    private void chooseUI(){
        System.out.println("which UI do you want to use?\n1: Gui\n2: Tui");
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextInt()==1){
            tui = null;
            gui = new GraphicalUI();
        }else{
            gui = null;
            tui = new TextualUI();
        }
    }
}
