package org.projectsw.View;
import org.projectsw.Distributed.Messages.InputMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.*;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class TextualUI extends Observable<InputMessage> implements Runnable{
    //TODO: idee
    /*
    * a inizio turno stampare in automatico la board
    * a inizio game mostrare i common e personal goal al giocatore
    * dopo la selezione di una tile bisogna stampare la board con le () e []
    * se non è il proprio turno stampare la board SENZA () e []
    * far finire il turno in automatico e far settare al controller/server il niovo giocatore giocante
    * (da ragionare) fare settare al server tutti le fasi del turno
    */
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.RUNNING;
    private final Object lock = new Object();
    private final Object lock2 = new Object();
    private boolean isNotCorrect;
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

    public UITurnState getTurnState(){
        synchronized(lock){
            return turnState;
        }
    }
    public UIEndState getEndState(){
        synchronized(lock2){
            return endState;
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

    public void setTurnState(UITurnState state){
        synchronized (lock){
            this.turnState = state;
        }
    }
    public void setEndState(UIEndState state){
        synchronized (lock2){
            this.endState = state;
        }
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            joinGame();
            if(!isNotCorrect)
                System.out.println(ConsoleColors.RED+"Nickname already taken..."+ConsoleColors.RESET);
        }while(!isNotCorrect);

        while(getEndState() != UIEndState.ENDING || (getEndState() == UIEndState.ENDING && this.clientUID != 1)) {
            System.out.println("Press 0 to see all possible actions...");
            System.out.println("---CHOOSE AN ACTION---");
            choice = scanner.nextInt();
            switch (choice) {
                case 0 -> printCommandMenu();
                case 1 -> {
                    if (getTurnState() == UITurnState.OPPONENT_TURN)
                        System.out.println(ConsoleColors.RED + "It's not your turn. Please wait..." + ConsoleColors.RESET);
                    else {
                        if (turnState==UITurnState.YOUR_TURN_SELECTION){
                            turnState=UITurnState.YOUR_TURN_COLUMN;
                            askBoard();
                            selectTiles();
                        } else {
                            System.out.println(ConsoleColors.RED + "You can't select a tile now..." + ConsoleColors.RESET);
                        }
                    }
                }
                case 2 -> {
                    //show shelf
                    if (turnState == UITurnState.OPPONENT_TURN)
                        System.out.println(ConsoleColors.RED + "It's not your turn. Please wait..." + ConsoleColors.RESET);
                    else {
                        if (turnState == UITurnState.YOUR_TURN_SELECTION || turnState == UITurnState.YOUR_TURN_END)  {
                            System.out.println(ConsoleColors.RED + "You can't insert a tile now..." + ConsoleColors.RESET);
                        }
                        else {
                            if (turnState == UITurnState.YOUR_TURN_COLUMN) {
                                turnState = UITurnState.YOUR_TURN_INSERTION;
                                selectColumn();
                            }
                            if (turnState == UITurnState.YOUR_TURN_INSERTION) {
                                selectTemporaryTiles();
                                try {
                                    setChangedAndNotifyObservers(new EndTurn(new InputController(clientUID)));
                                } catch (RemoteException e) {
                                    throw new RuntimeException("An error occurred while ending the turn: " + e);
                                }
                                System.out.println("You ended your turn.");
                                setTurnState(UITurnState.OPPONENT_TURN);
                            }
                        }
                    }
                }
                case 3 -> {}
                case 4 -> {}
                case 5 -> askBoard();
                case 6 -> {}
                case 7 -> {}
                case 8 -> writeInChat();
                case 9 -> {}          
                case 10 -> {//impossibile da testare su intellij, ma solo da cli linux e cli windows
                    try {
                        if (System.getProperty("os.name").contains("Windows")) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        } else {
                            Runtime.getRuntime().exec("clear");
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void printCommandMenu(){
        System.out.println("""
                 ---CHOOSE AN ACTION---
                1-  Select tiles from the board
                2-  Insert tiles in your shelf
                3-  See your personal goal
                4-  See the common goals
                5-  Show the board
                6-  Show your shelf
                7-  Show all the shelves
                8-  Write in chat
                9-  Show the chat
                10- Clear the cli
                """);
    }
    private void writeInChat(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.PURPLE+"Messages should be formatted like this:\n" + ConsoleColors.RESET+
                "nickname/message -> to send a secret message to the player with the specified nickname\n" +
                "message -> to send a message to everyone");
        string = scanner.nextLine();
    }

    public void update(ResponseMessage response){
        if(response.getModel().getClientID()==this.clientUID||response.getModel().getClientID()==Config.broadcastID)
            response.execute(this);
    }
    public void setNoMoreTemporaryTiles(boolean bool){
        this.noMoreTemporaryTiles = bool;
    }
    public void setNoMoreSelectableTiles(boolean bool){
        this.noMoreSelectableTiles = bool;
    }
    public boolean chooseColumn(){
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

    public Integer selectTemporaryTileInput(){
        System.out.println("Which tile do you want to insert?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Invalid input \n" + ConsoleColors.RESET + "Insert the tile number: ");
            scanner.next();
        }
        return scanner.nextInt()-1;
    }

    public Integer selectColumnInput(){
        System.out.println("In which column do you want to insert your tiles?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleColors.RED + "Invalid input \n" + ConsoleColors.RESET + "Insert the column: ");
            scanner.next();
        }
        return scanner.nextInt()-1;
    }

    private boolean chooseTiles(){
        System.out.println("Do you want to choose another tile?\n1: Yes\n2: No");
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
            return chooseTiles();
        }
    }

    private void selectTiles(){
        do{
            point = selectTilesInput();
            try {
                setChangedAndNotifyObservers(new TileSelection(new InputController(getClientUID(), getPoint())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while choosing the tiles: "+e.getCause());
            }
        }while(noMoreSelectableTiles && chooseTiles());
        try {
            setChangedAndNotifyObservers(new ConfirmSelectedTiles(new InputController(getClientUID())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the tile selection: "+e.getCause());
        }
    }

    private void selectColumn(){
        do{
            number = selectColumnInput();
        }while(chooseColumn());
        try {
            setChangedAndNotifyObservers(new ColumnSelection(new InputController(clientUID, number)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the column: "+e.getCause());
        }
    }

    private void selectTemporaryTiles(){
        do {
            number = selectTemporaryTileInput();
            try {
                setChangedAndNotifyObservers(new TemporaryTileSelection(new InputController(clientUID, number)));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while inserting the tiles: "+e.getCause());
            }
        }while(noMoreTemporaryTiles);
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



    private void askBoard() {
        try {
            setChangedAndNotifyObservers(new AskForBoard(new InputController(getClientUID())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the board: "+e.getMessage());
        }
    }

    public void showBoard(GameView model){
        Board board = new Board(model.getSelectablePoints(), model.getTemporaryPoints());
        board.setBoard(model.getGameBoard());
        System.out.println("-----GAME BOARD-----");
        board.printBoard();
    }

    public void showShelf(GameView model){
        System.out.println("\n--- "+model.getCurrentPlayerName()+" ---\n");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getCurrentPlayerShelf());
        shelf.printShelf();
    }

    public void showPersonalGoal(GameView model){
        System.out.println("\n--- YOUR PERSONAL GOAL ---\n");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getCurrentPlayerPersonalGoal());
        shelf.printShelf();
    }
    public void showCurrentPlayer(GameView model){
        if (getEndState() != UIEndState.ENDING || clientUID !=1)
            System.out.println("\nThe current player is: "+model.getCurrentPlayerName());
    }

    private void showChat(GameView model){
        for(Message message : model.getChat())
            System.out.println("\n"+message.getSender()+": "+message.getPayload());
    }

    private void joinGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert your nickname: ");
        nickname = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(new InitializePlayer(new InputController(this.getNickname())));
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
            setChangedAndNotifyObservers(new NumberOfPlayers(new InputController(getClientUID(),getNumber())));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error"+e.getMessage());
        }
    }

    public void setIsNotCorrect(boolean resp){
        this.isNotCorrect=resp;
    }

    public void kill(){
        System.out.println(ConsoleColors.RED + "Unable to join the game; lobby is full.\nClosing the process..." + ConsoleColors.RESET);
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
    public void printMedal(String metal, String place) {
        System.out.println(ConsoleColors.BLUE + "                        %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%%           %%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%                       \n" +
                ConsoleColors.BLUE +"                         %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%%         %%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%                        \n" +
                ConsoleColors.BLUE +"                          %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%%       %%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%                         \n" +
                ConsoleColors.BLUE +"                           %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%%     %%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%"+ConsoleColors.RESET+"     You placed "+place+"!                          \n" +
                ConsoleColors.BLUE +"                            %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%%   %%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%                           \n" +
                ConsoleColors.BLUE +"                             %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%% %%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%                            \n" +
                ConsoleColors.BLUE +"                              %,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%%%,"+ConsoleColors.RED +"%%%%%%,"+ConsoleColors.BLUE +"%                             \n" +
                ConsoleColors.BLUE +"                               %,"+ConsoleColors.RED +"%%%%%%,%%%%%%%%,"+ConsoleColors.BLUE +"%                              \n" +
                ConsoleColors.BLUE +"                                %,"+ConsoleColors.RED +"%%%%%%,%%%%%%,"+ConsoleColors.BLUE +"%                               \n" +
                ConsoleColors.BLUE +"                                 %,"+ConsoleColors.RED +"%%%%%%,%%%%,"+ConsoleColors.BLUE +"%                                \n" +
                ConsoleColors.BLUE +"                                 ,%,#        ,%,                                \n" + metal +
                "                                 ,,,,,,,,,,,,,,,                                \n" +
                "                             ,,,,,,,,,,,,,,,,,,,,,,,                            \n" +
                "                          .,,,,,*//*************,,,,,,                          \n" +
                "                         ,,,,,//*******************,,,,,                        \n" +
                "                        ,,,,//*********,,***********.,,,,                       \n" +
                "                       ,,,,//*********,,,,/**********,,,,,                      \n" +
                "                       ,,,,/*****,,,,,,,,,,,,,,/******,,,,                      \n" +
                "                       ,,,,/********,,,,,,,,,/*******,,,,,                      \n" +
                "                       ,,,,,/*******,,,,,,,,,********,,,,*                      \n" +
                "                        ,,,,,/******,//****/,/******,,,,*                       \n" +
                "                         ,,,,,,*******************,,,,,*                        \n" +
                "                           ,,,,,,,************,,,,,,,*                          \n" +
                "                             *,,,,,,,,,,,,,,,,,,,,,*                            \n" +
                "                                 **,,,,,,,,,,,**                                \n" +
                "                                                                           " +  ConsoleColors.RESET);
    }
    public void printNoMedal() {
        System.out.println("⠀⠀⠀⠀⢀⠀⠀⠀⠀⢀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣤⣤⣤⠤⠤⠤⠤⠤⣤⣤⣤⡴⠶⠶⠶⠤⠤⠤⠤⢤⣤⣤⣶⣦⣤⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣤⡤⠶⠶⠶⠤⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⣻⣿⣿⣿⣿⣿⣿⣿⣭⣁⠀⢀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⢸⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⡅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⣿⣿⡿⣿⣿⣿⣇⡈⠉⠉⠉⠉⠉⣻⠛⠛⠒⠲⢶⣾⣿⡿⠦⢤⣤⣤⣴⣿⣿⣦⣀⣀⣀⣀⣀⡀⠀⠀⠀⡀⠘⣷⣄⠀⠀⡴⠃⠀⠀⠀⠀⠀⠀⢰⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⣿⣿⣿⡀⠈⠛⢯⣉⠉⠛⠚⠲⠶⠯⣄⣀⣀⣠⣿⣿⠃⠀⠀⠀⠀⣠⣿⠋⠁⠀⠀⠀⠀⠀⠀⠉⠉⠉⣛⠿⢿⣿⣿⣿⡶⠦⠤⣄⣀⡀⠀⠀⠀⢸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠸⣿⣿⡨⢷⡀⠀⠠⠉⠳⢦⣀⠀⠀⠀⠈⢻⣿⣿⡿⠿⢧⣄⣀⡀⠸⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⣶⠟⠉⠀⠀⠀⠀⠀⠀⠈⠉⠛⠲⢶⣿⣿⣦⣄⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⡿⠙⡇⠀⢳⡤⠋⠀⠀⠀⢹⣷⢦⣤⣴⣿⠿⠿⠃⠀⠈⠀⠿⢿⣿⠿⠷⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⠿⠛⠋⠉⠉⠉⠉⠉⠙⠒⠂⠀⠠⠄\n" +
                "⠀⠀⠀⠀⡏⠀⣿⠉⠠⠿⣄⠀⠀⠀⢀⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⣰⠟⠁⠀⠀⠀⠀⠉⠛⠲⠤⢤⣀⣀⠀⠠⢿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡾⠁⠀⠀⠀⠀⠀⠀     You placed 4th...⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⢸⡟⠛⠻⡆⠀⠀⣿⣿⣶⣾⣿⣿⣿⣿⡿⠀⠉⠳⢤⡀⠀⣴⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣟⢳⣾⣷⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⢸⣇⡀⢀⣿⣶⣾⣿⣿⣿⣿⣿⠟⠉⢻⣆⠀⣰⣧⣀⣹⣿⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡾⠛⠋⠉⠉⠉⢻⡟⠛⠓⠒⠲⠤⢤⣄⡀⢺⣷⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⠀⠀⠉⠻⣟⠀⠀⠀⠉⣿⢿⡟⠋⠉⠉⠉⠙⠻⢦⣄⡀⠀⠀⠀⠀⠀⠀⡼⠋⠀⠀⠀⠀⠀⣰⠟⠀⠀⠀⠀⠀⠀⠀⠘⢹⠻⣷⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⣾⠟⠉⠙⢿⣿⣷⠀⠀⠀⠀⠹⣆⠀⢀⡾⠁⢸⡇⠀⠀⠀⠀⠀⠀⠀⠈⢹⠗⢦⣄⡀⠀⣸⠁⠀⠀⠀⠀⢀⡞⠁⠀⠀⠀⠀⠀⠀⠀⣀⡴⠟⠋⠉⠉⠉⠉⠓⠲⠤⣤⣀⡀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⡏⠀⠀⠀⠈⣿⡟⣷⣤⣤⣀⣠⣾⣶⣾⣃⣤⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⢈⣧⣼⣈⣹⣶⣿⣆⠀⠀⠀⣠⡟⠀⠀⠀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠀⠒⠀⠀⠀⠀\n" +
                "⠀⠀⠀⣿⡷⣄⢀⣠⣿⣥⣾⣿⣿⠟⠀⠉⠛⢿⡟⣿⠂⠀⠀⠀⠀⠀⠀⢀⣤⣶⡛⠉⠉⠉⠉⠉⠙⣿⣷⣦⣅⡀⠀⠀⠀⠀⠀⠀⢀⡾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⣿⡿⠛⠛⢿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⢻⣇⠀⠀⠀⠀⠀⠀⣰⠟⠉⠉⠻⣆⡀⠀⠀⠀⠀⠿⠛⠀⠈⠙⠓⢦⣄⡀⠀⠀⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⢸⡏⠀⠀⠀⠀⠙⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠹⣆⠀⠀⠀⠀⣼⠃⠀⠀⠀⠀⠈⠙⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢦⣴⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⢸⡇⠀⠀⠀⠀⠀⢹⣿⡷⠶⠂⢀⣤⡄⠀⠀⠀⠘⣆⠀⠀⢰⠇⠀⠀⠀⠀⠀⠀⠀⠸⡆⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣠⣾⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⢸⡇⠀⠀⠀⠀⠀⠸⡟⠀⠀⠀⠈⢧⠀⠀⠀⠀⠀⠘⣦⣀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⡤⠶⠒⠛⠉⠉⠉⠉⠉⠉⠉⠛⠻⠷⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⣸⣷⣄⠀⠀⢀⣠⣼⣿⣄⢠⣤⠶⠛⠛⠛⡛⠓⠶⢶⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⣠⠴⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⢠⣿⡿⠛⠉⠉⠻⢧⣿⣿⣿⠏⠀⠀⠀⠀⢿⠃⠀⠀⠀⠈⠻⣿⡀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠻⡿⠀⠀⠀⠀⠀⠀⠈⠻⣿⠀⠀⠀⠀⢠⡟⠀⠀⠀⠀⠀⠀⠹⣧⠀⠀⠀⣰⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⢠⣿⡂⠀⠀⠀⠀⠀⠀⠀⠹⣷⣤⣤⡴⠟⠓⠒⠒⠒⠒⠶⠤⢤⣭⣷⣤⣼⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⢠⣿⠟⠋⠙⠓⠒⠲⠤⣤⣤⢰⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⢸⡟⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣿⣟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠈⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠓⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
    }
}

