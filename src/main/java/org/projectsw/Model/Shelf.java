package org.projectsw.Model;

import org.projectsw.Exceptions.UnselectableColumnException;
import org.projectsw.Util.Config;
import org.projectsw.Exceptions.UpdatingOnWrongPlayerException;
import org.projectsw.Util.Observable;
import java.util.ArrayList;
import static org.projectsw.Model.TilesEnum.EMPTY;

/**
 * Class representing a shelf with a matrix of tiles, the shelf that every player is going to use.
 */

public class Shelf extends Observable<Game.Event> {
    private Tile[][] shelf;
    private ArrayList<Integer> selectableColumns;
    private Integer selectedColumn;
    private boolean selectionPossible;

    /**
     * Constructs a new empty shelf with 6 rows and 5 columns, setting as null all the other parameters.
     */
    public Shelf(){
        shelf = new Tile[Config.shelfHeight][Config.shelfLength];
        for(int i=0;i<Config.shelfHeight;i++){
            for(int j=0;j<Config.shelfLength;j++){
                shelf[i][j]= new Tile(TilesEnum.EMPTY, 0);
            }
        }
        selectableColumns = null;
        selectedColumn = null;
        selectionPossible = true;
    }

    /**
     * Constructs a new empty shelf with 6 rows and 5 columns setting the passed player
     * as player attribute and setting null all the others.
     */
    public Shelf(Player player){
        shelf = new Tile[Config.shelfHeight][Config.shelfLength];
        for(int i=0;i<Config.shelfHeight;i++){
            for(int j=0;j<Config.shelfLength;j++){
                shelf[i][j]= new Tile(TilesEnum.EMPTY, 0);
            }
        }
        selectableColumns = null;
        selectedColumn = null;
        selectionPossible = true;
    }

    /**
     * Constructs a new shelf with the same contents as the given shelf.
     * @param shelf the shelf to copy
     */
    public Shelf(Shelf shelf){
        this.shelf = shelf.getShelf();
        this.selectableColumns = shelf.getSelectableColumns();
        this.selectedColumn = shelf.getSelectedColumn();
        this.shelf = shelf.shelf;
        this.selectionPossible = shelf.isSelectionPossible();
        setChangedAndNotifyObservers(Game.Event.UPDATED_SHELF);
    }


    /**
     * Returns the matrix of tiles that the shelf holds.
     * @return the matrix of tiles
     */
    public Tile[][] getShelf(){
        return shelf;
    }

    /**
     * Returns the selectable columns attribute.
     * @return the arraylist of integers containing the indexes of selectable columns.
     */
    public ArrayList<Integer> getSelectableColumns() {
        return selectableColumns;
    }

    /**
     * Returns a specific tile of the shelf.
     * @param row coordinate for the row
     * @param column coordinate for the column
     * @throws IndexOutOfBoundsException if row or column exceed the shelf bounds (Config.shelfHeight or Config.shelfLength)
     * @return the tile at the coordinates row x column
     */
    public Tile getTileShelf(int row, int column) throws IndexOutOfBoundsException{
        if( row > Config.shelfHeight-1 || column > Config.shelfLength-1) throw new IndexOutOfBoundsException();
        return shelf[row][column];
    }

    /**
     * Return the selected column attribute.
     * @return the int corresponding to the selected column attribute.
     */
    public Integer getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Return the selectionPossible attribute.
     * @return the boolean corresponding to the selectionPossible attribute.
     */
    public boolean isSelectionPossible() {
        return selectionPossible;
    }

    /**
     * Sets the matrix of tiles for the shelf from the given shelf.
     * @param shelf the shelf where the matrix of tiles is taken from
     * @throws IllegalArgumentException if the parameter shelf hasn't the right dimensions (Config.shelfHeight or Config.shelfLength)
     */
    public void setShelf(Tile[][] shelf) throws IllegalArgumentException{
        if(shelf.length != Config.shelfHeight || shelf[0].length != Config.shelfLength) throw new IllegalArgumentException();
        this.shelf = shelf;
        setChangedAndNotifyObservers(Game.Event.UPDATED_SHELF);
    }

    /**
     * Sets the selectable columns attribute.
     * @param selectableColumns the arraylist of integers to ses as new array list of integers.
     */
    public void setSelectableColumns(ArrayList<Integer> selectableColumns) {
        this.selectableColumns = selectableColumns;
    }

    /**
     * Sets the selected column attribute.
     * @param selectedColumn the int to set as new selected column attribute.
     */
    public void setSelectedColumn(Integer selectedColumn) throws UnselectableColumnException {
        if (getSelectableColumns().contains(selectedColumn))
        this.selectedColumn = selectedColumn;
        else {
            throw new UnselectableColumnException();
        }
    }

    /**
     * Sets the selection possible attribute.
     * @param selectionPossible the boolean to set as new selection possible attribute.
     */
    public void setSelectionPossible(boolean selectionPossible) {
        this.selectionPossible = selectionPossible;
    }


    /**
     * Inserts a tile into the specified row and column of the shelf.
     * @param tile the tile to insert
     * @param row the row to insert the tile into
     * @param column the column to insert the tile into
     * @throws IndexOutOfBoundsException if the row or column is out of bounds (Config.shelfHeight or Config.shelfLength)
     * @throws IllegalArgumentException if row and column correspond to an EMPTY or an UNUSED tile
     */
    public void insertTiles(Tile tile, int row, int column) {
        if( row > Config.shelfHeight-1 || column > Config.shelfLength-1) throw new IndexOutOfBoundsException("Out of bounds");
        else if(tile.getTile().equals(TilesEnum.EMPTY) || tile.getTile().equals(TilesEnum.UNUSED)) throw new IllegalArgumentException("Trying to add a unused or empty tile to the shelf");
        else shelf[row][column] = tile;
        setChangedAndNotifyObservers(Game.Event.UPDATED_SHELF);
    }

    /**
     * Updates the selectable columns arrayList after checking the size of temporaryTiles arrayList of the player.
     */
    public void updateSelectableColumns(Player player) throws UpdatingOnWrongPlayerException {
        if(!player.getShelf().equals(this)) throw new UpdatingOnWrongPlayerException();
        ArrayList<Integer> selectableColumns = new ArrayList<>();
        for (int j=0; j<Config.shelfLength; j++) {
            int freeSpace = 0;
            for (int i=Config.shelfHeight-1; i>=0; i--){
                if (shelf[i][j].getTile().equals(EMPTY)) {
                    freeSpace++;
                } else break;
            }
            if(freeSpace >= player.getTemporaryTiles().size() && freeSpace > 0) selectableColumns.add(j);
        }
        this.selectableColumns = selectableColumns;
    }

    /**
     * Returns the maximum column space present in the shelf between 0 to Config.maximumTilesPickable.
     * @return an int equal to the maximum column space.
     */
    public int maxFreeColumnSpace(){
        int maxFreeColumnSpace = 0;
        for(int j=0;j<Config.shelfLength;j++){
            int freeColumnSpace = 0;
            for(int i=Config.shelfHeight-1;i>(Config.shelfHeight-1)-Config.maximumTilesPickable;i--){
                if(!shelf[i][j].getTile().equals(EMPTY)) break;
                freeColumnSpace++;
            }
            if(freeColumnSpace>maxFreeColumnSpace) {
                maxFreeColumnSpace = freeColumnSpace;
            }
        }
        return maxFreeColumnSpace;
    }

    /**
     * Clears the selectable columns arrayList.
     */
    public void cleanSelectableColumns() {
        selectableColumns.clear();
    }

    /**
     * Sets as null the selectedColumn attribute.
     */
    public void cleanSelectedColumn(){ selectedColumn = null; }

    //TODO DAVIDE: codice duplicato con la board linea 341 da sistemare
    /**
     * Prints the shelf.
     */
    public void printShelf(){
        for(int i=Config.shelfHeight-1;i>=0;i--){
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
        for(int h=0;h<Config.shelfLength;h++){
            if(selectedColumn == null){
                if(selectableColumns.contains(h)) System.out.print("(S)\t\t");
                else System.out.print("(U)\t\t");
            } else {
                if(h == selectedColumn) System.out.print("(D)\t\t");
                else  System.out.print("(N)\t\t");
            }
        }
        System.out.print("\n\n");
    }
}
