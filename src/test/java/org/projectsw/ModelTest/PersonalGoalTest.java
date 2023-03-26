package org.projectsw.ModelTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.projectsw.Model.PersonalGoal;
import org.projectsw.Model.TilesEnum;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    /**
     * Clean the list of used codes before each test
     */
    @BeforeEach
    void codesCleaner(){
        PersonalGoal.cleanUsedCodes();
    }

    /**
     * Test if the empty constructor returns a correct matrix of EMPTY tiles
     */
    @Test
    void emptyIntegrityTest(){
        PersonalGoal personalGoal = new PersonalGoal();
        assertEquals(6,personalGoal.getPersonalGoal().length);
        assertEquals(5,personalGoal.getPersonalGoal()[0].length);
        for(int j=0;j<6;j++){
            for(int h=0;h<5;h++){
                assertEquals(personalGoal.getPersonalGoal()[j][h], TilesEnum.EMPTY);
            }
        }
    }

    /**
     * Test if the constructor returns a correct and full matrix of tiles
     */
    @Test
    void integrityTest(){
        for(int i=0;i<12;i++){
            int cats = 0, books = 0, games = 0, frames = 0, plants = 0, trophies = 0, empty = 0;
            PersonalGoal personalGoal = new PersonalGoal(i);
            assertEquals(6,personalGoal.getPersonalGoal().length);
            assertEquals(5,personalGoal.getPersonalGoal()[0].length);
            for(int j=0;j<6;j++){
                for(int h=0;h<5;h++){
                    TilesEnum tile = personalGoal.getPersonalGoal()[j][h];
                    if(tile == TilesEnum.CATS) cats++;
                    if(tile == TilesEnum.FRAMES) frames++;
                    if(tile == TilesEnum.GAMES) games++;
                    if(tile == TilesEnum.TROPHIES) trophies++;
                    if(tile == TilesEnum.PLANTS) plants++;
                    if(tile == TilesEnum.BOOKS) books++;
                    if(tile == TilesEnum.EMPTY) empty++;
                    assertTrue(tile.equals(TilesEnum.CATS)||
                            tile.equals(TilesEnum.FRAMES)||
                            tile.equals(TilesEnum.BOOKS)||
                            tile.equals(TilesEnum.GAMES)||
                            tile.equals(TilesEnum.TROPHIES)||
                            tile.equals(TilesEnum.PLANTS)||
                            tile.equals(TilesEnum.EMPTY));
                }
            }
            assertEquals(1,cats);
            assertEquals(1,frames);
            assertEquals(1,games);
            assertEquals(1,trophies);
            assertEquals(1,plants);
            assertEquals(1,books);
            assertEquals(24,empty);
        }
    }

    /**
     * Tests if the constructor of the PersonalGoal throws correctly the IllegalArgumentException for a code
     * already used, even if this is setted with an exernal list
     */
    @Test
    void testInvalidPersonaGoalCodeUsed() {
        new PersonalGoal(0);
        assertThrows(IllegalArgumentException.class, () -> new PersonalGoal(0));
        PersonalGoal.cleanUsedCodes();
        assertTrue(PersonalGoal.getUsedCodes().isEmpty());
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        PersonalGoal.setUsedCodes(list);
        assertThrows(IllegalArgumentException.class, () -> new PersonalGoal(0));
    }

    /**
     * Tests if the returned personalGoal the correct one
     */
    @Test
    void testGetPersonalGoal(){
        PersonalGoal personalGoal = new PersonalGoal(0);
        assertTrue(personalGoal.getPersonalGoal()[3][1].equals(TilesEnum.GAMES)&&
                    personalGoal.getPersonalGoal()[0][0].equals(TilesEnum.PLANTS)&&
                    personalGoal.getPersonalGoal()[0][2].equals(TilesEnum.FRAMES)&&
                    personalGoal.getPersonalGoal()[1][4].equals(TilesEnum.CATS)&&
                    personalGoal.getPersonalGoal()[2][3].equals(TilesEnum.BOOKS)&&
                    personalGoal.getPersonalGoal()[5][2].equals(TilesEnum.TROPHIES));
    }

    /**
     * tests if the set, get and clean methods of usedCodes work correctly
     */
    @Test
    void testSetGetCleanUsedCodes() {
        List<Integer> tmp = new ArrayList<>();
        tmp.add(10);
        tmp.add(30);
        tmp.add(100);
        PersonalGoal.setUsedCodes(tmp);
        List<Integer> tmp2;
        tmp2 = PersonalGoal.getUsedCodes();
        assertEquals(100, (int) tmp2.get(2));
        assertEquals(30, (int) tmp2.get(1));
        assertEquals(10, (int) tmp2.get(0));
        PersonalGoal.cleanUsedCodes();
        assertTrue(PersonalGoal.getUsedCodes().isEmpty());

    }

}