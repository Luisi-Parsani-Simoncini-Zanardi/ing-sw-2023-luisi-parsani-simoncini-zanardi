package org.projectsw.Model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

public class PersonalGoal {
    private final Tiles[][] personalGoal;
    private static List<Integer> usedCodes = new ArrayList<>(); //called codes

    public PersonalGoal(int goalCode) throws IOException {
        if (usedCodes.contains(goalCode)) {
            throw new IllegalArgumentException("Goal code already used.");
        }
        usedCodes.add(goalCode);

        Gson gson = new Gson();
        String[][][] tmpMatrixes = gson.fromJson(new FileReader("./src/main/resources/PersonalGoals.json"), String[][][].class);

        personalGoal = new Tiles[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                String str = tmpMatrixes[goalCode][i][j];
                switch (str) {
                    case "EMPTY" -> personalGoal[i][j] = Tiles.EMPTY;
                    case "PLANTS" -> personalGoal[i][j] = Tiles.PLANTS;
                    case "TROPHIES" -> personalGoal[i][j] = Tiles.TROPHIES;
                    case "GAMES" -> personalGoal[i][j] = Tiles.GAMES;
                    case "FRAMES" -> personalGoal[i][j] = Tiles.FRAMES;
                    case "CATS" -> personalGoal[i][j] = Tiles.CATS;
                    case "BOOKS" -> personalGoal[i][j] = Tiles.BOOKS;
                    default -> throw new IllegalArgumentException("Invalid tile value: " + str);
                }
            }
        }
    }

    public Tiles[][] getPersonalGoal() { return personalGoal; }
}
