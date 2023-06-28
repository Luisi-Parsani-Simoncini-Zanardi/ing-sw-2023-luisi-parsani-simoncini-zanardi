package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.View.GraphicalUI.GuiManager;

import javax.swing.*;
import java.awt.*;

public class NoSelectableShelf extends JPanel {

    public NoSelectableShelf(Tile[][] shelf) {
        super();
        setLayout(new BorderLayout());
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(7,5,4,4));
        for(int i=Config.shelfHeight-1; i>=0; i--) {
            for(int j=0; j<Config.shelfLength; j++) {
                Tile tile = shelf[i][j];
                NoSelectableTile noSelectableTile = new NoSelectableTile(tile);
                gridPanel.add(noSelectableTile);
            }
        }
        gridPanel.setPreferredSize(new Dimension(15,15));
        add(gridPanel,BorderLayout.CENTER);
    }
}
