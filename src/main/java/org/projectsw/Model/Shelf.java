package org.projectsw.Model;

import org.projectsw.Config;
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
        shelf = new Tile[Config.shelfHeight][Config.shelfLength];
        for(int i=0;i<Config.shelfHeight;i++){
            for(int j=0;j<Config.shelfLength;j++){
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
        if( row > Config.shelfHeight-1 || column > Config.shelfLength-1) throw new IndexOutOfBoundsException();
        return shelf[row][column];
    }

    /**
     * Sets the matrix of tiles for the shelf from the given shelf.
     * @param shelf the shelf where the matrix of tiles is taken from
     */
    public void setShelf(Tile[][] shelf) throws IllegalArgumentException{
        if(shelf.length != Config.shelfHeight || shelf[0].length != Config.shelfLength) throw new IllegalArgumentException();
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
        if( row > Config.shelfHeight-1 || column > Config.shelfLength-1) throw new IndexOutOfBoundsException("Out of bounds");
        else if(tile.getTile().equals(TilesEnum.EMPTY)) throw new EmptyTilesException("You can't add an EMPTY tile to the shelf");
        else if(tile.getTile().equals(TilesEnum.UNUSED)) throw new UnusedTilesException("You can't add an UNUSED tile to the shelf");
        else shelf[row][column] = tile;
    }

    /**
     * Returns all the columns that have a number of empty spaces equal or greater than "temporaryTilesDimension".
     * @param temporaryTilesDimension the minimum number of free spaces that the returned columns must have.
     * @return an ArrayList containing all the indexes of columns that have a number of empty spaces equal or greater
     *         than "temporaryTilesDimension".
     */
    public ArrayList<Integer> getSelectableColumns(int temporaryTilesDimension) {
        ArrayList<Integer> selectableColumns = new ArrayList<>();
        for (int i = 0; i < Config.shelfLength; i++) {
            for (int j = 0; j < Config.shelfHeight; j++) {
                if (!shelf[j][i].getTile().equals(EMPTY) || j == Config.shelfHeight - 1) {
                    if (temporaryTilesDimension <= j) selectableColumns.add(i);
                    break;
                }
            }
        }
        return selectableColumns;
    }

    /**
     * Prints the shelf.
     */
    public void printShelf(){
        for(int i=0;i<Config.shelfHeight;i++){
            for(int j=0;j<Config.shelfLength;j++){
                Tile current = shelf[i][j];
                switch(current.getTile()){
                    case EMPTY -> System.out.print("EMPTY\t");
                    case UNUSED -> System.out.print("UNUSED\t");
                    case CATS -> System.out.print("CATS\t");
                    case TROPHIES -> System.out.print("TROPHIES\t");
                    case PLANTS -> System.out.print("PLANTS\t");
                    case FRAMES -> System.out.print("FRAMES\t");
                    case GAMES -> System.out.print("GAMES\t");
                    case BOOKS -> System.out.print("BOOKS\t");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
