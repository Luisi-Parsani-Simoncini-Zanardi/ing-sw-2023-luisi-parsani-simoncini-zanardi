package org.projectsw;

import org.projectsw.Model.*;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {


    /**
     * Checks if two Point objects are identical.
     * @param pointTest a test Point object
     * @param pointAssert another Point Tile object
     */
    public void assertEqualsPoint (Point pointTest, Point pointAssert) {
        assertEquals(pointTest.getX(), pointAssert.getX());
        assertEquals(pointTest.getY(), pointAssert.getY());
    }

    /**
     * Checks if two Tile objects are identical.
     * @param tileTest a test Tile object
     * @param tileAssert another test Tile object
     */
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
    }

    /**
     * check if two Chat objects are identical
     * @param chatTest a test Chat object
     * @param chatAssert another test Chat object
     */
    public void assertEqualsChat (Chat chatTest, Chat chatAssert) {
        for(int i=0; i<chatTest.getMessages().size(); i++) {
            assertEquals(chatTest.getMessages().get(i), chatAssert.getMessages().get(i));
        }
    }


    /**
     * check if two strategy objects are identical
     * @param strategyTest a test strategy object
     * @param strategyAssert another test strategy object
     */
    public void assertEqualsStrategy (CommonGoalStrategy strategyTest, CommonGoalStrategy strategyAssert) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertEquals(strategyTest.getNumObjects(), strategyAssert.getNumObjects());
        assertEquals(strategyTest.getStrategyCode(), strategyAssert.getStrategyCode());
    }

    /**
     * check if two commonGoal objects are identical
     * @param commonGoalTest a test commonGoal object
     * @param commonGoalAssert another test commonGoal object
     */
    public void assertEqualsCommonGoal (CommonGoal commonGoalTest, CommonGoal commonGoalAssert) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertEquals(commonGoalTest.getRedeemedNumber(), commonGoalAssert.getRedeemedNumber());
        assertEqualsStrategy(commonGoalTest.getStrategy(), commonGoalAssert.getStrategy());
    }
}
