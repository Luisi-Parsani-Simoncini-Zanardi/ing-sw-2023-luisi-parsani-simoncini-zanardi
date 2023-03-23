package org.projectsw.ModelTest;

import com.google.gson.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.projectsw.Model.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveGameStatusTest {

    public Game gameInizializer() throws IOException {
        Game game = new Game();
        CommonGoal commonGoal1 = new CommonGoal(0);
        CommonGoal commonGoal2 = new CommonGoal(1);
        CommonGoal[] commonGoals = {commonGoal1, commonGoal2};
        game.setCommonGoals(commonGoals);
        Player player1 = new Player("Davide", 0);
        player1.setShelf(new Shelf());
        player1.setPersonalGoal(new PersonalGoal(0));
        Player player2 = new Player("Lorenzo", 1);
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

    public void assertEqualsShelf (Shelf shelfTest, Shelf shelfAssert) {
        for(int i=0; i<shelfTest.getShelf().length; i++) {
            for(int j=0; j<shelfTest.getShelf()[i].length; j++){
                assertEquals(shelfTest.getShelf()[i][j], shelfAssert.getShelf()[i][j]);
            }
        }
    }

    public void assertEqualsPersonalGoal (PersonalGoal PersonalGoalTest, PersonalGoal PersonalGoalAssert) {
        for(int i=0; i<PersonalGoalTest.getPersonalGoal().length-1; i++){
            for(int j=0; j<PersonalGoalTest.getPersonalGoal()[i].length-1; j++) {
                assertEquals(PersonalGoalTest.getPersonalGoal()[i][j], PersonalGoalAssert.getPersonalGoal()[i][j]);
            }
        }
        for(int i=0; i<PersonalGoalTest.getUsedCodes().size(); i++) {
            assertEquals(PersonalGoalTest.getUsedCodes().get(i), PersonalGoalAssert.getUsedCodes().get(i));
        }
    }

    public void assertEqualsPlayer (Player playerTest, Player playerAssert) {
        assertEquals(playerTest.getNickname(), playerAssert.getNickname());
        assertEquals(playerTest.getPosition(), playerAssert.getPosition());
        assertEquals(playerTest.getPoints(), playerAssert.getPoints());
        assertEqualsShelf(playerTest.getShelf(), playerAssert.getShelf());
        assertEqualsPersonalGoal(playerTest.getPersonalGoal(), playerAssert.getPersonalGoal());
    }


    public void assertEqualsBag (Bag bagTest, Bag bagAssert) {
        for(int i=0; i<bagTest.getBag().size(); i++) {
            assertEquals(bagTest.getBag().get(i), bagAssert.getBag().get(i));
        }
    }

    public void assertEqualsBoard (Board boardTest, Board boardAssert) {
        for(int i=0; i<boardTest.getBoard().length; i++) {
            for(int j=0; j<boardTest.getBoard()[i].length; j++){
                assertEquals(boardTest.getBoard()[i][j], boardAssert.getBoard()[i][j]);
            }
        }
        assertEquals(boardTest.isEndGame(), boardAssert.isEndGame());
        assertEqualsBag(boardTest.getBag(), boardAssert.getBag());
    }

    public void assertEqualsChat (Chat chatTest, Chat chatAssert) {
        for(int i=0; i<chatTest.getChat().size(); i++) {
            assertEquals(chatTest.getChat().get(i), chatAssert.getChat().get(i));
        }
    }



    @Test
    public void jsonPlayerDeserializer() throws IOException {
        Game game = gameInizializer();
        Player playerTest = game.getPlayers().get(1);

        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "_");

        Gson gson = new Gson();
        JsonObject json = saveGameStatus.jsonPlayer(game.getPlayers().get(1));
        Player playerAssert = gson.fromJson(json, Player.class);

        System.out.println(json);
        System.out.println(playerTest.getShelf().getShelf());
        System.out.println(playerAssert.getShelf().getShelf());
        assertEquals(playerTest.getNickname(), playerAssert.getNickname());
        assertEquals(playerTest.getPosition(), playerAssert.getPosition());
        assertEquals(playerTest.getPoints(), playerAssert.getPoints());
        //assertEquals(playerTest.getShelf() , playerAssert.getShelf());

    }

    @Test
    public void prova() throws IOException {

        Game game = gameInizializer();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "_");

        String json = saveGameStatus.prova(game);
        Gson gson = new Gson();
        Game data = gson.fromJson(json, Game.class);

        assertEqualsPlayer(game.getFirstPlayer(), data.getFirstPlayer());
        assertEqualsPlayer(game.getCurrentPlayer(), data.getCurrentPlayer());
        for(int i=0; i<game.getPlayers().size(); i++) {
            assertEqualsPlayer(game.getPlayers().get(i), data.getPlayers().get(i));
        }
        assertEqualsBoard(game.getBoard(), data.getBoard());
        assertEqualsChat(game.getChat(), data.getChat());


    }

}


