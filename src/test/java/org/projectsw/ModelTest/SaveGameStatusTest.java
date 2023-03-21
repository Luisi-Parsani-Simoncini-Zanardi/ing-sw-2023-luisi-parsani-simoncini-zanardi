package org.projectsw.ModelTest;

import com.google.gson.JsonObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.projectsw.Model.*;

import java.io.IOException;
import java.util.ArrayList;

public class SaveGameStatusTest {

    public Game gameInizializer() throws IOException{
        Game game = new Game();
        CommonGoal commonGoal1 = new CommonGoal(0);
        CommonGoal commonGoal2 = new CommonGoal(1);
        CommonGoal[] commonGoals = {commonGoal1,commonGoal2};
        game.setCommonGoals(commonGoals);
        Player player1 = new Player("Davide",0);
        player1.setShelf(new Shelf());
        player1.setPersonalGoal(new PersonalGoal(0));
        Player player2 = new Player("Lorenzo",1);
        player2.setShelf(new Shelf());
        player2.setPersonalGoal(new PersonalGoal(1));
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.setCurrentPlayer(player1);
        game.setFirstPlayer(player2);
        PersonalGoal.cleanUsedCodes();

        return game;
    }

    @Test
    public void jsonPlayerDeserializer() throws IOException {
        Game game = gameInizializer();

        SaveGameStatus saveGameStatus = new SaveGameStatus(game,"_");

        JsonObject json = saveGameStatus.jsonPlayer(game.getPlayers().get(1));
        System.out.println(json.toString());
    }

}
