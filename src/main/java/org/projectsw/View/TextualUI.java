package org.projectsw.View;
import org.projectsw.Model.*;
import org.projectsw.Util.Observable;

import java.awt.*;
import java.util.Scanner;

public class TextualUI extends Observable<UIEvent> implements Runnable{

    private UIState state = UIState.OPPONENT_TURN;//in modo da aspettare all'inizio e partire solo quando il server tramite il controller mi da il via
    private final Object lock = new Object();
    private Integer number;
    private Point point;
    private String nickname;
    private String string;
    private int clientUID = 0;

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
    public String getString(){return this.string;}
    public Integer getNumber(){
        return this.number;
    }
    public Point getPoint(){
        return this.point;
    }
    public String getNickname(){return this.nickname;}
    public int getClientUID(){return clientUID;}

    @Override
    public void run() {
        insertNickname();
        //TODO: LORE sistemare i metodi della tui adattandoli alla nuova gameView
        while(getState() != UIState.GAME_ENDING){
             while(getState() == UIState.OPPONENT_TURN){
                /*synchronized (lock){//forse va eliminata perchè superflua
                    try{lock.wait();
                    }catch(InterruptedException e){
                        System.err.println("Interrupted while waiting for server: " + e.getMessage());
                    }
                }*/
            }
            System.out.println("---YOUR TURN---");
            do{
                point = selectTilesInput();
                setChangedAndNotifyObservers(UIEvent.TILE_SELECTION);
            }while(chooseTiles());
            setChangedAndNotifyObservers(UIEvent.CONFIRM_SELECTION);

            do{
                number = selectColumnInput();
            }while(chooseColumn());
            setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);

            number = selectTemporaryTile();
            setChangedAndNotifyObservers(UIEvent.TILE_INSERTION);
            setState(UIState.OPPONENT_TURN);
        }
    }
    public void update(GameView model, Game.Event arg){
        switch(arg){
            case UPDATED_BOARD -> {
                if (model.getCurrentPlayerName().equals(nickname))
                    showBoard(model);
            }
            //TODO LOLLO: sistemare showShelf (quando inserisce la tile va in errore il primo client e stampa la sua shelf sul secondo)
            //case UPDATED_SHELF -> showShelf(model);
            case SET_CLIENT_ID_RETURN -> {
                if (clientUID==0)
                    clientUID = model.getClientID();
            }

            case UPDATED_CURRENT_PLAYER -> showCurrentPlayer(model);
            case UPDATED_CHAT -> showChat(model);
            case ERROR ->  {
                if (model.getClientID() == clientUID) {
                    switch (model.getError()) {
                        case INVALID_NAME -> {
                            System.out.println(ConsoleColors.RED + "Nickname already in use. Try again..." + ConsoleColors.RESET);
                            insertNickname();
                        }
                        case INVALID_NUMBER_OF_PLAYERS -> {
                            retryNumberOfPlayers();
                        }
                        case LOBBY_CLOSED -> {
                            System.out.println(ConsoleColors.RED + "Sorry, the lobby is full. Exiting..." + ConsoleColors.RESET);
                            System.exit(0);
                        }
                        case EMPTY_TEMPORARY_POINTS -> {
                            System.out.println(ConsoleColors.RED + "Please select any tile" + ConsoleColors.RESET);
                        }
                        case INVALID_RECIPIENT -> {
                            //TODO LUCA: gestire l'eccezione
                        }
                        case UNSELECTABLE_TILE -> {
                            System.out.println(ConsoleColors.RED + "Invalid Tile. Try again..." + ConsoleColors.RESET);
                            point = selectTilesInput();
                            setChangedAndNotifyObservers(UIEvent.TILE_SELECTION);
                        }
                        case UNSELECTABLE_COLUMN -> {
                            System.out.println(ConsoleColors.RED + "Invalid Column. Try again..." + ConsoleColors.RESET);
                            do{
                                number = selectColumnInput();
                            }while(chooseColumn());
                            setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);
                        }
                        case INVALID_TEMPORARY_TILE -> {
                            //nickname = selectTemporaryTile();
                            System.out.println(ConsoleColors.RED + "You don't have this tile. Try again..." + ConsoleColors.RESET);                            number = selectTemporaryTile();
                            setChangedAndNotifyObservers(UIEvent.TILE_INSERTION);
                        }
                    }
                }
            }
            case NEXT_PLAYER_TURN_NOTIFY -> {
                if (model.getCurrentPlayerName().equals(nickname)) {
                    setState(UIState.YOUR_TURN);
                }
            }
        }
    }

    private boolean chooseColumn(){
        System.out.println("Are you sure?\n1: yes\n2: no");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        //TODO non capisco il perchè di questo if (continuo a chiedere e quando ho finito notifico)
        /*if(choice == 2){
            setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);//così rimuviamo un automatico la colonna scelta precedentemente
        }*/
        return choice == 2;
    }

    private Integer selectTemporaryTile(){
        System.out.println("Which tiles do you want to insert?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("invalid input \ninsert the tile number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private Integer selectColumnInput(){
        System.out.println("In which column do you want to insert your tiles?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("invalid input \ninsert the column: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
    private boolean chooseTiles(){
        System.out.println("Do you want to choose another tile?\n1: yes\n2: no");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt() == 1;
    }
    private Point selectTilesInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("insert the row: ");
        while (!scanner.hasNextInt()) {
            System.out.println("invalid input \ninsert the row: ");
            scanner.next();
        }
        int row = scanner.nextInt();
        System.out.println("insert the column: ");
        while (!scanner.hasNextInt()) {
            System.out.println("invalid input \ninsert the column: ");
            scanner.next();
        }
        int column = scanner.nextInt();
        return new Point(row, column);
    }

    private void showBoard(GameView model){
        Board board = new Board(model.getSelectablePoints());
        board.setBoard(model.getGameBoard());
        System.out.println("---GAME BOARD---");
        board.printBoard(model.getSelectablePoints());
    }

    private void showShelf(GameView model){
        System.out.println("\n--- "+model.getCurrentPlayerName()+" ---\n");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getCurrentPlayerShelf());
        shelf.printShelf();
    }

    private void showCurrentPlayer(GameView model){
        System.out.println("\nThe current player is: "+model.getCurrentPlayerName());
    }

    private void showChat(GameView model){
        for(Message message : model.getChat())
            System.out.println("\n"+message.getSender().getNickname()+": "+message.getContent());
    }

    private void insertNickname(){
        System.out.println("Insert your nickname: ");
        Scanner scanner = new Scanner(System.in);
        nickname = scanner.nextLine();
        point = null;
        setChangedAndNotifyObservers(UIEvent.SET_CLIENT_ID);
        if (clientUID == 1){
            System.out.println("Insert the number of players: ");
            try {
                number = Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException e) {
                retryNumberOfPlayers();
            }
            setChangedAndNotifyObservers(UIEvent.CHOOSE_NICKNAME_AND_PLAYER_NUMBER);
        }
        else {
            setChangedAndNotifyObservers(UIEvent.CHOOSE_NICKNAME);
        }
    }

    private void retryNumberOfPlayers (){
        System.out.println(ConsoleColors.RED + "Number of players not valid. Try again... " + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        try {
            number = Integer.valueOf(scanner.nextLine());
        } catch (NumberFormatException e) {
            retryNumberOfPlayers();
        }
        setChangedAndNotifyObservers(UIEvent.CHOOSE_NICKNAME_AND_PLAYER_NUMBER);
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
