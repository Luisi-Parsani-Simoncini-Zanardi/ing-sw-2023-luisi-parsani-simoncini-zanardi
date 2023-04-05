package org.projectsw.Controller;

import org.projectsw.Exceptions.InvalidNameException;

import org.projectsw.Exceptions.MaximumPlayerException;
import org.projectsw.Model.*;

import java.util.ArrayList;

/**
 * The class contains the application logic methods of the game.
 */
public class Engine {
    //TODO: finire metodi controller

    private Game game;

    /**
     * get the game on which the controller is running
     * @return current game
     */
    public Game getGame() { return this.game; }

    /**
     * Initializes the game and the save.
     */
    public void startGame(){
        this.game = new Game();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "");
        //TODO: !!!POST!!! aggiungere filepath
    }

    /**
     * Creates a player object and adds it to the array of players if there's enough room.
     * If the player is the first to join, also sets him as first and current player
     * @param nickname the nickname of the player to be created
     */
    public void playerJoin (String nickname){
        int playerLength = game.getPlayers().size();
        Player player = new Player(nickname, playerLength+1);
        if (playerLength==0){
            game.setFirstPlayer(player);
            game.setCurrentPlayer(player);
            //TODO: !!!POST!!! aggiungere funzione che chiede al player quanti giocatori ci sono
        }
        try {
            game.addPlayer(player);
        } catch (MaximumPlayerException e) {
            //send message
        } catch (InvalidNameException e) {
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

    /**
     * create a message with sender, content and recipients and add it to the chat
     * @param sender message sender
     * @param content message content
     * @param recipients message recipients
     */
    public void sayInChat(Player sender, String content, ArrayList<Player> recipients) throws InvalidNameException {
        Message message = new Message(sender, content);
        message.setRecipients(recipients);
        game.getChat().addChatLog(message);
    }

    public void fillBoard(){}

}
