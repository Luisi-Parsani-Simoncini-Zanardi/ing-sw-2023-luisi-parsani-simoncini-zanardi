package org.projectsw.View;
import org.projectsw.Model.*;
import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.View.Enums.UIEvent;
import org.projectsw.View.Enums.UIState;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextualUI extends Observable<UIEvent> implements Runnable{

    private UIState state = UIState.OPPONENT_TURN;
    private final Object lock = new Object();
    private Integer number;
    private Point point;
    private String nickname;
    private String string;
    private int clientUID;
    private Boolean noMoreSelectableTiles = true;
    private Boolean noMoreTemporaryTiles = true;

    public TextualUI()
    {
        displayLogo();
    }

    private UIState getState(){
        synchronized(lock){
            return state;
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
    public void setID(int ID){
        this.clientUID=ID;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public void setState(UIState state){
        synchronized (lock){
            this.state = state;
            lock.notifyAll();
        }
    }

    @Override
    public void run() {
        joinGame();
        while(getState() != UIState.GAME_ENDING || (getState() == UIState.GAME_ENDING && this.clientUID != 1)){
             while(getState() == UIState.OPPONENT_TURN){
                 //chatting
                 //takeInput();
            }
            System.out.println("---YOUR TURN---");

             selectTiles();

            do{
                number = selectColumnInput();
            }while(chooseColumn());
            try {
                setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while confirming the column: "+e.getCause());
            }
            do {
                number = selectTemporaryTile();
            try {
                setChangedAndNotifyObservers(UIEvent.TILE_INSERTION);
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while inserting the tiles: "+e.getCause());
            }
            }while(noMoreTemporaryTiles);
            setState(UIState.OPPONENT_TURN);
        }
    }
  
    public void update(GameView model, GameEvent arg){
        switch(arg){
            case UPDATED_BOARD -> {
                if (model.getCurrentPlayerName().equals(nickname))
                    showBoard(model);
            }
            case PERSONAL_GOAL -> {
                if (model.getCurrentPlayerName().equals(nickname))
                    showPersonalGoal(model);
            }
            case UPDATED_SHELF -> {if (model.getCurrentPlayerName().equals(nickname)) showShelf(model);}
            case UPDATED_TEMPORARY_TILES -> {
                if (model.getCurrentPlayerName().equals(nickname)) {
                    System.out.println("You have selected: ");
                    ArrayList<Tile> tiles = model.getTemporaryTiles();
                    for (int i = 0; i < tiles.size(); i++) {
                        int integer = i + 1;
                        switch (tiles.get(i).getTile()) {
                            case CATS -> System.out.println(integer + " " + ConsoleColors.CATS);
                            case TROPHIES -> System.out.println(integer + " " + ConsoleColors.TROPHIES);
                            case BOOKS -> System.out.println(integer + " " + ConsoleColors.BOOKS);
                            case FRAMES -> System.out.println(integer + " " + ConsoleColors.FRAMES);
                            case GAMES -> System.out.println(integer + " " + ConsoleColors.GAMES);
                            case PLANTS -> System.out.println(integer + " " + ConsoleColors.PLANTS);
                        }
                    }
                }
            }
            case UPDATED_CURRENT_PLAYER -> showCurrentPlayer(model);
            case UPDATED_CHAT -> showChat(model);
            case ERROR ->  {
                if (model.getClientID() == clientUID) {
                    switch (model.getError()) {
                        case LOBBY_CLOSED -> {
                            System.out.println(ConsoleColors.RED + "Sorry, the lobby is full. Exiting..." + ConsoleColors.RESET);
                            System.exit(0);
                        }
                        case EMPTY_TEMPORARY_POINTS -> {
                            System.out.println(ConsoleColors.RED + "You don't have any tiles selected. Please select any tile..." + ConsoleColors.RESET);
                            selectTiles();
                        }
                        case INVALID_RECIPIENT -> {
                            //TODO: gestire l'eccezione
                        }
                        case UNSELECTABLE_TILE -> {
                            System.out.println(ConsoleColors.RED + "Invalid Tile. Try again..." + ConsoleColors.RESET);
                            point = selectTilesInput();
                            try {
                                setChangedAndNotifyObservers(UIEvent.TILE_SELECTION);
                            } catch (RemoteException e) {
                                throw new RuntimeException("Network error while notifying a tile section error: "+e.getCause());
                            }
                        }
                        case UNSELECTABLE_COLUMN -> {
                            System.out.println(ConsoleColors.RED + "Invalid Column. Try again..." + ConsoleColors.RESET);
                            do{
                                number = selectColumnInput();
                            }while(chooseColumn());
                            try {
                                setChangedAndNotifyObservers(UIEvent.COLUMN_SELECTION);
                            } catch (RemoteException e) {
                                throw new RuntimeException("Network error while notifying a column section error: "+e.getCause());
                            }
                        }
                        case INVALID_TEMPORARY_TILE -> {
                            //nickname = selectTemporaryTile();
                            System.out.println(ConsoleColors.RED + "You don't have this tile. Try again..." + ConsoleColors.RESET);                            number = selectTemporaryTile();
                            try {
                                setChangedAndNotifyObservers(UIEvent.TILE_INSERTION);
                            } catch (RemoteException e) {
                                throw new RuntimeException("Network error while notifying a tile insertion error: "+e.getCause());
                            }
                        }
                    }
                }
            }
            case NEXT_PLAYER_TURN_NOTIFY -> {
                if (model.getCurrentPlayerName().equals(nickname)) {
                    setState(UIState.YOUR_TURN);
                    noMoreSelectableTiles = true;
                    noMoreTemporaryTiles = true;
                }
            }
            case SELECTION_NOT_POSSIBLE -> {
                noMoreSelectableTiles = false;
            }
            case EMPTY_TEMPORARY_TILES -> {
                noMoreTemporaryTiles = false;
            }
        }
    }

    private void takeInput() {
        Scanner scanner = new Scanner(System.in);
        string = scanner.nextLine();
        String[] splitted = string.split("/");
        switch(splitted[1]){
            case "msg" -> {
                try {
                    setChangedAndNotifyObservers(UIEvent.SAY_IN_CHAT);
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending the message to the chat: "+e.getMessage());
                }
            }
        }
    }

    private boolean chooseColumn(){
        System.out.println("Are you sure?\n1: Yes\n2: No");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Please insert a number..." + ConsoleColors.RESET);
            scanner.next();
        }
        int choice = scanner.nextInt();
        if (choice == 1 || choice == 2)
            return choice == 2;
        else {
            System.out.println(ConsoleColors.RED + "Invalid input. Try again..." + ConsoleColors.RESET);
            return chooseColumn();
        }
    }

    private Integer selectTemporaryTile(){
        System.out.println("Which tile do you want to insert?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Invalid input \n" + ConsoleColors.RESET + "Insert the tile number: ");
            scanner.next();
        }
        return scanner.nextInt()-1;
    }

    private Integer selectColumnInput(){
        System.out.println("In which column do you want to insert your tiles?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Invalid input \n" + ConsoleColors.RESET + "Insert the column: ");
            scanner.next();
        }
        return scanner.nextInt()-1;
    }

    private boolean chooseTiles(){
        System.out.println("Do you want to choose another tile?\n1: yes\n2: no");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Please insert a number..." + ConsoleColors.RESET);
            scanner.next();
        }
        int choice = scanner.nextInt();
        if (choice == 1 || choice == 2)
            return choice == 1;
        else {
            System.out.println(ConsoleColors.RED + "Invalid input. Try again..." + ConsoleColors.RESET);
            return chooseColumn();
        }
    }

    private Point selectTilesInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the row: ");
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Invalid input... \n" + ConsoleColors.RESET + "Insert the row: ");
            scanner.next();
        }
        int row = scanner.nextInt();
        System.out.println("Insert the column: ");
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Invalid input... \n" + ConsoleColors.RESET + "Insert the column: ");
            scanner.next();
        }
        int column = scanner.nextInt();
        return new Point(column-1, row-1);
    }

    private void showBoard(GameView model){
        Board board = new Board(model.getSelectablePoints(), model.getTemporaryPoints());
        board.setBoard(model.getGameBoard());
        System.out.println("-----GAME BOARD-----");
        board.printBoard();
    }

    private void showShelf(GameView model){
        System.out.println("\n--- "+model.getCurrentPlayerName()+" ---\n");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getCurrentPlayerShelf());
        shelf.printShelf();
    }

    private void showPersonalGoal(GameView model){
        System.out.println("\n--- YOUR PERSONAL GOAL ---\n");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getCurrentPlayerPersonalGoal());
        shelf.printShelf();
    }

    private void showCurrentPlayer(GameView model){
        System.out.println("\nThe current player is: "+model.getCurrentPlayerName());
    }

    private void showChat(GameView model){
        for(Message message : model.getChat())
            System.out.println("\n"+message.getSender().getNickname()+": "+message.getPayload());
    }

    private void joinGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert your nickname: ");
        nickname = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(UIEvent.CHOOSE_NICKNAME);
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: "+e.getCause());
        }
    }

    private void selectTiles(){
        do{
            point = selectTilesInput();
            try {
                setChangedAndNotifyObservers(UIEvent.TILE_SELECTION);
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while choosing the tiles: "+e.getCause());
            }
        }while(noMoreSelectableTiles && chooseTiles());
        try {
            setChangedAndNotifyObservers(UIEvent.CONFIRM_SELECTION);
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the tile selection: "+e.getCause());
        }
    }

    public void askNewNick(ArrayList<String> nicks){
        Scanner scanner = new Scanner(System.in);
        boolean control=false;
        do{
            System.out.println(ConsoleColors.RED +"Invalid nickname. Try again..."+ ConsoleColors.RESET+"\nInsert new nickname:");
            nickname = scanner.nextLine();
            if(!nicks.contains(nickname))
                control=true;
        }while(!control);
        try {
            setChangedAndNotifyObservers(UIEvent.NEW_CHOOSE_NICKNAME);
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: "+e.getCause());
        }
    }

    public void askNumber(){
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("Insert number of players: ");
            number = scanner.nextInt();
            if(number<Config.minPlayers || number>Config.maxPlayers)
                System.out.println(ConsoleColors.RED +"Invalid Number of players. Try again..."+ ConsoleColors.RESET);
        }while(number<Config.minPlayers || number>Config.maxPlayers);
        try {
            setChangedAndNotifyObservers(UIEvent.CHOOSE_NUMBER_OF_PLAYERS);
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: "+e.getCause());
        }
    }

    public void kill(){
        System.out.println(ConsoleColors.RED +"Unable to join the game; lobby is full.\nClosing the process..."+ ConsoleColors.RESET);
        printImageKill();
        System.exit(0);
    }

    private void displayLogo(){
        System.out.println(ConsoleColors.PURPLE_BOLD + "  __  __" + ConsoleColors.BLUE_BOLD + "        _____ _          _  __ _      ");
        System.out.println(ConsoleColors.PURPLE_BOLD + " |  \\/  |" + ConsoleColors.BLUE_BOLD + "      / ____| |        | |/ _(_)     ");
        System.out.println(ConsoleColors.PURPLE_BOLD + " | \\  / |_   _" + ConsoleColors.BLUE_BOLD + "| (___ | |__   ___| | |_ _  ___ ");
        System.out.println(ConsoleColors.PURPLE_BOLD + " | |\\/| | | | |" + ConsoleColors.BLUE_BOLD + "\\___ \\| '_ \\ / _ \\ |  _| |/ _ \\");
        System.out.println(ConsoleColors.PURPLE_BOLD + " | |  | | |_| |" + ConsoleColors.BLUE_BOLD + "____) | | | |  __/ | | | |  __/");
        System.out.println(ConsoleColors.PURPLE_BOLD + " |_|  |_|\\__, |" + ConsoleColors.BLUE_BOLD + "_____/|_| |_|\\___|_|_| |_|\\___|");
        System.out.println(ConsoleColors.PURPLE_BOLD + "          __/ |                               " );
        System.out.println("         |___/                                " + ConsoleColors.RESET);
    }

    private void printImageKill(){
        System.out.println(ConsoleColors.YELLOW + "\n                      "+ConsoleColors.GREY+"/^--^\\     /^--^\\     /^--^\\\n" +
                "                      \\____/     \\____/     \\____/\n" +
                "                     /      \\   /      \\   /      \\\n" +
                "                    |        | |        | |        |\n" +
                "                     \\__  __/   \\__  __/   \\__  __/\n" +ConsoleColors.YELLOW+
                "|^|^|^|^|^|^|^|^|^|^|^|^"+ConsoleColors.GREY+"\\ \\"+ConsoleColors.YELLOW+"^|^|^|^"+ConsoleColors.GREY+"/ /"+ConsoleColors.YELLOW+"^|^|^|^|^"+ConsoleColors.GREY+"\\ \\"+ConsoleColors.YELLOW+"^|^|^|^|^|^|^|^|^|^|^|^|\n" +
                "| | | | | | | | | | | | |"+ConsoleColors.GREY+"\\ \\"+ConsoleColors.YELLOW+"| | |"+ConsoleColors.GREY+"/ /"+ConsoleColors.YELLOW+"| | | | | |"+ConsoleColors.GREY+"\\ \\"+ConsoleColors.YELLOW+"| | | | | | | | | | | |\n" +
                "####### "+ConsoleColors.RED_BOLD+"PROCESS"+ConsoleColors.YELLOW +" ########"+ConsoleColors.GREY+"/ /"+ConsoleColors.YELLOW+"######"+ConsoleColors.GREY+"\\ \\"+ConsoleColors.YELLOW+"###########"+ConsoleColors.GREY+"/ /"+ConsoleColors.YELLOW+"####### "+ConsoleColors.RED_BOLD+"CLOSED"+ConsoleColors.YELLOW+" ########\n" +
                "| | | | | | | | | | | | "+ConsoleColors.GREY+"\\/"+ConsoleColors.YELLOW+"| | | | "+ConsoleColors.GREY+"\\/"+ConsoleColors.YELLOW+"| | | | | |"+ConsoleColors.GREY+"\\/"+ConsoleColors.YELLOW+" | | | | | | | | | | | |\n" +
                "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|"+ConsoleColors.RESET);
    }
}