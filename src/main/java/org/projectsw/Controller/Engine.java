package org.projectsw.Controller;

import org.projectsw.Exceptions.MaximumPlayerException;
import org.projectsw.Model.*;

public class Engine {
    private Game game;
    public void startGame(){
        this.game = new Game();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, ""); //filepath to be added
    }

    public void playerJoin (String nickname){
        int playerLength = game.getPlayers().size();
        Player player = new Player(nickname, playerLength+1);
        if (playerLength==0){
            game.setFirstPlayer(player);
            game.setCurrentPlayer(player);
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
