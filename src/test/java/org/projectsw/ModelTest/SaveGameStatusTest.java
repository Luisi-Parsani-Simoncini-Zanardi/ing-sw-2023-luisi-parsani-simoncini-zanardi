package org.projectsw.ModelTest;
import org.junit.jupiter.api.BeforeEach;
import org.projectsw.Controller.Engine;
import org.projectsw.Model.*;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.TestUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SaveGameStatusTest extends TestUtils {

    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Initializes a game given its properties.
     * @return the initialized game
     */

    public Game gameInitializer() {
        Game game = new Game();
        // Engine engine = new Engine();
        ArrayList<Tile> temporaryPoints= new ArrayList<>();
        temporaryPoints.add(new Tile(TilesEnum.GAMES, 1));
        temporaryPoints.add(new Tile(TilesEnum.CATS, 1));
        //  engine.setGame(game);
        game.initializeGame(2);
        Player player1 = new Player("Ganondorf", 0);

            //  player1.setTemporaryTiles(temporaryPoints);

        game.addPlayer(player1);
        game.addPlayer(new Player("xlr8", 1));
        game.setFirstPlayer(player1);
        game.setCurrentPlayer(player1);
        // engine.fillBoard();
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
    public void gameSerializeDeserializeTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Game game = gameInitializer();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "src/main/java/org/projectsw/Util/save.txt");
        saveGameStatus.saveGame();
        Game data = saveGameStatus.retrieveGame();


        assertEquals(game.getGameState(), data.getGameState());
        assertEquals(game.getNumberOfPlayers(), data.getNumberOfPlayers());
        assertEqualsPlayer(game.getFirstPlayer(), data.getFirstPlayer());
        assertEqualsPlayer(game.getCurrentPlayer(), data.getCurrentPlayer());
        for(int i=0; i<game.getPlayers().size(); i++) {
            assertEqualsPlayer(game.getPlayers().get(i), data.getPlayers().get(i));
        }
        assertEqualsBoard(game.getBoard(), data.getBoard());
        assertEqualsChat(game.getChat(), data.getChat());
        assertEqualsStrategy(game.getCommonGoals().get(0).getStrategy(), data.getCommonGoals().get(0).getStrategy());
        assertEqualsCommonGoal(game.getCommonGoals().get(0), data.getCommonGoals().get(0));
        assertEqualsCommonGoal(game.getCommonGoals().get(1), data.getCommonGoals().get(1));
    }

    @Test
    public void deleteTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Game game = gameInitializer();
        SaveGameStatus saveGameStatus = new SaveGameStatus(game, "C:\\Users\\Cristina\\Desktop\\saveGameFile\\save.txt");
        saveGameStatus.saveGame();
        saveGameStatus.deleteSaveFile();
        assertFalse(saveGameStatus.checkExistingSaveFile("C:\\Users\\Cristina\\Desktop\\saveGameFile\\save.txt"));
    }

    @Test
    void deleteSaveFile() {
        Engine engine = new Engine();
        engine.saveFileFound();
        engine.getSaveGameStatus().saveGame();
        engine.getSaveGameStatus().deleteSaveFile();
        assertFalse(engine.saveFileFound());
    }
}


