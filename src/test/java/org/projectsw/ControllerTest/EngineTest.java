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
import org.projectsw.View.SerializableInput;

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
    void getOptionChoosed() {

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

    @Test
    public void testPlayerJoinInLobby() {
        Engine engine = new Engine();
        // Setup
        String nickname = "John";
        String ID = "123";
        engine.getGame().setGameState(GameState.LOBBY);

        // Invoke the method
        engine.playerJoin(nickname, ID);

        // Check the game state
        assertEquals(GameState.LOBBY, engine.getGame().getGameState());

        // Check the number of players
        assertEquals(1, engine.getGame().getPlayers().size());

        // Check the first player
        Player firstPlayer = engine.getGame().getFirstPlayer();
        assertEquals(nickname, firstPlayer.getNickname());
        assertEquals(0, firstPlayer.getPosition());

        // Check the current player
        Player currentPlayer = engine.getGame().getCurrentPlayer();
        assertEquals(nickname, currentPlayer.getNickname());
        assertEquals(0, currentPlayer.getPosition());

        // Check the ID-Nickname mapping
        assertEquals(nickname, engine.getID_Nicks().getValue(ID));

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
        Engine engine = new Engine();
        Player player = new Player("pippo", 0);
        engine.getGame().getPlayers().add(player);
        assertFalse(engine.getLoadFromFile());

    }

    @Test
    void setIsActiveFromClient() {
        Engine engine = new Engine();
        Player player = new Player("pippo", 0);
        engine.getGame().getPlayers().add(player);
        try {
            Server server = new ServerImplementation();
            Client client = new ClientImplementation(server);
            engine.getClients_ID().put(client, "0");
            engine.getID_Nicks().put("0", "pippo");
            assertTrue(player.getIsActive());
            engine.setIsActiveFromClient(client, false);
            assertFalse(player.getIsActive());
        } catch (RemoteException e) {
            System.err.println("Error creating a Server in Engine test");
        }
    }

    @Test
    void takeNick() {
    }

    @Test
    void connect() {
        Engine engine = new Engine();
        Player player = new Player("pippo", 0);
        assertFalse(engine.getPlayerReconnection());
        engine.getGame().getPlayers().add(player);
        player.setIsActive(false);
        try {
            engine.Connect("0");
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(engine.getPlayerReconnection());
    }

    @Test
    void setNumberOfPlayers() {
        Engine engine = new Engine();
        engine.setNumberOfPlayers(2,"0");
        assertEquals(engine.getGame().getNumberOfPlayers(), 2);
    }

   /* @Test
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
    }*/

    @Test
    void transferMethods() {
        Engine engine = new Engine();
        engine.getGame().initializeGame(2);
        engine.playerJoin("pippo", "0");
        engine.currentPlayerTransfer("0");
        engine.commonGoalTransfer("0");
        engine.temporaryTilesTransfer("0");
        engine.personalGoalTransfer("0", "pippo");
        engine.shelfTransferAll("0");
        engine.shelfTransfer("pippo", "0");
        engine.boardTransfer("0");
    }

    @Test
    void reconnectionCheck() {
        Engine engine = new Engine();
        Player player = new Player("pippo", 0);
        player.setIsActive(false);
        engine.getGame().getPlayers().add(player);
        assertFalse(engine.getPlayerReconnection());
        String id = "000";
        engine.reconnectionCheck(id);
        assertTrue(engine.getPlayerReconnection());
    }

    @Test
    void notActive() {
        Engine engine = new Engine();
        Player player = new Player("pippo", 0);
        engine.getGame().getPlayers().add(player);
        assertTrue(engine.getGame().getPlayers().get(0).getIsActive());
        Client client = null;
        SerializableInput input = new SerializableInput("1", "pippo", client);
        engine.notActive(input);
        assertFalse(engine.getGame().getPlayers().get(0).getIsActive());
    }
}
