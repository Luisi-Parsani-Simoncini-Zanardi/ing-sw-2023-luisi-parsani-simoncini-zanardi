package org.projectsw.ControllerTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Exceptions.InvalidNameException;
import org.projectsw.Model.Game;
import org.projectsw.Controller.Engine;
import org.projectsw.Model.Message;
import org.projectsw.Model.Player;
import org.projectsw.testUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest extends testUtils {

    @Test
    void startGame() {
    }

    @Test
    void playerJoin() {
    }

    @Test
    void selctTiles() {
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

    @Test
    void sayInChatTest() throws InvalidNameException {

        Engine engine = new Engine();
        Game game = engine.getGame();
        String content = "content test for sayInChat";
        Player sender = new Player("Popi", 1);
        Player recipient = new Player("Pipo", 2);
        ArrayList<Player> recipients = new ArrayList<>();
        recipients.add(recipient);
        Message messageTest = new Message(sender, content);
        messageTest.setRecipients(recipients);
        engine.sayInChat(sender, content, recipients);

        game.getChat().getChat().forEach((element) -> {
            assertEqualsMessage(element, messageTest);
        });

    }

    @Test
    void fillBoard() {
    }
}