package org.projectsw.Model.CommonGoal;

import org.projectsw.Util.Config;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Enums.TilesEnum;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Square class represents a game strategy where the goal is to have at least 2 separate 2x2 squares on the shelf
 * that contain tiles of the same type.
 */
public class Square extends CommonGoalStrategy {

    /**
     * Creates a new instance of the Square class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Square(Integer strategyCode){
        super(strategyCode);
        this.setDescription("""
                Two groups each containing 4 tiles of
                the same type in a 2x2 square. The tiles
                of one square can be different from
                those of the other square.""");
    }

    /**
     * Checks that there are at least 2 separate 2x2 squares with tiles of the same type,
     * and that the tiles of the two squares are of the same type
     * @param shelf the player shelf
     * @return true in the shelf there are two separate squares of the same tiles
     */
    @Override
    public boolean check(Shelf shelf) {

        ArrayList<Point> coordinates = new ArrayList<>();
        ArrayList<TilesEnum> tilesEnums = new ArrayList<>(Arrays.asList(TilesEnum.values()));

        for (TilesEnum tileType : tilesEnums) {
            coordinates.clear();
            for (int y = Config.shelfHeight-1; y > 0; y--) {
                for (int x = 0; x < Config.shelfLength-1; x++) {
                    if (isValidSquare(shelf, tileType, x, y)) {
                        if (coordinates.isEmpty()) {
                            insertCoords(coordinates, x, y);
                        } else {
                            if (notContainsCoords(coordinates, x, y)) {
                                return true;
                            } else {
                                insertCoords(coordinates, x, y);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Inserts the coordinates of the tiles of the square, as well as the tiles adjacent to the square's sides.
     * @param coordinates the array in which insert the coordinates
     * @param x the x coordinate of the upper left square of the square
     * @param y the y coordinate of the upper left square of the square
     */
    private void insertCoords(ArrayList<Point> coordinates, int x, int y){
        coordinates.add(new Point(x, y));
        coordinates.add(new Point(x, y - 1));
        coordinates.add(new Point(x + 1, y));
        coordinates.add(new Point(x + 1, y - 1));
        coordinates.add(new Point(x, y+1));
        coordinates.add(new Point(x+1, y+1));
        coordinates.add(new Point(x+2, y));
        coordinates.add(new Point(x+2, y-1));
        coordinates.add(new Point(x, y-2));
        coordinates.add(new Point(x+1, y-2));
        coordinates.add(new Point(x-1, y));
        coordinates.add(new Point(x-1, y-1));
    }

    /**
     * Checks if the coordinates of the square are not in the array of invalid coordinates.
     * @param coordinates the array of not valid coordinates
     * @param x the x coordinate of the upper left square of the square
     * @param y the y coordinate of the upper left square of the square
     * @return false if the array contains the coordinates, true if not
     */
    private boolean notContainsCoords(ArrayList<Point> coordinates, int x, int y){
        return !coordinates.contains(new Point(x, y)) &&
                !coordinates.contains(new Point(x, y - 1)) &&
                !coordinates.contains(new Point(x + 1, y)) &&
                !coordinates.contains(new Point(x + 1, y - 1));
    }

    /**
     * Checks whether a pair of x, y coordinates in a shelf are the upper left corner of a valid square
     * of a determined tile type.
     * @param shelf the shelf in which the check is made
     * @param tileType the type of the tile
     * @param x the x coordinate of the upper left square of the square
     * @param y the y coordinate of the upper left square of the square
     * @return true if the square is valid, false if not
     */
    private boolean isValidSquare(Shelf shelf, TilesEnum tileType, int x, int y){
        return shelf.getTileShelf(y, x).getTile() == shelf.getTileShelf(y - 1, x).getTile() &&
                shelf.getTileShelf(y, x).getTile() == shelf.getTileShelf(y - 1, x + 1).getTile() &&
                shelf.getTileShelf(y, x).getTile() == shelf.getTileShelf(y, x + 1).getTile() &&
                shelf.getTileShelf(y, x).getTile() != TilesEnum.EMPTY &&
                shelf.getTileShelf(y, x).getTile() != TilesEnum.UNUSED &&
                shelf.getTileShelf(y, x).getTile() == tileType;
    }
}
