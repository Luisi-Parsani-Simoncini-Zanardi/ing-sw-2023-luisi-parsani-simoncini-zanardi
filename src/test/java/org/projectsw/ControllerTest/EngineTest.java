package org.projectsw.ControllerTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Controller.Engine;
import org.projectsw.Exceptions.*;
import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.RowColumn;
import org.projectsw.TestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.projectsw.Model.TilesEnum.*;

import java.util.ArrayList;

class EngineTest extends TestUtils {

    /**
     * Tests the creation of a game in LOBBY state, also checking if the filling of the lobby
     * works correctly and if the state of the game changes after the last join
     */
    @Test
    void startingGameSimulation() throws FirstJoinFailedException, JoinFailedException {
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

    /**
     * Tests if firstPlayerJoin correctly throws the FirstJoinFailedException when is passed a too low number of players
     */
    @Test
    void invalidNumberOfPlayersTooLowJoinTest() {
        Engine engine = new Engine();
        assertThrows(FirstJoinFailedException.class, () -> engine.firstPlayerJoin("Davide",1));
    }

    /**
     * Tests if firstPlayerJoin correctly throws the FirstJoinFailedException when is passed a too big number of players
     */
    @Test
    void invalidNumberOfPlayersTooBigJoinTest() {
        Engine engine = new Engine();
        assertThrows(FirstJoinFailedException.class, () -> engine.firstPlayerJoin("Davide",5));
    }

    @Test
    void invalidNicknameAlreadyUsedTest() throws FirstJoinFailedException{
        Engine engine = new Engine();
        engine.firstPlayerJoin("Davide",2);
        assertThrows(JoinFailedException.class, () -> engine.playerJoin("Davide"));
    }

    @Test
    void invalidJoinAttemptTest() throws FirstJoinFailedException,JoinFailedException{
        Engine engine = new Engine();
        engine.firstPlayerJoin("Davide",2);
        engine.playerJoin("Lore");
        assertThrows(JoinFailedException.class, () -> engine.playerJoin("Lollo"));
    }

    @Test
    void selectTiles() {
    }

    @Test
    void placeTiles() {
    }

    /**
     * Tests if the players get points for CommonGoals they have achieved.
     * Tests if no points are awarded if a CommonGoal has already been achieved by a player.
     * Tests if the redeemedNumber is updated if the CommonGoal achieved.
     */
    @Test
    void checkCommonGoals() {
        Engine engine = new Engine();
        try {
            engine.firstPlayerJoin("Davide", 2);
            engine.playerJoin("Lorenzo");
        }catch(Exception ignore){}
        //manually setting CommonGoal otherwise they are random and the test result will be influenced
        CommonGoal commonGoal1 = new CommonGoal(new RowColumn(2));
        CommonGoal commonGoal2 = new CommonGoal(new RowColumn(7));
        ArrayList<CommonGoal> common= new ArrayList<>();
        common.add(0,commonGoal1);
        common.add(1,commonGoal2);
        engine.getGame().setCommonGoals(common);

        //setting custom shelf for both players
        Shelf shelf = new Shelf();
        Shelf shelf1 = new Shelf();
        for(int i=0; i<2; i++){
            try{
                shelf.insertTiles(new Tile(TilesEnum.CATS,0),0,i);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES,0),1,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS,0),2,i);
                shelf.insertTiles(new Tile(PLANTS,0),3,i);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES,0),4,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES,0),5,i);
                shelf1.insertTiles(new Tile(TilesEnum.CATS,0),0,i);
                shelf1.insertTiles(new Tile(TilesEnum.TROPHIES,0),1,i);
                shelf1.insertTiles(new Tile(TilesEnum.BOOKS,0),2,i);
                shelf1.insertTiles(new Tile(PLANTS,0),3,i);
                shelf1.insertTiles(new Tile(TilesEnum.FRAMES,0),4,i);
                shelf1.insertTiles(new Tile(TilesEnum.GAMES,0),5,i);
            }catch(Exception ignore){}
        }
        engine.getGame().getPlayers().get(0).setShelf(shelf);
        for(int i=0; i<5; i++){
            try{
                shelf1.insertTiles(new Tile(TilesEnum.BOOKS,0),2,i);
                shelf1.insertTiles(new Tile(PLANTS,0),3,i);
                shelf1.insertTiles(new Tile(TilesEnum.FRAMES,0),4,i);
                shelf1.insertTiles(new Tile(TilesEnum.GAMES,0),5,i);
            }catch(Exception ignore){}
        }
        engine.getGame().getPlayers().get(1).setShelf(shelf1);

        //behaviour of the method tested
        engine.getGame().setCurrentPlayer(engine.getGame().getPlayers().get(0));
        engine.checkCommonGoals();
        engine.getGame().setCurrentPlayer(engine.getGame().getPlayers().get(1));
        engine.checkCommonGoals();
        engine.checkCommonGoals();

        assertEquals(8, engine.getGame().getPlayers().get(0).getPoints());
        assertEquals(14, engine.getGame().getPlayers().get(1).getPoints());
        assertEquals(2, engine.getGame().getCommonGoals().get(0).getRedeemedNumber());
        assertEquals(3, engine.getGame().getCommonGoals().get(1).getRedeemedNumber());
        assertTrue(engine.getGame().getPlayers().get(0).isCommonGoalRedeemed(0));
        assertFalse(engine.getGame().getPlayers().get(0).isCommonGoalRedeemed(1));
        assertTrue(engine.getGame().getPlayers().get(1).isCommonGoalRedeemed(0));
        assertTrue(engine.getGame().getPlayers().get(1).isCommonGoalRedeemed(1));
    }

    @Test
    void testCheckPersonalGoal() throws EmptyTilesException, UnusedTilesException {
        Engine engine = new Engine();
        try {
            engine.firstPlayerJoin("Davide", 2);
        }catch(Exception ignore){}
        engine.getGame().getCurrentPlayer().setPersonalGoal(new PersonalGoal(0));
        TilesEnum[][] shelf0 = {
                {PLANTS, EMPTY, FRAMES, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, CATS},
                {EMPTY, EMPTY, EMPTY, BOOKS, EMPTY},
                {EMPTY, GAMES, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, TROPHIES, EMPTY, EMPTY}
        };
        Shelf shelf = new Shelf();
        for (int i = 0; i < shelf0.length; i++) {
            for (int j = 0; j < shelf0[i].length; j++) {
                if (shelf0[i][j]!=EMPTY)
                shelf.insertTiles(new Tile(shelf0[i][j], 0), i, j);
            }
        }
        engine.getGame().getCurrentPlayer().setShelf(shelf);
        engine.checkPersonalGoal();
        assertEquals(engine.getGame().getCurrentPlayer().getPoints(), 12);

        TilesEnum[][] shelf1 = {
                {FRAMES, EMPTY, FRAMES, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, CATS},
                {EMPTY, EMPTY, PLANTS, BOOKS, EMPTY},
                {EMPTY, BOOKS, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {BOOKS, CATS, CATS, EMPTY, EMPTY}
        };
        for (int i = 0; i < shelf1.length; i++) {
            for (int j = 0; j < shelf1[i].length; j++) {
                if (shelf1[i][j]!=EMPTY)
                    shelf.insertTiles(new Tile(shelf1[i][j], 0), i, j);
            }
        }
        engine.getGame().getCurrentPlayer().setShelf(shelf);
        engine.getGame().getCurrentPlayer().setPoints(0);
        engine.checkPersonalGoal();
        assertEquals(engine.getGame().getCurrentPlayer().getPoints(), 4);

        TilesEnum[][] shelf2 = {
                {CATS, FRAMES, GAMES, BOOKS, PLANTS},
                {GAMES, TROPHIES, BOOKS, CATS, FRAMES},
                {PLANTS, BOOKS, TROPHIES, FRAMES, CATS},
                {FRAMES, GAMES, TROPHIES, PLANTS, CATS},
                {TROPHIES, CATS, FRAMES, PLANTS, BOOKS},
                {BOOKS, PLANTS, CATS, GAMES, TROPHIES}
        };
        for (int i = 0; i < shelf2.length; i++) {
            for (int j = 0; j < shelf2[i].length; j++) {
                if (shelf2[i][j]!=EMPTY)
                    shelf.insertTiles(new Tile(shelf2[i][j], 0), i, j);
            }
        }
        engine.getGame().getCurrentPlayer().setShelf(shelf);
        engine.getGame().getCurrentPlayer().setPoints(0);
        engine.checkPersonalGoal();
        assertEquals(engine.getGame().getCurrentPlayer().getPoints(), 1);

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

    //TODO: sistemare questo test, dopo il cambiamento di startGame non funziona piu'
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

    //TODO: capire perche' non funziona (e successivamente scrivere casi di test)
    @Test
    void checkFillBoardFalse() {
        Engine engine = new Engine();
        Tile[][] matrix = {
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0)},
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0)},
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0)},
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0)},
                {new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0)},
                {new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0)},
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0)},
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0)},
                {new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.EMPTY, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0), new Tile(TilesEnum.UNUSED, 0)}};

        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.updateBoard(matrix[i][j], i, j);
            }
        }

        assertEquals(board.getBoard(), matrix);

    }

    /**
     * Test if endGame switches from false to true and that only the first player to complete his shelf gets the point
     */
    @Test
    public void checkEndGame(){
        Engine engine = new Engine();
        try {
            engine.firstPlayerJoin("Davide", 2);
            engine.playerJoin("Lorenzo");
        }catch(Exception ignore){}
        Shelf shelf = new Shelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++ )
                try{
                    shelf.insertTiles(new Tile(TilesEnum.CATS,0),i,j);
                }catch(Exception ignore){}
        }
        assertFalse(engine.getGame().getBoard().isEndGame());

        engine.getGame().getPlayers().get(0).setShelf(shelf);
        engine.getGame().getPlayers().get(1).setShelf(shelf);
        engine.checkEndGame();

        assertTrue(engine.getGame().getBoard().isEndGame());

        engine.getGame().setCurrentPlayer(engine.getGame().getPlayers().get(1));
        engine.checkEndGame();

        assertEquals(1,engine.getGame().getPlayers().get(0).getPoints());
        assertEquals(0,engine.getGame().getPlayers().get(1).getPoints());
    }
}