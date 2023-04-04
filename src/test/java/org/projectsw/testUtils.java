package org.projectsw;

import org.projectsw.Model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testUtils {


    public void assertEqualsTile (Tile tileTest, Tile tileAssert) {
        assertEquals(tileTest.getTile(), tileAssert.getTile());
        assertEquals(tileTest.getImageNumber(), tileAssert.getImageNumber());
    }

    /**
     * Checks if two Shelf objects are identical.
     * @param shelfTest a test Shelf object
     * @param shelfAssert another test Shelf object
     */
    public void assertEqualsShelf (Shelf shelfTest, Shelf shelfAssert) {
        for(int i=0; i<shelfTest.getShelf().length; i++) {
            for(int j=0; j<shelfTest.getShelf()[i].length; j++){
                assertEqualsTile(shelfTest.getShelf()[i][j], shelfAssert.getShelf()[i][j]);
            }
        }
    }

    /**
     * Checks if two personalGoal objects are identical.
     * @param personalGoalTest a test PersonalGoal object
     * @param personalGoalAssert another test PersonalGoal object
     */
    public void assertEqualsPersonalGoal (PersonalGoal personalGoalTest, PersonalGoal personalGoalAssert) {
        for(int i=0; i<personalGoalTest.getPersonalGoal().length-1; i++){
            for(int j=0; j<personalGoalTest.getPersonalGoal()[i].length-1; j++) {
                assertEquals(personalGoalTest.getPersonalGoal()[i][j], personalGoalAssert.getPersonalGoal()[i][j]);
            }
        }
        for(int i = 0; i< PersonalGoal.getUsedCodes().size(); i++) {
            assertEquals(PersonalGoal.getUsedCodes().get(i), PersonalGoal.getUsedCodes().get(i));
        }
    }

    /**
     * Checks if two Player objects are identical.
     * @param playerTest a test Player object
     * @param playerAssert another test Player object
     */
    public void assertEqualsPlayer (Player playerTest, Player playerAssert) {
        assertEquals(playerTest.getNickname(), playerAssert.getNickname());
        assertEquals(playerTest.getPosition(), playerAssert.getPosition());
        assertEquals(playerTest.getPoints(), playerAssert.getPoints());
        assertEqualsShelf(playerTest.getShelf(), playerAssert.getShelf());
        assertEqualsPersonalGoal(playerTest.getPersonalGoal(), playerAssert.getPersonalGoal());
        assertEquals(playerTest.isCommonGoalRedeemed(0), playerAssert.isCommonGoalRedeemed(0));
        assertEquals(playerTest.isCommonGoalRedeemed(1), playerAssert.isCommonGoalRedeemed(1));
    }


    /**
     * Check if two Bag objects are identical.
     * @param bagTest a test Bag object
     * @param bagAssert another test Bag object
     */
    public void assertEqualsBag (Bag bagTest, Bag bagAssert) {
        for(int i=0; i<bagTest.getBag().size(); i++) {
            assertEqualsTile(bagTest.getBag().get(i), bagAssert.getBag().get(i));
        }
    }

    /**
     * check if two Board objects are identical
     * @param boardTest a test Board object
     * @param boardAssert another test Board object
     */
    public void assertEqualsBoard (Board boardTest, Board boardAssert) {
        for(int i=0; i<boardTest.getBoard().length; i++) {
            for(int j=0; j<boardTest.getBoard()[i].length; j++){
                assertEqualsTile(boardTest.getBoard()[i][j], boardAssert.getBoard()[i][j]);
            }
        }
        assertEquals(boardTest.isEndGame(), boardAssert.isEndGame());
        assertEqualsBag(boardTest.getBag(), boardAssert.getBag());
    }

    /**
     * check if two Message objects are identical
     * @param messageTest
     * @param messageAssert
     */
    public void assertEqualsMessage (Message messageTest, Message messageAssert) {
        assertEquals(messageTest.getContent(), messageAssert.getContent());
        assertEqualsPlayer(messageTest.getSender(), messageAssert.getSender());
        for (int i = 0; i<messageTest.getRecipients().size(); i++) {
            assertEqualsPlayer(messageTest.getRecipients().get(i), messageAssert.getRecipients().get(i));
        }
    }

    /**
     * check if two Chat objects are identical
     * @param chatTest a test Chat object
     * @param chatAssert another test Chat object
     */
    public void assertEqualsChat (Chat chatTest, Chat chatAssert) {
        for(int i=0; i<chatTest.getChat().size(); i++) {
            assertEquals(chatTest.getChat().get(i), chatAssert.getChat().get(i));
        }
    }

}
