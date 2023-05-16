package org.projectsw.Model;

import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import org.projectsw.Exceptions.MaxTemporaryTilesExceededException;
import java.util.ArrayList;
import java.util.Objects;
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
    private final ArrayList<Boolean> commonGoalRedeemed;
    private boolean isActive;

    /**
     * Constructs a new player with the given nickname and position.
     * Points are set 0 by default.
     * Shelf is instanced as an empty shelf.
     * The personal goal is assigned randomly.
     * @param nickname the nickname of the player
     * @param position the position of the player
     */
    public Player(String nickname, int position) {
        this.nickname=nickname;
        this.position=position;
        points=0;
        shelf = new Shelf();
        //BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR
        if (Objects.equals(nickname, "b4ckd00rsh3lf4lm0stfull"))
            shelf = new Shelf(4);
        //BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR
        personalGoal = tryPersonalGoal();
        temporaryTiles = new ArrayList<>();
        commonGoalRedeemed = new ArrayList<>();
        commonGoalRedeemed.add(false);
        commonGoalRedeemed.add(false);
        isActive=true;
    }

    /**
     * Returns true if the player is connected to the server, false otherwise.
     * @return true if the player is connected to the server, false otherwise
     */
    public boolean getIsActive(){
        return this.isActive;
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
    public ArrayList<Tile> getTemporaryTiles() {
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
     * Returns whether the number i common goal has been redeemed or not.
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
     * Sets the temporaryTiles of the player to the given temporaryTiles list.
     * @param temporaryTiles the temporaryTiles list to set for the player
     */
    public void setTemporaryTiles(ArrayList<Tile> temporaryTiles) throws MaxTemporaryTilesExceededException {
        if(temporaryTiles.size() > Config.maximumTilesPickable) throw new MaxTemporaryTilesExceededException();
        this.temporaryTiles = temporaryTiles;
    }

    /**
     * Sets the commonGoalRedeemed at the desired index to the desired status.
     * @param index the index of the position to be set
     * @param status the status to be set
     */
    public void setCommonGoalRedeemed(int index, Boolean status) {
        this.commonGoalRedeemed.set(index, status);
    }

    public void setIsActive(boolean state){this.isActive=state; }

    /**
     * Adds the given tile to the player's temporary tiles.
     * @param tile the tile to be added to the player's temporary tiles
     * @throws MaxTemporaryTilesExceededException if the player already has the maximum number of tiles (i.e., 3)
     */
    public void addTemporaryTile(Tile tile) throws MaxTemporaryTilesExceededException {
        if(Config.maximumTilesPickable <= temporaryTiles.size()) throw new MaxTemporaryTilesExceededException("Maximum number of tiles reached");
        else if(tile.getTile() == TilesEnum.EMPTY || tile.getTile() == TilesEnum.UNUSED) throw new IllegalArgumentException("You can't add an EMPTY tile");
        else temporaryTiles.add(tile);
    }

    /**
     *Removes and returns the tile at the specified index from the player's temporary tile list.
     *@param i the index of the tile to select and remove
     *@return the selected tile
     *@throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= temporaryTiles.size())
     */
    public Tile selectTemporaryTile(int i) throws IndexOutOfBoundsException {
        if(i < 0 || i >= temporaryTiles.size()) throw new IndexOutOfBoundsException();
        Tile temp = temporaryTiles.get(i);
        temporaryTiles.remove(i);
        return temp;
    }

    /**
     * Tries to instance a PersonalGoal object, and iterates in case of IllegalArgumentException,
     * each time generating a new random code.
     * @return a PersonalGoal which isn't used by any of the others players
     */
    public PersonalGoal tryPersonalGoal(){
        PersonalGoal generatedPersonalGoal;
        //if(PersonalGoal.getUsedCodes().size() == 12) return null;
        try {
            Random random = new Random();
            int randomNumber = random.nextInt(Config.numberOfPersonalGoals);
            generatedPersonalGoal = new PersonalGoal(randomNumber);
        } catch (IllegalArgumentException e) {
            return tryPersonalGoal();
        }
        return generatedPersonalGoal;
    }

    /**
     * Clears the temporaryTiles array list.
     */
    public void clearTemporaryTiles(){
        temporaryTiles.clear();
    }

    /**
     * Removes the given tile from the temporaryTiles array list.
     * @param tile the tile to be removed from the array
     */
    public void removeTemporaryTile(Tile tile){
        temporaryTiles.remove(tile);
    }
}
