package org.projectsw.Model;

import java.util.ArrayList;

public class Game{

    public Game (){}
    private Player firstPlayer;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Board board;
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setCommonGoals(CommonGoal[] commonGoals) {
        this.commonGoals = commonGoals;
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

}
