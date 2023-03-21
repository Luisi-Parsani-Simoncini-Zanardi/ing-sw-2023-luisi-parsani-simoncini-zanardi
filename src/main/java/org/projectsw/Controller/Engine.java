package org.projectsw.Controller;

import org.projectsw.Model.*;

import java.io.IOException;
import java.util.ArrayList;

public class Engine {
    private Game game;
    public void startGame(){
        this.game = new Game();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, ""); //filepath to be added
    }

    public void playerJoin (Player player){
        game.addPlayer(player);
    }
}
