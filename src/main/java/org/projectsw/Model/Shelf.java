package org.projectsw.Model;

import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.UnusedTilesException;

public class Shelf {
    private TilesEnum[][] shelf;

    public Shelf(){
        shelf = new TilesEnum[6][5];
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                shelf[i][j]= ;
            }
        }
    }
    public Shelf(Shelf shelf){
        this.shelf = shelf.shelf;
    }

    public TilesEnum[][] getShelf(){
        return shelf;
    }

    public void insertTiles(TilesEnum tile, int row, int column) throws EmptyTilesException, UnusedTilesException {
        if(row>5 || column > 4)
            throw new IndexOutOfBoundsException("Out of bounds");
        else if(tile.equals(TilesEnum.EMPTY))
            throw new EmptyTilesException("You can't add an EMPTY tile to the shelf");
        else if(tile.equals(TilesEnum.UNUSED))
            throw new UnusedTilesException("You can't add an UNUSED tile to the shelf");
        else
            shelf[row][column] = tile;
    }
}
