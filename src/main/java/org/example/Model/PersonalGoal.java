package org.example.Model;

public class PersonalGoal {
    private Tiles[][] personalGoal;

    public PersonalGoal(int cardCode){
        personalGoal = new Tiles[6][5];
        for (int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                personalGoal[i][j]=Tiles.EMPTY;
            }
        }
        switch(cardCode){
            case 1:
                personalGoal[3][1]=Tiles.GAMES;
                personalGoal[0][0]=Tiles.PLANTS;
                personalGoal[0][2]=Tiles.FRAMES;
                personalGoal[1][5]=Tiles.CATS;
                personalGoal[2][3]=Tiles.BOOKS;
                personalGoal[5][2]=Tiles.TROPHIES;
                break;
            case 2:
                personalGoal[2][2]=Tiles.GAMES;
                personalGoal[1][1]=Tiles.PLANTS;
                personalGoal[5][5]=Tiles.FRAMES;
                personalGoal[2][0]=Tiles.CATS;
                personalGoal[3][5]=Tiles.BOOKS;
                personalGoal[4][3]=Tiles.TROPHIES;
                break;
            case 3:
                personalGoal[1][3]=Tiles.GAMES;
                personalGoal[2][2]=Tiles.PLANTS;
                personalGoal[1][0]=Tiles.FRAMES;
                personalGoal[3][1]=Tiles.CATS;
                personalGoal[5][0]=Tiles.BOOKS;
                personalGoal[3][4]=Tiles.TROPHIES;
                break;
            case 4:
                personalGoal[0][4]=Tiles.GAMES;
                personalGoal[3][3]=Tiles.PLANTS;
                personalGoal[2][2]=Tiles.FRAMES;
                personalGoal[4][2]=Tiles.CATS;
                personalGoal[4][1]=Tiles.BOOKS;
                personalGoal[2][0]=Tiles.TROPHIES;
                break;
            case 5:
                personalGoal[5][0]=Tiles.GAMES;
                personalGoal[4][4]=Tiles.PLANTS;
                personalGoal[3][1]=Tiles.FRAMES;
                personalGoal[5][3]=Tiles.CATS;
                personalGoal[3][2]=Tiles.BOOKS;
                personalGoal[1][1]=Tiles.TROPHIES;
                break;
            case 6:
                personalGoal[4][1]=Tiles.GAMES;
                personalGoal[5][0]=Tiles.PLANTS;
                personalGoal[4][3]=Tiles.FRAMES;
                personalGoal[0][4]=Tiles.CATS;
                personalGoal[2][3]=Tiles.BOOKS;
                personalGoal[0][2]=Tiles.TROPHIES;
                break;
            case 7:
                personalGoal[4][4]=Tiles.GAMES;
                personalGoal[2][1]=Tiles.PLANTS;
                personalGoal[1][3]=Tiles.FRAMES;
                personalGoal[0][0]=Tiles.CATS;
                personalGoal[5][2]=Tiles.BOOKS;
                personalGoal[3][0]=Tiles.TROPHIES;
                break;
            case 8:
                personalGoal[5][3]=Tiles.GAMES;
                personalGoal[3][0]=Tiles.PLANTS;
                personalGoal[0][4]=Tiles.FRAMES;
                personalGoal[1][1]=Tiles.CATS;
                personalGoal[4][3]=Tiles.BOOKS;
                personalGoal[2][2]=Tiles.TROPHIES;
                break;
            case 9:
                personalGoal[0][2]=Tiles.GAMES;
                personalGoal[4][4]=Tiles.PLANTS;
                personalGoal[5][0]=Tiles.FRAMES;
                personalGoal[2][2]=Tiles.CATS;
                personalGoal[3][4]=Tiles.BOOKS;
                personalGoal[4][1]=Tiles.TROPHIES;
                break;
            case 10:
                personalGoal[1][1]=Tiles.GAMES;
                personalGoal[5][3]=Tiles.PLANTS;
                personalGoal[4][1]=Tiles.FRAMES;
                personalGoal[3][3]=Tiles.CATS;
                personalGoal[2][0]=Tiles.BOOKS;
                personalGoal[0][4]=Tiles.TROPHIES;
                break;
            case 11:
                personalGoal[2][0]=Tiles.GAMES;
                personalGoal[0][2]=Tiles.PLANTS;
                personalGoal[3][2]=Tiles.FRAMES;
                personalGoal[4][4]=Tiles.CATS;
                personalGoal[1][1]=Tiles.BOOKS;
                personalGoal[5][3]=Tiles.TROPHIES;
                break;
            case 12:
                personalGoal[4][4]=Tiles.GAMES;
                personalGoal[1][1]=Tiles.PLANTS;
                personalGoal[2][2]=Tiles.FRAMES;
                personalGoal[5][0]=Tiles.CATS;
                personalGoal[0][2]=Tiles.BOOKS;
                personalGoal[3][3]=Tiles.TROPHIES;
                break;
        }
    }

    public PersonalGoal getPersonalGoal(){return this;}
}
