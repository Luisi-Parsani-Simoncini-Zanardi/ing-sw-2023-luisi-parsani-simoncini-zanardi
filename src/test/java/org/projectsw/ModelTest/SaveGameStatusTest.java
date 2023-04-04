package org.projectsw.ModelTest;
//TODO: sistemare questa classe, non è una classe di test
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.projectsw.Model.*;
import java.lang.reflect.AccessibleObject;
import org.projectsw.testUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveGameStatusTest extends testUtils {

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
        Player player2 = new Player("Lorenzo", 1);
        player2.setShelf(new Shelf());
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.setCurrentPlayer(player1);
        game.setFirstPlayer(player2);
        PersonalGoal.cleanUsedCodes();

        return game;
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

        String substring = json.substring(json.indexOf("commonGoals") - 2);
        String newJson = json.replace(substring, "}");
        char[] charArray = substring.toCharArray();
        charArray[0] = '{';
        String newSub = String.valueOf(charArray);

        /*Gson gsonCommon = new Gson();
        JsonElement element = gsonCommon.fromJson(newSub, JsonElement.class);
        JsonArray commonGoalsArray = element.getAsJsonObject().getAsJsonArray("commonGoals");
        int code0 = commonGoalsArray.get(0).getAsJsonObject().get("commonGoalCode").getAsInt();
        int code1 = commonGoalsArray.get(1).getAsJsonObject().get("commonGoalCode").getAsInt();*/

        Game data = gson.fromJson(newJson, Game.class);
        // data.setCommonGoals(data.getCommonGoalsByCode(code0, code1));*/
        // Game data = gson.fromJson(json, Game.class);


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


