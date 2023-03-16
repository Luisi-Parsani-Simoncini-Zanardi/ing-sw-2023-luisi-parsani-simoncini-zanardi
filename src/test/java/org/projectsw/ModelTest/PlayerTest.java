package org.projectsw.ModelTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Model.PersonalGoal;
import org.projectsw.Model.Player;
import org.projectsw.Model.Shelf;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getPosition() {
        Player player = new Player("Lorenzo", 2);
        assertEquals(2,player.getPosition());
    }

    @Test
    void getNickname() {
        Player player = new Player("Paolo", 2);
        assertEquals("Paolo",player.getNickname());
    }

    @Test
    void setPoints() {
        Player player = new Player("Luca", 2);
        player.setPoints(999);
        assertEquals(999, player.getPoints());
    }

    @Test
    void getPoints() {
        Player player = new Player("Davide", 3);
        player.setPoints(57);
        assertEquals(57, player.getPoints());
    }

    @Test
    void setShelf() {
        Shelf shelf1 = new Shelf();
        Player player = new Player("Shelfie", 1);
        player.setShelf(shelf1);
        assertEquals(shelf1, player.getShelf());
    }

    @Test
    void getShelf() {
        Shelf shelf1 = new Shelf();
        Player player = new Player("Attila", 4);
        player.setShelf(shelf1);
        assertEquals(shelf1, player.getShelf());
    }

    @Test
    void setPersonalGoal() {
        Player player = new Player("Morfeo", 1);
        PersonalGoal personal1 = new PersonalGoal(1);
        player.setPersonalGoal(personal1);
        assertEquals(personal1, player.getPersonalGoal());

        PersonalGoal personal2 = new PersonalGoal(2);
        player.setPersonalGoal(personal2);
        assertEquals(personal2, player.getPersonalGoal());

        PersonalGoal personal3 = new PersonalGoal(3);
        player.setPersonalGoal(personal3);
        assertEquals(personal3, player.getPersonalGoal());

        PersonalGoal personal4 = new PersonalGoal(4);
        player.setPersonalGoal(personal4);
        assertEquals(personal4, player.getPersonalGoal());

        PersonalGoal personal5 = new PersonalGoal(5);
        player.setPersonalGoal(personal5);
        assertEquals(personal5, player.getPersonalGoal());

        PersonalGoal personal6 = new PersonalGoal(6);
        player.setPersonalGoal(personal6);
        assertEquals(personal6, player.getPersonalGoal());

        PersonalGoal personal7 = new PersonalGoal(7);
        player.setPersonalGoal(personal7);
        assertEquals(personal7, player.getPersonalGoal());

        PersonalGoal personal8 = new PersonalGoal(8);
        player.setPersonalGoal(personal8);
        assertEquals(personal8, player.getPersonalGoal());

        PersonalGoal personal9 = new PersonalGoal(9);
        player.setPersonalGoal(personal9);
        assertEquals(personal9, player.getPersonalGoal());

        PersonalGoal personal10 = new PersonalGoal(10);
        player.setPersonalGoal(personal10);
        assertEquals(personal10, player.getPersonalGoal());

        PersonalGoal personal11 = new PersonalGoal(11);
        player.setPersonalGoal(personal11);
        assertEquals(personal11, player.getPersonalGoal());

        PersonalGoal personal12 = new PersonalGoal(12);
        player.setPersonalGoal(personal12);
        assertEquals(personal12, player.getPersonalGoal());
    }

    @Test
    void getPersonalGoal() {
        PersonalGoal personal = new PersonalGoal(4);
        Player player = new Player("Riccardo", 3);
        player.setPersonalGoal(personal);
        assertEquals(personal, player.getPersonalGoal());
    }

    @Test
    void addTile() {
    }

    @Test
    void getTiles() {
    }

    @Test
    void selectTile() {
    }
}