package org.projectsw.Controller;

import org.projectsw.Exceptions.MaximumPlayerException;
import org.projectsw.Model.*;

public class Engine {
    //TODO: finire metodi

    /**
     * Initializes the game and the save
     */
    private Game game;
    public void startGame(){
        this.game = new Game();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "");
        //TODO: aggiungere filepath
    }

    /**
     * Creates a player object and adds it to the array of players if there's enough room
     * If the player is the first to join, also sets him as first and current player
     * @param nickname the nickname of the player to be created
     */
    public void playerJoin (String nickname){
        int playerLength = game.getPlayers().size();
        Player player = new Player(nickname, playerLength+1);
        //TODO: aggiungere vincolo nickname univoco
        if (playerLength==0){
            game.setFirstPlayer(player);
            game.setCurrentPlayer(player);
            //TODO: aggiungere funzione che chiede al player quanti giocatori ci sono
        }
        try {
            game.addPlayer(player);
        } catch (MaximumPlayerException e) {
            //send message
        }
    }

    //select from 1 to 3 adjacent tiles and with a free side, and put them in temporarytiles in the Player class
    public void selctTiles(){}

    public void placeTiles(){}

    //implemented with strategy pattern
    public void checkCommonGoals(){}

    public void checkPersonalGoal(){}

    public void endTurn(){}

    public void endGame(){}

    public void resetGame(){}

    public void sayInChat(){}

    public void fillBoard(){}

}
