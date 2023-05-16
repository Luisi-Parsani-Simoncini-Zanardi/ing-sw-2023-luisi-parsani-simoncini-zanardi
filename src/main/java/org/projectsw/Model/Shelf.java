package org.projectsw.Model;

import org.projectsw.Exceptions.UnselectableColumnException;
import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.Enums.TilesEnum;
import org.projectsw.Util.Config;
import org.projectsw.Util.Observable;
import org.projectsw.View.ConsoleColors;

import java.util.ArrayList;
import static org.projectsw.Model.Enums.TilesEnum.EMPTY;

/**
 * Class representing a shelf with a matrix of tiles, the shelf that every player is going to use.
 */

public class Shelf extends Observable<GameEvent> {
    private Tile[][] shelf;
    private ArrayList<Integer> selectableColumns;
    private Integer selectedColumn;
    private boolean selectionPossible;

    /**
     * Constructs a new empty shelf with 6 rows and 5 columns, setting as null all the other parameters.
     */
    public Shelf() {
        shelf = new Tile[Config.shelfHeight][Config.shelfLength];
        for (int i = 0; i < Config.shelfHeight; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                shelf[i][j] = new Tile(TilesEnum.EMPTY, 0);
            }
        }
        selectableColumns = null;
        selectedColumn = null;
        selectionPossible = true;
    }

    //BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR
    public Shelf(int b) {
        shelf = new Tile[Config.shelfHeight][Config.shelfLength];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < Config.shelfLength; j++) {
                shelf[i][j] = new Tile(TilesEnum.CATS, 0);
            }
        }
        for (int i = 3; i < Config.shelfHeight; i++) {
            for (int j = 0; j < 4; j++) {
                shelf[i][j] = new Tile(TilesEnum.CATS, 0);
            }
        }
        for (int i = 5; i < Config.shelfHeight; i++) {
            for (int j = 4; j < Config.shelfLength; j++) {
                shelf[i][j] = new Tile(TilesEnum.EMPTY, 0);
            }
        }
        selectableColumns = null;
        selectedColumn = null;
        selectionPossible = true;
    }
    //BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR BACKDOOR


    /**
     * Constructs a new shelf with the same contents as the given shelf.
     *
     * @param shelf the shelf to copy
     */
    public Shelf(Shelf shelf) {
        this.shelf = shelf.getShelf();
        this.selectableColumns = shelf.getSelectableColumns();
        this.selectedColumn = shelf.getSelectedColumn();
        this.shelf = shelf.shelf;
        this.selectionPossible = shelf.isSelectionPossible();
        //setChangedAndNotifyObservers(Game.Event.UPDATED_SHELF);
    }


    /**
     * Returns the matrix of tiles that the shelf holds.
     *
     * @return the matrix of tiles
     */
    public Tile[][] getShelf() {
        return shelf;
    }

    /**
     * Returns the selectable columns attribute.
     *
     * @return the arraylist of integers containing the indexes of selectable columns.
     */
    public ArrayList<Integer> getSelectableColumns() {
        return selectableColumns;
    }

    /**
     * Returns a specific tile of the shelf.
     *
     * @param row    coordinate for the row
     * @param column coordinate for the column
     * @return the tile at the coordinates row x column
     * @throws IndexOutOfBoundsException if row or column exceed the shelf bounds (Config.shelfHeight or Config.shelfLength)
     */
    public Tile getTileShelf(int row, int column) throws IndexOutOfBoundsException {
        if (row > Config.shelfHeight - 1 || column > Config.shelfLength - 1) throw new IndexOutOfBoundsException();
        return shelf[row][column];
    }

    /**
     * Return the selected column attribute.
     *
     * @return the int corresponding to the selected column attribute.
     */
    public Integer getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Return the selectionPossible attribute.
     *
     * @return the boolean corresponding to the selectionPossible attribute.
     */
    public boolean isSelectionPossible() {
        return selectionPossible;
    }

    /**
     * Sets the matrix of tiles for the shelf from the given shelf.
     *
     * @param shelf the shelf where the matrix of tiles is taken from
     * @throws IllegalArgumentException if the parameter shelf hasn't the right dimensions (Config.shelfHeight or Config.shelfLength)
     */
    public void setShelf(Tile[][] shelf) throws IllegalArgumentException {
        if (shelf.length != Config.shelfHeight || shelf[0].length != Config.shelfLength)
            throw new IllegalArgumentException();
        this.shelf = shelf;
        //setChangedAndNotifyObservers(Game.Event.UPDATED_SHELF);
    }

    /**
     * Sets the selectable columns attribute.
     *
     * @param selectableColumns the arraylist of integers to ses as new array list of integers.
     */
    public void setSelectableColumns(ArrayList<Integer> selectableColumns) {
        this.selectableColumns = selectableColumns;
    }

    /**
     * Sets the selected column attribute.
     *
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
     *
     * @param selectionPossible the boolean to set as new selection possible attribute.
     */
    public void setSelectionPossible(boolean selectionPossible) {
        this.selectionPossible = selectionPossible;
    }


    /**
     * Inserts a tile into the specified row and column of the shelf.
     *
     * @param tile   the tile to insert
     * @param row    the row to insert the tile into
     * @param column the column to insert the tile into
     * @throws IndexOutOfBoundsException if the row or column is out of bounds (Config.shelfHeight or Config.shelfLength)
     * @throws IllegalArgumentException  if row and column correspond to an EMPTY or an UNUSED tile
     */
    public void insertTiles(Tile tile, int row, int column) {
        if (row > Config.shelfHeight - 1 || column > Config.shelfLength - 1)
            throw new IndexOutOfBoundsException("Out of bounds");
        else if (tile.getTile().equals(TilesEnum.EMPTY) || tile.getTile().equals(TilesEnum.UNUSED))
            throw new IllegalArgumentException("Trying to add a unused or empty tile to the shelf");
        else shelf[row][column] = tile;
        //setChangedAndNotifyObservers(Game.Event.UPDATED_SHELF);
    }

    /**
     * Updates the selectable columns arrayList after checking the size of temporaryTiles arrayList of the player.
     */
    public void updateSelectableColumns(Player player) {
        if (!player.getShelf().equals(this)) {
            //TODO: gestire exception
        }
        ArrayList<Integer> selectableColumns = new ArrayList<>();
        for (int j = 0; j < Config.shelfLength; j++) {
            int freeSpace = 0;
            for (int i = Config.shelfHeight - 1; i >= 0; i--) {
                if (shelf[i][j].getTile().equals(EMPTY)) {
                    freeSpace++;
                } else break;
            }
            if (freeSpace >= player.getTemporaryTiles().size() && freeSpace > 0) selectableColumns.add(j);
        }
        this.selectableColumns = selectableColumns;
    }

    /**
     * Returns the maximum column space present in the shelf between 0 to Config.maximumTilesPickable.
     *
     * @return an int equal to the maximum column space.
     */
    public int maxFreeColumnSpace() {
        int maxFreeColumnSpace = 0;
        for (int j = 0; j < Config.shelfLength; j++) {
            int freeColumnSpace = 0;
            for (int i = Config.shelfHeight - 1; i > (Config.shelfHeight - 1) - Config.maximumTilesPickable; i--) {
                if (!shelf[i][j].getTile().equals(EMPTY)) break;
                freeColumnSpace++;
            }
            if (freeColumnSpace > maxFreeColumnSpace) {
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
    public void cleanSelectedColumn() {
        selectedColumn = null;
    }

    /**
     * Prints the shelf.
     */
    public void printShelf() {
        String printedString = "";
        for (int i = 0; i < Config.shelfLength; i++) {
            Integer integer = i + 1;
            printedString = printedString + "    " + integer + "     ";
        }
        System.out.println(printedString);
        for (int i = Config.shelfHeight-1; i >= 0 ; i--) {
            printedString = "";
            for (int j = 0; j < Config.shelfLength; j++) {
                printedString = printedString + printPadding(true, i, j) + stringColor(i, j) + printPadding(false, i, j);
            }
            System.out.println(printedString);
        }
        System.out.print("\n");
    }

    private String stringColor(int i, int j) {
        String result = "";
        TilesEnum type = shelf[i][j].getTile();
        switch (type) {
            case CATS -> result = result + ConsoleColors.CATS;
            case TROPHIES -> result = result + ConsoleColors.TROPHIES;
            case BOOKS -> result = result + ConsoleColors.BOOKS;
            case FRAMES -> result = result + ConsoleColors.FRAMES;
            case GAMES -> result = result + ConsoleColors.GAMES;
            case PLANTS -> result = result + ConsoleColors.PLANTS;
            case EMPTY -> result = " -------- ";
        }
        return result;
    }

    private String printPadding(boolean left, int i, int j) {
        float padding = 10;
        String paddingSpaces = "";
        padding -= shelf[i][j].getTile().toString().length();
        if (shelf[i][j].getTile() == EMPTY) return "";
        if (!left) {
            for (int k = 0; k < Math.floor(padding / 2); k++) {
                if (k == Math.floor(padding / 2) - 1) paddingSpaces = paddingSpaces + " ";
                else paddingSpaces = paddingSpaces + "-";
            }
        } else {
            for (int k = 0; k < Math.ceil(padding / 2); k++) {
                if (k == 0) paddingSpaces = paddingSpaces + " ";
                else paddingSpaces = paddingSpaces + "-";

            }
        }

            return paddingSpaces;
    }
}
