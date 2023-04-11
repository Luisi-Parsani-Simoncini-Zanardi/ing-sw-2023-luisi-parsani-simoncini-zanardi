package org.projectsw.ModelTest;
//TODO: !!!POST!!! sistemare questa classe una volta sistemato SaveGameStatus
import com.google.gson.Gson;
import org.projectsw.Model.*;
import org.projectsw.TestUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveGameStatusTest extends TestUtils {

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

     /** Checks if the gameToJson function correctly serialize and deserialize the Game class.
      * @throws NoSuchMethodException when there's no method defined as such
      * @throws InvocationTargetException when a called method generates an exception
      * @throws InstantiationException when the class cannot be instantiated
      * @throws IllegalAccessException when the caller cannot access the method or parameter
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

        Game data = gson.fromJson(newJson, Game.class);
        // data.setCommonGoals(data.getCommonGoalsByCode(code0, code1));*/
        // Game data = gson.fromJson(json, Game.class);


        assertEquals(game.getGameState(), data.getGameState());
        assertEquals(game.getNumberOfPlayers(), data.getNumberOfPlayers());
        assertEqualsPlayer(game.getFirstPlayer(), data.getFirstPlayer());
        assertEqualsPlayer(game.getCurrentPlayer(), data.getCurrentPlayer());
        for(int i=0; i<game.getPlayers().size(); i++) {
            assertEqualsPlayer(game.getPlayers().get(i), data.getPlayers().get(i));
        }
        assertEqualsBoard(game.getBoard(), data.getBoard());
        assertEqualsChat(game.getChat(), data.getChat());
        assertEqualsCommonGoal(game.getCommonGoals().get(0), data.getCommonGoals().get(0));
        assertEqualsCommonGoal(game.getCommonGoals().get(1), data.getCommonGoals().get(1));
    }
}


