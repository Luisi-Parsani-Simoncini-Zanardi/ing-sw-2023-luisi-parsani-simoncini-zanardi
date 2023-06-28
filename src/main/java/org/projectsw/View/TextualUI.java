package org.projectsw.View;
import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.*;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.Enums.UITurnState;
import org.projectsw.Util.RandomAlphanumericGen;
import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * The TextualUI class represents a textual user interface for a game.
 * It provides methods to interact with the user through the command line interface.
 * This class extends the Observable class and implements the Runnable interface.
 */
public class TextualUI extends Observable<InputMessage> implements Runnable {
    private UITurnState turnState = UITurnState.OPPONENT_TURN;
    private UIEndState endState = UIEndState.LOBBY;

    private final Object lock = new Object();
    private final Object lock2 = new Object();

    private volatile boolean connectFlag = true;
    private boolean flag = true;
    private boolean nickFlag = true;
    private boolean firstPlayerFlag = false;
    private boolean previousGameExist = false;
    private volatile boolean waitResult = true;
    private boolean endedTurn = false;
    private boolean reconnection = false;
    private boolean returnedFlag = false;
    private boolean stillChoosing = true;
    private boolean loadFromFile = false;
    private Integer number;
    private Point point;
    private String nickname;
    private String string;
    private String alphanumericKey;
    private String lastPlayerNick;
    private Boolean tileSelectionPossible = true;
    private Boolean temporaryTilesHold = true;
    private HashMap<String, String> nameColors;
    private final Client client;
    Scanner masterScanner = new Scanner(System.in);
    private ArrayList<Message> chatBuffer = new ArrayList<>();

    /**
     * The constructor for the TextualUI class.
     * @param client The client object used for communication with the server.
     */
    public TextualUI(Client client)
    {
        this.client = client;
        displayLogo();
    }

    /**
     * Sets the value of the returned flag.
     * @param returnedFlag The value to set for the returned flag.
     */
    public void setReturnedFlag(boolean returnedFlag){this.returnedFlag=returnedFlag;}

    /**
     * Gets the value of the flag.
     * @return The value of the flag.
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Gets the value of the turnState.
     * @return The value of the turnState.
     */
    public UITurnState getTurnState(){
        synchronized(lock){
            return turnState;
        }
    }

    /**
     * Gets the value of the endState.
     * @return The value of the endState.
     */
    public UIEndState getEndState(){
        synchronized(lock2){
            return endState;
        }
    }

    /**
     * Sets the value of the loadFromFile flag.
     * @param loadFromFile The value to set for the returned flag.
     */
    public void setLoadFromFile(boolean loadFromFile){this.loadFromFile = loadFromFile;}

    /**
     * Sets the value of the stillChoosing flag.
     * @param stillChoosing The value to set for the stillChoosing flag.
     */
    public void setStillChoosing(boolean stillChoosing){this.stillChoosing = stillChoosing;}

    /**
     * Sets the value of the firstPlayerFlag flag.
     * @param firstPlayerFlag The value to set for the firstPlayerFlag flag.
     */
    public void setFirstPlayerFlag(boolean firstPlayerFlag) {
        this.firstPlayerFlag = firstPlayerFlag;
    }

    /**
     * Sets the value of the nickFlag flag.
     * @param nickFlag The value to set for the nickFlag flag.
     */
    public void setNickFlag(boolean nickFlag){
        this.nickFlag=nickFlag;
    }

    /**
     * Sets the value of the previousGameExist flag.
     * @param previousGameExist The value to set for the previousGameExist flag.
     */
    public void setPreviousGameExist(boolean previousGameExist){
        this.previousGameExist=previousGameExist;
    }
    public String getAlphanumericKey() {return this.alphanumericKey; }

    /**
     * Gets the value of the client.
     * @return The value of the client.
     */
    public Client getClient(){return this.client;}
    public boolean getNickFlag() { return this.nickFlag; }

    /**
     * Gets the value of the string.
     * @return The value of the string.
     */
    public String getString(){return this.string;}

    /**
     * Gets the value of the number.
     * @return The value of the number.
     */
    public Integer getNumber(){
        return this.number;
    }

    /**
     * Gets the value of the point.
     * @return The value of the point.
     */
    public Point getPoint(){
        return this.point;
    }

    /**
     * Gets the value of the nickname.
     * @return The value of the nickname.
     */
    public String getNickname(){return this.nickname;}

    /**
     * Gets the value of the nameColors.
     * @return The value of the nameColors.
     */
    public HashMap<String, String> getNameColors(){return this.nameColors;}

    /**
     * Gets the value of the masterScanner.
     * @return The value of the masterScanner.
     */
    public Scanner getMasterScanner(){
        return this.masterScanner;
    }

    /**
     * Sets the value of the response flag.
     * @param response The value to set for the response flag.
     */
    public void setWaitResult(boolean response){
        this.waitResult = response;
    }

    /**
     * Sets the value of the nameColors.
     * @param nameColors The value to set for the nameColors.
     */
    public void setNameColors(HashMap<String, String> nameColors){
        this.nameColors = nameColors;
    }

    /**
     * Sets the value of the resp flag.
     * @param resp The value to set for the resp flag.
     */
    public void setFlag(boolean resp){
        this.flag =resp;
    }

    /**
     * Sets the value of the turnState.
     * @param state The value to set for the turnState.
     */
    public void setTurnState(UITurnState state){
        synchronized (lock){
            this.turnState = state;
        }
    }

    /**
     * Sets the value of the endState.
     * @param state The value to set for the endState.
     */
    public void setEndState(UIEndState state){
        synchronized (lock2){
            this.endState = state;
        }
    }

    /**
     * Sets the value of the connectFlag flag.
     * @param connectFlag The value to set for the connectFlag flag.
     */
    public void setConnectFlag(boolean connectFlag){this.connectFlag=connectFlag;}

    /**
     * Sets the value of the lastPlayerNick.
     * @param nick The value to set for the lastPlayerNick.
     */
    public void setLastPlayerName(String nick){
        this.lastPlayerNick=nick;
    }

    /**
     * Sets the value of the reconnection flag.
     * @param flag The value to set for the reconnection flag.
     */
    public void setReconnection(Boolean flag) {this.reconnection=flag; }

    /**
     * Sets the value of the noMoreTemporaryTiles flag.
     * @param bool The value to set for the noMoreTemporaryTiles flag.
     */
    public void setTemporaryTilesHold(boolean bool){
        this.temporaryTilesHold = bool;
    }

    /**
     * Sets the value of the noMoreSelectableTiles flag.
     * @param bool The value to set for the noMoreSelectableTiles flag.
     */
    public void setTileSelectionPossible(boolean bool){
        this.tileSelectionPossible = bool;
    }

    /**
     * Waits until the returned flag is set to true.
     * This method blocks the execution until the flag is set.
     */
    private void waitReturn() {
        returnedFlag=false;
        while (!returnedFlag) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("An error occurred while waiting for the returned flag: " + e);
                }
            }
        }
        returnedFlag=false;
    }

    /**
     * Runs the textual user interface.
     */
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int choice = 0;
        RandomAlphanumericGen randomizer = new RandomAlphanumericGen();
        alphanumericKey = randomizer.generateRandomString(100);
        connect();
        while (connectFlag) {
        }
        do {
            System.out.println("\nCHOOSE AN OPTION:");
            if (nickFlag)
                System.out.println("1: Insert your nickname");
            if (firstPlayerFlag)
                System.out.println("2: Insert the number of players");
            if (firstPlayerFlag && previousGameExist)
                System.out.println("3: Load the game from file");
            if (firstPlayerFlag || nickFlag) {
                try {
                    choice = masterScanner.nextInt();
                } catch (InputMismatchException e) {
                    choice = 0;
                    masterScanner.next();
                }
                switch (choice) {
                    case 1 -> askNickname();
                    case 2 -> askNumber();
                    case 3 -> askLoadGame();
                    default -> System.err.println("Invalid selection!!!");
                }
            }
        } while (nickFlag || firstPlayerFlag);
        endedTurn = false;
        if (getEndState() == UIEndState.LOBBY)
            System.out.println("Waiting response from the server...\n");
        if (stillChoosing && !firstPlayerFlag) {
            waitReturn();

            if (loadFromFile) {
                nickFlag = true;
                do {
                    System.out.println("\nCHOOSE AN OPTION:");
                    if (nickFlag)
                        System.out.println("1: Insert your nickname");
                    try {
                        choice = masterScanner.nextInt();
                    } catch (InputMismatchException e) {
                        choice = 0;
                        masterScanner.next();
                    }
                    switch (choice) {
                        case 1 -> askNameAgainForReload();
                        default -> System.err.println("Invalid selection!!!");
                    }
                } while (nickFlag || firstPlayerFlag);
            }
        }

        while (getEndState() == UIEndState.LOBBY) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("An error occurred while waiting for the game to start: " + e);
                }
            }
        }
        System.out.println("Game started!");
        flag = true;
        askCurrentPlayer();
        do {
            writeBufferMessage();
            System.out.println("---CHOOSE AN ACTION---");
            System.out.println("Press 0 to see all possible actions...");
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
                        if (getTurnState() == UITurnState.OPPONENT_TURN) {
                            System.err.println("It's not your turn. Please wait...");
                        }
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
                        } else {
                            if (getTurnState() == UITurnState.YOUR_TURN_SELECTION) {
                                System.err.println("You can't insert a tile now...");
                            } else {
                                askShelf();
                                askTemporaryTiles();
                                waitReturn();
                                if (getTurnState() == UITurnState.YOUR_TURN_COLUMN) {
                                    setTurnState(UITurnState.YOUR_TURN_INSERTION);
                                    selectColumn();
                                    waitReturn();
                                }
                                if (getTurnState() == UITurnState.YOUR_TURN_INSERTION) {
                                    selectTemporaryTiles();
                                    System.out.println("You ended your turn.");
                                    try {
                                        setChangedAndNotifyObservers(new EndTurn(new SerializableInput(alphanumericKey, getNickname(), client)));
                                    } catch (RemoteException e) {
                                        throw new RuntimeException("An error occurred while ending the turn: " + e);
                                    }
                                    waitReturn();
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
            }
        } while (getEndState() == UIEndState.RUNNING || waitResult);
        ending();
    }

    /**
     * Performs the necessary actions when the game ends.
     * Closes the scanner, sets the flag to false, and prints messages.
     * If the nickname is the last player's nickname, asks for results, waits for return, and kills the client.
     */
    public void ending(){
        getMasterScanner().close();
        System.err.println("The game ended. You can no longer do actions.");
        setFlag(false);
        System.out.println("Wait for results please...");
        if(nickname.equals(lastPlayerNick)) {
            try {
                setChangedAndNotifyObservers(new AskForResults(new SerializableInput(alphanumericKey, getNickname(), client)));
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while asking for results: "+e.getMessage());
            }
        }
        waitReturn();
        try {
            client.kill();
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while killing the client: "+e.getMessage());
        }
    }

    /**
     * Prints the command menu for the game.
     * Displays a numbered list of available commands.
     */
    private void printCommandMenu(){
        System.out.print("""
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

    /**
     * Connects to the server.
     * Notifies the observers with a Connect object containing the necessary input.
     * Handles network errors by throwing a RuntimeException.
     */
    private void connect(){
        try {
            setChangedAndNotifyObservers(new Connect(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred connecting to the server: "+e.getMessage());
        }
    }

    /**
     * Exits the game.
     * Notifies the observers with the necessary input to perform cleanup actions and end the game.
     * Handles network errors by throwing a RuntimeException.
     */
    public void exit(){
        System.out.println("Exiting...");
        try {
            setChangedAndNotifyObservers(new NotActive(new SerializableInput(alphanumericKey, getNickname(), client)));
            setChangedAndNotifyObservers(new DeleteModelObserver(new SerializableInput(alphanumericKey, client)));
            if(getTurnState()!=UITurnState.OPPONENT_TURN)
                setChangedAndNotifyObservers(new EndTurnExit(new SerializableInput(alphanumericKey, getNickname(), client)));
            client.kill();
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while removing the tui observer: "+e.getMessage());
        }
    }

    /**
     * Writes a message in the chat.
     * Prompts the user to enter a message and sends it to the server.
     * Messages can be either public or private (directed to a specific player).
     * Handles network errors by throwing a RuntimeException.
     */
    private void writeInChat(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Messages should be formatted like this:
                nickname/message -> to send a secret message to the player with the specified nickname
                message -> to send a message to everyone""");
        string = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(new ChatMessage(new SerializableInput(alphanumericKey, getNickname(),getString(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while sending the message: "+e.getMessage());
        }
    }

    /**
     * Updates the client based on the received response message.
     * Executes the response if the alphanumeric ID matches the client's key or the broadcast ID.
     * @param response the response message to process and execute
     */
    public void update(ResponseMessage response){
        if(response.getModel().getAlphanumericID().equals(this.alphanumericKey)||response.getModel().getAlphanumericID().equals(Config.broadcastID))
            response.execute(this);
    }

    /**
     * Prompts the user to choose a column.
     * Returns true if the user chooses to proceed, false otherwise.
     * @return true if the user chooses to proceed, false otherwise
     */
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

    /**
     * Prompts the user to select a temporary tile input.
     * Returns the selected tile number minus one.
     * @return the selected tile number minus one
     */
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

    /**
     * Prompts the user to select a column input.
     * Returns the selected column number minus one.
     * @return the selected column number minus one
     */
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

    /**
     * Prompts the user to select a temporary tile input.
     * Returns the selected tile number minus one.
     * @return the selected tile number minus one
     */
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

    /**
     * Allows the user to select tiles from the board.
     * It prompts the user to select tiles until there are no more selectable tiles or the user chooses to stop.
     * After each tile selection, it notifies the observers with the selected tile.
     * Finally, it confirms the selected tiles.
     */
    private void selectTiles(){
        do{
            point = selectTilesInput();
            try {
                setChangedAndNotifyObservers(new ConfirmTileSelection(new SerializableInput(alphanumericKey, getNickname(), getPoint(), client)));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while choosing the tiles: "+e.getCause());
            }
            waitReturn();
        }while(tileSelectionPossible && chooseTiles());
        try {
            setChangedAndNotifyObservers(new ConfirmSelectedTiles(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the tile selection: "+e.getCause());
        }
    }

    /**
     * Allows the user to select a column on the board.
     * It prompts the user to select a column until the user chooses to stop.
     * After each column selection, it notifies the observers with the selected column.
     * Finally, it confirms the selected column.
     */
    private void selectColumn(){
        do{
            number = selectColumnInput();
        }while(chooseColumn());
        try {
            setChangedAndNotifyObservers(new ConfirmColumnSelection(new SerializableInput(alphanumericKey, number, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while confirming the column: "+e.getCause());
        }
    }

    /**
     * Allows the user to select temporary tiles to insert.
     * It prompts the user to select a temporary tile until there are no more temporary tiles left.
     * After each tile selection, it notifies the observers with the selected tile placement.
     * Finally, it waits for the return flag to be set to continue the loop.
     */
    private void selectTemporaryTiles(){
        do {
            number = selectTemporaryTileInput();
            try {
                setChangedAndNotifyObservers(new ConfirmTilePlacement(new SerializableInput(alphanumericKey, getNickname(), number, client)));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while inserting the tiles: "+e.getCause());
            }
            waitReturn();
        }while(temporaryTilesHold);
    }

    /**
     * Prompts the user to input the row and column for tile selection.
     * It validates the user's input and returns the selected point (row, column).
     * @return The selected point (row, column).
     */
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

    /**
     * Sends a request to the server to retrieve the current game board.
     * It waits for the response and updates the local board state.
     */
    private void askBoard() {
        try {
            setChangedAndNotifyObservers(new AskForBoard(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the board: "+e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the current shelf.
     * It waits for the response and updates the local shelf state.
     */
    private void askShelf() {
        try {
            setChangedAndNotifyObservers(new AskForShelf(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the shelf: "+e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the all the shelves.
     * It waits for the response and updates the local all the shelves state.
     */
    private void askAllShelves() {
        try {
            setChangedAndNotifyObservers(new AskForAllShelves(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the personal goal.
     * It waits for the response and updates the local personal goal.
     */
    private void askPersonalGoal() {
        try {
            setChangedAndNotifyObservers(new AskForPersonalGoal(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the temporary tiles.
     * It waits for the response and updates the local temporary tiles.
     */
    private void askTemporaryTiles() {
        try {
            setChangedAndNotifyObservers(new AskForTemporaryTiles(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
    }

    /**
     * Sends a request to the server to retrieve the common goals.
     * It waits for the response and updates the local common goals.
     */
    private void askCommonGoals() {
        try {
            setChangedAndNotifyObservers(new AskForCommonGoals(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for all shelves: "+e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the current player.
     * It waits for the response and updates the local current player.
     */
    private void askCurrentPlayer() {
        try {
            setChangedAndNotifyObservers(new AskForCurrentPlayer(new SerializableInput(alphanumericKey, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while asking for the current player: "+e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the global chat.
     * It waits for the response and updates the local global chat.
     */
    private void askGlobalChat(){
        try {
            setChangedAndNotifyObservers(new AskForChat(new SerializableInput(alphanumericKey, getNickname(),Config.everyone, client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while asking for the Global chat" + e.getMessage());
        }
        waitReturn();
    }

    /**
     * Sends a request to the server to retrieve the chat.
     * It waits for the response and updates the local chat.
     */
    private void askSpecificChat(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the name of the player that you want to see the chat with:");
        string = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(new AskForChat(new SerializableInput(alphanumericKey, getNickname(),getString(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while asking for the Specific chat: " + e.getMessage());
        }
        waitReturn();
    }

    /**
     * Prompts the player to enter their previous nickname when reloading a game from a file.
     * Sends the entered nickname to the server and waits for a response.
     */
    public void askNameAgainForReload() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Loading game from file, insert your previous nickname:\n");
        nickname = scanner.nextLine();
        try {
            setChangedAndNotifyObservers(new SendNickname(new SerializableInput(alphanumericKey, this.getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: " + e.getCause());
        }
        waitReturn();
    }

    /**
     * Prompts the player to enter their nickname.
     * If the `nickFlag` is set to true, the player can enter their nickname.
     * If the `nickFlag` is set to false, an error message is displayed.
     * If the player is reconnecting to a game, it calls the `reconnectionJoin()` method.
     * Sends the entered nickname to the server and waits for a response.
     */
    public void askNickname() {
        if(nickFlag) {
            Scanner scanner = new Scanner(System.in);
            //playerReconnection();
            if (!reconnection) {
                do {
                    System.out.println("Insert your nickname: ");
                    nickname = scanner.nextLine();
                    if (nickname.equals(Config.broadcastID))
                        System.err.println("You can't choose \"broadcast\" as nickname...");
                } while (nickname.equals(Config.broadcastID));
                try {
                    setChangedAndNotifyObservers(new SendNickname(new SerializableInput(alphanumericKey, this.getNickname(), client)));
                } catch (RemoteException e) {
                    throw new RuntimeException("An error occurred: " + e.getCause());
                }
            } else {
                reconnectionJoin();
            }
            waitReturn();
        }else{
            System.err.println("You can't choose your nickname now!!!");
        }
    }

    /**
     * Prompts the first player to enter the number of players.
     * If the `firstPlayerFlag` is set to true, the player can enter the number of players.
     * If the `firstPlayerFlag` is set to false, an error message is displayed.
     * Uses a `Scanner` to read the user input and validates the entered number.
     * Sends the entered number of players to the server and waits for a response.
     * Updates the `firstPlayerFlag` and `nickFlag` accordingly.
     */
    public void askNumber(){
        if(firstPlayerFlag) {
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Insert number of players: ");
                try {
                    number = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Invalid Number of players. Try again...");
                    System.out.println("Insert number of players: ");
                    scanner.next();
                    number = scanner.nextInt();
                }
                if (number < Config.minPlayers || number > Config.maxPlayers)
                    System.err.println("Invalid Number of players. Try again...");
            } while (number < Config.minPlayers || number > Config.maxPlayers);
            try {
                setChangedAndNotifyObservers(new ConfirmNumberOfPlayers(new SerializableInput(alphanumericKey, getNumber(), client)));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error" + e.getMessage());
            }
        }else{
            System.err.println("You can't choose the number of players now!!!");
        }
        firstPlayerFlag=false;
        nickFlag = true;
    }

    /**
     * Prompts the first player to choose whether to load a previous game or not.
     * If the `firstPlayerFlag` is true and a previous game exists, the player can choose to load the game.
     * If the `firstPlayerFlag` is false or no previous game exists, an error message is displayed.
     * Uses a `Scanner` to read the user input.
     * Sends the load game selection to the server and waits for a response.
     * Prompts the player to enter their previous nickname and sends it to the server.
     * Updates the `nickFlag` and `firstPlayerFlag` accordingly.
     */
    public void askLoadGame(){
        if(firstPlayerFlag&& previousGameExist) {
            Scanner scanner = new Scanner(System.in);
            try {
                setChangedAndNotifyObservers(new LoadGameSelection(new SerializableInput(alphanumericKey, client)));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error" + e.getMessage());
            }
            waitReturn();
            do {
                nickFlag = true;
                System.out.println("\ninsert your previous nickname: ");
                nickname = scanner.nextLine();
                try {
                    setChangedAndNotifyObservers(new SendNickname(new SerializableInput(alphanumericKey, this.getNickname(), client)));
                } catch (RemoteException e) {
                    throw new RuntimeException("An error occurred: " + e.getCause());
                }
                waitReturn();
            } while(nickFlag);
        }else if(!previousGameExist){
            System.err.println("There isn't a file to load!!!");
            return;
        }else{
            System.err.println("You can't do this selection now!!!");
        }
        firstPlayerFlag=false;
    }

    /**
     * Displays the current game board.
     * @param model The SerializableGame object containing the game board.
     */
    public void showBoard(SerializableGame model){
        Board board = new Board(model.getSelectablePoints(), model.getTemporaryPoints());
        board.setBoard(model.getGameBoard());
        System.out.println("-----GAME BOARD-----");
        board.printBoard();
    }

    /**
     * Displays the current shelf.
     * @param model The SerializableGame object containing the shelf.
     */
    public void showShelf(SerializableGame model){
        System.out.println("\n--- "+nameColors.get(model.getPlayerName())+model.getPlayerName()+ConsoleColors.RESET+" ---");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getPlayerShelf());
        shelf.printShelf();
    }

    /**
     * Displays all the shelves.
     * @param model The SerializableGame object containing all the shelves.
     */
    public void showAllShelves(SerializableGame model){
        for (String name : model.getAllShelves().keySet())
        {
            System.out.println("\n--- " + nameColors.get(name) + name + ConsoleColors.RESET + " ---");
            Shelf shelf = new Shelf();
            shelf.setShelf(model.getAllShelves().get(name));
            shelf.printShelf();
        }
    }

    /**
     * Displays the personal goal.
     * @param model The SerializableGame object containing the personal goal.
     */
    public void showPersonalGoal(SerializableGame model){
        System.out.println("---YOUR PERSONAL GOAL---");
        Shelf shelf = new Shelf();
        shelf.setShelf(model.getPlayerPersonalGoal());
        shelf.printShelf();
    }

    /**
     * Displays the common goals.
     * @param model The SerializableGame object containing the common goals.
     */
    public void showCommonGoals(SerializableGame model){
        System.out.println("---COMMON GOALS---\n");
        System.out.println(model.getCommonGoalDesc().get(0) + "\n");
        System.out.println(model.getCommonGoalDesc().get(1));
    }

    /**
     * Displays the current player.
     * @param model The SerializableGame object containing the current player.
     */
    public void showCurrentPlayer(SerializableGame model){
        if (model.getPlayerName().equals(nickname))
            System.out.println(ConsoleColors.PURPLE+"   --It's your turn--"+ConsoleColors.RESET);
        else
            System.out.println("The current player is: "+nameColors.get(model.getPlayerName()) + model.getPlayerName()+ConsoleColors.RESET);
    }

    /**
     * Displays the chat.
     */
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

    /**
     * Handles the reconnection process for a disconnected player.
     * Prompts the player to enter their old nickname and sends it to the server.
     */
    private void reconnectionJoin() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Looks like this game has a disconnected player, insert your old nickname to play: ");
            nickname = scanner.nextLine();
            if (nickname.equals(Config.broadcastID))
                System.err.println("You can't choose \"broadcast\" as nickname...");
        } while (nickname.equals(Config.broadcastID));
        try {
            setChangedAndNotifyObservers(new SendNickname(new SerializableInput(alphanumericKey, this.getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred: " + e.getCause());
        }
    }

    private void playerReconnection() {
        try {
            setChangedAndNotifyObservers(new AmIReconnecting(new SerializableInput(alphanumericKey, getNickname(), client)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Terminates the client process.
     * If the `option` is 0, it indicates that the client was unable to join the game because the lobby is full.
     * In this case, an error message is displayed, and the process is closed after printing an image.
     * Otherwise, the `kill()` method of the client is called to terminate the process.
     * If an error occurs while closing the process, a message is displayed instructing the user to manually close the client.
     * Finally, the process is exited with a status of 0.
     * @param option the option indicating the reason for the termination
     */
    public void kill(int option){
        if(option==0) {
            System.err.println("Unable to join the game; lobby is full.\nClosing the process...");
            printImageKill();
        }
        try {
            client.kill();
        } catch (RemoteException e) {
            System.err.println("Error while closing the process, please manually close the client");
            System.exit(0);
        }
        System.exit(0);
    }

    /**
     * Displays the game logo using colored console output.
     */
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

    /**
     * Displays the kill image using colored console output.
     */
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

    /**
     * Displays the results medals using colored console output.
     */
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

    /**
     * Displays the cobweb using colored console output.
     */
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

    /**
     * Adds a message to the chat buffer.
     * @param message The message to be added to the buffer.
     */
    public void addBufferMessage(Message message)
    {
        chatBuffer.add(message);
    }

    /**
     * Prints the messages stored in the chat buffer and clears the buffer.
     */
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

