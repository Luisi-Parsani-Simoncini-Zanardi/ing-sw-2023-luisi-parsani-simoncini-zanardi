package org.projectsw.Model;

import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Model.CommonGoal.*;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Util.Observable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The class contains information about the game state,
 * including the board, players (with info on the currently playing one and the first one), chat, and common goals.
 */
public class Game extends Observable<ResponseMessage> {

    private GameState gameState;
    private int numberOfPlayers;
    private Player firstPlayer;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Board board;
    private Chat chat;
    private ArrayList<CommonGoal> commonGoals;
    private String currentClientID;
    private String currentPlayerNickname;

    /**
     * Creates a new instance of a SILLY Game, with a new chat, an empty player list,
     * a full-unused board and an empty commonGals list. First and current player are not set yet.
     * This is a silly constructor, so the number of players is set to 0;
     */
    public Game(){
        gameState = GameState.LOBBY;
        chat = new Chat();
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        try {
            commonGoals = this.randomCommonGoals();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Creates a new instance of a Game in LOBBY state, creating it with a new chat, an empty player list,
     * a board set for the right number of players, the correct number of players and an empty commonGals list.
     * Also sets the given first player to current and first player.
     * @throws IllegalArgumentException if the position of the player is wrong or if the number of players is not
     *                                  between 2 and 4
     */
    public void initializeGame(int number) {
        setNumberOfPlayers(number);
        board = new Board(number);
    }

    public String getCurrentPlayerNickname(){
        return this.currentPlayerNickname;
    }

    public void setCurrentPlayerNickname(String nickname){
        this.currentPlayerNickname = nickname;
    }
    /**
     * Sets the number of players
     * @param numPlayers is the number of player
     */
    public void setNumberOfPlayers(int numPlayers){
            this.numberOfPlayers= numPlayers;
    }

    /**
     * Returns the current state of the game.
     * @return the current state of the game.
     */
    public GameState getGameState() { return gameState; }

    /**
     * Returns the number of players of the game.
     * @return the number of players of the game.
     */
    public int getNumberOfPlayers(){
            return numberOfPlayers;
    }

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
     * Returns the next player of the game
     * @return the next player of the game
     */
    public Player getNextPlayer() {
        if ((getCurrentPlayer().getPosition() + 1) == getNumberOfPlayers())
            return getPlayers().get(0);
        return getPlayers().get(getCurrentPlayer().getPosition() + 1);
    }

    /**
     * Returns the list of players in the game.
     * @return the list of players in the game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<String> getPlayersNickname() {
        ArrayList<String> nicks = new ArrayList<>();
        for (Player player: players)
            nicks.add(player.getNickname());
        return nicks;
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

    public String getCurrentClientID(){
        return this.currentClientID;
    }
    public void setCurrentClientID(String nick){
        this.currentClientID=nick;
    }

    /**
     * Sets the game state as the passed parameter.
     * @param gameState the game state to set.
     */
    public void setGameState(GameState gameState) { this.gameState = gameState; }

    /**
     * Sets the first player of the game.
     * @param firstPlayer the first player of the game
     * @throws IllegalArgumentException if the position of the given player is different from 0
     */
    public void setFirstPlayer(Player firstPlayer){
        if(firstPlayer.getPosition() == 0) this.firstPlayer=firstPlayer;
        else throw new IllegalArgumentException();
    }

    /**
     * Returns a player's position by passing his nickname as a parameter
     * @param nickname is the player's nickname
     * @return the player position
     */
    public int getPositionByNick(String nickname){
        for(Player player : getPlayers()){
            if(nickname.equals(player.getNickname()))
                return player.getPosition();
        }
        return -1;
    }

    /**
     * Sets the current player of the game.
     * @param currentPlayer the current player of the game
     */
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer=currentPlayer;
    }

    /**
     * Sets the list of players in the game from a given list of players.
     * @param players the list of players to copy
     * @throws IllegalArgumentException if the passed list has players with unsorted positions (thrown in addPlayer)
     */
    public void setPlayers(ArrayList<Player> players) {
        for(Player player : players){
            addPlayer(player);
        }
    }

    /**
     * Sets the game board.
     * @param board the game board
     */
    public void setBoard(Board board) {
        this.board = board;
        /*try {
            setChangedAndNotifyObservers(GameEvent.UPDATED_BOARD);
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while setting the board: "+e.getCause());
        }*/
    }

    /**
     * Sets the game chat.
     * @param chat the new game chat
     */
    public void setChat(Chat chat) {
        this.chat = chat;
        /*try {
            setChangedAndNotifyObservers(GameEvent.UPDATED_CHAT);
        } catch (RemoteException e) {
            throw new RuntimeException("Network error while setting the chat: "+e.getCause());
        }*/
    }

    /**
     * Sets the common goals of the game.
     * @param commonGoals the common goals of the game
     */
    public void setCommonGoals(ArrayList<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }


    /**
     * Adds a new player to the game.
     * @param player the player to be added
     * @throws IllegalArgumentException if the passed player has a position that not corresponds to the next free one
     *
     */
    public void addPlayer(Player player) {
        players.add(player);
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

    /**
     * Return an arrayList of commonGoals based on the indexes passed
     * @param indexes strategyCodes of the commonGoals
     * @return commonGoals ArrayList
     * @throws NoSuchMethodException when there's no method defined as such
     * @throws InvocationTargetException when a called method generates an exception
     * @throws InstantiationException when the class cannot be instantiated
     * @throws IllegalAccessException when the caller cannot access the method or parameter
     */
    public ArrayList<CommonGoal> commonGoalByIndex(int[] indexes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        CommonGoalStrategy strategyIst;
        CommonGoal commonGoalIst;
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        ArrayList<Class<?>> randomStrategyClasses;
        randomStrategyClasses = fillCommonGoalsStrategyArray();

        for(int index : indexes) {
            Class<?> randomClass = randomStrategyClasses.get(index-1);
            strategyIst = (CommonGoalStrategy)randomClass.getDeclaredConstructor(Integer.class).newInstance(index);
            commonGoalIst = new CommonGoal(strategyIst);
            commonGoals.add(commonGoalIst);
        }
        return commonGoals;
    }
}
