package org.projectsw.Controller;

import org.projectsw.Util.Config;
import org.projectsw.Distributed.Client;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.*;
import org.projectsw.View.UIEvent;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import static org.projectsw.Model.TilesEnum.EMPTY;
import static org.projectsw.Model.TilesEnum.UNUSED;


/**
 * The class contains the application logic methods of the game.
 */
public class Engine{
    private final ArrayList<Client> clients = new ArrayList<>();
    private Game game;
    private SaveGameStatus saveGameStatus;
    private boolean chooseNumberOfPlayers=false;
    private final Object lock = new Object();


    /**
     * get the Clients
     * @return the clients
     */
    public ArrayList<Client> getClients() { return this.clients; }

    //TODO: getGame eliminabile usato solo nei test
    /**
     * get the game on which the controller is running
     * @return current game
     */
    public Game getGame() { return this.game;}

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
     * @throws LobbyClosedException if the name of the player is already used
     *                             of if the function is called when the lobby is closed
     */
    public void playerJoin (String nickname) {
            if (game.getGameState().equals(GameStates.LOBBY)) {
                int newPlayerPosition = game.getPlayers().size();
                Player newPlayer = new Player(nickname, newPlayerPosition);
                game.addPlayer(newPlayer);
                if(this.game.getPlayers().size()==1){
                    this.game.setFirstPlayer(newPlayer);
                    this.game.setCurrentPlayerLobby(newPlayer);
                }
                if (game.getPlayers().size() == game.getNumberOfPlayers()) {
                    startGame();
                }
            } else {
                game.setError(ErrorName.LOBBY_CLOSED);
            }
    }



    /**
     * Sets the game status to RUNNING, saves the first instance of the game and lunch the first turn.
     */
    private void startGame(){
        game.setGameState(GameStates.RUNNING);
        saveGameStatus = new SaveGameStatus(game, "");
        fillBoard();
        game.nextTurnNotify();
    }

    /**
     * If the selected point isn't already selected it adds a point to the temporaryPoints list after checking if the
     * size of temporaryPoints is smaller than the maximum remaining space in player's columns.
     * If the selected point is already selected it calls deselect tiles on that point.
     * @param selectedPoint the point that the player wants to select.
     * @throws UnselectableTileException if the selected point is an empty/unused tile, of if the selected point
     *                                   can't be selected by the rules.
     */
    public void selectTiles(Point selectedPoint) throws NoMoreColumnSpaceException {
        if(game.getBoard().getTemporaryPoints().contains(selectedPoint)) deselectTiles(selectedPoint);
        else {
            try {
                if (selectionPossible()) {
                    game.getBoard().addTemporaryPoints(selectedPoint);
                    game.finishedUpdateBoard();
                }
            } catch (UnselectableTileException e){
                game.setError(ErrorName.UNSELECTABLE_TILE);
            } /*catch (NoMoreColumnSpaceException e){
                game.setError(ErrorName.NO_MORE_COLUMN_SPACE);
            }*/
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
    public void confirmSelectedTiles() throws MaxTemporaryTilesExceededException, UpdatingOnWrongPlayerException {
        if(game.getBoard().getTemporaryPoints().isEmpty()) game.setError(ErrorName.EMPTY_TEMPORARY_POINTS);
        ArrayList<Point> selectedPoints = game.getBoard().getTemporaryPoints();
        for(Point point : selectedPoints){
            Tile tile = game.getBoard().getTileFromBoard(point);
            game.getCurrentPlayer().addTemporaryTile(tile);
        }
        game.getBoard().cleanTemporaryPoints();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
        try {
            game.setChangedAndNotifyObservers(Game.Event.UPDATED_TEMPORARY_TILES);
        } catch (RemoteException e) {
            throw new RuntimeException("Network error occured: "+e.getCause());
        }
    }

    /**
     * Checks if the player has already selected a tile, if he did it, it calls deselectTiles, if he didn't and the column at the
     * specified index is selectable, it sets the passed index as selected column of the player.
     * @param index The index of column that player wants to select.
     * @throws UnselectableColumnException if the column is not selectable.
     */
    public void selectColumn(Integer index) throws UnselectableColumnException, UpdatingOnWrongPlayerException {
        if(game.getCurrentPlayer().getShelf().isSelectionPossible()){
            if(game.getCurrentPlayer().getShelf().getSelectedColumn() == null) {
                try {
                    game.getCurrentPlayer().getShelf().setSelectedColumn(index);
                } catch (UnselectableColumnException e){
                    game.setError(ErrorName.UNSELECTABLE_COLUMN);
                }
            } else deselectColumn();
        } //else throw new UnselectableColumnException("Tiles are already placed, column no longer changeable");
    }

    /**
     * Sets as null the selected column index and updates the selectable columns arrayList in currentPlayer's shelf.
     */
    private void deselectColumn() throws UpdatingOnWrongPlayerException {
        game.getCurrentPlayer().getShelf().cleanSelectedColumn();
        game.getCurrentPlayer().getShelf().updateSelectableColumns(game.getCurrentPlayer());
    }

    /**
     * Add the tile at the selected index of temporaryTiles to the player's shelf in the previously selected column.
     * @param temporaryIndex the selected index of temporaryTiles.
     */
    public void placeTiles(Integer temporaryIndex) throws UpdatingOnWrongPlayerException {
        game.getCurrentPlayer().getShelf().setSelectionPossible(false);
        try {
            Tile tileToInsert = game.getCurrentPlayer().selectTemporaryTile(temporaryIndex);
            int selectedColumn = game.getCurrentPlayer().getShelf().getSelectedColumn();
            for (int i = Config.shelfHeight - 1; i >= 0; i--) {
                if (!game.getCurrentPlayer().getShelf().getShelf()[i][selectedColumn].getTile().equals(EMPTY)) {
                    if (i != Config.shelfHeight - 1) {
                        game.getCurrentPlayer().getShelf().insertTiles(tileToInsert, i + 1, selectedColumn);
                        game.finishedUpdateShelf();
                    }
                    break;
                }
                if (i == 0) {
                    game.getCurrentPlayer().getShelf().insertTiles(tileToInsert, i, selectedColumn);
                    game.finishedUpdateShelf();
                    break;
                }
            }
            if (game.getCurrentPlayer().getTemporaryTiles().isEmpty()) {
                deselectColumn();
                game.getCurrentPlayer().getShelf().setSelectionPossible(true);
                endTurn();
            }
        } catch (IndexOutOfBoundsException e) {
            game.setError(ErrorName.INVALID_TEMPORARY_TILE);
        }
    }

    public void placeMultipleTiles(String order) {
        if(!(order.length() == game.getCurrentPlayer().getTemporaryTiles().size())) {
            for (int i = 0; i < order.length(); i++) {
                Integer tile = Character.getNumericValue(order.charAt(i));
                try {
                    placeTiles(tile);
                } catch (UpdatingOnWrongPlayerException e) {
                }
            }
        } else {
            game.setError(ErrorName.INVALID_TEMPORARY_TILE);
        }
    }

    /**
     * Calls place tiles for all the indexes contained in order arrayList.
     * @param order the arraylist that contains all the indexes of the TemporaryTiles sorted by selection order.
     */
    public void placeAllTiles(ArrayList<Integer> order) throws UpdatingOnWrongPlayerException {
        if(order.size() != game.getCurrentPlayer().getTemporaryTiles().size()) throw new IllegalArgumentException();
        for(Integer index : order){
            placeTiles(index);
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
        //getSaveGameStatus().saveGame();
        game.getCurrentPlayer().clearTemporaryTiles();
        if (getGame().getBoard().isBoardEmpty())
            this.fillBoard();
        if (getGame().getCurrentPlayer().getPosition() == (getGame().getNumberOfPlayers()-1) && getGame().getBoard().isEndGame()) {
            this.endGame();
        }
        else {
            getGame().setCurrentPlayer(getGame().getNextPlayer());
            game.finishedUpdateBoard();
            game.nextTurnNotify();
        }
    }

    /**
     * Checks if a player has completed his shelf and if so sets endGame and adds the point to the player
     */
    public void checkEndGame(){
        if(!this.getGame().getBoard().isEndGame()){
            if(this.fullShelf(this.getGame().getCurrentPlayer().getShelf())){
                this.getGame().getBoard().setEndGame(true);
                this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + 1);
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
     * logic for the end game. Calculate personalGoals points and return the winner
     * @return winner of the game
     */
    public Player endGame(){
        this.checkPersonalGoal();
        this.checkEndgameGoal();
        Player winner = this.getWinner();
        this.resetGame();
        return winner;
    }

    /**
     * reset game
     */
    public void resetGame(){
        this.game = null;
    }

    /**
     * Creates a message with sender, content and recipients and adds it to the chat.
     * @param sender message sender
     * @param content message content
     * @param recipients message recipients
     */
    public void sayInChat(Player sender, String content, ArrayList<Player> recipients) {
        Message message = new Message(sender, content);
        try {
            message.setRecipients(recipients);
        } catch (InvalidRecipientException e) {
            game.setError(ErrorName.INVALID_RECIPIENT);
        }
        game.getChat().addChatLog(message);
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
            game.finishedUpdateBoard();
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

    public void update(InputController input, UIEvent UiEvent) throws UnselectableTileException, NoMoreColumnSpaceException, MaxTemporaryTilesExceededException, UpdatingOnWrongPlayerException, UnselectableColumnException {
        game.setClientID(input.getClientID());
            switch (UiEvent) {
                case TILE_SELECTION -> selectTiles(input.getCoordinate());
                case CONFIRM_SELECTION -> confirmSelectedTiles();
                case COLUMN_SELECTION -> selectColumn(input.getIndex());
                case TILE_INSERTION -> placeTiles(input.getIndex());
            }

    }
}
