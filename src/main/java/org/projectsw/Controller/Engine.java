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
import java.util.concurrent.TimeUnit;
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
    private boolean optionChoosed = false;

    /**

     Constructs a new instance of the Engine class.
     This constructor initializes the server to null and creates a new instance of the Game class.
     */
    public Engine(){
        server=null;
        game = new Game();
    }

    /**

     Constructs a new instance of the Engine class with the specified server.
     @param server The server to be associated with the engine.
     */
    public Engine(Server server){
        this.clientObserverHashMap=new HashMap<>();
        this.server=server;
    }

    /**

     Returns the current value of the optionChoosed variable.
     @return The current value of the optionChoosed variable.
     */
    public Boolean getOptionChoosed() { return this.optionChoosed; }

    /**

     Sets the optionChoosed to the specified optionChoosed.
     @param optionChoosed The optionChoosed to be set as the optionChoosed.
     */
    public void setOptionChoosed(Boolean optionChoosed) { this.optionChoosed = optionChoosed; }


    public ArrayList<String> getFreeNamesUsedInLastGame() { return this.freeNamesUsedInLastGame; }
    /**

     Returns the current value of the loadFromFile variable.
     @return The current value of the loadFromFile variable.
     */
    public Boolean getLoadFromFile() { return this.loadFromFile; }
    /**

     Returns the current value of the firstClient variable.
     @return The current value of the firstClient variable.
     */
    public String getFirstClient() {return this.firstClient; }

    /**

     Returns the current value of the playerReconnect variable.
     @return The current value of the playerReconnect variable.
     */
    public Boolean getPlayerReconnection() { return this.playerReconnect; }

    /**

     Returns the current value of the clients_ID variable.
     @return The current value of the clients_ID variable.
     */
    public OneToOneHashmap<Client, String> getClients_ID() { return this.clients_ID; }

    /**

     Returns the current value of the ID_Nicks variable.
     @return The current value of the ID_Nicks variable.
     */
    public OneToOneHashmap<String,String> getID_Nicks(){return this.ID_Nicks;}

    /**

     Returns the current value of the clientObserverHashMap variable.
     @return The current value of the clientObserverHashMap variable.
     */
    public HashMap<Client, Observer<Game, ResponseMessage>> getClientObserverHashMap(){return this.clientObserverHashMap;}

    /**

     Returns the current value of the game variable.
     @return The current value of the game variable.
     */
    public Game getGame() {
        return this.game;
    }

    /**

     Retrieves the player object associated with the specified nickname.
     @param nickname The nickname of the player to be retrieved.
     @return The Player object with the specified nickname, or null if no player is found with the given nickname.
     */
    public Player getPlayerFromNickname(String nickname) {
        for(Player player : game.getPlayers()){
            if(player.getNickname().equals(nickname)){
                return player;
            }
        }
        return null;
    }

    /**

     Checks if a save file exists and returns the result.
     This method creates a new instance of the SaveGameStatus class, passing the game object and the file path as parameters,
     and calls the checkExistingSaveFile() method to determine if a save file exists.
     @return true if a save file exists, false otherwise.
     */
    public boolean saveFileFound(){
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        return saveGameStatus.checkExistingSaveFile();
    }

    /**

     Retrieves the saved game from the existing save file.
     This method creates a new instance of the SaveGameStatus class, passing the game object and the file path as parameters,
     and calls the retrieveGame() method to retrieve the game data from the save file.
     @return The retrieved Game object from the save file.
     */
    public Game retrieveGame(){
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        return saveGameStatus.retrieveGame();
    }

    /**

     Sets the active game to the specified Game object.
     @param activeGame The Game object to be set as the active game.
     */
    public void setGame(Game activeGame){
            this.game=activeGame;
    }

    /**

     Returns the current value of the saveGameStatus variable.
     @return The current value of the saveGameStatus variable.
     */
    public SaveGameStatus getSaveGameStatus() { return this.saveGameStatus; }

    /**

     Sets the saveGameStatus to the specified saveGameStatus object.
     @param saveGameStatus The saveGameStatus object to be set as the active game.
     */
    public void setSaveGameStatus(SaveGameStatus saveGameStatus) { this.saveGameStatus = saveGameStatus; }


    /**

     Handles the process when a player joins the game.
     If the game is in the lobby state, the player is added to the game with the provided nickname and ID.
     If the player is the first one to join, they become the first player and the current player.
     Other players are notified of the new player's arrival if the game has not reached the maximum number of players.
     Any existing players beyond the maximum are marked for removal.
     The player's nickname and ID are stored in the ID_Nicks map.
     If the game reaches the maximum number of players and is still in the lobby state, the game is started.
     If the game is not in the lobby state, the player is removed as an observer and their ID is removed from the ID_Nicks map.
     @param nickname the nickname of the player
     @param ID the ID of the player
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
                        smallJoin(nick);
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
                removeObserver(ID, 0);
                ID_Nicks.removeByKey(ID);
            }
    }

    /**

     Performs a small join operation by adding a new player to the game with the specified nickname.
     This method creates a new Player object with the given nickname and assigns it the position equal to the current number of players in the game.
     The new player is then added to the game.
     @param nickname The nickname of the player to be added.
     */
    public void smallJoin(String nickname) {
        int newPlayerPosition = game.getPlayers().size();
        Player newPlayer = new Player(nickname, newPlayerPosition);
        game.addPlayer(newPlayer);
    }

    /**

     Performs a killing spree operation by removing multiple observers from the engine.
     This method iterates through the list of IDs to be killed and calls the removeObserver() method to remove each observer with the specified ID.
     After removing all the observers, the list of IDs to kill is cleared.
     @param idToKill The list of IDs to be killed (removed as observers).
     */
    private void killingSpree(ArrayList<String> idToKill){
        for(String id : idToKill){
            removeObserver(id, 0);
        }
        IDToKill.clear();
    }

    /**

     Starts the game by changing the game state to "RUNNING", saving the game status,
     filling the board, notifying players of name colors, notifying the next player's turn,
     and notifying the nickname of the last player.
     The game status is saved to the specified file path.
     */
    public void startGame() {
        game.setGameState(GameState.RUNNING);
        saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        fillBoard();
        //for(String ID : ID_Nicks.getAllKey()) {
            try {
                game.setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(Config.broadcastID, randomColors())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while setting the name colors: " + e);
            }
            try {
                game.setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastID, getGame())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while notifying the next player: " + e.getCause());
            }
            try{
                game.setChangedAndNotifyObservers(new LastPlayerNick(new SerializableGame(Config.broadcastID,getGame().getPlayers().get(getGame().getNumberOfPlayers()-1).getNickname())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while notifying the next player: " + e.getCause());
            }
        //}
        saveGameStatus.saveGame();
    }

    /**

     Handles the selection of tiles by a player.
     If the selected point is already in the temporary points list, the tiles are deselected.
     Otherwise, if the selection is possible, the selected point is added to the temporary points list,
     the selectable points on the board are updated, and the updated board is sent to the observers.
     If there are no selectable points left or the player's shelf has reached maximum capacity,
     an error message indicating the selection is not possible is sent to the observers.
     If the selected tile is not selectable, an error message is sent to the observers.
     Finally, a flag indicating the completion of the selection process is sent to the observers.
     @param ID the ID of the player
     @param selectedPoint the selected point on the board
     */
    public void selectTiles(String ID, Point selectedPoint) {
        if(game.getBoard().getTemporaryPoints().contains(selectedPoint)) deselectTiles(selectedPoint);
        else {
            try {
                if (selectionPossible()) {  //TODO il controllo fatto a riga 185 avviene anche dentro al metodo selectionPossible, controllare
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
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Remove the given point from the temporaryPoints list.
     @param point the point to remove.
     */
    public void deselectTiles(Point point){
        game.getBoard().removeTemporaryPoints(point);
    }

    /**

     Checks if the selection is possible for the current player.
     This method determines if there is enough free column space in the player's shelf to accommodate the selected tiles.
     @return true if the selection is possible, false otherwise.
     */
    private boolean selectionPossible() {
        return game.getCurrentPlayer().getShelf().maxFreeColumnSpace() > game.getBoard().getTemporaryPoints().size();
    }

    /**

     Confirms the selection of tiles by a player.
     If the temporary points list is empty, an error message indicating empty temporary points is sent to the observers.
     Otherwise, the tiles corresponding to the selected points are added to the player's temporary tiles list,
     the temporary points list is cleared, and the selectable columns on the player's shelf are updated.
     The temporary tiles are then transferred to the appropriate destination.
     @param ID the ID of the player
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
        try {
            game.setChangedAndNotifyObservers(new GuiUpdateBoards(new SerializableGame(Config.broadcastID,game)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while sending the boards update"+e.getMessage());
        }
        game.getBoard().cleanTemporaryPoints();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
        temporaryTilesTransfer(ID);
    }


    /**

     Selects a column on the player's shelf.
     If the player's shelf allows for column selection:
     If no column is currently selected, the specified column index is set as the selected column.
     If a column is already selected, it is deselected.
     Finally, a flag indicating the completion of the selection process is sent to the observers.
     @param ID the ID of the player
     @param index the index of the column to select
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
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Deselects the currently selected column in the current player's shelf.
     This method performs the following steps:
     Cleans the selected column in the current player's shelf by calling the cleanSelectedColumn() method.
     Updates the selectable columns in the current player's shelf by calling the updateSelectableColumns() method.
     */
    private void deselectColumn() {
        game.getCurrentPlayer().getShelf().cleanSelectedColumn();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
    }

    /**

     Sends the player's shelf and temporary tiles to the observers.
     First, the player's shelf is sent to update the observers with the current state of the player's shelf.
     If the player has any temporary tiles, they are also sent to the observers.
     @param ID the ID of the player
     */
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

     Places a tile from the player's temporary tiles onto the player's shelf.
     The selection possibility on the player's shelf is set to false to prevent further tile placement.
     The tile at the specified temporary index is selected from the player's temporary tiles.
     The selected column on the shelf is obtained.
     Starting from the bottom row of the shelf, the method searches for the first non-empty tile in the selected column.
     If a non-empty tile is found, the tile to insert is placed one row above it (if possible), and the updated shelf and tiles are sent to the observers.
     If the search reaches the top row without finding a non-empty tile, the tile to insert is placed in the top row, and the updated shelf and tiles are sent to the observers.
     If the player no longer has any temporary tiles, the selected column is deselected, the selection possibility on the shelf is set to true,
     and a notification indicating the completion of the tile placement is sent to the observers.
     If the specified temporary index is invalid, an error message indicating an invalid temporary tile is sent to the observers.
     Finally, a flag indicating the completion of the method is sent to the observers.
     @param ID the ID of the player
     @param temporaryIndex the index of the temporary tile to place
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
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Checks the common goals to see if any of them can be redeemed by the current player.
     If a common goal's requirements are met by the player's shelf and the goal has not been previously redeemed by the player,
     the player earns points based on the number of times the goal has been redeemed, and the goal's redeemed count is decreased.
     The player's points are updated accordingly, and the goal is marked as redeemed by the player.
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

     Sends the game results to the observers.
     The updated game state, including the results, is serialized and sent to the observers.
     A flag indicating the completion of the method is also sent to the observers.
     Any network errors that occur during the process are handled and an error message is thrown.
     */
    public synchronized void sendResults() {
        try {
            getGame().setChangedAndNotifyObservers(new ResultsNotify(new SerializableGame(Config.broadcastID, getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred while sending the results: "+e.getMessage());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(Config.broadcastID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Checks the personal goals of each player and updates their points based on the redeemed tiles.
     For each player, it compares their personal goal with the tiles on their shelf and counts the number of redeemed tiles.
     Based on the number of redeemed tiles, the player's points are updated accordingly.
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

     Converts a Shelf object to a 2D array of TilesEnum.
     Each element in the resulting array represents the tile at the corresponding position on the shelf.
     @param shelf The Shelf object to be converted.
     @return A 2D array of TilesEnum representing the tiles on the shelf.
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

     Checks the endgame goal for each player.
     Updates the player's points based on the completion of the endgame goal.
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

     Ends the turn for the current player.
     Checks common goals and updates player points accordingly.
     Checks if the end game condition is met.
     Clears the temporary tiles of the current player.
     Refills the board if it is empty.
     Sets the next active player as the current player.
     If the current player is in the first position and the board is in the end game state, ends the game.
     Notifies the next active player and broadcasts the updated game state.
     Saves the game status.
     @param ID The ID of the player ending the turn.
     @param nickName The nickname of the player ending the turn.
     */
    public synchronized void endTurn(String ID, String nickName) {
        this.checkCommonGoals();
        this.checkEndGame(ID, nickName);
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
                    dcEndTurn(ID, nickName);
                }
                getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastID,getGame())));
                getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastID,getGame())));
            } catch (RemoteException e){
                throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
            }
        }
        getSaveGameStatus().saveGame();
    }

    /**

     Pauses the execution for 10 seconds.
     */
    public static void waitFor10Seconds() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //called when the current player disconnect
    public void sendNexTurn() {
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastID,getGame())));
            getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastID,getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**

     Ends the turn for the current player in the game.
     This method performs the following actions:
     Checks for common goals.
     Checks if the game should end based on the provided player ID and nickname.
     Clears the temporary tiles of the current player.
     Fills the board if it is empty.
     Sets the next active player as the current player.
     Ends the game if the current player's position is 0 and the board has reached the end game state.
     Saves the game status.
     @param ID the ID of the current player
     @param nickName the nickname of the current player
     */
    private void dcEndTurn(String ID, String nickName) {
        this.checkCommonGoals();
        this.checkEndGame(ID, nickName);
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

    /**

     Ends the turn forcefully for the current player in the game.
     This method performs the following actions:
     Clears the temporary tiles of the current player.
     Fills the board if it is empty.
     Sets the next player as the current player.
     Notifies the next player and updates the game state.
     Saves the game status.
     This method is synchronized to ensure thread safety.
     */
    public synchronized void endTurnForced() {
        getGame().getCurrentPlayer().clearTemporaryTiles();
        if (getGame().getBoard().isBoardEmpty())
            this.fillBoard();
        getGame().setCurrentPlayer(getGame().getNextPlayer());
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastID,getGame())));
            getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastID,getGame())));
        } catch (RemoteException e){
            throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
        }
        getSaveGameStatus().saveGame();
    }

    /**

     Checks if the game should end based on the provided player ID and nickname.
     This method performs the following actions:
     Checks if the game has reached the end game state on the board.
     If the current player's shelf is full, sets the end game flag to true, increments the player's points,
     and notifies observers of the end game.
     If the provided nickname has a position greater than 0, sends force ending notifications to players
     with positions lower than the provided nickname's position.
     Notifies observers of the returned flag.
     @param ID the ID of the player
     @param nickName the nickname of the player
     */
    public void checkEndGame(String ID, String nickName) {
        if(!this.getGame().getBoard().isEndGame()){
            if(this.fullShelf(this.getGame().getCurrentPlayer().getShelf())){
                this.getGame().getBoard().setEndGame(true);
                this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + 1);
                try {
                    getGame().setChangedAndNotifyObservers(new EndgameNotify(new SerializableGame(Config.broadcastID, getGame())));
                    if(getGame().getPositionByNick(nickName)>0)
                        for(int i = 0; i<getGame().getPositionByNick(nickName); i++)
                            getGame().setChangedAndNotifyObservers(new ForceEnding(new SerializableGame(getGame().getPlayers().get(i).getNickname())));
                } catch (RemoteException e) {
                    throw new RuntimeException("An error occurred while updating the status: " + e);
                }
            }
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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

     Ends the game.
     This method performs the following actions:
     Checks the personal goal.
     Checks the endgame goal.
     Deletes the save file.
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

     Sends a chat message within the specified scope.
     This method performs the following actions:
     Checks if the specified scope contains an invalid nickname.
     If the nickname is valid, sends the chat message to observers.
     If the nickname is invalid, sends an error message to observers.
     Notifies observers of the returned flag.
     @param scope the scope of the chat message
     @param ID the ID of the player
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
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Sends a chat message from the specified sender with the given content and scope.
     This method performs the following actions:
     Checks if the scope is an error scope. If so, sends an error message to observers.
     Checks if the scope contains an invalid nickname. If so, sends an error message to observers.
     Adds the chat message to the chat log.
     Sends the chat message to observers.
     @param sender the nickname of the sender
     @param content the content of the chat message
     @param scope the scope of the chat message
     @param ID the ID of the player
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
            getGame().setChangedAndNotifyObservers(new ChatMessage(new SerializableGame(Config.broadcastID, message)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while sending the chat to the clients: " + e.getMessage());
        }
    }

    /**

     Removes an observer with the specified ID and number from the game.
     This method performs the following actions:
     Decrements the counter.
     Notifies observers of the observer's removal.
     Deletes the observer from the game.
     Removes the observer from the clientObserverHashMap.
     Removes the ID from the Clients_ID map.
     Removes the ID from the ID_Nicks map.
     @param id the ID of the observer
     @param num the number of the observer
     */
    public synchronized void removeObserver(String id, int num) {
        counter--;
        try {
            game.setChangedAndNotifyObservers(new Kill(new SerializableGame(id,num)));
        } catch (RemoteException e) {
            game.deleteObserver(clientObserverHashMap.get(getClients_ID().getKey(id)));
        }
        game.deleteObserver(clientObserverHashMap.get(getClients_ID().getKey(id)));
        clientObserverHashMap.remove(getClients_ID().getKey(id));
        getClients_ID().removeByValue(id);
        getID_Nicks().removeByKey(id);
    }

    /**

     Performs an everlasting kill by notifying all observers of the kill event.
     This method performs the following actions:
     Notifies observers of the kill event with a broadcast ID and a dummy number.
     */
    public void everlastingKill() {
        try {
            game.setChangedAndNotifyObservers(new Kill(new SerializableGame(Config.broadcastID,0)));
        } catch (RemoteException e) {
        }
    }

    /**

     Checks if the nickname is a nickname of the players in game of equals everyone
     @param nickname is the nickname to check
     @return true if nickname is the nickname of a player in game or the broadcast string
     */
    private boolean invalidNickname(String nickname){
        if(nickname.equals(Config.everyone))
            return true;
        if(game.getPlayersNickname(getActivePlayers()).contains(nickname))
            return true;
        return !game.getPlayersNickname(getInactivePlayers()).contains(nickname) && playerReconnect;
    }

    /**

     Retrieves a list of inactive players in the game.
     @return an ArrayList of inactive players
     */
    private ArrayList<Player> getInactivePlayers() {
        ArrayList<Player> dcPlayer = new ArrayList<>();
        for(Player player : game.getPlayers()){
            if(!player.getIsActive()){
                dcPlayer.add(player);
            }
        }
        return dcPlayer;
    }

    /**

     Retrieves a list of active players in the game.
     @return an ArrayList of inactive players
     */
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

    /**

     Sets the game state and related properties from a saved game.
     @param gameSave the saved game object
     */
    private void setGameFromSave(Game gameSave) {
        this.game.setGameState(gameSave.getGameState());
        this.game.setNumberOfPlayers(gameSave.getNumberOfPlayers());
        this.game.setFirstPlayer(gameSave.getFirstPlayer());
        this.game.setCurrentPlayer(gameSave.getCurrentPlayer());
        this.game.setPlayers(gameSave.getPlayers());
        this.game.setBoard(gameSave.getBoard());
        this.game.setCommonGoals(gameSave.getCommonGoals());
    }

    /**

     Initializes the game from a saved state.
     This method performs the following actions:
     Retrieves the saved game.
     Sets the game state and related properties from the saved game.
     Updates the necessary flags and variables.
     Notifies observers of the option choice and the returned flag.
     @param ID the ID of the player
     */
    public synchronized void initializeFromSave(String ID) {
        if(saveFileFound())
            setGameFromSave(retrieveGame());
        this.loadFromFile = true;
        this.freeNamesUsedInLastGame = game.getPlayersNickname();
        optionChooseSet(ID);
    }

    /**

     Sends a request to load a previous game with the specified alphanumeric ID.
     This method performs the following actions:
     Notifies observers to ask for loading a previous game with the given ID.
     @param alphanumericID the alphanumeric ID of the previous game
     */
    private void askLoadGame(String alphanumericID){
        try {
            getGame().setChangedAndNotifyObservers(new AskLoadGame(new SerializableGame(alphanumericID)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while asking to load a previous game: " + e.getMessage());
        }
    }

    /**

     Loads a game from a file with the specified ID and nickname.
     This method performs the following actions:
     If the ID matches the first client, clears the ID_Nicks map.
     Checks if the nickname is in the list of free names used in the last game.
     If it is, removes the nickname from the list, adds the ID-nickname mapping to the ID_Nicks map, and notifies observers of a successful nickname choice.
     If it is not, sends an error message to the client and notifies observers of a returned flag.
     If the ID does not match the first client:
     Checks if the nickname is in the list of free names used in the last game.
     If it is, removes the nickname from the list, adds the ID-nickname mapping to the ID_Nicks map, and notifies observers of a successful nickname choice.
     If it is not, sends an error message to the client and notifies observers of a returned flag.
     If the list of free names used in the last game is empty, starts the game from the loaded file.
     @param ID the ID of the client
     @param nickname the nickname chosen by the client
     */
    private void loadFromFile(String ID, String nickname) {
        if(ID.equals(firstClient)){
            ID_Nicks.clear();
            if(freeNamesUsedInLastGame.contains(nickname)){
                freeNamesUsedInLastGame.remove(nickname);
                ID_Nicks.put(ID, nickname);
                try {
                    getGame().setChangedAndNotifyObservers(new OkNickname(new SerializableGame(ID)));
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                }
            } else {
                try {
                    getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(ID, "Nickname not in the last game!!!")));
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                }
                try {
                    game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            if (freeNamesUsedInLastGame.contains(nickname)) {
                freeNamesUsedInLastGame.remove(nickname);
                ID_Nicks.put(ID, nickname);
                try {
                    getGame().setChangedAndNotifyObservers(new OkNickname(new SerializableGame(ID)));
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                }
            } else {
                try {
                    getGame().setChangedAndNotifyObservers(new ErrorMessage(new SerializableGame(ID, "Nickname not in the last game or already taken!!!")));
                } catch (RemoteException e) {
                    throw new RuntimeException("Network error while sending nickname error message: " + e.getMessage());
                }
                try {
                    game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(freeNamesUsedInLastGame.isEmpty())
            startGameFromFile();
    }

    /**

     Initializes a player based on the provided input.
     This method performs the following actions:
     Checks if the client nickname is invalid. If it is, sends a wrong nickname message to observers and returns.
     Sends an OK nickname message to observers.
     If the client nickname is not already in use and the alphanumeric ID is not equal to the first client's ID, adds the ID-nickname mapping to the ID_Nicks map.
     If it's not a player reconnect, calls the playerJoin() method.
     If it's a player reconnect, sets the player's active status to true and notifies observers of the name colors and the next player's turn.
     Resets the playerReconnect flag to false.
     @param input the serializable input containing the client's nickname and alphanumeric ID
     */
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
                game.setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(Config.broadcastID, randomColors())));
                game.setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastID, getGame())));
            } catch (RemoteException e){
                throw new RuntimeException("An error occurred while notifying the next player: "+e.getCause());
            }
        }
        this.playerReconnect = false;
    }

    /**

     Sets the active status of a player based on the client's choice.
     @param client the client for which to set the active status
     @param choice the choice indicating whether the player is active or not
     */
    public void setIsActiveFromClient(Client client, Boolean choice){
        getPlayerFromNickname(ID_Nicks.getValue(clients_ID.getValue(client))).setIsActive(choice);
    }

    /**

     Retrieves the nickname associated with a client.
     @param client the client for which to retrieve the nickname
     @return the nickname associated with the client
     */
    public String getNickFromClient(Client client) {
        return ID_Nicks.getValue(clients_ID.getValue(client));
    }


    /**

     Handles the process of assigning a nickname to a client.
     If the client's alphanumeric ID matches the first client's ID:
     If loading from a saved game, attempts to load the game with the provided nickname.
     If not loading from a saved game, checks if the nickname is already taken. If not, initializes the player with the nickname.
     If the client's alphanumeric ID does not match the first client's ID:
     If not loading from a saved game and the first player is not yet assigned, checks if the nickname is already taken.
     If not, assigns the nickname to the client.
     If loading from a saved game, attempts to load the game with the provided nickname.
     @param input the input containing the client's alphanumeric ID and nickname
     */
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
                loadFromFile(input.getAlphanumericID(), input.getClientNickname());
            }
        }
    }

    private void checkKill(){
        for(String nick : ID_Nicks.getAllValue())
            if(!game.getPlayersNickname().contains(nick)) {
                removeObserver(ID_Nicks.getKey(nick), 0);
            }

    }

    /**

     Sends a request to the clients to input the number of players in the game.
     @param alphanumericID the alphanumeric ID of the client initiating the request
     */
    private void askNumOfPlayers(String alphanumericID){
        try {
            getGame().setChangedAndNotifyObservers(new AskNumberOfPlayers(new SerializableGame(alphanumericID)));
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while sending setting client flags: "+e.getMessage());
        }
    }

    /**

     Connects a client to the game.
     @param alphanumericID the alphanumeric ID of the client
     @throws RemoteException if a network error occurs during the connection process
     @throws InterruptedException if the thread is interrupted while waiting for the connection
     */
    public synchronized void Connect(String alphanumericID) throws RemoteException, InterruptedException {
        if(getInactivePlayers().size() > 0){
            playerReconnect = true;
        }
        counter++;
        if (counter == 1) {
            firstClient = alphanumericID;
            if (saveFileFound())
                askLoadGame(alphanumericID);
            else
                askNumOfPlayers(alphanumericID);
        }
        if((game.getNumberOfPlayers() != 0 && counter> game.getNumberOfPlayers()) || counter == 5){
            removeObserver(alphanumericID, 0);
        } else {
            if(saveFileFound()){
                game.setChangedAndNotifyObservers((new gameFound(new SerializableGame(alphanumericID))));
            }
            getGame().setChangedAndNotifyObservers(new AckConnection(new SerializableGame(alphanumericID, optionChoosed)));
        }
    }

    /**

     Starts the game from a saved state.
     If all players from the previous game have reconnected and chosen their nicknames, the game is started.
     The game state, including player colors, current player, and next player turn, is sent to the clients.
     */
    private void startGameFromFile(){
        if (freeNamesUsedInLastGame.isEmpty() && loadFromFile) {
            ArrayList<String> ids = new ArrayList<>(clients_ID.getAllValue());
            for (String id : ids){
                if(!ID_Nicks.getAllKey().contains(id)){
                    removeObserver(id, 0);
                }
            }
            try {
                getGame().setChangedAndNotifyObservers(new SendNameColors(new SerializableGame(Config.broadcastID, randomColors())));
                getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(Config.broadcastID, getGame())));
                getGame().setChangedAndNotifyObservers(new NextPlayerTurn(new SerializableGame(Config.broadcastID, getGame())));
                getGame().setChangedAndNotifyObservers(new LastPlayerNick(new SerializableGame(Config.broadcastID,getGame().getPlayers().get(getGame().getNumberOfPlayers()-1).getNickname())));
            } catch (RemoteException e) {
                throw new RuntimeException("An error occurred while setting the name colors: " + e.getMessage());
            }
        }
    }

    /**

     Sets the number of players for the game and initializes the game state.
     This method is called when the number of players is chosen by the first client.
     The game is initialized with the specified number of players.
     The option chosen flag is set to true, indicating that the number of players has been selected.
     The game state is sent to the clients.
     @param numberOfPlayers The number of players for the game.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void setNumberOfPlayers(int numberOfPlayers,String ID){
        loadFromFile=false;
        getGame().initializeGame(numberOfPlayers);
        optionChooseSet(ID);
    }

    private void optionChooseSet(String ID) {
        optionChoosed = true;
        try {
            game.setChangedAndNotifyObservers(new optionChoosed(new SerializableGame(Config.broadcastID, loadFromFile)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Transfers the game board to the client identified by the specified ID.
     This method sends the game board to the client and notifies the client that the board has been transferred.
     If there is a network error during the process, the client observer is removed.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void boardTransfer(String ID) {
        try {
            getGame().setChangedAndNotifyObservers(new SendBoard(new SerializableGame(ID ,getGame())));
        } catch (RemoteException e) {
            game.deleteObserver(clientObserverHashMap.get(getClients_ID().getKey(ID)));
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**

     Transfers the game shelf to the client identified by the specified ID.
     This method sends the game shelf to the client and notifies the client that the board has been transferred.
     If there is a network error during the process, the client observer is removed.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void shelfTransfer(String clientNickname, String ID) {
        int pos = getGame().getPositionByNick(clientNickname);
        try {
            getGame().setChangedAndNotifyObservers(new SendShelf(new SerializableGame(ID, getGame().getPlayers().get(pos).getNickname(), getGame().getPlayers().get(pos).getShelf())));
        } catch (RemoteException e) {
            game.deleteObserver(clientObserverHashMap.get(getClients_ID().getKey(ID)));
        }
       try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Transfers all the game shelves to the client identified by the specified ID.
     This method sends the all game shelves to the client and notifies the client that the board has been transferred.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void shelfTransferAll(String ID) {
        try {
            getGame().setChangedAndNotifyObservers(new SendAllShelves(new SerializableGame(ID, game.getPlayers())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Transfers the game personal Goal to the client identified by the specified ID.
     This method sends the game personal Goal to the client and notifies the client that the board has been transferred.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void personalGoalTransfer(String ID, String nickname) {
        try {
            game.setChangedAndNotifyObservers(new SendPersonalGoal(new SerializableGame(ID, getGame(), nickname)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Transfers the game temporary Tiles to the client identified by the specified ID.
     This method sends the game temporary Tiles to the client and notifies the client that the board has been transferred.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void temporaryTilesTransfer(String ID) {
        try {
            game.setChangedAndNotifyObservers(new SendTemporaryTiles(new SerializableGame(ID, getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("A network error occurred: "+e.getCause());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Transfers the game common Goal to the client identified by the specified ID.
     This method sends the game common Goal to the client and notifies the client that the board has been transferred.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void commonGoalTransfer(String ID) {
        try {
            game.setChangedAndNotifyObservers(new SendCommonGoals(new SerializableGame(ID, game)));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the board: "+e.getMessage());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Transfers the game current Player to the client identified by the specified ID.
     This method sends the game current Player to the client and notifies the client that the board has been transferred.
     @param ID The alphanumeric ID of the client.
     */
    public synchronized void currentPlayerTransfer(String ID){
        try {
            getGame().setChangedAndNotifyObservers(new SendCurrentPlayer(new SerializableGame(ID,getGame())));
        } catch (RemoteException e) {
            throw new RuntimeException("An error occurred while transferring the current player: "+e.getCause());
        }
        try {
            game.setChangedAndNotifyObservers(new ReturnedFlag(new SerializableGame(ID)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**

     Checks if any inactive player needs to reconnect.
     For each player in the game, if the player is inactive, it sends a request to the client identified by the specified ID
     to provide the nickname for reconnection. It also sets the playerReconnect flag to true.
     @param ID The alphanumeric ID of the client.
     */
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

    /**

     Sets the isActive flag of the player with the specified nickname to false.
     @param input The input containing the client nickname.
     */
    public synchronized void notActive(SerializableInput input){
        for(Player player : getGame().getPlayers()){
            if(input.getClientNickname().equals(player.getNickname()))
                player.setIsActive(false);
        }
    }

    /**

     Generates random colors for each player in the game.
     @return A HashMap containing player nicknames as keys and their corresponding random colors as values.
     */
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
