package org.projectsw.Model;

import org.projectsw.Exceptions.MaximumPlayerException;

import java.io.IOException;
import java.util.ArrayList;

public class Game{

    public Game (){
        Board board = null;
        try {
            board = new Board();
        } catch (IOException e) {
            //retry
        }
        setBoard(board);

        Chat chat = new Chat();
        setChat(chat);

        ArrayList<Player>players = new ArrayList<Player>();
        setPlayers(players);
    }
    private Player firstPlayer;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Board board;
    private Chat chat;
    private CommonGoal[] commonGoals;

    public void setFirstPlayer(Player firstPlayer){
        this.firstPlayer=firstPlayer;
    }

    public Player getFirstPlayer(){
        return firstPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer=currentPlayer;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) throws MaximumPlayerException {
        int playerLength = getPlayers().size();
        if (playerLength<4){
            players.add(player);
        }
        else {
            throw new MaximumPlayerException("Maximum number of players reached");
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    public void setCommonGoals(CommonGoal[] commonGoals) {
        this.commonGoals = commonGoals;
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

}
