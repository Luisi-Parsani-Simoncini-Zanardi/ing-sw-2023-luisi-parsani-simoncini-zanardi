package org.projectsw.Model.CommonGoal;

import org.projectsw.Model.Shelf;
import org.projectsw.Model.TilesEnum;
import java.awt.Point;
import java.util.ArrayList;

public class Square extends ShapeBehavior {
    public Square(){
        super();
        this.commonGoalCode = 1;
    }

    /**
     * @param shelf is the player shelf
     * @return true in the shelf there are two separate squares of the same tiles
     */
    @Override
    public boolean check(Shelf shelf) {

        ArrayList<Point> coordinates = new ArrayList<>();

        for (int y=5; y>0; y--)
        {
            for (int x=4; x>0; x--)
            {
                if(shelf.getTileShelf(x,y).getTile() == shelf.getTileShelf(x,y-1).getTile() &&
                        shelf.getTileShelf(x,y).getTile() == shelf.getTileShelf(x-1,y-1).getTile() &&
                        shelf.getTileShelf(x,y).getTile() == shelf.getTileShelf(x-1, y).getTile() &&
                        shelf.getTileShelf(x,y).getTile() != TilesEnum.EMPTY)
                {
                    Point upperLeft = new Point(x,y);
                    Point lowerLeft = new Point(x,y-1);
                    Point upperRight = new Point(x-1,y);
                    Point lowerRight = new Point(x-1,y-1);

                    if (coordinates.isEmpty())
                    {
                        coordinates.add(upperLeft);
                        coordinates.add(lowerLeft);
                        coordinates.add(upperRight);
                        coordinates.add(lowerRight);
                    }
                    else
                    {
                        if (coordinates.contains(upperLeft)||
                                coordinates.contains(lowerLeft)||
                                coordinates.contains(upperRight)||
                                coordinates.contains(lowerRight))
                        {
                            coordinates.add(upperLeft);
                            coordinates.add(lowerLeft);
                            coordinates.add(upperRight);
                            coordinates.add(lowerRight);
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
