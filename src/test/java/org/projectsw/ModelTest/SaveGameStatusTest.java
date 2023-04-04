package org.projectsw.ModelTest;
//TODO: sistemare questa classe, non è una classe di test
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.projectsw.Model.*;
import java.lang.reflect.AccessibleObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveGameStatusTest {

    /**
     * Initializes a game given its properties.
     * @return the initialized game
     * @throws NoSuchMethodException when there's no method defined as such
     * @throws InvocationTargetException when a called method generates an exception
     * @throws InstantiationException when the class cannot be instantiated
     * @throws IllegalAccessException when the caller cannot access the method or parameter
     */

    public Game gameInitializer() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
     * Checks if two Tile objects are identical.
     * @param tileTest a test Tile object
     * @param tileAssert another test Tile object
     */
    public void assertEqualsTile (Tile tileTest, Tile tileAssert) {
        assertEquals(tileTest.getTile(), tileAssert.getTile());
        assertEquals(tileTest.getImageNumber(), tileAssert.getImageNumber());
    }

    /**
     * Checks if two Shelf objects are identical.
     * @param shelfTest a test Shelf object
     * @param shelfAssert another test Shelf object
     */
    public void assertEqualsShelf (Shelf shelfTest, Shelf shelfAssert) {
        for(int i=0; i<shelfTest.getShelf().length; i++) {
            for(int j=0; j<shelfTest.getShelf()[i].length; j++){
                assertEqualsTile(shelfTest.getShelf()[i][j], shelfAssert.getShelf()[i][j]);
            }
        }
    }

    /**
     * Checks if two personalGoal objects are identical.
     * @param personalGoalTest a test PersonalGoal object
     * @param personalGoalAssert another test PersonalGoal object
     */
    public void assertEqualsPersonalGoal (PersonalGoal personalGoalTest, PersonalGoal personalGoalAssert) {
        for(int i=0; i<personalGoalTest.getPersonalGoal().length-1; i++){
            for(int j=0; j<personalGoalTest.getPersonalGoal()[i].length-1; j++) {
                assertEquals(personalGoalTest.getPersonalGoal()[i][j], personalGoalAssert.getPersonalGoal()[i][j]);
            }
        }
        for(int i = 0; i< PersonalGoal.getUsedCodes().size(); i++) {
            assertEquals(PersonalGoal.getUsedCodes().get(i), PersonalGoal.getUsedCodes().get(i));
        }
    }

    /**
     * Checks if two Player objects are identical.
     * @param playerTest a test Player object
     * @param playerAssert another test Player object
     */
    public void assertEqualsPlayer (Player playerTest, Player playerAssert) {
        assertEquals(playerTest.getNickname(), playerAssert.getNickname());
        assertEquals(playerTest.getPosition(), playerAssert.getPosition());
        assertEquals(playerTest.getPoints(), playerAssert.getPoints());
        assertEqualsShelf(playerTest.getShelf(), playerAssert.getShelf());
        assertEqualsPersonalGoal(playerTest.getPersonalGoal(), playerAssert.getPersonalGoal());
        //assertEquals(playerTest.isPersonalGoalRedeemed(), playerAssert.isPersonalGoalRedeemed()); attribute removed
        assertEquals(playerTest.isCommonGoalRedeemed(0), playerAssert.isCommonGoalRedeemed(0));
        assertEquals(playerTest.isCommonGoalRedeemed(1), playerAssert.isCommonGoalRedeemed(1));
    }


    /**
     * Check if two Bag objects are identical.
     * @param bagTest a test Bag object
     * @param bagAssert another test Bag object
     */
    public void assertEqualsBag (Bag bagTest, Bag bagAssert) {
        for(int i=0; i<bagTest.getBag().size(); i++) {
            assertEqualsTile(bagTest.getBag().get(i), bagAssert.getBag().get(i));
        }
    }

    /**
     * check if two Board objects are identical
     * @param boardTest a test Board object
     * @param boardAssert another test Board object
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
     * @param chatTest a test Chat object
     * @param chatAssert another test Chat object
     */
    public void assertEqualsChat (Chat chatTest, Chat chatAssert) {
        for(int i=0; i<chatTest.getChat().size(); i++) {
            assertEquals(chatTest.getChat().get(i), chatAssert.getChat().get(i));
        }
    }

    /*
     * check if two CommonGoal objects are identical
     * @param commonGoalTest a test Common object
     * @param commonGoalAssert

    public void assertEqualsCommonGoal (CommonGoal commonGoalTest, CommonGoal commonGoalAssert) {
        assertEquals(commonGoalTest.getGoalCode(), commonGoalAssert.getGoalCode());
        assertEquals(commonGoalTest.getRedeemedNumber(), commonGoalAssert.getRedeemedNumber());
    }*/


     /** Checks if the gameToJson function correctly serialize and deserialize the Game class.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void gameDeserializerTest() throws  NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException{

        Game game = gameInitializer();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "_");
        String json = saveGameStatus.gameToJson();
        Gson gson = new Gson();
        System.out.println(json);

        /*String substring = json.substring(json.indexOf("commonGoals") - 2);
        String newJson = json.replace(substring, "}");
        char[] charArray = substring.toCharArray();
        charArray[0] = '{';
        String newSub = String.valueOf(charArray);

        Gson gsonCommon = new Gson();
        JsonElement element = gsonCommon.fromJson(newSub, JsonElement.class);
        JsonArray commonGoalsArray = element.getAsJsonObject().getAsJsonArray("commonGoals");
        int code0 = commonGoalsArray.get(0).getAsJsonObject().get("commonGoalCode").getAsInt();
        int code1 = commonGoalsArray.get(1).getAsJsonObject().get("commonGoalCode").getAsInt();

        Game data = gson.fromJson(newJson, Game.class);
        data.setCommonGoals(data.getCommonGoalsByCode(code0, code1));*/
        Game data = gson.fromJson(json, Game.class);


        assertEqualsPlayer(game.getFirstPlayer(), data.getFirstPlayer());
        assertEqualsPlayer(game.getCurrentPlayer(), data.getCurrentPlayer());
        for(int i=0; i<game.getPlayers().size(); i++) {
            assertEqualsPlayer(game.getPlayers().get(i), data.getPlayers().get(i));
        }
        assertEqualsBoard(game.getBoard(), data.getBoard());
        assertEqualsChat(game.getChat(), data.getChat());
        /*for(int i=0; i<game.getCommonGoals().size(); i++) {
            assertEqualsCommonGoal(game.getCommonGoals().get(i), data.getCommonGoals().get(i));
        }*/
    }
}


