package org.example.Model;

public class Game{
    //manca notify

    public Game (){}
    private Player firstPlayer;
    private Player currentPlayer;
    private Player[] players;
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

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }
    //da rivedere

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
