package org.projectsw.Controller;

import org.projectsw.Distributed.Messages.InputMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ChatMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import org.projectsw.Distributed.Client;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.*;
import org.projectsw.Util.OneToOneHashmap;
import org.projectsw.View.ConsoleColors;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;

import static org.projectsw.Model.Enums.TilesEnum.EMPTY;
import static org.projectsw.Model.Enums.TilesEnum.UNUSED;

/**
 * The class contains the application logic methods of the game.
 */
public class Engine{
    private final OneToOneHashmap<Client, String> clients = new OneToOneHashmap<>();
    private Game game;
    private static int counter = 0;
    private SaveGameStatus saveGameStatus;
    private final Object lock = new Object();
    private final Server server;
    private ArrayList<String> freeNamesUsedInLastGame = new ArrayList<>();
    public boolean loadFromFile = false;

    /**
     * constructor
     * @param server is the server bound to this controller
     */
    public Engine(Server server){
        this.server=server;
    }
    /**
     * get the Clients
     * @return the clients
     */
    public OneToOneHashmap<Client, String> getClients() { return this.clients; }

    /**
     * get the game on which the controller is running
     * @return current game
     */
    public Game getGame() { return this.game;}

    private boolean saveFileFound(){
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        return saveGameStatus.checkExistingSaveFile();
    }
    private Game retrieveGame(){
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        return saveGameStatus.retrieveGame();
    }

    public void setGame(Game activeGame){
            this.game=activeGame;
    }

    public void setCurrentNickname(String nickname){
        getGame().setClientPlayerNickname(nickname);
    }

    /**
     * get the saveGameStatus of the game
     * @return saveGameStatus of the game
     */
    public SaveGameStatus getSaveGameStatus() { return this.saveGameStatus; }

    /**
     * If the game state isn't LOBBY the join request is negated. If the game state is LOBBY it creates a new
     * player object with the right position and puts it in the arrayList of players.
     * Then checks if the lobby is fulled: if it is, calls the method to start the game,
     * if it isn't the game state remains LOBBY, waiting for new join requests.
     * @param nickname the nickname of the player to be created.
     */
    public void playerJoin (String nickname) {
            if (game.getGameState().equals(GameState.LOBBY)) {
                int newPlayerPosition = game.getPlayers().size();
                Player newPlayer = new Player(nickname, newPlayerPosition);
                game.addPlayer(newPlayer);
                if(this.game.getPlayers().size()==1){
                    this.game.setFirstPlayer(newPlayer);
                    this.game.setCurrentPlayer(newPlayer);
                }
                if (game.getPlayers().size() == game.getNumberOfPlayers()) {
                    startGame();
                }
            }
    }

    /**
     * Sets the game status to RUNNING, saves the first instance of the game and lunch the first turn.
     */
    private void startGame(){
        game.setGameState(GameState.RUNNING);
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        try {
            game.setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(Config.broadcastNickname, randomColors())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while setting the name colors: " + e);
        }
        fillBoard();
        try {
            game.setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname, getGame())));
        } catch (RemoteException e){
            throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
        }
        saveGameStatus.saveGame();
    }

    /**
     * If the selected point isn't already selected it adds a point to the temporaryPoints list after checking if the
     * size of temporaryPoints is smaller than the maximum remaining space in player's columns.
     * If the selected point is already selected it calls deselect tiles on that point.
     * @param selectedPoint the point that the player wants to select.
     */
    public void selectTiles(Point selectedPoint){
        if(game.getBoard().getTemporaryPoints().contains(selectedPoint)) deselectTiles(selectedPoint);
        else {
            try {
                if (selectionPossible()) {
                    game.getBoard().addTemporaryPoints(selectedPoint);
                    game.getBoard().updateSelectablePoints();
                    try {
                        game.setChangedAndNotifyObservers(new SendBoard(new SerializableGame(getGame().getCurrentClientID(),getGame())));
                    } catch (RemoteException e) {
                        throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
                    }
                    if (game.getBoard().getSelectablePoints().size() == 0 ||
                        game.getCurrentPlayer().getShelf().maxFreeColumnSpace() == game.getBoard().getTemporaryPoints().size()) {
                        try {
                            game.setChangedAndNotifyObservers(new ErrorSelectionNotPossible(new SerializableGame(getGame().getCurrentClientID())));
                        } catch (RemoteException e) {
                            throw new RuntimeException("An error occurred while notifying that the selection is not possible: " + e.getCause());
                        }
                    }
                }
            } catch (UnselectableTileException e){
                try {
                    game.setChangedAndNotifyObservers(new ErrorUnselectableTile(new SerializableGame(getGame().getCurrentClientID())));
                } catch (RemoteException ex) {
                    throw new RuntimeException("An error occurred while notifying that the selection is not possible: " + ex.getCause());
                }
            }
        }
    }

    /**
     * Remove the given point from the temporaryPoints list.
     * @param point the point to remove.
     */
    private void deselectTiles(Point point){
        game.getBoard().removeTemporaryPoints(point);
    }

    /**
     * The function returns true if the maximum columns space in the current's player shelf is bigger then the board's
     * temporaryPoints arraylist size, meaning that the selections is still possible.
     * @return true if the selection is possible, false if it isn't.
     */
    private boolean selectionPossible() {
        return game.getCurrentPlayer().getShelf().maxFreeColumnSpace() > game.getBoard().getTemporaryPoints().size();
    }

    /**
     * Calls a getTileFromBoard for every point in temporaryPoints, so adds the corresponding tiles in temporaryTiles,
     * after the copying cleans the temporaryPoints list and update the selectable columns.
     * Thanks to other exceptions in selectTiles the TemporaryPoints passed already do not correspond to empty or unused tiles,
     * but if they don't addTemporaryTile throws InvalidArgumentException.
     */
    public void confirmSelectedTiles() {
        if(game.getBoard().getTemporaryPoints().isEmpty()) {
            try {
                game.setChangedAndNotifyObservers(new ErrorEmptyTemporaryTiles(new SerializableGame(game.getCurrentClientID())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while sending and Empty Temporary Points Error"+e.getMessage());
            }
            return;
        }

        ArrayList<Point> selectedPoints = game.getBoard().getTemporaryPoints();
        for(Point point : selectedPoints){
            Tile tile = game.getBoard().getTileFromBoard(point);
            game.getCurrentPlayer().addTemporaryTile(tile);
        }
        game.getBoard().cleanTemporaryPoints();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
        temporaryTilesTransfer();
    }

    /**
     * Checks if the player has already selected a tile, if he did it, it calls deselectTiles, if he didn't and the column at the
     * specified index is selectable, it sets the passed index as selected column of the player.
     * @param index The index of column that player wants to select.
     */
    public void selectColumn(Integer index) {
        if(game.getCurrentPlayer().getShelf().isSelectionPossible()){
            if(game.getCurrentPlayer().getShelf().getSelectedColumn() == null) {
                try {
                    game.getCurrentPlayer().getShelf().setSelectedColumn(index);
                } catch (UnselectableColumnException e){
                    try {
                        game.setChangedAndNotifyObservers(new ErrorUnselectableColumn(new SerializableGame(game.getCurrentClientID())));
                    } catch (RemoteException e2) {
                        throw new RuntimeException("An error occurred while sending an Unselectable Column Error"+e2.getMessage());
                    }
                }
            } else deselectColumn();
        } //else throw new UnselectableColumnException("Tiles are already placed, column no longer changeable");
    }

    /**
     * Sets as null the selected column index and updates the selectable columns arrayList in currentPlayer's shelf.
     */
    private void deselectColumn() {
        game.getCurrentPlayer().getShelf().cleanSelectedColumn();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
    }

    private void sendShelfAndTiles(){
        try {
            game.setChangedAndNotifyObservers(new SendShelf(new SerializableGame(getGame().getCurrentClientID(), game)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while updating the shelf: " + e.getCause());
        }

        if (!game.getCurrentPlayer().getTemporaryTiles().isEmpty()) {
            try {
                game.setChangedAndNotifyObservers(new SendTemporaryTiles(new SerializableGame(getGame().getCurrentClientID(), game)));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error while sending temporary tiles: "+e.getMessage());
            }
        }
    }
    /**
     * Add the tile at the selected index of temporaryTiles to the player's shelf in the previously selected column.
     * @param temporaryIndex the selected index of temporaryTiles.
     */
    public void placeTiles(Integer temporaryIndex){
        game.getCurrentPlayer().getShelf().setSelectionPossible(false);
        try {
            Tile tileToInsert = game.getCurrentPlayer().selectTemporaryTile(temporaryIndex);
            int selectedColumn = game.getCurrentPlayer().getShelf().getSelectedColumn();
            for (int i = Config.shelfHeight - 1; i >= 0; i--) {
                if (!game.getCurrentPlayer().getShelf().getShelf()[i][selectedColumn].getTile().equals(EMPTY)) {
                    if (i != Config.shelfHeight - 1) {
                        game.getCurrentPlayer().getShelf().insertTiles(tileToInsert, i + 1, selectedColumn);
                        sendShelfAndTiles();
                    }
                    break;
                }
                if (i == 0) {
                    game.getCurrentPlayer().getShelf().insertTiles(tileToInsert, i, selectedColumn);
                    sendShelfAndTiles();
                    break;
                }
            }
            if (game.getCurrentPlayer().getTemporaryTiles().isEmpty()) {
                deselectColumn();
                game.getCurrentPlayer().getShelf().setSelectionPossible(true);
                try {
                    game.setChangedAndNotifyObservers(new FinishedInserting(new SerializableGame(game.getCurrentClientID())));
                } catch (RemoteException e) {
                    throw new RuntimeException("An error occurred while notifying that the insertion is not possible: " + e.getCause());
                }
            }
        } catch (IndexOutOfBoundsException e) {
            try {
                game.setChangedAndNotifyObservers(new ErrorInvalidTemporaryTile(new SerializableGame(game.getCurrentClientID())));
            } catch (RemoteException e2) {
                throw new RuntimeException("An error occurred while sending an Invalid Temporary Tile Error"+e2.getMessage());
            }
        }
    }

    /**
     * Function that checks if the player has the requirements of the commonGoals in the game.
     * In the positive case it assigns the points and marks that the player has obtained
     * the points of the CommonGoal in question
     */
    public void checkCommonGoals(){
        for(int i=0; i<Config.numberOfCommonGoals; i++){
            if(this.getGame().getCommonGoals().get(i).checkRequirements(this.getGame().getCurrentPlayer().getShelf()) &&
                    !this.getGame().getCurrentPlayer().isCommonGoalRedeemed(i)){
                try {
                    int earnedPoints = this.getGame().getCommonGoals().get(i).getRedeemedNumber() * 2;
                    this.getGame().getCommonGoals().get(i).decreaseRedeemedNumber();
                    this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + earnedPoints);
                    this.getGame().getCurrentPlayer().setCommonGoalRedeemed(i,true);
                }catch(MinimumRedeemedPointsException ignore){}
            }
        }
    }

    /**
     * Sends results to the clients
     */

    public void sendResults(){
        try {
            getGame().setChangedAndNotifyObservers(new ResultsNotify(new SerializableGame(Config.broadcastNickname, getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while sending the results: "+e.getMessage());
        }
    }
    /**
     * Checks the number of tiles in the personal goal placed correctly and assigns the points earned.
     */
    public void checkPersonalGoal(){
        for (Player player : game.getPlayers()) {
            int numberRedeemed = 0;
            TilesEnum[][] shelf = tileToTilesEnum(player.getShelf());
            for (int i = 0; i < Config.shelfHeight; i++) {
                for (int j = 0; j < Config.shelfLength; j++) {
                    if (player.getPersonalGoal().getPersonalGoal()[i][j] == shelf[i][j] &&
                            player.getPersonalGoal().getPersonalGoal()[i][j] != EMPTY)
                        numberRedeemed++;
                }
            }
            switch (numberRedeemed) {
                case 0 -> player.setPoints(player.getPoints());
                case 1 -> player.setPoints(player.getPoints() + 1);
                case 2 -> player.setPoints(player.getPoints() + 2);
                case 3 -> player.setPoints(player.getPoints() + 4);
                case 4 -> player.setPoints(player.getPoints() + 6);
                case 5 -> player.setPoints(player.getPoints() + 9);
                case 6 -> player.setPoints(player.getPoints() + 12);

                default -> throw new IllegalArgumentException("Invalid tile value: " + numberRedeemed);
            }
        }
    }

    /**
     * Auxiliary method that transforms a shelf in a matrix of TilesEnum.
     * @param shelf the shelf to be transformed
     * @return the correspondent matrix of TilesEnum
     */
    private TilesEnum[][] tileToTilesEnum (Shelf shelf){
        TilesEnum[][] tmp = new TilesEnum[Config.shelfHeight][Config.shelfLength];
        for (int i = 0; i < Config.shelfHeight; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                tmp[i][j] = shelf.getTileShelf(i, j).getTile();
            }
        }
        return tmp;
    }

    /**
     * Checks the number adjacent tiles of the same type and assigns the points earned.
     */
    public void checkEndgameGoal(){
        int dim;
        for (Player player : game.getPlayers()) {
            ArrayList<Point> coordinates = new ArrayList<>();
            boolean[][] matrix = new boolean[Config.shelfHeight][Config.shelfLength];
            Shelf shelf = player.getShelf();
            for (int i = Config.shelfHeight-1; i > -1; i--) {
                for (int j = 0; j < Config.shelfLength; j++) {
                    if (shelf.getTileShelf(i, j).getTile() != TilesEnum.EMPTY) {
                        dim = 0;
                        if (!matrix[i][j])
                            dim = this.customShelfIterator(coordinates, shelf, matrix, shelf.getTileShelf(i, j).getTile(), i, j);
                        if (dim == 3)
                            player.setPoints(player.getPoints() + 2);
                        else if (dim == 4)
                            player.setPoints(player.getPoints() + 3);
                        else if (dim == 5)
                            player.setPoints(player.getPoints() + 5);
                        else if (dim > 5)
                            player.setPoints(player.getPoints() + 8);
                    }
                }
            }
        }
    }

    /**
     * Method that given a shelf position and type iterates over all the joint Tiles present, and returns the size of the found group
     * @param coordinates is an array of coordinates
     * @param shelf is the player's shelf
     * @param matrix is an array of booleans to keep track of the shelf boxes that have already been navigated
     * @param type is the Tile type of the group
     * @param row is the current row in the shelf
     * @param column is the current column in the shelf
     * @return returns the size of the found group
     */
    private int customShelfIterator(ArrayList<Point> coordinates, Shelf shelf, boolean [][]matrix, TilesEnum type, int row , int column){
        Point nextPoint;

        if(row-1 > -1 && !matrix[row-1][column] && shelf.getTileShelf(row-1,column).getTile()==type && !coordinates.contains(new Point(row-1,column)))
            coordinates.add(new Point(row-1,column));
        if(row+1 < Config.shelfHeight && !matrix[row+1][column] && shelf.getTileShelf(row+1,column).getTile()==type && !coordinates.contains(new Point(row+1,column)))
            coordinates.add(new Point(row+1,column));
        if(column-1 > -1 && !matrix[row][column-1] && shelf.getTileShelf(row,column-1).getTile()==type && !coordinates.contains(new Point(row,column-1)))
            coordinates.add(new Point(row,column-1));
        if(column+1 < Config.shelfLength && !matrix[row][column+1] && shelf.getTileShelf(row,column + 1).getTile()==type && !coordinates.contains(new Point(row,column+1)))
            coordinates.add(new Point(row,column+1));

        matrix[row][column]=true;
        if(coordinates.size()!=0) {
            nextPoint = coordinates.get(0);
            coordinates.remove(0);
            return 1 + customShelfIterator(coordinates, shelf, matrix, type, (int) nextPoint.getX(), (int) nextPoint.getY());
        }
        return 1;
    }

    /**
     * end turn logic
     */
    public void endTurn(){
        this.checkCommonGoals();
        this.checkEndGame();
        getGame().getCurrentPlayer().clearTemporaryTiles();
        if (getGame().getBoard().isBoardEmpty())
            this.fillBoard();
        do {
            getGame().setCurrentPlayer(getGame().getNextPlayer());
        }while(!getGame().getCurrentPlayer().getIsActive());
        if (getGame().getCurrentPlayer().getPosition() == 0 && getGame().getBoard().isEndGame()) {
            this.endGame();
        }
        else {
            try {
                getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastNickname,getGame())));
                getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname,getGame())));
            } catch (RemoteException e){
                throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
            }
        }
        getSaveGameStatus().saveGame();
    }

    public void endTurnForced(){
        getGame().getCurrentPlayer().clearTemporaryTiles();
        if (getGame().getBoard().isBoardEmpty())
            this.fillBoard();
        getGame().setCurrentPlayer(getGame().getNextPlayer());
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastNickname,getGame())));
            getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname,getGame())));
        } catch (RemoteException e){
            throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
        }
        getSaveGameStatus().saveGame();
    }

    /**
     * Checks if a player has completed his shelf and if so sets endGame and adds the point to the player
     */
    public void checkEndGame(){
        if(!this.getGame().getBoard().isEndGame()){
            if(this.fullShelf(this.getGame().getCurrentPlayer().getShelf())){
                this.getGame().getBoard().setEndGame(true);
                this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + 1);
                try {
                    getGame().setChangedAndNotifyObservers(new EndgameNotify(new SerializableGame(Config.broadcastNickname, getGame())));
                    if(getGame().getPositionByNick(getGame().getClientPlayerNickname())>0)
                        for(int i = 0; i<getGame().getPositionByNick(getGame().getClientPlayerNickname()); i++)
                            getGame().setChangedAndNotifyObservers(new ForceEnding(new SerializableGame(getGame().getPlayers().get(i).getNickname())));
                } catch (RemoteException e) {
                    throw new RuntimeException("An error occurred while updating the status: " + e);
                }
            }
        }
    }

    /**
     * Auxiliary method that returns true if the player shelf is full, false otherwise
     * @param shelf is the player shelf
     * @return true if the player shelf is true, false otherwise
     */
    private boolean fullShelf(Shelf shelf){
        for(int i=0; i< Config.shelfHeight; i++)
            for(int j=0; j< Config.shelfLength; j++)
                if(shelf.getTileShelf(i,j).getTile()==TilesEnum.EMPTY)
                    return false;
        return true;
    }

    /**
     * Get the player with the most amount of points
     * @return winner of the game
     */
    public Player getWinner() {
        return Collections.max(getGame().getPlayers(), Comparator.comparing(Player::getPoints));
    }

    /**
     * logic for the end game. Calculate personalGoals and EndgameGoal points, sending them to the clients
     */
    public void endGame() {
        this.checkPersonalGoal();
        this.checkEndgameGoal();
    }
    /**
     * reset game
     */
    public void resetGame(){
        this.game = null;
    }

    /**
     * Send the chat to a client
     * @param scope it is the chat you want to view: everyone to view the global chat
     *              or a player's nickname to view the chat with the specific player
     */
    public void sendChat(String scope){
        if(validNickname(scope)) {
            try {
                getGame().setChangedAndNotifyObservers(new SendChat(new SerializableGame(getGame().getCurrentClientID(), getGame().getChat(), scope)));
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending the chat" + e.getMessage());
            }
        }else{
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(getGame().getCurrentClientID(), new Message(Config.error, Config.error, ConsoleColors.RED + "The entered nickname is not in game..." + ConsoleColors.RESET))));
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending the chat" + e.getMessage());
            }
        }
    }

    /**
     * Creates a message with sender, content and recipients and adds it to the chat.
     * @param sender message sender
     * @param content message content
     * @param scope message scope
     */
    public void sayInChat(String sender, String content, String scope) {
        if (scope.equals(Config.error)) {
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(getGame().getCurrentClientID(), new Message(sender, Config.error, ConsoleColors.RED + "Incorrectly formatted message..." + ConsoleColors.RESET))));
                return;
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending the chat: " + e.getMessage());
            }
        }
        if (!validNickname(scope)) {
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(getGame().getCurrentClientID(), new Message(sender, Config.error, ConsoleColors.RED + "The entered nickname doesn't exist..." + ConsoleColors.RESET))));
                return;
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending the chat: " + e.getMessage());
            }
        }
        Message message = new Message(sender, scope, content);
        getGame().getChat().addChatLog(message);
        try {
            getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(Config.broadcastNickname, message)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while sending the chat to the clients: " + e.getMessage());
        }
    }

    public void removeObserver(Client client){
        try {
            server.removeObserver(client);
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while removing the observer: "+e.getMessage());
        }
    }

    /**
     * Checks if the nickname is a nickname of the players in game of equals everyone
     * @param nickname is the nickname to check
     * @return true if nickname is the nickname of a player in game
     */
    private boolean validNickname(String nickname){
        if(nickname.equals(Config.everyone))
            return true;
        for(Player player : this.getGame().getPlayers()){
            if(player.getNickname().equals(nickname))
                return true;
        }
        return false;
    }
    /**
     * Fills the board if the board contains only tiles with no other adjacent tiles.
     */
    public void fillBoard(){
        if (!(isBoardValid())){
            for(int i=0; i< Config.boardHeight; i++){
                for (int j=0; j< Config.boardLength; j++) {
                    if (game.getBoard().getBoard()[i][j].getTile()==EMPTY){
                        game.getBoard().updateBoard(game.getBoard().getBag().pop(), i, j);
                    }
                }
            }
        }
    }

    /**
     * Returns false if the board contains only tiles with no other adjacent tiles, true otherwise.
     * @return false if the board contains only tiles with no other adjacent tiles, true otherwise
     */
    private boolean isBoardValid(){
        for(int i=0; i< Config.boardHeight; i++){
            for (int j=0; j< Config.boardLength; j++) {
                try {
                    if (!(isEmptyOrUnusedBoard(i, j)) &&
                            (!(isEmptyOrUnusedBoard(i - 1, j)) ||
                                    !(isEmptyOrUnusedBoard(i, j - 1)) ||
                                    !(isEmptyOrUnusedBoard(i + 1, j)) ||
                                    !(isEmptyOrUnusedBoard(i, j + 1))))
                        return true;
                } catch (IndexOutOfBoundsException ignore) {}
            }
        }
        return false;
    }

    /**
     * Returns true if the selected tile is either EMPTY or UNUSED.
     * @param x the x coordinate of the tile on the board
     * @param y the y coordinate of the tile on the board
     * @return true if the selected tile is either EMPTY or UNUSED, false otherwise
     */
    private boolean isEmptyOrUnusedBoard (int x, int y){
        return (game.getBoard().getBoard()[y][x].getTile() == EMPTY) ||
                (game.getBoard().getBoard()[y][x].getTile() == UNUSED);
    }

    private void setGameFromSave(Game gameSave) {
        this.game.setGameState(gameSave.getGameState());
        this.game.setNumberOfPlayers(gameSave.getNumberOfPlayers());
        this.game.setFirstPlayer(gameSave.getFirstPlayer());
        this.game.setCurrentPlayer(gameSave.getCurrentPlayer());
        this.game.setPlayers(gameSave.getPlayers());
        this.game.setBoard(gameSave.getBoard());
        this.game.setCommonGoals(gameSave.getCommonGoals());
    }
    public void initializeFromSave(int selection) {
        if (selection == 1) {
            setGameFromSave(retrieveGame());
            this.loadFromFile = true;
            this.freeNamesUsedInLastGame = game.getPlayersNickname();
        } else {
            this.loadFromFile = false;
        }
    }

    private void askLoadGame(String alphanumericID){
        try {
            getGame().setChangedAndNotifyObservers(new AskLoadGame(new SerializableGame(alphanumericID)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while asking to load a previous game: " + e.getMessage());
        }
    }

    private void loadFromFile(Client client,String alphanumericID){
        do {
            try {
                getGame().setChangedAndNotifyObservers(new AskNickname(new SerializableGame(alphanumericID)));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error while asking nickname: " + e.getMessage());
            }
            if (!freeNamesUsedInLastGame.contains(getGame().getClientPlayerNickname()))
                try {
                    getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(alphanumericID, "Nickname not in the last game!!!")));
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                }
        } while (!freeNamesUsedInLastGame.contains(getGame().getClientPlayerNickname()));
        freeNamesUsedInLastGame.remove(getGame().getClientPlayerNickname());
        this.getClients().put(client, alphanumericID);
    }

    private void firstPlayerInitialize(Client client, String alphanumericID){
        try {
            getGame().setChangedAndNotifyObservers(new AskNumberOfPlayers(new SerializableGame(alphanumericID)));
            getGame().initializeGame(this.getGame().getNumberOfPlayers());
            getGame().setChangedAndNotifyObservers(new AskNickname(new SerializableGame(alphanumericID)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while setting the game: " + e.getMessage());
        }
        playerJoin(getGame().getClientPlayerNickname());
        this.getClients().put(client, alphanumericID);
    }
    private void initializePlayer(Client client, String alphanumericID){
        do {
            try {
                getGame().setChangedAndNotifyObservers(new AskNickname(new SerializableGame(alphanumericID)));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error while asking a player nickname: " + e.getMessage());
            }
            if (validNickname(getGame().getClientPlayerNickname())) {
                try {
                    getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(alphanumericID, "Nickname already taken!!!")));
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                }
            }
        } while (validNickname(getGame().getClientPlayerNickname()));
        this.getClients().put(client, alphanumericID);
        playerJoin(getGame().getClientPlayerNickname());
    }
    public synchronized void initializeGame(Client client, String alphanumericID) throws RemoteException {
        counter++;
        if(getClients().getAllKey().size() >= getGame().getNumberOfPlayers() && counter!=1) {
            this.getClients().put(client, alphanumericID);
            getGame().setChangedAndNotifyObservers(new Kill(new SerializableGame(alphanumericID, 0)));
        }
        if (counter == 1) {
            if (saveFileFound())
                askLoadGame(alphanumericID);
            if (loadFromFile)
                loadFromFile(client,alphanumericID);
            else
                firstPlayerInitialize(client, alphanumericID);
        }
        if (getClients().getAllKey().size() < getGame().getNumberOfPlayers()&&counter!=1) {
            if (!loadFromFile)
                initializePlayer(client, alphanumericID);
            else
                loadFromFile(client, alphanumericID);
        }
        startGameFromFile();
    }

    private void startGameFromFile(){
        if (freeNamesUsedInLastGame.isEmpty() && loadFromFile) {
            try {
                getGame().setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(Config.broadcastNickname, randomColors())));
                getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastNickname, getGame())));
                getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname, getGame())));
                getGame().setChangedAndNotifyObservers(new LastPlayerNick(new SerializableGame(Config.broadcastNickname,getGame().getPlayers().get(getGame().getNumberOfPlayers()-1).getNickname())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while setting the name colors: " + e);
            }
        }
    }
    public void setNumberOfPlayers(int numberOfPlayers){
        getGame().setNumberOfPlayers(numberOfPlayers);
    }

    public void boardTransfer(){
        try {
            getGame().setChangedAndNotifyObservers(new SendBoard(new SerializableGame(getGame().getCurrentClientID() ,getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public void shelfTransfer(String clientNickname){
        int pos = getGame().getPositionByNick(clientNickname);
        if(pos == -1){
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(getGame().getCurrentClientID(),new Message(Config.error,Config.error,ConsoleColors.RED_BOLD+"Can't find client position"+ConsoleColors.RESET))));
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending position error: "+e.getMessage());
            }
        }
        try {
            getGame().setChangedAndNotifyObservers(new SendShelf(new SerializableGame(getGame().getCurrentClientID(), getGame().getPlayers().get(pos).getNickname(), getGame().getPlayers().get(pos).getShelf())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public void shelfTransferAll(){
        try {
            getGame().setChangedAndNotifyObservers(new SendAllShelves(new SerializableGame(getGame().getCurrentClientID(), game.getPlayers())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public void personalGoalTransfer(){
        try {
            game.setChangedAndNotifyObservers(new SendPersonalGoal(new SerializableGame(getGame().getCurrentClientID(), getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public void temporaryTilesTransfer(){
        try {
            game.setChangedAndNotifyObservers(new SendTemporaryTiles(new SerializableGame(getGame().getCurrentClientID(), getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred: "+e.getCause());
        }
    }

    public void commonGoalTransfer(){
        try {
            game.setChangedAndNotifyObservers(new SendCommonGoals(new SerializableGame(getGame().getCurrentClientID(), game)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public void currentPlayerTransfer(){
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(getGame().getCurrentClientID(),getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the current player: "+e.getCause());
        }
    }

    public void update(Client client, InputMessage input) throws RemoteException {
        if (input instanceof Connect) {
                input.execute(client, this);
        } else {
            synchronized (lock) {
                getGame().setCurrentClientID(input.getInput().getAlphanumericID());
                if(input.getInput().getClientNickname()!=null)
                    setCurrentNickname(input.getInput().getClientNickname());
                input.execute(this);
            }
        }
    }















    private HashMap<String, String> randomColors() {
        HashMap<String, String> colors = new HashMap<>();
        ArrayList<Integer> alreadyUsed = new ArrayList<>();
        for (int i=0; i<game.getPlayers().size(); i++){
            Random random = new Random();
            int randomNumber = random.nextInt(8);
            while (alreadyUsed.contains(randomNumber))
            {
                randomNumber = random.nextInt(8);
            }
            alreadyUsed.add(randomNumber);
            switch (randomNumber){
                case 0 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.RED);
                case 1 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.GREEN);
                case 2 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.YELLOW);
                case 3 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.BLUE);
                case 4 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.PURPLE);
                case 5 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.CYAN);
                case 6 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.ORANGE);
                case 7 -> colors.put(game.getPlayers().get(i).getNickname(), ConsoleColors.MAGENTA);
            }
        }
        return colors;
    }
}
