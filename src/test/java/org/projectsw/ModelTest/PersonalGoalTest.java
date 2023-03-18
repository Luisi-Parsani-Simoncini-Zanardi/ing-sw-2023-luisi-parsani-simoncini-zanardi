package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.PersonalGoal;
import org.projectsw.Model.Tiles;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    //test that the method returns the right personalGoal
    @Test
    void getPersonalGoal() {
        PersonalGoal personal = new PersonalGoal(1);

        assertTrue(
        personal.getPersonalGoal()[3][1].equals(Tiles.GAMES)&&
        personal.getPersonalGoal()[0][0].equals(Tiles.PLANTS)&&
        personal.getPersonalGoal()[0][2].equals(Tiles.FRAMES)&&
        personal.getPersonalGoal()[1][4].equals(Tiles.CATS)&&
        personal.getPersonalGoal()[2][3].equals(Tiles.BOOKS)&&
        personal.getPersonalGoal()[5][2].equals(Tiles.TROPHIES));
    }
}