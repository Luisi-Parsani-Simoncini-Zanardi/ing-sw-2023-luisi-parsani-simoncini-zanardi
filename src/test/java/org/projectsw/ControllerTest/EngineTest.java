package org.projectsw.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.ClientImplementation;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Distributed.Server;
import org.projectsw.Distributed.ServerImplementation;
import org.projectsw.Exceptions.UnselectableColumnException;
import org.projectsw.Exceptions.UnselectableTileException;
import org.projectsw.Model.*;
import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.TestUtils;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observer;
import org.projectsw.View.SerializableInput;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.projectsw.Model.Enums.TilesEnum.EMPTY;

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
        Engine engine = new Engine();
        try {
            Server server = new ServerImplementation();
            Client client = new ClientImplementation(server);
            engine.getClients_ID().put(client, "0");
            assertEquals(engine.getClients_ID().getKey("0"), client);
        } catch (RemoteException e) {
            System.err.println("Error creating a Server in Engine test");
        }
    }

    /**
     * Test the getID_Nicks() engine function
     */
    @Test
    void getID_Nicks() {
        Engine engine = new Engine();
            engine.getID_Nicks().put("0", "pippo");
            assertEquals(engine.getID_Nicks().getKey("pippo"), "0");
    }

    /**
     * Test the getFirstClient() engine function
     */
    @Test
    void getFirstClient() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        try {
            engine.Connect("0");
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals("0", engine.getFirstClient());
    }

    /**
     * Test the getClientObserverHashMap() engine function
     */
    @Test
    void getClientObserverHashMap() {
        Server server;
        try {
            server = new ServerImplementation();
            Client client = new ClientImplementation(server);
            Engine engine = new Engine();
            Game game = new Game();
            engine.setGame(game);
            Player player = new Player("pippo", 0);
            engine.getGame().getPlayers().add(player);
            Observer<Game, ResponseMessage> observer = (o, response) -> {
            };
            engine.getClientObserverHashMap().put(client, observer);
            assertEquals(observer, engine.getClientObserverHashMap().get(client));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test if the method getGame() returns the game correctly
     */
    @Test
    void getGame() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
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

    /**
     * Test the getSaveGameStatus() engine function
     */
    @Test
    void getSaveGameStatus() {
        Engine engine = new Engine();
        engine.saveFileFound();
        SaveGameStatus save = new SaveGameStatus(engine.getGame(), "...");
        engine.setSaveGameStatus(save);
        assertEquals(save, engine.getSaveGameStatus());
    }

    /**
     * Test the getOptionChoosed() engine function
     */
    @Test
    void getOptionChoosed() {
        Engine engine = new Engine();
        engine.setOptionChoosed(true);
        assertEquals(true, engine.getOptionChosen());
    }

    /**
     * Test the startGame() engine function
     */
    @Test
    void startGame() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        engine.startGame();
        assertEquals(engine.getGame().getGameState(), GameState.RUNNING);
    }

    /**
     * Test the smallJoin() engine function
     */
    @Test
    void smallJoin() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        engine.smallJoin("pippo");
        assertEquals(engine.getGame().getPlayers().size(), 1);
    }

    /**
     * Test the playerJoin() engine function
     */
    @Test
    void playerJoin() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        assertEquals(engine.getGame().getPlayers().size(), 0);
        engine.playerJoin("pippo", "0");
        assertEquals(engine.getGame().getPlayers().size(), 1);
    }

    /**
     * Test the testPlayerJoinInLobby() engine function
     */
    @Test
    public void testPlayerJoinInLobby() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
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
     * Test the deselectTiles() engine function
     */
    @Test
    void deselectTiles() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        engine.fillBoard();
        engine.getGame().getBoard().getSelectablePoints().add(new Point(4,2));
        try {
            engine.getGame().getBoard().addTemporaryPoints(new Point(4,2));
        } catch (UnselectableTileException e) {
            throw new RuntimeException(e);
        }
        engine.deselectTiles(new Point(4,2));
        assertEquals(engine.getGame().getBoard().getTemporaryPoints().size(), 0);
    }

    /**
     * Test that temporaryPoints and selectablePoints are updated correctly after the call of selectTiles()
     */
    @Test
    void selectTiles() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
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

    /**
     * Test the confirmSelectedTiles() engine function
     */
    @Test
    void confirmSelectedTiles() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        ArrayList<Integer> sel = new ArrayList<>();
        sel.add(1);
        engine.fillBoard();
        player.getShelf().setSelectableColumns(sel);
        assertEquals(player.getTemporaryTiles().size(), 0);
        Point point = new Point(5,2);
        engine.getGame().getBoard().getSelectablePoints().add(point);
        try {
            engine.getGame().getBoard().addTemporaryPoints(point);
        } catch (UnselectableTileException e) {
            throw new RuntimeException(e);
        }
        engine.confirmSelectedTiles("0");
        assertEquals(player.getTemporaryTiles().size(), 1);
    }

    /**
     * Test the selectColumn() engine function
     */
    @Test
    void selectColumn() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        ArrayList<Integer> sel = new ArrayList<>();
        sel.add(1);
        player.getShelf().setSelectableColumns(sel);
        assertNull(player.getShelf().getSelectedColumn());
        engine.selectColumn("0", 1);
        assertEquals(player.getShelf().getSelectedColumn(), 1);
    }

    /**
     * Test the placeTiles() engine function
     */
    @Test
    void placeTiles() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        ArrayList<Integer> sel = new ArrayList<>();
        sel.add(1);
        try {
            player.getShelf().setSelectableColumns(sel);
            player.getShelf().setSelectedColumn(1);
        } catch (UnselectableColumnException e) {
            throw new RuntimeException(e);
        }
        Tile tile = new Tile(TilesEnum.GAMES, 0);
        engine.getGame().getCurrentPlayer().addTemporaryTile(tile);
        assertEquals(player.getTemporaryTiles().size(), 1);
        engine.placeTiles("0", 0);
        assertEquals(player.getTemporaryTiles().size(), 0);
    }

    /**
     * Test the checkCommonGoals() engine function
     */
    @Test
    void checkCommonGoals() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        engine.checkPersonalGoal();
        for(Player players: engine.getGame().getPlayers()){
            assertEquals(players.getPoints(), 0);
        }
    }

    /**
     * Test the checkPersonalGoal() engine function
     */
    @Test
    void checkPersonalGoal() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        engine.checkPersonalGoal();
        for(Player players: engine.getGame().getPlayers()){
            assertEquals(players.getPoints(), 0);
        }
    }

    /**
     * Test the checkEndgameGoal() engine function
     */
    @Test
    void checkEndgameGoal() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        engine.checkEndgameGoal();
        for(Player players: engine.getGame().getPlayers()){
            assertEquals(players.getPoints(), 0);
        }
    }

    /**
     * Test the saveFileFound() engine function
     */
    @Test
    void saveFileFound() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        engine.saveFileFound();
        engine.getSaveGameStatus().saveGame();
        assertTrue(engine.saveFileFound());
    }

    /**
     * Test the retrieveGame() engine function
     */
    @Test
    void retrieveGame() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        engine.saveFileFound();
        engine.getSaveGameStatus().saveGame();
        assertEquals(engine.retrieveGame().getPlayers(), engine.getGame().getPlayers());
    }

    /**
     * Test the endTurn() engine function
     */
    @Test
    void endTurn() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.saveFileFound();
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        assertEquals(engine.getGame().getCurrentPlayer().getNickname(),"pippo");
        engine.endTurn("0", "pippo");
        assertEquals(engine.getGame().getCurrentPlayer().getNickname(),"lupus");
    }

    /**
     * Test the endTurnForced() engine function
     */
    @Test
    void endTurnForced() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.saveFileFound();
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        engine.endTurnForced();
        assertEquals(engine.getPlayerFromNickname("pippo").getTemporaryTiles().size(), 0);
        assertEquals(engine.getGame().getCurrentPlayer().getNickname(), "lupus");

    }

    /**
     * Test the checkEndGame() engine function
     */
    @Test
    void checkEndGame() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        player.setPoints(100);
        for(int i=0; i< Config.shelfHeight; i++)
            for(int j=0; j< Config.shelfLength; j++) {
                Tile tile = new Tile(TilesEnum.GAMES, 0);
                player.getShelf().insertTiles(tile, i,j);
            }
        engine.getGame().setCurrentPlayer(player);
        engine.getGame().getBoard().setEndGame(false);
        engine.checkEndGame("0", "pippo");
        assertTrue(engine.getGame().getBoard().isEndGame());
        assertEquals(player.getPoints(), 101);
    }

    /**
     * Test the endGame() engine function
     */
    @Test
    void endGame() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        engine.saveFileFound();
        engine.endGame();
        engine.getSaveGameStatus().saveGame();
        assertTrue(engine.saveFileFound());
        engine.getSaveGameStatus().deleteSaveFile();
        assertFalse(engine.saveFileFound());
    }

    /**
     * Test the sayInChat() engine function
     */
    @Test
    void sayInChat() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Chat chat = new Chat();
        assertEquals(chat.getMessages(), engine.getGame().getChat().getMessages());
        engine.sayInChat("pippo", "ciao", Config.everyone, "0");
        engine.sayInChat("pippo", "ciao", Config.error, "0");
        Message mess = new Message("pippo", Config.everyone, "ciao");
        chat.addChatLog(mess);
        assertEquals(chat.getMessages().get(0).getPayload(), engine.getGame().getChat().getMessages().get(0).getPayload());

    }

    /**
     * Test the removeObserver() engine function
     */
    @Test
    void removeObserver() {
        try {
            Server server = new ServerImplementation();
            Client client = new ClientImplementation(server);
            Engine engine = new Engine();
            Game game = new Game();
            engine.setGame(game);
            Player player = new Player("pippo", 0);
            engine.getGame().getPlayers().add(player);
            Observer<Game, ResponseMessage> observer = (o, response) -> {
            };
            engine.getClientObserverHashMap().put(client, observer);
            engine.getClients_ID().put(client, "0");
            engine.getID_Nicks().put("0", "pippo");
            engine.removeObserver("0", 0);
            assertEquals(engine.getClientObserverHashMap(), new HashMap<>());
            assertEquals(engine.getClients_ID().getAllKey(), new ArrayList<>());
            assertEquals(engine.getID_Nicks().getAllKey(), new ArrayList<>());
        } catch (RemoteException e) {
            System.err.println("Error creating a Server in Engine test");
        }
    }

    /**
     * Test the fillBoard() engine function
     */
    @Test
    void fillBoard() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Bag bag = engine.getGame().getBoard().getBag();
        for(int i=0; i< Config.boardHeight; i++){
            for (int j=0; j< Config.boardLength; j++) {
                if (engine.getGame().getBoard().getBoard()[i][j].getTile()==EMPTY){
                    bag.pop();
                }
            }
        }
        engine.fillBoard();
        assertEquals(bag, engine.getGame().getBoard().getBag());
    }

    /**
     * Test the initializeFromSave() engine function
     */
    @Test
    void initializeFromSave() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("pluto", 0);
        engine.getGame().initializeGame(2);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setFirstPlayer(player);
        assertFalse(engine.getLoadFromFile());
        ArrayList<String> empty = new ArrayList<>();
        ArrayList<String> pippo = new ArrayList<>();
        engine.saveFileFound();
        pippo.add("pippo");
        pippo.add("pluto");
        pippo.add("pippo");
        pippo.add("pluto");
        assertEquals(empty, engine.getFreeNamesUsedInLastGame());
        assertFalse(engine.getOptionChosen());
        engine.getSaveGameStatus().saveGame();
        engine.initializeFromSave("0");
        assertTrue(engine.getLoadFromFile());
        assertTrue(engine.getOptionChosen());
        assertEquals(pippo, engine.getFreeNamesUsedInLastGame());
    }

    /**
     * Test the getNickFromClient() engine function
     */
    @Test
    void getNickFromClient() {
        Server server;
        try {
            server = new ServerImplementation();
            Client client = new ClientImplementation(server);
            Engine engine = new Engine();
            Game game = new Game();
            engine.setGame(game);
            Player player = new Player("pippo", 0);
            engine.getGame().getPlayers().add(player);
            Observer<Game, ResponseMessage> observer = (o, response) -> {
            };
            engine.getClientObserverHashMap().put(client, observer);
            engine.getClients_ID().put(client, "0");
            engine.getID_Nicks().put("0", "pippo");
            assertEquals(engine.getNickFromClient(client), "pippo");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the setIsActiveFromClient() engine function
     */
    @Test
    void setIsActiveFromClient() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
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

    /**
     * Test the takeNick() engine function
     */
    @Test
    void takeNick() {
        Server server;
        Client client;
        try {
            server = new ServerImplementation();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            client = new ClientImplementation(server);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        engine.getGame().getPlayers().add(player);
        engine.getGame().setCurrentPlayer(player);
        engine.getGame().setFirstPlayer(player);
        engine.takeNick(new SerializableInput("0", "lupus", new Point(1,1), client));
        assertEquals(engine.getGame().getPlayers().size(), 2);
        engine.initializeFromSave("0");
        engine.takeNick(new SerializableInput("0", "zzz", new Point(1,1), client));
    }

    /**
     * Test the connect() engine function
     */
    @Test
    void connect() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
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
        engine.saveFileFound();
        engine.getSaveGameStatus().deleteSaveFile();
        try {
            engine.Connect("1");
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(engine.getPlayerReconnection());
        engine.saveFileFound();
        engine.getSaveGameStatus().deleteSaveFile();
    }

    @Test
    void connect2() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        Player player = new Player("pippo", 0);
        assertFalse(engine.getPlayerReconnection());
        engine.getGame().getPlayers().add(player);
        engine.saveFileFound();
        engine.getSaveGameStatus().deleteSaveFile();
        try {
            engine.Connect("1");
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertFalse(engine.getPlayerReconnection());
        engine.saveFileFound();
        engine.getSaveGameStatus().deleteSaveFile();
    }

    /**
     * Test the setNumberOfPlayers() engine function
     */
    @Test
    void setNumberOfPlayers() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.setNumberOfPlayers(2,"0");
        assertEquals(engine.getGame().getNumberOfPlayers(), 2);
    }

    /**
     * Test the transferMethods() engine function
     */
    @Test
    void transferMethods() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        engine.getGame().initializeGame(2);
        engine.playerJoin("pippo", "0");
        engine.currentPlayerTransfer("0");
        engine.commonGoalTransfer("0");
        engine.temporaryTilesTransfer("0");
        engine.personalGoalTransfer("0", "pippo");
        engine.shelfTransferAll("0");
        engine.shelfTransfer("pippo", "0");
        engine.boardTransfer("0");
        engine.sendChat(" ", "0");
        engine.sendResults(1);
        engine.everlastingKill();
    }

    /**
     * Test the reconnectionCheck() engine function
     */
    @Test
    void reconnectionCheck() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        Player player = new Player("pippo", 0);
        player.setIsActive(false);
        engine.getGame().getPlayers().add(player);
        assertFalse(engine.getPlayerReconnection());
        String id = "000";
        engine.reconnectionCheck(id);
        assertTrue(engine.getPlayerReconnection());
    }

    /**
     * Test the notActive() engine function
     */
    @Test
    void notActive() {
        Engine engine = new Engine();
        Game game = new Game();
        engine.setGame(game);
        Player player = new Player("pippo", 0);
        engine.getGame().getPlayers().add(player);
        assertTrue(engine.getGame().getPlayers().get(0).getIsActive());
        Client client = null;
        SerializableInput input = new SerializableInput("1", "pippo", client);
        engine.notActive(input);
        assertFalse(engine.getGame().getPlayers().get(0).getIsActive());
    }
}
