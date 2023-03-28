package org.projectsw.ModelTest;

import com.google.gson.*;
import org.junit.Test;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveGameStatusTest {

    /**
     * initializes a game given its properties
     * @return intitialized game
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public Game gameInizializer() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Game game = new Game();
        game.setCommonGoals(game.randomCommonGoals());
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

    /**
     * check if two Tile objects are identical
     * @param tileTest
     * @param tileAssert
     */
    public void assertEqualsTile (Tile tileTest, Tile tileAssert) {
        assertEquals(tileTest.getTile(), tileAssert.getTile());
        assertEquals(tileTest.getImageNumber(), tileAssert.getImageNumber());
    }

    /**
     * check if two Shelf objects are identical
     * @param shelfTest
     * @param shelfAssert
     */
    public void assertEqualsShelf (Shelf shelfTest, Shelf shelfAssert) {
        for(int i=0; i<shelfTest.getShelf().length; i++) {
            for(int j=0; j<shelfTest.getShelf()[i].length; j++){
                assertEqualsTile(shelfTest.getShelf()[i][j], shelfAssert.getShelf()[i][j]);
            }
        }
    }

    /**
     * check if two personalGoal objects are identical
     * @param personalGoalTest
     * @param personalGoalAssert
     */
    public void assertEqualsPersonalGoal (PersonalGoal personalGoalTest, PersonalGoal personalGoalAssert) {
        for(int i=0; i<personalGoalTest.getPersonalGoal().length-1; i++){
            for(int j=0; j<personalGoalTest.getPersonalGoal()[i].length-1; j++) {
                assertEquals(personalGoalTest.getPersonalGoal()[i][j], personalGoalAssert.getPersonalGoal()[i][j]);
            }
        }
        for(int i=0; i<personalGoalTest.getUsedCodes().size(); i++) {
            assertEquals(personalGoalTest.getUsedCodes().get(i), personalGoalAssert.getUsedCodes().get(i));
        }
    }

    /**
     * check if two Player objects are identical
     * @param playerTest
     * @param playerAssert
     */
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


    /**
     * check if two Bag objects are identical
     * @param bagTest
     * @param bagAssert
     */
    public void assertEqualsBag (Bag bagTest, Bag bagAssert) {
        for(int i=0; i<bagTest.getBag().size(); i++) {
            assertEqualsTile(bagTest.getBag().get(i), bagAssert.getBag().get(i));
        }
    }

    /**
     * check if two Board objects are identical
     * @param boardTest
     * @param boardAssert
     */
    public void assertEqualsBoard (Board boardTest, Board boardAssert) {
        for(int i=0; i<boardTest.getBoard().length; i++) {
            for(int j=0; j<boardTest.getBoard()[i].length; j++){
                assertEqualsTile(boardTest.getBoard()[i][j], boardAssert.getBoard()[i][j]);
            }
        }
        assertEquals(boardTest.isEndGame(), boardAssert.isEndGame());
        assertEqualsBag(boardTest.getBag(), boardAssert.getBag());
    }

    /**
     * check if two Chat objects are identical
     * @param chatTest
     * @param chatAssert
     */
    public void assertEqualsChat (Chat chatTest, Chat chatAssert) {
        for(int i=0; i<chatTest.getChat().size(); i++) {
            assertEquals(chatTest.getChat().get(i), chatAssert.getChat().get(i));
        }
    }

    /**
     * check if two CommonGoal objects are identical
     * @param commonGoalTest
     * @param commonGoalAssert
     */
    public void assertEqualsCommonGoal (CommonGoal commonGoalTest, CommonGoal commonGoalAssert) {
        assertEquals(commonGoalTest.getGoalCode(), commonGoalAssert.getGoalCode());
        assertEquals(commonGoalTest.getRedeemedNumber(), commonGoalAssert.getRedeemedNumber());
    }

    /**
     * check if the gameToJson function correctly serialize and deserialize the Game class
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void gameDeserializerTest() throws  NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Game game = gameInizializer();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "_");
        String json = saveGameStatus.gameToJson();
        Gson gson = new Gson();

        String sottostringa = json.substring(json.indexOf("commonGoals") - 2);
        String newJson = json.replace(sottostringa, "}");
        char[] charArray = sottostringa.toCharArray();
        charArray[0] = '{';
        String newSott = String.valueOf(charArray);

        Gson gsonCommon = new Gson();
        JsonElement element = gsonCommon.fromJson(newSott, JsonElement.class);
        JsonArray commonGoalsArray = element.getAsJsonObject().getAsJsonArray("commonGoals");
        int code0 = commonGoalsArray.get(0).getAsJsonObject().get("commonGoalCode").getAsInt();
        int code1 = commonGoalsArray.get(1).getAsJsonObject().get("commonGoalCode").getAsInt();

        Game data = gson.fromJson(newJson, Game.class);
        data.setCommonGoals(data.getCommonGoalsByCode(code0, code1));


        assertEqualsPlayer(game.getFirstPlayer(), data.getFirstPlayer());
        assertEqualsPlayer(game.getCurrentPlayer(), data.getCurrentPlayer());
        for(int i=0; i<game.getPlayers().size(); i++) {
            assertEqualsPlayer(game.getPlayers().get(i), data.getPlayers().get(i));
        }
        assertEqualsBoard(game.getBoard(), data.getBoard());
        assertEqualsChat(game.getChat(), data.getChat());
        for(int i=0; i<game.getCommonGoals().size(); i++) {
            assertEqualsCommonGoal(game.getCommonGoals().get(i), data.getCommonGoals().get(i));
        }
    }
}


