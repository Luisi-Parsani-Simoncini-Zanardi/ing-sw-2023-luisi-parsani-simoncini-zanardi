package org.projectsw.ModelTest;

import com.google.gson.*;
import org.junit.Test;
import org.projectsw.Model.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveGameStatusTest {

    public Game gameInizializer() throws IOException {
        Game game = new Game();
        /*CommonGoal commonGoal1 = new CommonGoal(0);
        CommonGoal commonGoal2 = new CommonGoal(1);
        CommonGoal[] commonGoals = {commonGoal1, commonGoal2};
        game.setCommonGoals(commonGoals);*/
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

    public void assertEqualsTile (Tile tileTest, Tile tileAssert) {
        assertEquals(tileTest.getTile(), tileAssert.getTile());
        assertEquals(tileTest.getImageNumber(), tileAssert.getImageNumber());
    }
    public void assertEqualsShelf (Shelf shelfTest, Shelf shelfAssert) {
        for(int i=0; i<shelfTest.getShelf().length; i++) {
            for(int j=0; j<shelfTest.getShelf()[i].length; j++){
                assertEqualsTile(shelfTest.getShelf()[i][j], shelfAssert.getShelf()[i][j]);
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
        assertEquals(playerTest.isPersonalGoalRedeemed(), playerAssert.isPersonalGoalRedeemed());
        assertEquals(playerTest.isCommonGoalRedeemed(0), playerAssert.isCommonGoalRedeemed(0));
        assertEquals(playerTest.isCommonGoalRedeemed(1), playerAssert.isCommonGoalRedeemed(1));
    }


    public void assertEqualsBag (Bag bagTest, Bag bagAssert) {
        for(int i=0; i<bagTest.getBag().size(); i++) {
            assertEqualsTile(bagTest.getBag().get(i), bagAssert.getBag().get(i));
        }
    }

    public void assertEqualsBoard (Board boardTest, Board boardAssert) {
        for(int i=0; i<boardTest.getBoard().length; i++) {
            for(int j=0; j<boardTest.getBoard()[i].length; j++){
                assertEqualsTile(boardTest.getBoard()[i][j], boardAssert.getBoard()[i][j]);
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
    public void gameDeserializerTest() throws IOException {

        Game game = gameInizializer();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "_");

        String json = saveGameStatus.gameToJson();
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


