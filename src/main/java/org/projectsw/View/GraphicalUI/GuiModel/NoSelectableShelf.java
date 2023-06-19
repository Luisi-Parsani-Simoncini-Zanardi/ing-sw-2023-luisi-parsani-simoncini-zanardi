package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GuiManager;

import javax.swing.*;
import java.awt.*;

public class NoSelectableShelf extends JPanel {

    public NoSelectableShelf(SerializableGame game) {
        super();
        setLayout(new GridLayout(6,5,4,4));
        Tile[][] shelf = game.getPlayerShelf();
        for(int i=0; i<Config.shelfHeight; i++) {
            for(int j=0; j<Config.shelfLength; j++) {
                Tile tile = shelf[i][j];
                NoSelectableTile noSelectableTile = new NoSelectableTile(tile);
                add(noSelectableTile);
            }
        }
    }
}
