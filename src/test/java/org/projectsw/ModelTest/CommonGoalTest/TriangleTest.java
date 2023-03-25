package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.Triangle;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * tests if the algorithm successfully checks that the shelf meets the requirements of the chosen CommonGoal in
     * every possible combination
     */
    @Test
    void checkTrue() {
        CommonGoal triangle = new Triangle();
        Shelf shelf1 = new Shelf();
        for(int i=0; i<5; i++) {
            for (int j=5; i < j ; j--){
                try {
                    shelf1.insertTiles(new Tile(TilesEnum.CATS,0), j, i);
                } catch (Exception e) {}
            }
        }
        assertTrue(triangle.check(shelf1));

        Shelf shelf2 = new Shelf();
        for(int i=0; i<5; i++) {
            for (int j=5; i-1 < j ; j--){
                try {
                    shelf2.insertTiles(new Tile(TilesEnum.CATS,0), j, i);
                } catch (Exception e) {}
            }
        }
        assertTrue(triangle.check(shelf2));

        Shelf shelf3 = new Shelf();
        for(int i=0; i<5; i++) {
            for (int j=5; j > 4-i ; j--){
                try {
                    shelf3.insertTiles(new Tile(TilesEnum.CATS,0), j, i);
                } catch (Exception e) {}
            }
        }
        assertTrue(triangle.check(shelf3));

        Shelf shelf4 = new Shelf();
        for(int i=0; i<5; i++) {
            for (int j=5; j > 3-i ; j--){
                try {
                    shelf4.insertTiles(new Tile(TilesEnum.CATS,0), j, i);
                } catch (Exception e) {}
            }
        }
        assertTrue(triangle.check(shelf4));
    }

    /**
     *     tests that the algorithm returns false when the shelf has wrong columns height
     */
    @Test
    void checkFalse() {
        CommonGoal triangle = new Triangle();
        Shelf shelf1 = new Shelf();
        for(int i=0; i<5; i++) {
            for (int j=5; i < j ; j--){
                try {
                    shelf1.insertTiles(new Tile(TilesEnum.CATS,0), j, i);
                } catch (Exception e) {}
            }
        }
        try {
            shelf1.insertTiles(new Tile(TilesEnum.CATS, 0), 4, 4);
        }catch(Exception e){}
        assertFalse(triangle.check(shelf1));
    }
}