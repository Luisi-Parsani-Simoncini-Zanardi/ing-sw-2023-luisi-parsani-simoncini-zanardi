package org.projectsw.View;
import org.projectsw.Distributed.Client;
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
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.LOBBY;

    private final Object lock = new Object();
    private final Object lock2 = new Object();

    private boolean flag = true;
    private volatile boolean waitResult = true;
    private boolean endedTurn = false;

    private Integer number;
    private Point point;
    private String nickname;
    private String string;

    private String lastPlayerNick;

    private Boolean noMoreSelectableTiles = true;
    private Boolean noMoreTemporaryTiles = true;

    private HashMap<String, String> nameColors;
    private final Client client;
    Scanner masterScanner = new Scanner(System.in);
  
    private ArrayList<Message> chatBuffer = new ArrayList<>();
  
    public TextualUI(Client client)
    {
        this.client = client;
        displayLogo();
    }

    public boolean getFlag() {
        return flag;
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

    public Client getClient(){return this.client;}
    public String getString(){return this.string;}
    public Integer getNumber(){
        return this.number;
    }
    public Point getPoint(){
        return this.point;
    }
    public String getNickname(){return this.nickname;}
    public HashMap<String, String> getNameColors(){return this.nameColors;}
    public Scanner getMasterScanner(){
        return this.masterScanner;
    }
    public void setWaitResult(boolean response){
        this.waitResult = response;
    }
    public void setNameColors(HashMap<String, String> nameColors){
        this.nameColors = nameColors;
    }
    public void setFlag(boolean resp){
        this.flag=resp;
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
    public void setLastPlayerName(String nick){
        this.lastPlayerNick=nick;
    }


    @Override
    public void run() {
        int choice = 0;
        do {
            joinGame();
            if (flag)
                System.err.println("Nickname already taken...");
        } while (flag);
        endedTurn = false;
        if (getEndState() == UIEndState.LOBBY)
            System.out.println("Waiting for more people to join...\n");
        while (getEndState() == UIEndState.LOBBY) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("An error occurred while waiting for the game to start: " + e);
                }
            }
        }
        System.out.println("\nGame started!");
        System.out.println("---CHOOSE AN ACTION---");
        System.out.println("Press 0 to see all possible actions...");
        flag = true;
        askCurrentPlayer();
        do {
            writeBufferMessage();
            if(flag) {
                try {
                    choice = masterScanner.nextInt();
                } catch (InputMismatchException | IllegalStateException ignored) {
                }
            }
            if (flag) {
                switch (choice) {
                    case 0 -> printCommandMenu();
                    case 1 -> {
                        if (getTurnState() == UITurnState.OPPONENT_TURN)
                            System.err.println("It's not your turn. Please wait...");
                        else {
                            if (getTurnState() == UITurnState.YOUR_TURN_SELECTION) {
                                setTurnState(UITurnState.YOUR_TURN_COLUMN);
                                askBoard();
                                selectTiles();
                            } else {
                                System.err.println("You can't select a tile now...");
                            }
                        }
                    }
                    case 2 -> {
                        if (getTurnState() == UITurnState.OPPONENT_TURN) {
                            System.err.println("It's not your turn. Please wait...");
                            System.out.println("---CHOOSE AN ACTION---");
                        } else {
                            if (getTurnState() == UITurnState.YOUR_TURN_SELECTION) {
                                System.err.println("You can't insert a tile now...");
                            } else {
                                askShelf();
                                askTemporaryTiles();
                                if (getTurnState() == UITurnState.YOUR_TURN_COLUMN) {
                                    setTurnState(UITurnState.YOUR_TURN_INSERTION);
                                    selectColumn();
                                }
                                if (getTurnState() == UITurnState.YOUR_TURN_INSERTION) {
                                    selectTemporaryTiles();
                                    //writeBufferMessage();
                                    System.out.println("You ended your turn.");
                                    try {
                                        setChangedAndNotifyObservers(new EndTurn(new SerializableInput(getNickname())));
                                    } catch (RemoteException e) {
                                        throw new RuntimeException("An error occurred while ending the turn: " + e);
                                    }
                                    if (getEndState().equals(UIEndState.ENDING))
                                        setWaitResult(false);
                                    setTurnState(UITurnState.OPPONENT_TURN);
                                    endedTurn=true;
                                }
                            }
                        }
                    }
                    case 3 -> askPersonalGoal();
                    case 4 -> askCommonGoals();
                    case 5 -> askBoard();
                    case 6 -> askShelf();
                    case 7 -> askAllShelves();
                    case 8 -> askCurrentPlayer();
                    case 9 -> writeInChat();
                    case 10 -> showChat();
                    case 11 -> {//impossibile da testare su intellij, ma solo da cli linux e cli windows
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
                    case 12 -> exit();
                    default -> System.err.println("Invalid command. Try again...");
                }
                if (!endedTurn&&flag)
                    System.out.println("\n---CHOOSE AN ACTION---");
            }
        } while (getEndState() == UIEndState.RUNNING || waitResult);
        ending();
    }

    public void ending(){
        getMasterScanner().close();
        System.err.println("The game ended. You can no longer do actions.");
        setFlag(false);
        System.out.println("Wait for results please...");
        if(nickname.equals(lastPlayerNick)) {
            try {
                setChangedAndNotifyObservers(new AskForResults(new SerializableInput(getNickname())));
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while asking for results: "+e.getMessage());
            }
        }else {
            setWaitResult(true);
        }
        while (waitResult) Thread.onSpinWait();
        try {
            client.kill(new SerializableGame(getNickname(),1));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while killing the client: "+e.getMessage());
        }
    }

    private void printCommandMenu(){
        System.out.println("""
                1-  Select tiles from the board
                2-  Insert tiles in your shelf
                3-  Show your personal goal
                4-  Show the common goals
                5-  Show the board
                6-  Show your shelf
                7-  Show all the shelves
                8-  Show the current player
                9-  Write in chat
                10- Show the chat
                11- Clear the cli
                12- Exit
                """);
    }

    public void exit(){
        System.out.println("Exiting...");
        try {
            setChangedAndNotifyObservers(new NotActive(new SerializableInput(getNickname())));
            setChangedAndNotifyObservers(new DeleteModelObserver(new SerializableInput(getNickname())));
            if(getTurnState()!=UITurnState.OPPONENT_TURN)
                setChangedAndNotifyObservers(new EndTurnExit(new SerializableInput(getNickname())));
            client.kill(new SerializableGame(getNickname(),1));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while removing the tui observer: "+e.getMessage());
        }
    }
    private void writeInChat(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Messages should be formatted like this:
                nickname/message -> to send a secret message to the player with the specified nickname
                message -> to send a message to everyone""");
        string = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(new ChatMessage(new SerializableInput(getNickname(),getString())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while sending the message: "+e.getMessage());
        }
    }

    public void update(ResponseMessage response){
        if(response.getModel().getClientNickname().equals(this.getNickname())||response.getModel().getClientNickname().equals(Config.broadcastNickname))
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
            System.err.println("Please insert a number...");
            scanner.next();
        }
        int choice = scanner.nextInt();
        if (choice == 1 || choice == 2)
            return choice == 2;
        else {
            System.err.println("Invalid input. Try again...");
            return chooseColumn();
        }
    }

    public Integer selectTemporaryTileInput(){
        System.out.println("Which tile do you want to insert?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.err.println("Invalid input");
            System.out.println("Insert the tile number: ");
            scanner.next();
        }
        return scanner.nextInt()-1;
    }

    public Integer selectColumnInput(){
        System.out.println("\nIn which column do you want to insert your tiles?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.err.println("Invalid input");
            System.out.println("Insert the column: ");
            scanner.next();
        }
        return scanner.nextInt()-1;
    }

    private boolean chooseTiles(){
        System.out.println("Do you want to choose another tile?\n1: Yes\n2: No");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.err.println("Please insert a number...");
            scanner.next();
        }
        int choice = scanner.nextInt();
        if (choice == 1 || choice == 2) {
            setTurnState(UITurnState.YOUR_TURN_COLUMN);
            return choice == 1;
        }
        else {
            System.err.println("Invalid input. Try again...");
            return chooseTiles();
        }
    }

    private void selectTiles(){
        do{
            point = selectTilesInput();
            try {
                setChangedAndNotifyObservers(new ConfirmTileSelection(new SerializableInput(getNickname(), getPoint())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while choosing the tiles: "+e.getCause());
            }
        }while(noMoreSelectableTiles && chooseTiles());
        try {
            setChangedAndNotifyObservers(new ConfirmSelectedTiles(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the tile selection: "+e.getCause());
        }
    }

    private void selectColumn(){
        do{
            number = selectColumnInput();
        }while(chooseColumn());
        try {
            setChangedAndNotifyObservers(new ConfirmColumnSelection(new SerializableInput(getNickname(), number)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the column: "+e.getCause());
        }
    }

    private void selectTemporaryTiles(){
        do {
            number = selectTemporaryTileInput();
            try {
                setChangedAndNotifyObservers(new ConfirmTilePlacement(new SerializableInput(getNickname(), number)));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while inserting the tiles: "+e.getCause());
            }
        }while(noMoreTemporaryTiles);
    }

    private Point selectTilesInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the row: ");
        while (!scanner.hasNextInt()) {
            System.err.println("Invalid input");
            System.out.println("Insert the row: ");
            scanner.next();
        }
        int row = scanner.nextInt();
        System.out.println("Insert the column: ");
        while (!scanner.hasNextInt()) {
            System.err.println("Invalid input");
            System.out.println("Insert the column: ");
            scanner.next();
        }
        int column = scanner.nextInt();
        return new Point(column-1, row-1);
    }

    private void askBoard() {
        try {
            setChangedAndNotifyObservers(new AskForBoard(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the board: "+e.getMessage());
        }
    }

    private void askShelf() {
        try {
            setChangedAndNotifyObservers(new AskForShelf(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
    }

    private void askAllShelves() {
        try {
            setChangedAndNotifyObservers(new AskForAllShelves(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
    }

    private void askPersonalGoal() {
        try {
            setChangedAndNotifyObservers(new AskForPersonalGoal(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
    }

    private void askTemporaryTiles() {
        try {
            setChangedAndNotifyObservers(new AskForTemporaryTiles(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
    }

    private void askCommonGoals() {
        try {
            setChangedAndNotifyObservers(new AskForCommonGoals(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
    }

    private void askCurrentPlayer() {
        try {
            setChangedAndNotifyObservers(new AskForCurrentPlayer(new SerializableInput(getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the current player: "+e.getMessage());
        }
    }

    public void showBoard(SerializableGame model){
        Board board = new Board(model.getSelectablePoints(), model.getTemporaryPoints());
        board.setBoard(model.getGameBoard());
        System.out.println("-----GAME BOARD-----");
        board.printBoard();
    }

    public void showShelf(SerializableGame model){
        System.out.println("\n--- "+nameColors.get(model.getPlayerName())+model.getPlayerName()+ConsoleColors.RESET+" ---");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getPlayerShelf());
        shelf.printShelf();
    }

    public void showAllShelves(SerializableGame model){
        for (String name : model.getAllShelves().keySet())
        {
            System.out.println("\n--- " + nameColors.get(name) + name + ConsoleColors.RESET + " ---");
            Shelf shelf = new Shelf();
            shelf.setShelf(model.getAllShelves().get(name));
            shelf.printShelf();
        }
    }

    public void showPersonalGoal(SerializableGame model){
        System.out.println("---YOUR PERSONAL GOAL---");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getPlayerPersonalGoal());
        shelf.printShelf();
    }

    public void showCommonGoals(SerializableGame model){
        System.out.println("---COMMON GOALS---\n");
        System.out.println(model.getCommonGoalDesc().get(0) + "\n");
        System.out.println(model.getCommonGoalDesc().get(1) + "\n");
    }

    public void showCurrentPlayer(SerializableGame model){
        if (getFlag())
            System.out.println("The current player is: "+nameColors.get(model.getPlayerName()) + model.getPlayerName()+ConsoleColors.RESET);
    }

    private void showChat() {
        number = 0;
        Scanner scanner = new Scanner(System.in);
            System.out.print("""
                    Do you want to print the global chat or the chat with a specific player?
                    1- Global chat
                    2- A specific chat
                    """);
            try {
                number = scanner.nextInt();
            } catch (InputMismatchException ignore) {
            }
            if (number == 1) {
                askGlobalChat();
            } else if (number == 2) {
                askSpecificChat();
            } else {
                System.err.println("Invalid input...");
            }
    }
    private void askGlobalChat(){
        try {
            setChangedAndNotifyObservers(new AskForChat(new SerializableInput(getNickname(),Config.everyone)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while asking for the Global chat" + e.getMessage());
        }
    }

    private void askSpecificChat(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the name of the player that you want to see the chat with:");
        string = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(new AskForChat(new SerializableInput(getNickname(),getString())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while asking for the Specific chat: " + e.getMessage());
        }
    }

    private void joinGame() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Insert your nickname: ");
            nickname = scanner.nextLine();
            if(nickname.equals(Config.broadcastNickname))
                System.err.println("You can't choose \"broadcast\" as nickname...");
        }while(nickname.equals(Config.broadcastNickname));
        try {
            setChangedAndNotifyObservers(new InitializePlayer(new SerializableInput(this.getNickname())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: "+e.getCause());
        }
    }

    public void askNumber(){
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("Insert number of players: ");
            try {
                number = scanner.nextInt();
            } catch (InputMismatchException e)
            {
                System.err.println("Invalid Number of players. Try again...");
                System.out.println("Insert number of players: ");
                scanner.next();
                number = scanner.nextInt();
            }
            if(number<Config.minPlayers || number>Config.maxPlayers)
                System.err.println("Invalid Number of players. Try again...");
        }while(number<Config.minPlayers || number>Config.maxPlayers);
        try {
            setChangedAndNotifyObservers(new ConfirmNumberOfPlayers(new SerializableInput(getNumber())));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error"+e.getMessage());
        }
    }

    public void askLoadGame(){
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Save file found. Would you like to load the game from it?\n1: yes\n2: no");
            number = scanner.nextInt();
            if(number != 1 && number != 2)
                System.err.println("Invalid Input, try again...");
        } while (number != 1 && number != 2);
        if (number == 1) {
            try {
                setChangedAndNotifyObservers(new LoadGameSelection(new SerializableInput(getNumber())));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error" + e.getMessage());
            }
        }
    }

    public void kill(int option){
        if(option==0) {
            System.err.println("Unable to join the game; lobby is full.\nClosing the process...");
            printImageKill();
        }
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
        System.out.println("""
                ⠀⠀⠀⠀⢀⠀⠀⠀⠀⢀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣤⣤⣤⠤⠤⠤⠤⠤⣤⣤⣤⡴⠶⠶⠶⠤⠤⠤⠤⢤⣤⣤⣶⣦⣤⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣤⡤⠶⠶⠶⠤⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⣻⣿⣿⣿⣿⣿⣿⣿⣭⣁⠀⢀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⢸⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⡅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⣿⣿⡿⣿⣿⣿⣇⡈⠉⠉⠉⠉⠉⣻⠛⠛⠒⠲⢶⣾⣿⡿⠦⢤⣤⣤⣴⣿⣿⣦⣀⣀⣀⣀⣀⡀⠀⠀⠀⡀⠘⣷⣄⠀⠀⡴⠃⠀⠀⠀⠀⠀⠀⢰⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⣿⣿⣿⡀⠈⠛⢯⣉⠉⠛⠚⠲⠶⠯⣄⣀⣀⣠⣿⣿⠃⠀⠀⠀⠀⣠⣿⠋⠁⠀⠀⠀⠀⠀⠀⠉⠉⠉⣛⠿⢿⣿⣿⣿⡶⠦⠤⣄⣀⡀⠀⠀⠀⢸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠸⣿⣿⡨⢷⡀⠀⠠⠉⠳⢦⣀⠀⠀⠀⠈⢻⣿⣿⡿⠿⢧⣄⣀⡀⠸⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⣶⠟⠉⠀⠀⠀⠀⠀⠀⠈⠉⠛⠲⢶⣿⣿⣦⣄⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⡿⠙⡇⠀⢳⡤⠋⠀⠀⠀⢹⣷⢦⣤⣴⣿⠿⠿⠃⠀⠈⠀⠿⢿⣿⠿⠷⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⠿⠛⠋⠉⠉⠉⠉⠉⠙⠒⠂⠀⠠⠄
                ⠀⠀⠀⠀⡏⠀⣿⠉⠠⠿⣄⠀⠀⠀⢀⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⣰⠟⠁⠀⠀⠀⠀⠉⠛⠲⠤⢤⣀⣀⠀⠠⢿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡾⠁⠀⠀⠀⠀⠀⠀     You placed 4th...⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⢸⡟⠛⠻⡆⠀⠀⣿⣿⣶⣾⣿⣿⣿⣿⡿⠀⠉⠳⢤⡀⠀⣴⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣟⢳⣾⣷⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⢸⣇⡀⢀⣿⣶⣾⣿⣿⣿⣿⣿⠟⠉⢻⣆⠀⣰⣧⣀⣹⣿⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡾⠛⠋⠉⠉⠉⢻⡟⠛⠓⠒⠲⠤⢤⣄⡀⢺⣷⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⠀⠀⠉⠻⣟⠀⠀⠀⠉⣿⢿⡟⠋⠉⠉⠉⠙⠻⢦⣄⡀⠀⠀⠀⠀⠀⠀⡼⠋⠀⠀⠀⠀⠀⣰⠟⠀⠀⠀⠀⠀⠀⠀⠘⢹⠻⣷⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⣾⠟⠉⠙⢿⣿⣷⠀⠀⠀⠀⠹⣆⠀⢀⡾⠁⢸⡇⠀⠀⠀⠀⠀⠀⠀⠈⢹⠗⢦⣄⡀⠀⣸⠁⠀⠀⠀⠀⢀⡞⠁⠀⠀⠀⠀⠀⠀⠀⣀⡴⠟⠋⠉⠉⠉⠉⠓⠲⠤⣤⣀⡀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⡏⠀⠀⠀⠈⣿⡟⣷⣤⣤⣀⣠⣾⣶⣾⣃⣤⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⢈⣧⣼⣈⣹⣶⣿⣆⠀⠀⠀⣠⡟⠀⠀⠀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠀⠒⠀⠀⠀⠀
                ⠀⠀⠀⣿⡷⣄⢀⣠⣿⣥⣾⣿⣿⠟⠀⠉⠛⢿⡟⣿⠂⠀⠀⠀⠀⠀⠀⢀⣤⣶⡛⠉⠉⠉⠉⠉⠙⣿⣷⣦⣅⡀⠀⠀⠀⠀⠀⠀⢀⡾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⣿⡿⠛⠛⢿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⢻⣇⠀⠀⠀⠀⠀⠀⣰⠟⠉⠉⠻⣆⡀⠀⠀⠀⠀⠿⠛⠀⠈⠙⠓⢦⣄⡀⠀⠀⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⢸⡏⠀⠀⠀⠀⠙⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠹⣆⠀⠀⠀⠀⣼⠃⠀⠀⠀⠀⠈⠙⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢦⣴⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⢸⡇⠀⠀⠀⠀⠀⢹⣿⡷⠶⠂⢀⣤⡄⠀⠀⠀⠘⣆⠀⠀⢰⠇⠀⠀⠀⠀⠀⠀⠀⠸⡆⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣠⣾⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⢸⡇⠀⠀⠀⠀⠀⠸⡟⠀⠀⠀⠈⢧⠀⠀⠀⠀⠀⠘⣦⣀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⡤⠶⠒⠛⠉⠉⠉⠉⠉⠉⠉⠛⠻⠷⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⣸⣷⣄⠀⠀⢀⣠⣼⣿⣄⢠⣤⠶⠛⠛⠛⡛⠓⠶⢶⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⣠⠴⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⢠⣿⡿⠛⠉⠉⠻⢧⣿⣿⣿⠏⠀⠀⠀⠀⢿⠃⠀⠀⠀⠈⠻⣿⡀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠻⡿⠀⠀⠀⠀⠀⠀⠈⠻⣿⠀⠀⠀⠀⢠⡟⠀⠀⠀⠀⠀⠀⠹⣧⠀⠀⠀⣰⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⢠⣿⡂⠀⠀⠀⠀⠀⠀⠀⠹⣷⣤⣤⡴⠟⠓⠒⠒⠒⠒⠶⠤⢤⣭⣷⣤⣼⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⢠⣿⠟⠋⠙⠓⠒⠲⠤⣤⣤⢰⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⢸⡟⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣿⣟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠈⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠓⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀""");
    }

    public void addBufferMessage(Message message)
    {
        chatBuffer.add(message);
    }

    public void writeBufferMessage()
    {
        if (chatBuffer.size()!=0)
            System.out.println("\n---INCOMING MESSAGES---");
        for (Message message : chatBuffer) {
            if (message.getScope().equals(Config.everyone)) {
                System.out.println(getNameColors().get(message.getSender()) + message.getSender() + ConsoleColors.RESET + " in global chat: " + message.getPayload());
            } else System.out.println(getNameColors().get(message.getSender()) + message.getSender() + ConsoleColors.RESET + " in private chat: " + message.getPayload());

        }
        chatBuffer.clear();
        System.out.print("\n");
    }
}

