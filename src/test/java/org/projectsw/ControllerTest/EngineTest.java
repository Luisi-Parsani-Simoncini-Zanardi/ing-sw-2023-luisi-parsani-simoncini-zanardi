package org.projectsw.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.ClientImplementation;
import org.projectsw.Distributed.Server;
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
    void codesCleaner() {
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Test that the clients_ID hashmap has the right values
     */
    @Test
    void getClients_ID() {
        Server server = null;
        Client client = null;
        try {
            server = new ServerImplementation();
            client = new ClientImplementation(server);
        } catch (RemoteException e) {
            System.err.println("Error creating a Server in Engine test");
        }


    }

    @Test
    void getID_Nicks() {
    }

    @Test
    void getClientObserverHashMap() {
    }

    /**
     * Test if the method getGame() returns the game correctly
     */
    @Test
    void getGame() {
        Engine engine = new Engine();
        assertNotNull(engine.getGame());
        assertEquals(GameState.LOBBY, engine.getGame().getGameState());
        assertNotNull(engine.getGame().getChat());
        assertNotNull(engine.getGame().getPlayers());
        assertNotNull(engine.getGame().getCommonGoals());
        assertEquals(0, engine.getGame().getNumberOfPlayers());
        engine.getGame().setGameState(GameState.RUNNING);
        engine.getGame().setNumberOfPlayers(4);
        assertEquals(GameState.RUNNING, engine.getGame().getGameState());
        assertEquals(4, engine.getGame().getNumberOfPlayers());
    }

    /**
     * Test if the method returns correctly the player associated with the passed nickname, or null otherwise
     */
    @Test
    void getPlayerFromNickname() {
        Engine engine = new Engine();
        Game game = new Game();
        Player player1 = new Player("Lorenzo",0);
        Player player2 = new Player("Luca",1);
        Player player3 = new Player("Davide",2);
        Player player4 = new Player("Piero",4);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        engine.setGame(game);
        assertEquals(player1, engine.getPlayerFromNickname("Lorenzo"));
        assertEquals(player2, engine.getPlayerFromNickname("Luca"));
        assertEquals(player3, engine.getPlayerFromNickname("Davide"));
        assertEquals(player4, engine.getPlayerFromNickname("Piero"));
        assertNull(engine.getPlayerFromNickname("Elisa"));
    }

    /**
     * Test that setGame is setting the engine game correctly
     */
    @Test
    void setGame() {
        Engine engine = new Engine();
        Game game = new Game();
        Player player1 = new Player("Lorenzo",0);
        Player player2 = new Player("Luca",1);
        Player player3 = new Player("Davide",2);
        Player player4 = new Player("Piero",4);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        engine.setGame(game);
        assertEquals(game, engine.getGame());
    }

    @Test
    void getSaveGameStatus() {
    }

    @Test
    void playerJoin() {
    }

    /**
     * Test that temporaryPoints and selectablePoints are updated correctly after the call of selectTiles()
     */
    @Test
    void selectTiles() {
        Engine engine = new Engine();
        engine.getGame().initializeGame(4);
        engine.playerJoin("Lorenzo","0");
        engine.playerJoin("Piero","1");
        engine.playerJoin("Asia","2");
        engine.playerJoin("Riccardo","3");
        Board board = engine.getGame().getBoard();
        assertEquals(0, engine.getGame().getBoard().getTemporaryPoints().size());
        assertEquals(20, engine.getGame().getBoard().getSelectablePoints().size());assertEquals(0, engine.getGame().getBoard().getTemporaryPoints().size());
        engine.selectTiles("0", new Point(0,0));
        assertEquals(0, engine.getGame().getBoard().getTemporaryPoints().size());
        assertEquals(20, engine.getGame().getBoard().getSelectablePoints().size());
        engine.selectTiles("0", new Point(4,1));
        assertEquals(board.getTemporaryPoints(),engine.getGame().getBoard().getTemporaryPoints());
        assertEquals(board.getSelectablePoints(),engine.getGame().getBoard().getSelectablePoints());
    }

    @Test
    void confirmSelectedTiles() {
    }

    @Test
    void selectColumn() {
    }

    @Test
    void placeTiles() {
    }

    @Test
    void checkCommonGoals() {
    }

    @Test
    void sendResults() {
    }

    @Test
    void checkPersonalGoal() {
    }

    @Test
    void checkEndgameGoal() {
    }

    @Test
    void endTurn() {
    }

    @Test
    void sendNexTurn() {
    }

    @Test
    void endTurnForced() {
    }

    @Test
    void checkEndGame() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void endGame() {
    }

    @Test
    void resetGame() {
    }

    @Test
    void sendChat() {
    }

    @Test
    void sayInChat() {
    }

    @Test
    void removeObserver() {
    }

    @Test
    void fillBoard() {
    }

    @Test
    void initializeFromSave() {
    }

    @Test
    void setIsActiveFromClient() {
    }

    @Test
    void takeNick() {
    }

    @Test
    void connect() {
    }

    @Test
    void setNumberOfPlayers() {
    }

    @Test
    void boardTransfer() {
    }

    @Test
    void shelfTransfer() {
    }

    @Test
    void shelfTransferAll() {
    }

    @Test
    void personalGoalTransfer() {
    }

    @Test
    void temporaryTilesTransfer() {
    }

    @Test
    void commonGoalTransfer() {
    }

    @Test
    void currentPlayerTransfer() {
    }

    @Test
    void reconnectionCheck() {
    }

    @Test
    void notActive() {
    }
}
