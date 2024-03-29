package org.projectsw.Model;

import com.google.gson.Gson;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Model.Enums.DataForGame;
import org.projectsw.Util.Config;

import java.util.*;

/**
 * Class to represent a player's personal goal card in the game.
 * It contains a 6x5 grid of TilesEnum.
 * Each player is assigned a unique goal code upon instantiation, which is used to generate
 * the corresponding goal card from a JSON file.
 */
public class PersonalGoal {
    private TilesEnum[][] personalGoal;
    private static List<Integer> usedCodes = new ArrayList<>(); //called codes

    private String[][][] personals = new DataForGame().getPersonals();

    /**
     * Constructs a new instance of the PersonalGoal class.
     * Initializes the personal goal array with empty tiles.
     */
    public PersonalGoal(){
        personalGoal = new TilesEnum[Config.shelfHeight][Config.shelfLength];
        for (int i = 0; i < Config.shelfHeight; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                personalGoal[i][j] = TilesEnum.EMPTY;
            }
        }
    }

    /**
     * Constructs a new PersonalGoal object with the given goal code.
     * @param goalCode the unique code assigned to this player's goal card
     * @throws IllegalArgumentException if the goal code has already been used by another player
     */
    public PersonalGoal(int goalCode){
            if (usedCodes.contains(goalCode))
                throw new IllegalArgumentException("Goal code already used.");
            else if (goalCode < 0 || goalCode > Config.numberOfPersonalGoals-1)
                throw new IllegalArgumentException("Invalid goal code");
            else usedCodes.add(goalCode);

            Gson gson = new Gson();
            // String[][][] tmpMatrix = gson.fromJson(new FileReader("./src/main/resources/PersonalGoals.json"), String[][][].class);

            personalGoal = new TilesEnum[Config.shelfHeight][Config.shelfLength];
            for (int i = 0; i < Config.shelfHeight; i++) {
                for (int j = 0; j < Config.shelfLength; j++) {
                    String str = personals[goalCode][i][j];
                    switch (str) {
                        case "EMPTY" -> personalGoal[i][j] = TilesEnum.EMPTY;
                        case "PLANTS" -> personalGoal[i][j] = TilesEnum.PLANTS;
                        case "TROPHIES" -> personalGoal[i][j] = TilesEnum.TROPHIES;
                        case "GAMES" -> personalGoal[i][j] = TilesEnum.GAMES;
                        case "FRAMES" -> personalGoal[i][j] = TilesEnum.FRAMES;
                        case "CATS" -> personalGoal[i][j] = TilesEnum.CATS;
                        case "BOOKS" -> personalGoal[i][j] = TilesEnum.BOOKS;
                        default -> throw new IllegalArgumentException("Invalid tile value: " + str);
                    }
                }
            }
    }

    /**
     * Returns the matrix of TilesEnum values that represent the player's goal.
     * @return the 6x5 grid of TilesEnum
     */
    public TilesEnum[][] getPersonalGoal() { return personalGoal; }

    /**
     * Returns the list of all goal codes that have been used by players.
     * @return the list of all goal codes that have been used by players
     */
    public static List<Integer> getUsedCodes() {
        return usedCodes;
    }

    /**
     * Sets the list of all goal codes that have been used by players.
     * @param usedCodes the list of all goal codes that have been used by players
     */
    public static void setUsedCodes(List<Integer> usedCodes) {
        PersonalGoal.usedCodes = usedCodes;
    }

    /**
     * Clears the list of all goal codes that have been used by players.
     */
    public static void cleanUsedCodes(){
        PersonalGoal.getUsedCodes().clear();
    }
}
