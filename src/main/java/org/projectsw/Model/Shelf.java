package org.projectsw.Model;

import org.projectsw.Exceptions.EmptyTilesException;
import org.projectsw.Exceptions.UnusedTilesException;
import java.util.ArrayList;
import static org.projectsw.Model.TilesEnum.EMPTY;

/**
 * Class representing a shelf with a matrix of tiles, the shelf that every player is going to use.
 */
public class Shelf {
    private Tile[][] shelf;
    private int selectedColumnIndex;

    /**
     * Constructs a new empty shelf with 6 rows and 5 columns.
     */
    public Shelf(){
        shelf = new Tile[6][5];
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                shelf[i][j]= new Tile(TilesEnum.EMPTY, 0);
            }
        }
    }

    /**
     * Constructs a new shelf with the same contents as the given shelf.
     * @param shelf the shelf to copy
     */
    public Shelf(Shelf shelf){
        this.shelf = shelf.shelf;
        this.selectedColumnIndex = shelf.getSelectedColumnIndex();
    }


    /**
     * Returns the matrix of tiles that the shelf holds.
     * @return the matrix of tiles
     */
    public Tile[][] getShelf(){
        return shelf;
    }

    /**
     * Returns the selectedColumnIndex attribute.
     * @return the selectedColumnIndex attribute
     */
    public int getSelectedColumnIndex() {
        return selectedColumnIndex;
    }

    /**
     * Returns a specific tile of the shelf.
     * @param row coordinate for the row
     * @param column coordinate for the column
     * @return the tile at the coordinates row x column
     */
    public Tile getTileShelf(int row, int column) throws IndexOutOfBoundsException{
        if( row > 5 || column > 4) throw new IndexOutOfBoundsException();
        return shelf[row][column];
    }

    /**
     * Sets the matrix of tiles for the shelf from the given shelf.
     * @param shelf the shelf where the matrix of tiles is taken from
     */
    public void setShelf(Tile[][] shelf) throws IllegalArgumentException{
        if(shelf.length != 6 || shelf[0].length != 5) throw new IllegalArgumentException();
        this.shelf = shelf;
    }

    /**
     * Sets the selectedColumnIndex attribute.
     * @param selectedColumnIndex the int to set as new selectedColumnIndex.
     */
    public void setSelectedColumnIndex(int selectedColumnIndex) {
        this.selectedColumnIndex = selectedColumnIndex;
    }

    /**
     * Inserts a tile into the specified row and column of the shelf.
     * @param tile the tile to insert
     * @param row the row to insert the tile into
     * @param column the column to insert the tile into
     * @throws EmptyTilesException if the tile is empty
     * @throws UnusedTilesException if the tile is unused
     * @throws IndexOutOfBoundsException if the row or column is out of bounds
     */
    public void insertTiles(Tile tile, int row, int column) throws EmptyTilesException, UnusedTilesException, IndexOutOfBoundsException {
        if(row>5 || column > 4) throw new IndexOutOfBoundsException("Out of bounds");
        else if(tile.getTile().equals(TilesEnum.EMPTY)) throw new EmptyTilesException("You can't add an EMPTY tile to the shelf");
        else if(tile.getTile().equals(TilesEnum.UNUSED)) throw new UnusedTilesException("You can't add an UNUSED tile to the shelf");
        else shelf[row][column] = tile;
    }

    public ArrayList<Integer> getSelectableColumns(int temporaryTilesDimension){
        ArrayList<Integer> selectableColumns = new ArrayList<>();
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                if(!shelf[j][i].getTile().equals(EMPTY)){
                    System.out.println(temporaryTilesDimension+"<="+j);
                    if(temporaryTilesDimension <= j) selectableColumns.add(i);
                    break;
                }
            }
        }
        return selectableColumns;
    }
}
