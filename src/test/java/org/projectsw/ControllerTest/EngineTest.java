package org.projectsw.ControllerTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Model.*;
import org.projectsw.TestUtils;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class EngineTest extends TestUtils {

    /**
     * Tests the creation of a game in LOBBY state, also checking if the filling of the lobby
     * works correctly and if the state of the game changes after the last join
     */
    @Test
    void startingGameSimulation() {
        //creates a new engine and checks if it has an empty game
        Engine engine = new Engine();
        assertNull(engine.getGame());
        //calls firstPlayerJoin and checks if all the parameters of game are correctly initialized
        engine.firstPlayerJoin("Davide",4);
        assertEquals(1,engine.getGame().getPlayers().size());
        Player player1 = engine.getGame().getPlayers().get(0);
        assertEquals("Davide",player1.getNickname());
        assertEquals(0,player1.getPosition());
        assertEquals(player1,engine.getGame().getCurrentPlayer());
        assertEquals(player1,engine.getGame().getFirstPlayer());
        assertEquals(GameStates.LOBBY,engine.getGame().getGameState());
        assertEquals(4,engine.getGame().getNumberOfPlayers());
        assertEqualsBoard(new Board(4),engine.getGame().getBoard());
        //calls playerJoin and checks if the player is added correctly
        //player2
        engine.playerJoin("Lollo");
        assertEquals(2,engine.getGame().getPlayers().size());
        Player player2 = engine.getGame().getPlayers().get(1);
        assertEquals("Lollo",player2.getNickname());
        assertEquals(1,player2.getPosition());
        //player3
        engine.playerJoin("Luca");
        assertEquals(3,engine.getGame().getPlayers().size());
        Player player3 = engine.getGame().getPlayers().get(2);
        assertEquals("Luca",player3.getNickname());
        assertEquals(2,player3.getPosition());
        //player4
        engine.playerJoin("Lore");
        assertEquals(4,engine.getGame().getPlayers().size());
        Player player4 = engine.getGame().getPlayers().get(3);
        assertEquals("Lore",player4.getNickname());
        assertEquals(3,player4.getPosition());
        //checks if now the state of the game is changed
        assertEquals(GameStates.RUNNING,engine.getGame().getGameState());
    }

    @Test
    void selectTiles() {
    }

    @Test
    void placeTiles() {
    }

    @Test
    void checkCommonGoals() {
    }

    @Test
    void checkPersonalGoal() {
    }

    @Test
    void endTurn() {
    }

    @Test
    void endGame() {
    }

    @Test
    void resetGame() {
    }

    //the function start game is changed and now this test doesen't work
    @Test
    void sayInChatTest() throws InvalidNameException {
        Engine engine = new Engine();
        engine.startGame();
        Game game = engine.getGame();
        String content = "content test for sayInChat";
        Player sender = new Player("Popi", 1);
        Player recipient = new Player("Pipo", 2);
        ArrayList<Player> recipients = new ArrayList<>();
        recipients.add(recipient);
        Message messageTest = new Message(sender, content);
        messageTest.setRecipients(recipients);
        engine.sayInChat(sender, content, recipients);

        game.getChat().getChat().forEach((element) -> assertEqualsMessage(element, messageTest));

    }

    @Test
    void fillBoard() {
    }
}