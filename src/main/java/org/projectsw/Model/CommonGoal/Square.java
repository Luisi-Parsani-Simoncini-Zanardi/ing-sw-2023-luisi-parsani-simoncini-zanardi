package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;
import java.awt.Point;
import java.util.ArrayList;

public class Square extends CommonGoalStrategy {

    /**
     * Creates a new instance of the Square class using the unique code of the CommonGoal
     * @param strategyCode is the unique code of the CommonGoal instance to be created
     */
    public Square(int strategyCode){
        super(strategyCode);
    }

    /**
     * Checks that there are at least 2 separate 2x2 squares with tiles of the same type,
     * and that the tiles of the two squares are of the same type
     * @param shelf is the player shelf
     * @return true in the shelf there are two separate squares of the same tiles
     */
    @Override
    public boolean check(Shelf shelf) {

        ArrayList<Point> coordinates = new ArrayList<>();
        TilesEnum[] tilesEnums = TilesEnum.values();

        for (TilesEnum tileType : tilesEnums) {
            coordinates.clear();
            for (int y = 5; y > 0; y--) {
                for (int x = 0; x < 4; x++) {
                    if (shelf.getTileShelf(y, x).getTile() == shelf.getTileShelf(y - 1, x).getTile() &&
                            shelf.getTileShelf(y, x).getTile() == shelf.getTileShelf(y - 1, x + 1).getTile() &&
                            shelf.getTileShelf(y, x).getTile() == shelf.getTileShelf(y, x + 1).getTile() &&
                            shelf.getTileShelf(y, x).getTile() != TilesEnum.EMPTY &&
                            shelf.getTileShelf(y, x).getTile() == tileType)

                    {
                        Point upperLeft = new Point(x, y);
                        Point lowerLeft = new Point(x, y - 1);
                        Point upperRight = new Point(x + 1, y);
                        Point lowerRight = new Point(x + 1, y - 1);

                        if (coordinates.isEmpty()) {
                            coordinates.add(upperLeft);
                            coordinates.add(lowerLeft);
                            coordinates.add(upperRight);
                            coordinates.add(lowerRight);

                        } else {
                            if (!(coordinates.contains(upperLeft) ||
                                    coordinates.contains(lowerLeft) ||
                                    coordinates.contains(upperRight) ||
                                    coordinates.contains(lowerRight))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
