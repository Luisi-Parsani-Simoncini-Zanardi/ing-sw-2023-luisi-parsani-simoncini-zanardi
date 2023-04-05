package org.projectsw.Controller;

import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Model.*;
import java.util.ArrayList;

/**
 * The class contains the application logic methods of the game.
 */
public class Engine {

    private Game game;

    /**
     * get the game on which the controller is running
     * @return current game
     */
    public Game getGame() { return this.game; }

    //TODO: finire metodi controller

    /**
     * Sets the game status to RUNNING, saves the first instance of the game and lunch the first turn.
     */
    public void startGame(){
        game.setGameState(GameStates.RUNNING);
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "");//TODO: !!!POST!!! aggiungere filepath
        //chiama il metodo di inizio turno
    }

    /**
     * Creates a player object with position 0 and create a new game using the game constructor (the one that also sets the first player)
     * the game is initialized using the first player and the number of players selected, the state of the game at the end of the
     * execution is LOBBY.
     * @param nicknameFirstPlayer the nickname of the first player joining in the game
     * @param numberOfPlayers the number of players selected by the first player
     */
    public void firstPlayerJoin(String nicknameFirstPlayer, int numberOfPlayers){
        Player firstPlayer = new Player(nicknameFirstPlayer,0);
        game = new Game(firstPlayer,numberOfPlayers);
    }

    /**
     * If the game state isn't LOBBY the join request is negated. If the game state is LOBBY it creates a new
     * player object with the right position checking if the lobby is fulled: if it is, calls the method to start the game,
     * if it isn't the game state remains LOBBY, waiting for new join requests.
     * @param nickname the nickname of the player to be created
     */
    public void playerJoin (String nickname){
        if(game.getGameState().equals(GameStates.LOBBY)){
            try {
                int newPlayerPosition = game.getPlayers().size();
                Player newPlayer = new Player(nickname,newPlayerPosition);
                game.addPlayer(newPlayer);
                if (game.getPlayers().size() == game.getNumberOfPlayers()) {
                    startGame();
                }
            } catch (InvalidNameException e) {
                System.out.println("Player join failed, the nickname is already used");
            }
        }
        else System.out.println("Player join failed, the game isn't in the lobby state");
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
