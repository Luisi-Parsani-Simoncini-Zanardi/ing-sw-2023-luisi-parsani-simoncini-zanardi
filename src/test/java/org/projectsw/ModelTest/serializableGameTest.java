package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Controller.Engine;
import org.projectsw.Model.Chat;
import org.projectsw.Model.Player;
import org.projectsw.Model.SerializableGame;
import org.projectsw.TestUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class serializableGameTest extends TestUtils {

    @Test
    void SerializableGame() {
        Chat chat = new Chat();
        SerializableGame ser = new SerializableGame("0", chat, "pippo");
        assertEquals(ser.getChat(), chat.getMessages());
        assertEquals(ser.getAlphanumericID(), "0");
        assertEquals(ser.getClientNickname(), "pippo");
    }

    @Test
    void gettersTests() {
        Engine engine = new Engine();
        engine.saveFileFound();
        engine.getGame().initializeGame(2);
        Player player = new Player("pippo", 0);
        Player player1 = new Player("lupus", 1);
        engine.getGame().getPlayers().add(player);
        engine.getGame().getPlayers().add(player1);
        engine.getGame().setCurrentPlayer(player);
        SerializableGame ser = new SerializableGame("0", engine.getGame(), "pippo");
        assertEquals(ser.getChat(), new Chat().getMessages());
        assertEquals(ser.getGameBoard(), engine.getGame().getBoard().getBoard());
        assertEquals(ser.getAlphanumericID(), "0");
        assertEquals(ser.getPlayerShelf(), engine.getGame().getCurrentPlayer().getShelf().getShelf());
        assertEquals(ser.getPlayerName(), "pippo");
        assertNull(ser.getBool());
        assertNull(ser.getInteger());
        assertNull(ser.getClientNickname());
        assertEquals(ser.getSelectablePoints(), new ArrayList<Point>());
        assertEquals(ser.getTemporaryPoints(), new ArrayList<Point>());
        assertEquals(ser.getTemporaryTiles().size(), 0);
        assertEquals(Arrays.stream(ser.getPlayerPersonalGoal()).count(), Arrays.stream(engine.getGame().getCurrentPlayer().getPersonalGoal().getPersonalGoal()).count());
        HashMap<String, Integer> map = new HashMap<>();
        map.put("pippo", 0);
        map.put("lupus", 0);
        assertEquals(ser.getResults(), map);
        assertNull(ser.getNameColors());
        assertNull(ser.getAllShelves());
        assertEquals(ser.getCommonGoalDesc().get(0), engine.getGame().getCommonGoals().get(0).getStrategy().getDescription());
        assertNull(ser.getMessage());
    }
}
