package org.projectsw.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Distributed.ServerImplementation;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.RowColumn;
import org.projectsw.TestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.projectsw.Model.Enums.TilesEnum.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

class EngineTest extends TestUtils {

    /**
     * Cleans the list of used codes before each test.
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Tests the creation of a game in LOBBY state, also checking if the filling of the lobby
     * works correctly and if the state of the game changes after the last join
     */
    @Test
    void startingGameSimulation() throws RemoteException {
        //creates a new engine and checks if it has an empty game
        Engine engine = new Engine(new ServerImplementation());
        assertNull(engine.getGame());
        //initialize game
        //
        /*calls firstPlayerJoin and checks if all the parameters of game are correctly initialized
        engine.firstPlayerJoin("Davide", 4);
        assertEquals(1, engine.getGame().getPlayers().size());
        Player player1 = engine.getGame().getPlayers().get(0);
        assertEquals("Davide", player1.getNickname());
        assertEquals(0, player1.getPosition());
        assertEquals(player1, engine.getGame().getCurrentPlayer());
        assertEquals(player1, engine.getGame().getFirstPlayer());
        assertEquals(GameState.LOBBY, engine.getGame().getGameState());
        assertEquals(4, engine.getGame().getNumberOfPlayers());
        assertEqualsBoard(new Board(4), engine.getGame().getBoard());
        //calls playerJoin and checks if the player is added correctly
        //player2
        engine.playerJoin("Lollo");
        assertEquals(2, engine.getGame().getPlayers().size());
        Player player2 = engine.getGame().getPlayers().get(1);
        assertEquals("Lollo", player2.getNickname());
        assertEquals(1, player2.getPosition());
        //player3
        engine.playerJoin("Luca");
        assertEquals(3, engine.getGame().getPlayers().size());
        Player player3 = engine.getGame().getPlayers().get(2);
        assertEquals("Luca", player3.getNickname());
        assertEquals(2, player3.getPosition());
        //player4
        engine.playerJoin("Lore");
        assertEquals(4, engine.getGame().getPlayers().size());
        Player player4 = engine.getGame().getPlayers().get(3);
        assertEquals("Lore", player4.getNickname());
        assertEquals(3, player4.getPosition());
        //checks if now the state of the game is changed
        assertEquals(GameState.RUNNING, engine.getGame().getGameState());
    */}
}