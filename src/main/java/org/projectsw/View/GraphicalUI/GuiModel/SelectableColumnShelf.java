package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Enums.GameState;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GameMainFrame;
import org.projectsw.View.GraphicalUI.GuiManager;

import javax.swing.*;
import java.awt.*;

public class SelectableColumnShelf extends JPanel {

    GameMainFrame gameMainFrame;
    GuiManager guiManager;

    public SelectableColumnShelf(SerializableGame game, GuiManager  guiManager, GameMainFrame gameMainFrame) {
        super();

        this.guiManager = guiManager;
        this.gameMainFrame = gameMainFrame;

        setLayout(new GridLayout(7,5,4,4));
        Tile[][] shelf = game.getPlayerShelf();
        for(int i = 0; i< Config.shelfHeight; i++) {
            for(int j=0; j<Config.shelfLength; j++) {
                Tile tile = shelf[i][j];
                NoSelectableTile noSelectableTile = new NoSelectableTile(tile);
                add(noSelectableTile);
            }
        }
        for(int h=0;h<Config.shelfLength;h++) {
            ColumnButton columnButton = new ColumnButton("^ Place in this column ^",h);
            columnButton.addActionListener(e -> {
                guiManager.sendColumnSelection(columnButton.getColumn(),gameMainFrame);
            });
            add(columnButton);
        }
    }
}
