package org.projectsw.Model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PersonalGoal {
    private final TilesEnum[][] personalGoal;
    private static List<Integer> usedCodes = new ArrayList<>(); //called codes

    public PersonalGoal(int goalCode) throws IOException {
        if (usedCodes.contains(goalCode)) {
            throw new IllegalArgumentException("Goal code already used.");
        }
        usedCodes.add(goalCode);

        Gson gson = new Gson();
        String[][][] tmpMatrixes = gson.fromJson(new FileReader("./src/main/resources/PersonalGoals.json"), String[][][].class);

        personalGoal = new TilesEnum[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                String str = tmpMatrixes[goalCode][i][j];
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

    public static List<Integer> getUsedCodes() {
        return usedCodes;
    }

    public static void setUsedCodes(List<Integer> usedCodes) {
        PersonalGoal.usedCodes = usedCodes;
    }
    public void cleanUsedCodes(){
        PersonalGoal.getUsedCodes().clear();
    }

    public TilesEnum[][] getPersonalGoal() { return personalGoal; }
}
