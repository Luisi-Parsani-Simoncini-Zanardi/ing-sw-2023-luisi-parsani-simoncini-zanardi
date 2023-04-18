package org.projectsw.ModelTest.CommonGoalTest;

import org.junit.jupiter.api.Test;
import org.projectsw.Config;
import org.projectsw.Model.CommonGoal.CommonGoal;
import org.projectsw.Model.CommonGoal.CommonGoalStrategy;
import org.projectsw.Model.CommonGoal.RowColumn;
import org.projectsw.Model.Shelf;
import org.projectsw.Model.Tile;
import org.projectsw.Model.TilesEnum;

import static org.junit.jupiter.api.Assertions.*;

class RowColumnTest {

    /**
     * Checks if there are enough columns with all different tiles
     */
    @Test
    void trueDifferentColumn() {
        CommonGoalStrategy strategy = new RowColumn(2);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i = 0; i< Config.shelfLength; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), i,5);
            }catch(Exception ignore){}
        }
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are not enough columns with all different tiles
     */
    @Test
    void falseDifferentColumn() {
        CommonGoalStrategy strategy = new RowColumn(2);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<1; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.TROPHIES, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), i,5);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are too much Tile types in the columns
     */
    @Test
    void fewTilesTypesDifferentColumn() {
        CommonGoalStrategy strategy = new RowColumn(2);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<3; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), i,5);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are empty Tile types in the columns
     */
    @Test
    void emptyTypesDifferentColumn() {
        CommonGoalStrategy strategy = new RowColumn(2);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<3; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), i,5);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf is empty
     */
    @Test
    void emptyShelfDifferentColumn() {
        CommonGoalStrategy strategy = new RowColumn(2);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are at least 3 columns with at most 3 different tile types
     */
    @Test
    void trueColumnGroup() {
        CommonGoalStrategy strategy = new RowColumn(5);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfLength; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,5);
            }catch(Exception ignore){}
        }
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are less than 3 columns with at most 3 different tile types
     */
    @Test
    void falseColumnGroup() {
        CommonGoalStrategy strategy = new RowColumn(5);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<2; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,5);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the columns have more than 3 Tiles type
     */
    @Test
    void tooMuchTypesTypesColumnGroup() {
        CommonGoalStrategy strategy = new RowColumn(5);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfLength; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,5);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the columns have empty Tiles
     */
    @Test
    void emptyTypesColumnGroup() {
        CommonGoalStrategy strategy = new RowColumn(5);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfLength; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,5);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf is empty
     */
    @Test
    void emptyShelfTypesColumnGroup() {
        CommonGoalStrategy strategy = new RowColumn(5);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are at least 2 row with all different Tiles types
     */
    @Test
    void trueDifferentRows() {
        CommonGoalStrategy strategy = new RowColumn(6);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfHeight; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are less than 2 rows that all have different tiles
     */
    @Test
    void falseDifferentRows() {
        CommonGoalStrategy strategy = new RowColumn(6);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<1; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.FRAMES, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are too much Tiles types in the shelf rows
     */
    @Test
    void fewTilesTypesDifferentRows() {
        CommonGoalStrategy strategy = new RowColumn(6);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfHeight; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are empty Tiles types in the shelf rows
     */
    @Test
    void emptyTilesDifferentRows() {
        CommonGoalStrategy strategy = new RowColumn(6);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfHeight; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf is empty
     */
    @Test
    void emptyShelfDifferentRows() {
        CommonGoalStrategy strategy = new RowColumn(6);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are at least 4 row with at most 3 different tile types
     */
    @Test
    void trueRowGroups() {
        CommonGoalStrategy strategy = new RowColumn(7);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfHeight; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertTrue(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are less than 4 rows row with at most 3 different tile types
     */
    @Test
    void falseRowGroups() {
        CommonGoalStrategy strategy = new RowColumn(7);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<3; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are less than 4 rows, but they have more than 4 Tiles types
     */
    @Test
    void tooMuchTilesTypesRowGroups() {
        CommonGoalStrategy strategy = new RowColumn(7);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<Config.shelfHeight; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 0,i);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), 1,i);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), 2,i);
                shelf.insertTiles(new Tile(TilesEnum.BOOKS, 0), 3,i);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), 4,i);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if there are empty tiles in the rows
     */
    @Test
    void emptyTilesTypesRowGroups() {
        CommonGoalStrategy strategy = new RowColumn(7);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        for(int i=0; i<6; i++) {
            try {
                shelf.insertTiles(new Tile(TilesEnum.EMPTY, 0), i,0);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,1);
                shelf.insertTiles(new Tile(TilesEnum.GAMES, 0), i,2);
                shelf.insertTiles(new Tile(TilesEnum.CATS, 0), i,3);
                shelf.insertTiles(new Tile(TilesEnum.PLANTS, 0), i,4);
            }catch(Exception ignore){}
        }
        assertFalse(common.checkRequirements(shelf));
    }

    /**
     * Checks if the player's shelf is empty
     */
    @Test
    void emptyShelfTypesRowGroups() {
        CommonGoalStrategy strategy = new RowColumn(7);
        CommonGoal common = new CommonGoal(strategy);
        Shelf shelf = new Shelf();

        assertFalse(common.checkRequirements(shelf));
    }
}