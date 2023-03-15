package org.projectsw.Model;

public class PersonalGoal {
    private Tiles[][] personalGoal;

    private static int[] used;//codici già chiamati

    public PersonalGoal(int cardCode){
        personalGoal = new Tiles[6][5];
        for (int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                personalGoal[i][j]=Tiles.EMPTY;
            }
        }
    }

    public Tiles[][] getPersonalGoal() { return personalGoal; }
}
