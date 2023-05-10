package org.projectsw.Distributed;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.ConsoleColors;
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
        displayLogo();
        try {
            server.register(this);
        } catch (RemoteException e) {
            throw new RuntimeException("Cannot register client on server" + e.getMessage());
        }
        chooseUI();
        if(tui != null)
            tui.addObserver((o, arg) -> {
                switch (arg){
                    case TILE_SELECTION -> {
                        try {
                            server.update(new InputController(tui.getClientUID(), tui.getPoint()), arg);
                        } catch (RemoteException e) {
                            throw new RuntimeException("Cannot send the tile selection to the server" + e.getMessage());
                        }
                    }
                    case TILE_INSERTION, COLUMN_SELECTION -> {
                        try {
                            server.update(new InputController(tui.getClientUID(), tui.getNumber()), arg);
                        } catch (RemoteException e) {
                            throw new RuntimeException("Cannot send the chat message to the server: " + e.getMessage());
                        }
                    }
                    case SAY_IN_CHAT -> {
                        try {
                            server.update(new InputController(tui.getClientUID(), tui.getString()), arg);
                        } catch (RemoteException e) {
                            throw new RuntimeException("Cannot send the message to the server: " + e.getMessage());
                        }
                    }
                    case CHOOSE_NICKNAME -> {
                        try {
                            server.update(new InputController(tui.getClientUID(), tui.getNickname()), arg);
                        } catch (RemoteException e) {
                            throw new RuntimeException("Cannot send the client nickname to the server: " + e.getMessage());
                        }
                    }
                    case CHOOSE_NICKNAME_AND_PLAYER_NUMBER -> {
                        try {
                            server.update(new InputController(tui.getClientUID(), tui.getNumber(), tui.getNickname()), arg);
                        } catch (RemoteException e) {
                            throw new RuntimeException("Cannot send the client nickname and the number of players to the server: " + e.getMessage());
                        }
                    }
                    default -> {
                        try {
                            server.update(new InputController(tui.getClientUID()), arg);
                        } catch (RemoteException e) {
                            throw new RuntimeException("Cannot send the client input to the server: " + e.getMessage());
                        }
                    }
            }
            });
        else
            gui.addObserver((o, arg) -> {
                /*try {
                    server.update(new InputController(tui.getClientUID(), tui.getPoint(),tui.getNumber(),tui.getNickname()), arg);
                    cambiare una volta finita GUI
                } catch (RemoteException e) {
                    throw new RuntimeException("Cannot send the client input" + e.getMessage());
                }*/
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

    public void displayLogo(){
        System.out.println(ConsoleColors.PURPLE_BOLD + "  __  __" + ConsoleColors.BLUE_BOLD + "        _____ _          _  __ _      ");
        System.out.println(ConsoleColors.PURPLE_BOLD + " |  \\/  |" + ConsoleColors.BLUE_BOLD + "      / ____| |        | |/ _(_)     ");
        System.out.println(ConsoleColors.PURPLE_BOLD + " | \\  / |_   _" + ConsoleColors.BLUE_BOLD + "| (___ | |__   ___| | |_ _  ___ ");
        System.out.println(ConsoleColors.PURPLE_BOLD + " | |\\/| | | | |" + ConsoleColors.BLUE_BOLD + "\\___ \\| '_ \\ / _ \\ |  _| |/ _ \\");
        System.out.println(ConsoleColors.PURPLE_BOLD + " | |  | | |_| |" + ConsoleColors.BLUE_BOLD + "____) | | | |  __/ | | | |  __/");
        System.out.println(ConsoleColors.PURPLE_BOLD + " |_|  |_|\\__, |" + ConsoleColors.BLUE_BOLD + "_____/|_| |_|\\___|_|_| |_|\\___|");
        System.out.println(ConsoleColors.PURPLE_BOLD + "          __/ |                               " );
        System.out.println("         |___/                                " + ConsoleColors.RESET);
    }

}
