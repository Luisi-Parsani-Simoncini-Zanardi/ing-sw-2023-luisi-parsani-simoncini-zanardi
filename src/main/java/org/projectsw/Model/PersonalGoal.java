package org.projectsw.Model;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PersonalGoal {
    private Tiles[][] personalGoal;

    private static int[] used;//codici già chiamati

    public PersonalGoal(int cardCode) throws IOException {

        Gson gson = new Gson();

        personalGoal = new Tiles[6][5];
        for (int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                personalGoal[i][j]=Tiles.EMPTY;
            }
        }
    }

    public Tiles[][] getPersonalGoal() { return personalGoal; }
}
