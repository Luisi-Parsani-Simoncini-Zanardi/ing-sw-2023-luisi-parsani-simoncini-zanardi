package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    //test that the redeem code is the right one
    @Test
    void getGoalCode() {
        CommonGoal common = new CommonGoal(3);
        assertEquals(3, common.getGoalCode());
    }

    //test that the method increase correctly the redeemedNumber attribute
    //test that redeemedNumber can't be more than 4
    @Test
    void increaseRedeemedNumber() {
        CommonGoal common = new CommonGoal(3);
        assertEquals(0, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(1, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(2, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(3, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(4, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
            assertEquals("There are no more points to redeem",e.getMessage());
        }

        assertEquals(4, common.getRedeemedNumber());
    }

    //test that the method getRedeemedNumber returns the correct value of redeemedNumber attribute
    @Test
    void getRedeemedNumber() {
        CommonGoal common = new CommonGoal(3);
        assertEquals(0, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(1, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(2, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(3, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
        }
        assertEquals(4, common.getRedeemedNumber());

        try {
            common.increaseRedeemedNumber();
        }catch(Exception e){
            assertEquals("There are no more points to redeem",e.getMessage());
        }

        assertEquals(4, common.getRedeemedNumber());
    }
}