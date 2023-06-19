package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GuiManager;

import javax.swing.*;
import java.awt.*;

public class NoSelectableShelf extends JPanel {
    private final GuiManager guiManager;

    public NoSelectableShelf(SerializableGame game, GuiManager guiManager) {
        super();
        setLayout(new GridLayout(6,5));
        this.guiManager = guiManager;

        Tile[][] shelf = game.getPlayerShelf();

        for(int i = 0; i< Config.shelfLength; i++) {
            for(int j=0;j<Config.shelfHeight;j++) {
                Tile tile = shelf[i][j];
                NoSelectableTile noSelectableTile = new NoSelectableTile(tile);
                add(noSelectableTile);
            }
        }
    }
}
