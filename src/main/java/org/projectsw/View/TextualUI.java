package org.projectsw.View;
import org.projectsw.Model.*;
import org.projectsw.Util.Observable;

import java.awt.*;
import java.util.Scanner;

public class TextualUI extends Observable<UIEvent> implements Runnable{

    private UIState state = UIState.OPPONENT_TURN;//in modo da aspettare all'inizio e partire solo quando il server tramite il controller mi da il via
    private final Object lock = new Object();
    private Integer number;
    private Point coordinate;
    private String nickname;

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
        return this.number;
    }
    public Point getCoordinate(){
        return this.coordinate;
    }
    public String getNickname(){return this.nickname;}

    //TODO: da finire
    @Override
    public void run() {
        try{
            insertNickname();
        }catch(RuntimeException e){
            System.out.println("");
            insertNickname();
        }
     /*   while(getState() != UIState.GAME_ENDING){
             while(getState() == UIState.OPPONENT_TURN){
                synchronized (lock){//forse va eliminata perchè superflua
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
                number = selectColumnInput();
            }while(chooseColumn());
            setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);

            //TODO: parte place tiles con richiesta di ordine di inserimento nella shelf all'utente

            setChangedAndNotifyObservers(UIEvent.TILE_INSERTION);
            setState(UIState.OPPONENT_TURN);
        }*/
    }
    public void update(GameView model, Game.Event arg){
        switch(arg){
            case UPDATED_BOARD -> showBoard(model);
            case UPDATED_SHELF -> showShelf(model);
            case UPDATED_CURRENT_PLAYER -> showCurrentPlayer(model);
            case UPDATED_CHAT -> showChat(model);
        }
    }

    private boolean chooseColumn(){
        System.out.println("Are you sure?\n1: yes\n2: no");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if(choice == 2){
            setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);//così rimuviamo un automatico la colonna scelta precedentemente
        }
        return choice == 2;
    }

    private Integer selectColumnInput(){
        System.out.println("In which column do you want to insert your tiles?");
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
        System.out.println("insert the row: ");
        int row = scanner.nextInt();
        System.out.println("insert the column: ");
        int column = scanner.nextInt();
        return new Point(row, column);
    }

    private void showBoard(GameView model){
        Board board = model.getGameBoard();
        if(board == null)
            return;
        System.out.println("---GAME BOARD---");
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

    private void insertNickname(){
        Scanner scanner = new Scanner(System.in);
        nickname = scanner.nextLine();
        coordinate = null;
        number = null;
        setChangedAndNotifyObservers(UIEvent.CHOOSE_NICKNAME);
    }
}
















//per gestire la chat si può far partire un tread chat in cui si può solo scrivere in chat per ogni giocatore che aspetta
//per gestirlo bisognerà usare synchronized (lock) la tui si metterà in continuazione in ascolto di un input che verrà mandato
// ad entrambi i thread di chat e del gioco, ci sarà un controllo di formato (ad esempio: per scrivere in chat bisogna usare un determinato formato
//  /msg "questo è il messaggio". se si legge /msg allora è un messaggio da gestire e il thread della chat lo farà mentre quello del gioco lo scarta).
//poi si fa una setChangedAndNotifyObservers(UIEvent.SAY_IN_CHAT); e aggiorno le view
//la chat deve anche poter inviare a un singolo giocatore il messaggio quindi bisognerà fare una classe chatSegreta
//notificherà il singolo client (es: /msg/nomeGiocatore "questo è il messaggio"). dato che potrà avere un singolo observer bisogna farne una per client
// alla creazione dello stesso. poremmo metterla in player in modo che ognuno abbia la sua e quando si vuole scrivere a un determinato giocatore
//si va a modificare solo la sua chat e si notifica l'observer cioè il client (e di conseguenza la UI) del player destinatario
//Questo però potrebbe creare un ciclo di oggetti tra game,player e chat che possono dare problemi alla serializzazione (loop
// infinito). suggerisco di rifare chat come interfaccia e fare due figli:
// chatSegreta(che va nel player)
// chatPubblica (che va nel game)
//in questo modo la serializzazione non ha problemi sicuramente e l'implementazione usa il paradigna a oggetti at its finest
