package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.InvalidNumberOfPlayersException;
import org.projectsw.Model.GameView;
import org.projectsw.Model.InputController;
import org.projectsw.View.UIEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import static org.projectsw.Config.maxPlayers;

public class ServerImpl extends UnicastRemoteObject implements Server{

    private final Engine controller = new Engine();

    public ServerImpl() throws RemoteException {
        super();
    }
    public ServerImpl(int port) throws RemoteException {
        super(port);
    }
    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void register(Client client) throws RemoteException {
        //TODO: qui si dovrà gestire la possibile reconnect se un savegame è presente
        if(this.controller.getClients().size()==0){
            int num = insertNumOfPlayers();
            try {
                this.controller.firstPlayerJoin(client.getNickname(), num);
            } catch (InvalidNumberOfPlayersException e) {
                throw new RuntimeException(e);
            }
        }else if(this.controller.getClients().size()<maxPlayers){
            try {
                this.controller.playerJoin(client.getNickname());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        this.controller.getClients().add(client);

        this.controller.getGame().addObserver((o, arg) -> {
            try {
                client.update(new GameView(this.controller.getGame()), arg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);//da gestire esplicitamente
            }
        });
    }

    private int insertNumOfPlayers(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("insert the number of players: ");
        return scanner.nextInt();
    }

    @Override
    public void update(Client client, UIEvent arg, InputController input) throws RemoteException {
        this.controller.update(client, arg, input);
    }
}
