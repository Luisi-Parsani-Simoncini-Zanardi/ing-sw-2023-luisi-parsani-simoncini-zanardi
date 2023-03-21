package org.projectsw.Model;

import org.projectsw.Exceptions.MaximumPlayerException;
import java.io.IOException;
import java.util.ArrayList;

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
    private CommonGoal[] commonGoals;

    /**
     * Creates a new instance of the Game class with a new board, chat, and empty player list,
     * first and current player are not set yet.
     */
    public Game(){
        Board board = null;
        try {
            board= new Board();
        }catch (IOException e) {
            System.out.println("Error opening the json");
        }
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
    public CommonGoal[] getCommonGoals() {
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
    public void setCommonGoals(CommonGoal[] commonGoals) {
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
}
