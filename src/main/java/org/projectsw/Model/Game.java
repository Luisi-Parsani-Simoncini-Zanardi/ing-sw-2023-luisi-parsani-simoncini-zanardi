package org.projectsw.Model;

import org.projectsw.Exceptions.MaximumPlayerException;
import org.projectsw.Model.CommonGoal.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The class contains information about the game state,
 * including the board, players (the current playing one and the first one), chat, and common goals.
 */
public class Game{
    private Player firstPlayer;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Board board;
    private Chat chat;
    private ArrayList<CommonGoal> commonGoals;


    /**
     * Creates a new instance of the Game class with a new chat and an empty player list,
     * first and current player are not set yet.
     */
    public Game(){
        Board board = new Board();
        setBoard(board);
        Chat chat = new Chat();
        setChat(chat);

        ArrayList<Player>players = new ArrayList<>();
        setPlayers(players);

    }

    /**
     * Returns the first player of the game.
     * @return the first player of the game.
     */
    public Player getFirstPlayer(){
        return firstPlayer;
    }

    /**
     * Returns the current player of the game.
     * @return the current player of the game.
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Returns the list of players in the game.
     * @return the list of players in the game.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the game board.
     * @return the game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the common goals for the game.
     * @return the common goals for the game.
     */
    public ArrayList<CommonGoal> getCommonGoals() {
        return commonGoals;
    }


    /**
     * Returns the chat of the game.
     * @return the chat of the game.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Sets the first player of the game.
     * @param firstPlayer the first player of the game.
     */
    public void setFirstPlayer(Player firstPlayer){
        this.firstPlayer=firstPlayer;
    }

    /**
     * Sets the current player of the game.
     * @param currentPlayer the current player of the game.
     */
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer=currentPlayer;
    }

    /**
     * Sets the list of players in the game from a given list of players.
     * @param players the list of players to copy.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Sets the game board.
     * @param board the game board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the game chat.
     * @param chat the new game chat.
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * Sets the common goals of the game.
     * @param commonGoals the common goals of the game.
     */
    public void setCommonGoals(ArrayList<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    /**
     * Adds a new player to the game.
     * @param player the player to be added
     * @throws MaximumPlayerException if the maximum number of players has already been reached (4 players)
     */
    public void addPlayer(Player player) throws MaximumPlayerException {
        int playerLength = getPlayers().size();
        if (playerLength<4){
            players.add(player);
        }
        else {
            throw new MaximumPlayerException("Maximum number of players reached");
        }
    }

    /**
     * @return method that return an ArrayList of generic classes containing all CommonGoal children classes
     */
    private ArrayList<Class<?>> fillCommonGoalsArray(){
        ArrayList<Class<?>> randomGoalsClasses = new ArrayList<>();

        randomGoalsClasses.add(Square.class);
        randomGoalsClasses.add(DifferentColumn.class);
        randomGoalsClasses.add(ColumnEqualsFour.class);
        randomGoalsClasses.add(ColumnEqualsTwo.class);
        randomGoalsClasses.add(ColumnGroup.class);
        randomGoalsClasses.add(DifferentRow.class);
        randomGoalsClasses.add(RowGroup.class);
        randomGoalsClasses.add(Edges.class);
        randomGoalsClasses.add(EightEquals.class);
        randomGoalsClasses.add(Cross.class);
        randomGoalsClasses.add(Diagonal.class);
        randomGoalsClasses.add(Triangle.class);

        return randomGoalsClasses;
    }

    /**
     * create a custom 2 elements CommonGoal array given their code
     * @param code0
     * @param code1
     * @return custom CommonGoal array
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public ArrayList<CommonGoal> getCommonGoalsByCode(int code0, int code1) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ArrayList<Class<?>> allCommon;
        allCommon = fillCommonGoalsArray();
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add((CommonGoal)allCommon.get(code0-1).getDeclaredConstructor().newInstance());
        commonGoals.add((CommonGoal)allCommon.get(code1-1).getDeclaredConstructor().newInstance());

        return commonGoals;
    }
    
    /**
     *
     * @return the ArrayList of commonGoals containing two random commonGoals chosen by the children of CommonGoal
     * @throws NoSuchMethodException thrown if getDeclaredConstructor doesn't find the method constructor of the class
     * @throws InvocationTargetException wraps an exception thrown by the target constructor
     * @throws InstantiationException thrown if newInstance() try to instantiate an object that can't be instantiated
     * @throws IllegalAccessException thrown when newInstance() tries to reflectively create an instance but randomCommonGoals
     * does not have access to the definition of the specified constructor
     */

    public ArrayList<CommonGoal> randomCommonGoals() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        CommonGoal singleIst;
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        ArrayList<Class<?>> randomGoalsClasses;
        randomGoalsClasses = fillCommonGoalsArray();

        Random random = new Random();
        int index1 = random.nextInt(randomGoalsClasses.size());
        Class<?> randomClass = randomGoalsClasses.get(index1);
        singleIst = (CommonGoal)randomClass.getDeclaredConstructor().newInstance();
        commonGoals.add(singleIst);
        randomGoalsClasses.remove(index1);

        index1 = random.nextInt(randomGoalsClasses.size());
        randomClass = randomGoalsClasses.get(index1);
        singleIst = (CommonGoal)randomClass.getDeclaredConstructor().newInstance();
        commonGoals.add(singleIst);

        return commonGoals;

    }

}
