package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;

import javax.swing.*;
import java.awt.*;

public class SelectableColumnShelf extends JPanel {
    public SelectableColumnShelf(SerializableGame game) {
        super();
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
            JButton columnButton = new JButton("^ Place in this column ^");
            columnButton.addActionListener(e -> {

            });
            add(columnButton);
        }
    }
}
