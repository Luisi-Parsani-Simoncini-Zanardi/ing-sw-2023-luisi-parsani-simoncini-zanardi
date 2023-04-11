package org.projectsw.Controller;

import org.projectsw.Exceptions.FirstJoinFailedException;
import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Exceptions.JoinFailedException;
import org.projectsw.Exceptions.MinimumRedeemedPointsException;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * The class contains the application logic methods of the game.
 */
public class Engine {

    private Game game;
    private SaveGameStatus saveGameStatus;

    /**
     * get the game on which the controller is running
     * @return current game
     */
    public Game getGame() { return this.game; }

    /**
     * get the saveGameStatus of the game
     * @return saveGameStatus of the game
     */
    public SaveGameStatus getSaveGameStatus() { return this.saveGameStatus; }

//TODO: finire metodi controller

    /**
     * Creates a player object with position 0 and create a new game using the game constructor (the one that also sets the first player).
     * The game is initialized using the first player and the number of players selected, the state of the game at the end of the
     * execution is LOBBY.
     * @param nicknameFirstPlayer the nickname of the first player joining in the game.
     * @param numberOfPlayers the number of players selected by the first player.
     * @throws FirstJoinFailedException if the position of player is not 0 of if the number of players is not correctly chosen.
     */
    public void firstPlayerJoin(String nicknameFirstPlayer, int numberOfPlayers) throws FirstJoinFailedException{
        try {
            Player firstPlayer = new Player(nicknameFirstPlayer,0);
            game = new Game(firstPlayer,numberOfPlayers);
        } catch (IllegalArgumentException e) {
            if (numberOfPlayers<2 || numberOfPlayers>4) throw new FirstJoinFailedException("Invalid number of players");
            else throw new FirstJoinFailedException("Invalid Position");
        }
    }

    /**
     * If the game state isn't LOBBY the join request is negated. If the game state is LOBBY it creates a new
     * player object with the right position and puts it in the arrayList of players.
     * Then checks if the lobby is fulled: if it is, calls the method to start the game,
     * if it isn't the game state remains LOBBY, waiting for new join requests.
     * @param nickname the nickname of the player to be created.
     * @throws JoinFailedException if the name of the player is already used
     *                             of if the function is called when the lobby is closed
     */
    public void playerJoin (String nickname) throws JoinFailedException {
        if(game.getGameState().equals(GameStates.LOBBY)){
            try {
                int newPlayerPosition = game.getPlayers().size();
                Player newPlayer = new Player(nickname,newPlayerPosition);
                game.addPlayer(newPlayer);
                if (game.getPlayers().size() == game.getNumberOfPlayers()) {
                    startGame();
                }
            } catch (InvalidNameException e) {
                throw new JoinFailedException("Name already used");
            }
        }
        else throw new JoinFailedException("The lobby is closed");
    }

    /**
     * Sets the game status to RUNNING, saves the first instance of the game and lunch the first turn.
     */
    public void startGame(){
        game.setGameState(GameStates.RUNNING);
        saveGameStatus = new SaveGameStatus(game, "");//TODO: !!!POST!!! aggiungere filepath

    }

    //turno
        //chiama selectTiles/deselectTiles ad ogni click su board
        //confermo la mia scelta, chiamo comfirmSelectedtiles
        //chiama select column
        //chiama placeTiles
        //chiama endTurn
    //            //chiama checkCommonGoal
    //            //chiama checkEndGame
    //                //controlla di non essere già in endgame
    //                //controlla che il giocatore abbia riempito la shelf
    //                    // se vero, setta endGame e assegna punto
    //            //chiama saveGameStatus
    //            //controlla se la board e' "vuota", e in caso chiama fillBoard
    //            //passa il turno al giocatore successivo, o se era l'ultimo giocatore chiama endGame
    //                    //endGame calcola i punteggi e assegna il vincitore e poi chiama resetGame

    //select from 1 to 3 adjacent tiles and with a free side, and put them in temporarytiles in the Player class
    public void selectTiles(int row, int column){
        //checkSelectableTile(), eccede il numero fornito da checkRemaningColumnSpace
        //butta in array coordinate (controllando se ha raggiunto il massimo)
    }

    public void deselectTiles(int row, int column){
        //rimuove da array coordinate
    }

    private boolean checkSelectableTile(){
        //la tile è selezionabile?
        //la tile è adiacente alle altre nell'array?
        return true;
    }

    public int checkRemaningColumnSpace() {
        return 0;
    }

    public void comfirmSelectedTiles(){
        //rimuove da board addTiles di player

    }

    public void selectColumn(){}

    public void placeTiles(){}

    /**
     * Function that checks if the player has the requirements of the commonGoals in the game.
     * In the positive case it assigns the points and marks that the player has obtained
     * the points of the CommonGoal in question
     */
    public void checkCommonGoals(){
        for(int i=0; i<2; i++){
            if(this.getGame().getCommonGoals().get(i).checkRequirements(this.getGame().getCurrentPlayer().getShelf()) &&
                !this.getGame().getCurrentPlayer().isCommonGoalRedeemed(i)){
                try {
                    int earnedPoints = this.getGame().getCommonGoals().get(i).getRedeemedNumber() * 2;
                    this.getGame().getCommonGoals().get(i).decreaseRedeemedNumber();
                    this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + earnedPoints);
                    this.getGame().getCurrentPlayer().setCommonGoalRedeemed(i,true);
                }catch(MinimumRedeemedPointsException ignore){}
            }
        }
    }

    public void checkPersonalGoal(){}

    public void checkEndgameGoal(){}

    /**
     * end turn logic
     */
    public void endTurn(){
        this.checkCommonGoals();
        this.checkEndGame();
        getSaveGameStatus().saveGame();
        if (getGame().getBoard().isBoardEmpty())
            this.fillBoard();
        if (getGame().getCurrentPlayer().getPosition() == (getGame().getNumberOfPlayers()-1) && getGame().getBoard().isEndGame()) {
            this.endGame();
        }
        else {
            getGame().setCurrentPlayer(getGame().getNextPlayer());
        }
    }

    /**
     * Checks if a player has completed his shelf and if so sets endGame and adds the point to the player
     */
    public void checkEndGame(){
        if(!this.getGame().getBoard().isEndGame()){
            if(this.fullShelf(this.getGame().getCurrentPlayer().getShelf())){
                this.getGame().getBoard().setEndGame(true);
                this.getGame().getCurrentPlayer().setPoints(this.getGame().getCurrentPlayer().getPoints() + 1);
            }
        }
    }

    /**
     * Auxiliary method that returns true if the player shelf is full, false otherwise
     * @param shelf is the player shelf
     * @return true if the player shelf is true, false otherwise
     */
    private boolean fullShelf(Shelf shelf){
        for(int i=0; i<6; i++)
            for(int j=0; j<5; j++)
                if(shelf.getTileShelf(i,j).getTile()==TilesEnum.EMPTY)
                    return false;
        return true;
    }

    /**
     * Get the player with the most amount of points
     * @return winner of the game
     */
    public Player getWinner() {
        Player winner =  Collections.max(getGame().getPlayers(), Comparator.comparing(s -> s.getPoints()));
        return winner;
    }

    /**
     * logic for the end game. Calculate personalGoals points and return the winner
     * @return winner of the game
     */
    public Player endGame(){
        this.checkPersonalGoal();
        this.checkEndgameGoal();
        Player winner = this.getWinner();
        this.resetGame();
        return winner;
    }

    /**
     * reset game
     */
    public void resetGame(){
        this.game = null;
    }

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
