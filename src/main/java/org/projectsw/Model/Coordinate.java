package org.projectsw.Model;

/**
 * The Coordinates class represents the row and column coordinates of a 2-dimensional space,
 * it can be a shelf or a board.
 */
public class Coordinate {
    private int row;
    private int column;

    /**
     * Constructs a new Coordinates object with the given row and column values.
     * @param row the row coordinate.
     * @param column the column coordinate.
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row coordinate of this object.
     * @return the row coordinate.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column coordinate of this object.
     * @return the column coordinate.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the row coordinate of this object.
     * @param row the new row coordinate.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the column coordinate of this object.
     * @param column the new column coordinate.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return  "row=" + row + ", column=" + column;
    }
}
