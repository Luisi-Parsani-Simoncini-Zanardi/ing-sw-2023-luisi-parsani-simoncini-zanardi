package org.example.Model;

public class Shelf {
    private Tiles[][] shelf;

    public Shelf(){
        shelf = new Tiles[6][5];
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                shelf[i][j]=Tiles.EMPTY;
            }
        }
    }
    public Shelf(Shelf shelf){
        this.shelf = shelf.shelf;
    }

    public Shelf getShelf(){
        return this;
    }

    public void insertTiles(Tiles tile, int column, int row){
        shelf[row][column] = tile;
    }
}
