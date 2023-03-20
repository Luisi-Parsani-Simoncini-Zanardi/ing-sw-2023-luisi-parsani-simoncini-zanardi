package org.projectsw.Model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PersonalGoal {
    private final Tiles[][] personalGoal;

    private static int[] used;//codici già chiamati

    public PersonalGoal(int goalCode) throws IOException{
        Gson gson = new Gson();
        String[][][] tmpMatrixes = gson.fromJson(new FileReader("./src/main/resources/PersonalGoals.json"), String[][][].class);

        personalGoal = new Tiles[6][5];
        for (int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                String str = tmpMatrixes[goalCode][i][j];
                if(str.equals("EMPTY")){personalGoal[i][j] = Tiles.EMPTY;}
                if(str.equals("PLANTS")){personalGoal[i][j] = Tiles.PLANTS;}
                if(str.equals("TROPHIES")){personalGoal[i][j] = Tiles.TROPHIES;}
                if(str.equals("GAMES")){personalGoal[i][j] = Tiles.GAMES;}
                if(str.equals("EMPTY")){personalGoal[i][j] = Tiles.FRAMES;}
                if(str.equals("EMPTY")){personalGoal[i][j] = Tiles.CATS;}
                if(str.equals("EMPTY")){personalGoal[i][j] = Tiles.BOOKS;}
            }
        }
    }

    public Tiles[][] getPersonalGoal() { return personalGoal; }
}
