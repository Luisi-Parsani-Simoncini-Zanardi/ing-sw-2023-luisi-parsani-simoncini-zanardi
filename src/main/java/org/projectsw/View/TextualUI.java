package org.projectsw.View;

import org.projectsw.Model.Board;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.Message;
import org.projectsw.Util.Observable;

import java.awt.*;
import java.util.Scanner;

public class TextualUI extends Observable<UIEvent> implements Runnable{

    private UIState state;
    private final Object lock = new Object();
    private Integer index;
    private Point coordinate;

    private UIState getState(){
        synchronized(lock){
            return state;
        }
    }
    public void setState(UIState state){
        synchronized (lock){
            this.state = state;
            lock.notifyAll();
        }
    }
    public Integer getIndex(){
        return this.index;
    }
    public Point getCoordinate(){
        return this.coordinate;
    }

    //TODO da finire
    @Override
    public void run() {
        while(getState() != UIState.GAME_ENDING){
            while(getState() == UIState.OPPONENT_TURN){
                synchronized (lock){
                    try{lock.wait();
                    }catch(InterruptedException e){
                        System.err.println("Interrupted while waiting for server: " + e.getMessage());
                    }
                }
            }
            System.out.println("---YOUR TURN---");
            do{
                coordinate = selectTilesInput();
                setChangedAndNotifyObservers(UIEvent.TILE_SELECTION);
            }while(chooseTiles());
            setChangedAndNotifyObservers(UIEvent.CONFIRM_SELECTION);
            do{
                index = selectColumnInput();
            }while(chooseColumn());
            //parte place tiles con richiesta di ordine di inserimento nella shelf all'utente
            setChangedAndNotifyObservers(UIEvent.TILE_INSERTION);
            setState(UIState.OPPONENT_TURN);
        }
    }

    private boolean chooseColumn(){
        System.out.println("\nAre you sure?\n1: yes\n2: no");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt() == 1;
    }

    private Integer selectColumnInput(){
        System.out.println("\nIn which column do you want to insert your tiles?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    private boolean chooseTiles(){
        System.out.println("do you want to choose another tile?\n1: yes\n2: no");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt() == 1;
    }
    private Point selectTilesInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\ninsert the row: ");
        int row = scanner.nextInt();
        System.out.println("\ninsert the column: ");
        int column = scanner.nextInt();
        return new Point(row, column);
    }

    private void showBoard(GameView model){
        Board board = model.getGameBoard();
        if(board == null)
            return;
        System.out.println("\n");
        board.printBoard();
    }

    private void showShelf(GameView model){
        System.out.println("\n--- "+model.getCurrentPlayerName()+" ---\n");
        model.getCurrentPlayerShelf().printShelf();
    }

    private void showCurrentPlayer(GameView model){
        System.out.println("\nThe current player is: "+model.getCurrentPlayerName());
    }

    private void showChat(GameView model){
        for(Message message : model.getChat().getChat())
            System.out.println("\n"+message.getSender().getNickname()+": "+message.getContent());
    }

    public void update(GameView model, Game.Event arg){
        switch(arg){
            case UPDATED_BOARD -> showBoard(model);
            case UPDATED_SHELF -> showShelf(model);
            case UPDATED_CURRENT_PLAYER -> showCurrentPlayer(model);
            case UPDATED_CHAT -> showChat(model);
        }
    }
}
