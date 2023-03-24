package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.PersonalGoal;
import org.projectsw.Model.TilesEnum;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    /**
     * tests if the method returns the correct personalGoal
     */
    @Test
    void testGetPersonalGoal(){
        PersonalGoal personalGoal = new PersonalGoal(0);
        PersonalGoal.cleanUsedCodes();
        assertTrue(personalGoal.getPersonalGoal()[3][1].equals(TilesEnum.GAMES)&&
                    personalGoal.getPersonalGoal()[0][0].equals(TilesEnum.PLANTS)&&
                    personalGoal.getPersonalGoal()[0][2].equals(TilesEnum.FRAMES)&&
                    personalGoal.getPersonalGoal()[1][4].equals(TilesEnum.CATS)&&
                    personalGoal.getPersonalGoal()[2][3].equals(TilesEnum.BOOKS)&&
                    personalGoal.getPersonalGoal()[5][2].equals(TilesEnum.TROPHIES));
    }

    /**
     * test if the constructor returns a correct and full matrix of tiles
     */
    @Test
    void integrityTest(){
        for(int i=0;i<12;i++){
            PersonalGoal personalGoal = new PersonalGoal(i);
            for(int j=0;j<6;j++){
                for(int h=0;h<5;h++){
                    TilesEnum tile = personalGoal.getPersonalGoal()[j][h];
                    assertTrue(tile.equals(TilesEnum.CATS)||
                                tile.equals(TilesEnum.FRAMES)||
                                tile.equals(TilesEnum.BOOKS)||
                                tile.equals(TilesEnum.GAMES)||
                                tile.equals(TilesEnum.TROPHIES)||
                                tile.equals(TilesEnum.PLANTS)||
                                tile.equals(TilesEnum.EMPTY));
                }
            }
        }
        PersonalGoal.cleanUsedCodes();
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