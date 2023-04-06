package org.projectsw.Model;

import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Model.CommonGoal.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The class contains information about the game state,
 * including the board, players (with info on the currently playing one and the first one), chat, and common goals.
 */
public class Game{

    private GameStates gameState;
    private final int numberOfPlayers;
    private Player firstPlayer;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Board board;
    private Chat chat;
    private ArrayList<CommonGoal> commonGoals;
    private ArrayList<Coordinates> temporaryCoordinates;


    /**
     * Creates a new instance of a SILLY Game, with a new chat, an empty player list,
     * a full-unused board and an empty commonGals list. First and current player are not set yet.
     * This is a silly constructor, so the number of players is set to 0;
     */
    public Game(){
        gameState = GameStates.SILLY;
        numberOfPlayers = 0;
        board = new Board();
        chat = new Chat();
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
    }

    /**
     * Creates a new instance of a Game in LOBBY state, creating it with a new chat, an empty player list,
     * a board set for the right number of players, the correct number of players and an empty commonGals list.
     * Also sets the given first player to current and first player.
     * @param firstPlayer the first player joining the game.
     * @param numberOfPlayers the number of players selected by the first player.
     * @throws IllegalArgumentException if the position of the player is wrong or if the number of players is not
     *                                  between 2 and 4
     */
    public Game(Player firstPlayer, int numberOfPlayers){
        //TODO creare eccezioni ad-hoc per questi errori in modo da poter gestire con due catch separate il metodo in engine
        if(numberOfPlayers<2 || numberOfPlayers>4) throw new IllegalArgumentException("Number of players not between 2 and 4");
        if(firstPlayer.getPosition() != 0) throw new IllegalArgumentException("The first player you want insert has a !=0 position");
        gameState = GameStates.LOBBY;
        this.numberOfPlayers = numberOfPlayers;
        board = new Board(numberOfPlayers);
        chat = new Chat();
        players = new ArrayList<>();
        players.add(firstPlayer);
        this.firstPlayer = firstPlayer;
        this.currentPlayer = firstPlayer;
        commonGoals = new ArrayList<>();
    }

    /**
     * Returns the current state of the game.
     * @return the current state of the game.
     */
    public GameStates getGameState() {return gameState;}

    /**
     * Returns the number of players of the game.
     * @return the number of players of the game.
     */
    public int getNumberOfPlayers() {return numberOfPlayers;}

    /**
     * Returns the first player of the game.
     * @return the first player of the game
     */
    public Player getFirstPlayer(){
        return firstPlayer;
    }

    /**
     * Returns the current player of the game.
     * @return the current player of the game
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Returns the list of players in the game.
     * @return the list of players in the game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the game board.
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the common goals for the game.
     * @return the common goals for the game
     */
    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    /**
     * Returns the chat of the game.
     * @return the chat of the game
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Returns the temporary Coordinates of selected tiles vector.
     * @return the Coordinates vector.
     */
    public ArrayList<Coordinates> getTemporaryCoordinates() {
        return temporaryCoordinates;
    }

    /**
     * Sets the game state as the passed parameter.
     * @param gameState the game state to set.
     */
    public void setGameState(GameStates gameState) {this.gameState = gameState;}

    /**
     * Sets the first player of the game.
     * @param firstPlayer the first player of the game
     */
    public void setFirstPlayer(Player firstPlayer){
        this.firstPlayer=firstPlayer;
    }

    /**
     * Sets the current player of the game.
     * @param currentPlayer the current player of the game
     */
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer=currentPlayer;
    }

    //TODO sistemare questa funzione con nuove eccezioni, la lista di players deve essere lunga quanto numberOfPlayers e i nomi non devono essere duplicati
    /**
     * Sets the list of players in the game from a given list of players.
     * @param players the list of players to copy
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Sets the game board.
     * @param board the game board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the game chat.
     * @param chat the new game chat
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * Sets the common goals of the game.
     * @param commonGoals the common goals of the game
     */
    public void setCommonGoals(ArrayList<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    /**
     * Sets the temporaryCoordinates arrayList as a given arrayList of Coordinates.
     * @param temporaryCoordinates the arrayList of Coordinates to set.
     */
    public void setTemporaryCoordinates(ArrayList<Coordinates> temporaryCoordinates) {
        this.temporaryCoordinates = temporaryCoordinates;
    }

    /**
     * Adds a new player to the game.
     * @param player the player to be added
     * @throws InvalidNameException if the nickname is not unique
     */
    public void addPlayer(Player player) throws InvalidNameException {
        int playerLength = getPlayers().size();
        for (int i = 0; i<playerLength; i++) {
            if(getPlayers().get(i).getNickname().equals(player.getNickname()))
                throw new InvalidNameException("Invalid name, must be unique");
        }
        players.add(player);
    }

    /**
     * Adds a new Coordinates object to the temporaryCoordinates arrayList
     * @param coordinates the Coordinates to add.
     */
    public void addTemporaryCoordinate (Coordinates coordinates){
        temporaryCoordinates.add(coordinates);
    }

    /**
     * Cleans the arrayList of temporaryCoordinates (remove all the elements).
     */
    public void cleanTemporaryTiles() {
        temporaryCoordinates.clear();
    }

    /**
     * Returns an ArrayList of generic classes containing all CommonGoal children classes.
     * @return an ArrayList of generic classes containing all CommonGoal children classes
     */
    private ArrayList<Class<?>> fillCommonGoalsStrategyArray(){
        ArrayList<Class<?>> randomGoalsClasses = new ArrayList<>();

        randomGoalsClasses.add(Square.class);
        randomGoalsClasses.add(RowColumn.class);
        randomGoalsClasses.add(Groups.class);
        randomGoalsClasses.add(Groups.class);
        randomGoalsClasses.add(RowColumn.class);
        randomGoalsClasses.add(RowColumn.class);
        randomGoalsClasses.add(RowColumn.class);
        randomGoalsClasses.add(EdgesEightEquals.class);
        randomGoalsClasses.add(EdgesEightEquals.class);
        randomGoalsClasses.add(Cross.class);
        randomGoalsClasses.add(Diagonal.class);
        randomGoalsClasses.add(Triangle.class);

        return randomGoalsClasses;
    }
    
    /**
     * Returns the ArrayList of commonGoals containing two random commonGoals chosen from the children of CommonGoal.
     * @return the ArrayList of commonGoals containing two random commonGoals chosen from the children of CommonGoal
     * @throws NoSuchMethodException when there's no method defined as such
     * @throws InvocationTargetException when a called method generates an exception
     * @throws InstantiationException when the class cannot be instantiated
     * @throws IllegalAccessException when the caller cannot access the method or parameter
     */

    public ArrayList<CommonGoal> randomCommonGoals() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        CommonGoalStrategy strategyIst;
        CommonGoal commonGoalIst;
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        ArrayList<Class<?>> randomStrategyClasses;
        randomStrategyClasses = fillCommonGoalsStrategyArray();

        Random random = new Random();
        int index = random.nextInt(randomStrategyClasses.size());
        Class<?> randomClass = randomStrategyClasses.get(index);
        strategyIst = (CommonGoalStrategy)randomClass.getDeclaredConstructor(Integer.class).newInstance(index+1);
        commonGoalIst = new CommonGoal(strategyIst);
        commonGoals.add(commonGoalIst);
        randomStrategyClasses.remove(index);

        index = random.nextInt(randomStrategyClasses.size());
        randomClass = randomStrategyClasses.get(index);
        strategyIst = (CommonGoalStrategy)randomClass.getDeclaredConstructor(Integer.class).newInstance(index+1);
        commonGoalIst = new CommonGoal(strategyIst);
        commonGoals.add(commonGoalIst);

        return commonGoals;

    }

}
