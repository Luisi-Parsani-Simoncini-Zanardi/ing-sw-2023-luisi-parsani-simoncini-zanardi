package org.projectsw.Model;

import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.MaximumTilesException;
import org.projectsw.Exceptions.UnusedTilesException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a player in the game.
 */
public class Player {
    private final int position;
    private final String nickname;
    private int points;
    private Shelf shelf;
    private PersonalGoal personalGoal;
    private ArrayList<Tile> temporaryTiles;
    private boolean personalGoalRedeemed;
    private ArrayList<Boolean> commonGoalRedeemed;

    /**
     * Constructs a new player with the given nickname and position.
     * points are set 0 as default
     * flags are set false as default
     * shelf is instanced as an empty shelf
     * one of the 12 possible personal goals is instanced by generating a random personal goal code and giving it as a parameter to the personalGoal constructor
     * @param nickname the nickname of the player
     * @param position the position of the player
     */
    public Player(String nickname, int position) {
        this.position=position;
        this.nickname=nickname;
        points=0;
        temporaryTiles = new ArrayList<>();
        personalGoalRedeemed = false;
        commonGoalRedeemed = new ArrayList<Boolean>();
        commonGoalRedeemed.add(false);
        commonGoalRedeemed.add(false);
        shelf = new Shelf();
        Random random = new Random();
        int randomNumber = random.nextInt(12);
        personalGoal = tryPersonalGoal(randomNumber);
    }

    /**
     * Returns the position of the player.
     * @return the position of the player
     */
    public int getPosition(){
        return position;
    }

    /**
     * Returns the nickname of the player.
     * @return the nickname of the player
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Returns the points scored by the player.
     * @return the points scored by the player
     */
    public int getPoints(){
        return points;
    }

    /**
     * Returns the shelf of the player.
     * @return the shelf of the player
     */
    public Shelf getShelf() {
        return shelf;
    }

    /**
     * Returns the temporary tiles of the player.
     * @return the temporary tiles of the player
     */
    public ArrayList<Tile> getTiles() {
        return temporaryTiles;
    }

    /**
     * Returns the personal goal of the player.
     * @return the personal goal of the player
     */
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Returns whether the personal goal has been redeemed or not
     * @return true if the personal goal has been redeemed, false otherwise
     */
    public boolean isPersonalGoalRedeemed(){
        return personalGoalRedeemed;
    }

    /**
     * Returns whether the number i common goal has been redeemed or not
     * @param i the index of the personalGoalArray element to be checked
     * @return true if the personal goal has been redeemed, false otherwise
     */
    public boolean isCommonGoalRedeemed(int i){
        return commonGoalRedeemed.get(i);
    }

    /**
     * Sets the shelf of the player to the given shelf.
     * @param shelf the shelf to set for the player
     */
    public void setShelf(Shelf shelf) {
        this.shelf=shelf;
    }

    /**
     * Sets the points scored by the player to the given points.
     * @param points the points to set for the player
     */
    public void setPoints(int points){
        this.points=points;
    }

    /**
     * Sets the personalGoal of the player to the given personalGoal.
     * @param personalGoal the personalGoal to set for the player
     */
    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal=personalGoal;
    }

    /**
     * Sets personalGoalRedeemed to the desired status
     * @param status the status to be assigned
     */
    public void setPersonalGoalRedeemed (boolean status){
        personalGoalRedeemed = status;
    }

    /**
     * Sets the number i element of the commonGoal array the desired status
     * @param status the status to be assigned
     * @param i the index of the personalGoalArray element to be assigned
     */
    public void setCommonGoalRedeemed (boolean status, int i){
        commonGoalRedeemed.set(i, status);
    }

    /**
     * Adds the given tile to the player's temporary tiles.
     * @param tiles the tile to add to the player's temporary tiles
     * @throws MaximumTilesException if the player already has the maximum number of tiles (i.e., 3)
     * @throws EmptyTilesException if the tile is empty
     * @throws UnusedTilesException if the tile is unused
     */
    public void addTile(Tile tiles) throws MaximumTilesException, EmptyTilesException, UnusedTilesException {

        if(temporaryTiles.size()>2){
            throw new MaximumTilesException("Maximum number of tiles reached");
        }else if(tiles.getTile() == TilesEnum.EMPTY){
            throw new EmptyTilesException("You can't add an EMPTY tile");
        }else if(tiles.getTile() == TilesEnum.UNUSED){
            throw new UnusedTilesException("You can't add an UNUSED tile");
        }else{
            temporaryTiles.add(tiles);
        }
    }

    /**
     *Removes and returns the tile at the specified index from the player's temporary tile list.
     *@param num the index of the tile to select and remove
     *@return the selected tile
     *@throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= temporaryTiles.size())
     */
    public Tile selectTile(int num){
        Tile temp;
        temp = temporaryTiles.get(num);
        temporaryTiles.remove(num);
        return temp;
    }

    /**
     * Tries to instance a PersonalGoal object, and iterates in case of IllegalArgumentException, each time generating a new random code
     * @param goalCode the unique code assigned to this player's goal card
     * @return a PersonalGoal object if the code is not used
     */
    public PersonalGoal tryPersonalGoal (int goalCode) {
        PersonalGoal generatedPersonalGoal = null;
        try {
            generatedPersonalGoal = new PersonalGoal(goalCode);
        } catch (IOException e) {
            System.out.println("Error opening the json file");
        } catch (IllegalArgumentException e) {
            Random random = new Random();
            int randomNumber = random.nextInt(12);
            return tryPersonalGoal(randomNumber);
        }
        return generatedPersonalGoal;
    }
}
