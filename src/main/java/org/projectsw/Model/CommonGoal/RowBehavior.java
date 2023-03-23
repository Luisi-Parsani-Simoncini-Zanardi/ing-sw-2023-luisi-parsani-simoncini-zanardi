package org.projectsw.Model.CommonGoal;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import java.util.ArrayList;

public interface RowBehavior {

    public boolean check(Shelf shelf);
}