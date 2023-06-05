package org.projectsw.Controller;

import org.projectsw.Distributed.Messages.ResponseMessages.*;
import org.projectsw.Distributed.Messages.ResponseMessages.ChatMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import org.projectsw.Distributed.Client;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.*;
import org.projectsw.Util.Observer;
import org.projectsw.Util.OneToOneHashmap;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.SerializableInput;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;

import static org.projectsw.Model.Enums.TilesEnum.EMPTY;
import static org.projectsw.Model.Enums.TilesEnum.UNUSED;

/**
 * The class contains the application logic methods of the game.
 */
public class Engine{
    private HashMap<Client, Observer<Game, ResponseMessage>> clientObserverHashMap;
    private final OneToOneHashmap<Client, String> clients_ID = new OneToOneHashmap<>();
    private final OneToOneHashmap<String, String> ID_Nicks = new OneToOneHashmap<>();
    private Game game;
    private static int counter = 0;
    private SaveGameStatus saveGameStatus;
    private final Server server;
    private ArrayList<String> freeNamesUsedInLastGame = new ArrayList<>();
    public boolean loadFromFile = false;
    private boolean playerReconnect = false;
    private String firstClient;
    private ArrayList<String> IDToKill = new ArrayList<>();

    /**
     * constructor
     * @param server is the server bound to this controller
     */
    public Engine(Server server){
        this.clientObserverHashMap=new HashMap<>();
        this.server=server;
    }
    /**
     * get the Clients
     * @return the clients
     */
    public OneToOneHashmap<Client, String> getClients_ID() { return this.clients_ID; }
    //public HashMap<Client, Observer<Game, ResponseMessage>> getClientObserverHashMap(){return this.clientObserverHashMap;}
    public OneToOneHashmap<String,String> getID_Nicks(){return this.ID_Nicks;}
    public HashMap<Client, Observer<Game, ResponseMessage>> getClientObserverHashMap(){return this.clientObserverHashMap;}
    /**
     * get the game on which the controller is running
     * @return current game
     */
    public Game getGame() {
        return this.game;
    }

    public Player getPlayerFromNickname(String nickname) {
        for(Player player : game.getPlayers()){
            if(player.getNickname().equals(nickname)){
                return player;
            }
        }
        return null;
    }

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
    public void playerJoin (String nickname, String ID){
            if (game.getGameState().equals(GameState.LOBBY)) {
                int newPlayerPosition = game.getPlayers().size();
                Player newPlayer = new Player(nickname, newPlayerPosition);
                game.addPlayer(newPlayer);
                if(this.game.getPlayers().size()==1){
                    this.game.setFirstPlayer(newPlayer);
                    this.game.setCurrentPlayer(newPlayer);
                    for(String nick : getID_Nicks().getAllValue()) {
                        if(game.getPlayers().size() < game.getNumberOfPlayers() ){
                        playerJoin(nick, "");
                        } else {
                            IDToKill.add(ID_Nicks.getKey(nick));
                        }
                    }
                    ID_Nicks.put(ID, nickname);
                    killingSpree(IDToKill);
                }
                if (game.getPlayers().size() == game.getNumberOfPlayers() && game.getGameState().equals(GameState.LOBBY)) {
                    startGame();
                }
            } else {
                removeObserver(ID);
                ID_Nicks.removeByKey(ID);
            }
    }

    private void killingSpree(ArrayList<String> idToKill){
        for(String id : idToKill){
            removeObserver(id);
            ID_Nicks.removeByKey(id);
            IDToKill.remove(id);
        }
    }

    /**
     * Sets the game status to RUNNING, saves the first instance of the game and lunch the first turn.
     */
    private void startGame() {
        game.setGameState(GameState.RUNNING);
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        fillBoard();
        for(String ID : ID_Nicks.getAllKey()) {
            try {
                game.setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(ID, randomColors())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while setting the name colors: " + e);
            }
            try {
                game.setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(ID, getGame())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while notifying the next player: " + e.getCause());
            }
        }
        saveGameStatus.saveGame();
    }

    /**
     * If the selected point isn't already selected it adds a point to the temporaryPoints list after checking if the
     * size of temporaryPoints is smaller than the maximum remaining space in player's columns.
     * If the selected point is already selected it calls deselect tiles on that point.
     * @param selectedPoint the point that the player wants to select.
     */
    public void selectTiles(String ID, Point selectedPoint) {
        if(game.getBoard().getTemporaryPoints().contains(selectedPoint)) deselectTiles(selectedPoint);
        else {
            try {
                if (selectionPossible()) {
                    game.getBoard().addTemporaryPoints(selectedPoint);
                    game.getBoard().updateSelectablePoints();
                    try {
                        game.setChangedAndNotifyObservers(new SendBoard(new SerializableGame(ID,getGame())));
                    } catch (RemoteException e) {
                        throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
                    }
                    if (game.getBoard().getSelectablePoints().size() == 0 ||
                        game.getCurrentPlayer().getShelf().maxFreeColumnSpace() == game.getBoard().getTemporaryPoints().size()) {
                        try {
                            game.setChangedAndNotifyObservers(new ErrorSelectionNotPossible(new SerializableGame(ID)));
                        } catch (RemoteException e) {
                            throw new RuntimeException("An error occurred while notifying that the selection is not possible: " + e.getCause());
                        }
                    }
                }
            } catch (UnselectableTileException e){
                try {
                    game.setChangedAndNotifyObservers(new ErrorUnselectableTile(new SerializableGame(ID)));
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
    public synchronized void confirmSelectedTiles(String ID) {
        if(game.getBoard().getTemporaryPoints().isEmpty()) {
            try {
                game.setChangedAndNotifyObservers(new ErrorEmptyTemporaryTiles(new SerializableGame(ID)));
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
        temporaryTilesTransfer(ID);
    }

    /**
     * Checks if the player has already selected a tile, if he did it, it calls deselectTiles, if he didn't and the column at the
     * specified index is selectable, it sets the passed index as selected column of the player.
     * @param index The index of column that player wants to select.
     */
    public synchronized void selectColumn(String ID,Integer index) {
        if(game.getCurrentPlayer().getShelf().isSelectionPossible()){
            if(game.getCurrentPlayer().getShelf().getSelectedColumn() == null) {
                try {
                    game.getCurrentPlayer().getShelf().setSelectedColumn(index);
                } catch (UnselectableColumnException e){
                    try {
                        game.setChangedAndNotifyObservers(new ErrorUnselectableColumn(new SerializableGame(ID)));
                    } catch (RemoteException e2) {
                        throw new RuntimeException("An error occurred while sending an Unselectable Column Error"+e2.getMessage());
                    }
                }
            } else deselectColumn();
        }
    }

    /**
     * Sets as null the selected column index and updates the selectable columns arrayList in currentPlayer's shelf.
     */
    private void deselectColumn() {
        game.getCurrentPlayer().getShelf().cleanSelectedColumn();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
    }

    private void sendShelfAndTiles(String ID) {
       // waitForPreviousMethod();
        try {
            game.setChangedAndNotifyObservers(new SendShelf(new SerializableGame(ID, game)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while updating the shelf: " + e.getCause());
        }

        if (!game.getCurrentPlayer().getTemporaryTiles().isEmpty()) {
            try {
                game.setChangedAndNotifyObservers(new SendTemporaryTiles(new SerializableGame(ID, game)));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error while sending temporary tiles: "+e.getMessage());
            }
        }
    }
    /**
     * Add the tile at the selected index of temporaryTiles to the player's shelf in the previously selected column.
     * @param temporaryIndex the selected index of temporaryTiles.
     */
    public synchronized void placeTiles(String ID, Integer temporaryIndex) {
        game.getCurrentPlayer().getShelf().setSelectionPossible(false);
        try {
            Tile tileToInsert = game.getCurrentPlayer().selectTemporaryTile(temporaryIndex);
            int selectedColumn = game.getCurrentPlayer().getShelf().getSelectedColumn();
            for (int i = Config.shelfHeight - 1; i >= 0; i--) {
                if (!game.getCurrentPlayer().getShelf().getShelf()[i][selectedColumn].getTile().equals(EMPTY)) {
                    if (i != Config.shelfHeight - 1) {
                        game.getCurrentPlayer().getShelf().insertTiles(tileToInsert, i + 1, selectedColumn);
                        sendShelfAndTiles(ID);
                    }
                    break;
                }
                if (i == 0) {
                    game.getCurrentPlayer().getShelf().insertTiles(tileToInsert, i, selectedColumn);
                    sendShelfAndTiles(ID);
                    break;
                }
            }
            if (game.getCurrentPlayer().getTemporaryTiles().isEmpty()) {
                deselectColumn();
                game.getCurrentPlayer().getShelf().setSelectionPossible(true);
                try {
                    game.setChangedAndNotifyObservers(new FinishedInserting(new SerializableGame(ID)));
                } catch (RemoteException e) {
                    throw new RuntimeException("An error occurred while notifying that the insertion is not possible: " + e.getCause());
                }
            }
        } catch (IndexOutOfBoundsException e) {
            try {
                game.setChangedAndNotifyObservers(new ErrorInvalidTemporaryTile(new SerializableGame(ID)));
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
    public synchronized void sendResults() {
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
    public synchronized void endTurn(String nickName) {
        this.checkCommonGoals();
        this.checkEndGame(nickName);
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
                while (!game.getCurrentPlayer().getIsActive())
                {
                    dcEndTurn(nickName);
                }
                getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastNickname,getGame())));
                getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname,getGame())));
            } catch (RemoteException e){
                throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
            }
        }
        getSaveGameStatus().saveGame();
    }

    //called when the current player disconnect
    public void sendNexTurn() {
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastNickname,getGame())));
            getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname,getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void dcEndTurn(String nickName) {
        this.checkCommonGoals();
        this.checkEndGame(nickName);
        getGame().getCurrentPlayer().clearTemporaryTiles();
        if (getGame().getBoard().isBoardEmpty())
            this.fillBoard();
        do {
            getGame().setCurrentPlayer(getGame().getNextPlayer());
        }while(!getGame().getCurrentPlayer().getIsActive());
        if (getGame().getCurrentPlayer().getPosition() == 0 && getGame().getBoard().isEndGame()) {
            this.endGame();
        }
        getSaveGameStatus().saveGame();
    }

    public synchronized void endTurnForced() {
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
    public void checkEndGame(String nickName) {
        if(!this.getGame().getBoard().isEndGame()){
            if(this.fullShelf(this.getGame().getCurrentPlayer().getShelf())){
                this.getGame().getBoard().setEndGame(true);
                this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + 1);
                try {
                    getGame().setChangedAndNotifyObservers(new EndgameNotify(new SerializableGame(Config.broadcastNickname, getGame())));
                    if(getGame().getPositionByNick(nickName)>0)
                        for(int i = 0; i<getGame().getPositionByNick(nickName); i++)
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
        saveGameStatus.deleteSaveFile("src/main/java/org/projectsw/Util/save.txt");
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
    public synchronized void sendChat(String scope, String ID) {
        if(invalidNickname(scope)) {
            try {
                getGame().setChangedAndNotifyObservers(new SendChat(new SerializableGame(ID, getGame().getChat(), scope)));
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending the chat" + e.getMessage());
            }
        }else{
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(ID, new Message(Config.error, Config.error, ConsoleColors.RED + "The entered nickname is not in game..." + ConsoleColors.RESET))));
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
    public synchronized void sayInChat(String sender, String content, String scope, String ID) {
        if (scope.equals(Config.error)) {
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(ID, new Message(sender, Config.error, ConsoleColors.RED + "Incorrectly formatted message..." + ConsoleColors.RESET))));
                return;
            } catch (RemoteException e) {
                throw new RuntimeException("A network error occurred while sending the chat: " + e.getMessage());
            }
        }
        if (!invalidNickname(scope)) {
            try {
                getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(ID, new Message(sender, Config.error, ConsoleColors.RED + "The entered nickname doesn't exist..." + ConsoleColors.RESET))));
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

    public synchronized void removeObserver(String id) {
        try {
            game.setChangedAndNotifyObservers(new Kill(new SerializableGame(id,0)));
        } catch (RemoteException e) {
            game.deleteObserver(clientObserverHashMap.get(getClients_ID().getKey(id)));
        }
        game.deleteObserver(clientObserverHashMap.get(getClients_ID().getKey(id)));
        clientObserverHashMap.remove(getClients_ID().getKey(id));
        getClients_ID().removeByValue(id);
        getID_Nicks().removeByKey(id);
    }

    /**
     * Checks if the nickname is a nickname of the players in game of equals everyone
     * @param nickname is the nickname to check
     * @return true if nickname is the nickname of a player in game or the broadcast string
     */
    private boolean invalidNickname(String nickname){
        if(nickname.equals(Config.everyone))
            return true;
        if(game.getPlayersNickname(getActivePlayers()).contains(nickname))
            return true;
        return !game.getPlayersNickname(getInactivePlayers()).contains(nickname) && playerReconnect;
    }

    private ArrayList<Player> getInactivePlayers() {
        ArrayList<Player> dcPlayer = new ArrayList<>();
        for(Player player : game.getPlayers()){
            if(!player.getIsActive()){
                dcPlayer.add(player);
            }
        }
        return dcPlayer;
    }

    private ArrayList<Player> getActivePlayers() {
        ArrayList<Player> activePlayer = new ArrayList<>();
        for(Player player : game.getPlayers()){
            if(player.getIsActive()){
                activePlayer.add(player);
            }
        }
        return activePlayer;
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
    public synchronized void initializeFromSave(String ID) {
        setGameFromSave(retrieveGame());
        this.loadFromFile = true;
        this.freeNamesUsedInLastGame = game.getPlayersNickname();
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void askLoadGame(String alphanumericID){
        try {
            getGame().setChangedAndNotifyObservers(new AskLoadGame(new SerializableGame(alphanumericID)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while asking to load a previous game: " + e.getMessage());
        }
    }

    private void loadFromFile(String ID, String nickname) {
        if (freeNamesUsedInLastGame.contains(nickname)) {
            freeNamesUsedInLastGame.remove(nickname);
            if(!ID_Nicks.getAllKey().contains(ID))
                ID_Nicks.put(ID, nickname);
        } else {
            try {
                getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(ID, "Nickname not in the last game!!!")));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
            }
        }
        if(ID.equals(firstClient))
            for(String nick : ID_Nicks.getAllValue()){
                loadFromFile(getID_Nicks().getKey(nick), nick);
            }
        startGameFromFile();
    }

    private void initializePlayer(SerializableInput input)  {
        if (invalidNickname(input.getClientNickname())) {
            try {
                getGame().setChangedAndNotifyObservers(new WrongNickname(new SerializableGame(input.getAlphanumericID())));
            } catch (RemoteException e) {
                throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
            }
            return;
        }
        try {
            getGame().setChangedAndNotifyObservers(new OkNickname(new SerializableGame(input.getAlphanumericID())));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
        }
        if(!ID_Nicks.getAllValue().contains(input.getClientNickname()) && !input.getAlphanumericID().equals(firstClient)){
            ID_Nicks.put(input.getAlphanumericID(), input.getClientNickname());
        }
        if(!playerReconnect)
            playerJoin(input.getClientNickname(), input.getAlphanumericID());
        if(playerReconnect){
            getPlayerFromNickname(input.getClientNickname()).setIsActive(true);
            try {
                game.setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(Config.broadcastNickname, randomColors())));
                game.setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastNickname, getGame())));
            } catch (RemoteException e){
                throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
            }
        }
        this.playerReconnect = false;
    }
    public void setIsActiveFromClient(Client client, Boolean choice){
        getPlayerFromNickname(ID_Nicks.getValue(clients_ID.getValue(client))).setIsActive(choice);
    }

    public synchronized void takeNick(SerializableInput input) {
        if(input.getAlphanumericID().equals(firstClient)){
            if(loadFromFile){
                loadFromFile(input.getAlphanumericID(), input.getClientNickname());
            }else{
                if(ID_Nicks.getAllValue().contains(input.getClientNickname())){
                    try {
                        getGame().setChangedAndNotifyObservers(new WrongNickname(new SerializableGame(input.getAlphanumericID())));
                    } catch (RemoteException e) {
                        throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                    }
                } else {
                    initializePlayer(input);
                }
            }
        }else{
            if(!loadFromFile) {
                if (game.getFirstPlayer() == null) {
                    if(!ID_Nicks.getAllValue().contains(input.getClientNickname())) {
                        ID_Nicks.put(input.getAlphanumericID(), input.getClientNickname());
                        try {
                            getGame().setChangedAndNotifyObservers(new OkNickname(new SerializableGame(input.getAlphanumericID())));
                        } catch (RemoteException e) {
                            throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                        }
                    } else {
                        try {
                            getGame().setChangedAndNotifyObservers(new WrongNickname(new SerializableGame(input.getAlphanumericID())));
                        } catch (RemoteException e) {
                            throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                        }
                    }
                } else {
                    initializePlayer(input);
                }
            }else{
                if (game.getFirstPlayer() == null) {
                    if(!ID_Nicks.getAllValue().contains(input.getClientNickname())) {
                        ID_Nicks.put(input.getAlphanumericID(), input.getClientNickname());
                        try {
                            getGame().setChangedAndNotifyObservers(new OkNickname(new SerializableGame(input.getAlphanumericID())));
                        } catch (RemoteException e) {
                            throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                        }
                    }
                    else{
                        try {
                            getGame().setChangedAndNotifyObservers(new WrongNickname(new SerializableGame(input.getAlphanumericID())));
                        } catch (RemoteException e) {
                            throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                        }
                    }
                }else{
                    loadFromFile(input.getAlphanumericID(), input.getClientNickname());
                }
            }
        }
    }

    private void checkKill(){
        for(String nick : ID_Nicks.getAllValue())
            if(!game.getPlayersNickname().contains(nick)) {
                removeObserver(ID_Nicks.getKey(nick));
            }

    }
    private void askNumOfPlayers(String alphanumericID){
        try {
            getGame().setChangedAndNotifyObservers(new AskNumberOfPlayers(new SerializableGame(alphanumericID)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while sending setting client flags: "+e.getMessage());
        }
    }
    public synchronized void Connect(String alphanumericID) throws RemoteException, InterruptedException {
        counter++;
        if (counter == 1) {
            firstClient = alphanumericID;
            if (saveFileFound())
                askLoadGame(alphanumericID);
            else
                askNumOfPlayers(alphanumericID);
        }
        if((game.getNumberOfPlayers() != 0 && counter> game.getNumberOfPlayers()) || counter == 5){
            game.setChangedAndNotifyObservers(new Kill(new SerializableGame(alphanumericID,0)));
            counter--;
        } else {
            getGame().setChangedAndNotifyObservers(new AckConnection(new SerializableGame(alphanumericID)));
        }
    }

    private void startGameFromFile(){
        if (freeNamesUsedInLastGame.isEmpty() && loadFromFile) {
            checkKill();
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
    public synchronized void setNumberOfPlayers(int numberOfPlayers){
        loadFromFile=false;
        getGame().initializeGame(numberOfPlayers);
    }
    public synchronized void setNumberOfPlayers(int numberOfPlayers,String ID){
        loadFromFile=false;
        getGame().initializeGame(numberOfPlayers);
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void boardTransfer(String ID) {
        try {
            getGame().setChangedAndNotifyObservers(new SendBoard(new SerializableGame(ID ,getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void shelfTransfer(String clientNickname, String ID) {
        int pos = getGame().getPositionByNick(clientNickname);
        try {
            getGame().setChangedAndNotifyObservers(new SendShelf(new SerializableGame(ID, getGame().getPlayers().get(pos).getNickname(), getGame().getPlayers().get(pos).getShelf())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public synchronized void shelfTransferAll(String ID) {
        try {
            getGame().setChangedAndNotifyObservers(new SendAllShelves(new SerializableGame(ID, game.getPlayers())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public synchronized void personalGoalTransfer(String ID, String nickname) {
        try {
            game.setChangedAndNotifyObservers(new SendPersonalGoal(new SerializableGame(ID, getGame(), nickname)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public synchronized void temporaryTilesTransfer(String ID) {
        try {
            game.setChangedAndNotifyObservers(new SendTemporaryTiles(new SerializableGame(ID, getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred: "+e.getCause());
        }
    }

    public synchronized void commonGoalTransfer(String ID) {
        try {
            game.setChangedAndNotifyObservers(new SendCommonGoals(new SerializableGame(ID, game)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
    }

    public synchronized void currentPlayerTransfer(String ID){
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(ID,getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the current player: "+e.getCause());
        }
    }

    public synchronized void reconnectionCheck(String ID){
        for(Player player : game.getPlayers()){
            if(!player.getIsActive()){
                try {
                    game.setChangedAndNotifyObservers(new AskReconnectName(new SerializableGame(ID)));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                playerReconnect = true;
            }
        }
    }

    public synchronized void notActive(SerializableInput input){
        for(Player player : getGame().getPlayers()){
            if(input.getClientNickname().equals(player.getNickname()))
                player.setIsActive(false);
        }
    }

    private HashMap<String, String> randomColors() {
        HashMap<String, String> colors = new HashMap<>();
        ArrayList<Integer> alreadyUsed = new ArrayList<>();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(8);
            while (alreadyUsed.contains(randomNumber)) {
                randomNumber = random.nextInt(8);
            }
            alreadyUsed.add(randomNumber);
            switch (randomNumber) {
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
