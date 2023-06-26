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
    GuiManager guiManager;

    public SelectableColumnShelf(Tile[][] shelf, GuiManager  guiManager) {
        super();
        this.guiManager = guiManager;

        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(7,5,4,4));
        for(int i = 0; i< Config.shelfHeight; i++) {
            for(int j=0; j<Config.shelfLength; j++) {
                Tile tile = shelf[i][j];
                NoSelectableTile noSelectableTile = new NoSelectableTile(tile);
                gridPanel.add(noSelectableTile);
            }
        }
        for(int h=0;h<Config.shelfLength;h++) {
            ColumnButton columnButton = new ColumnButton("^ Place in this column ^",h);
            columnButton.addActionListener(e -> {
                if(e.getSource() instanceof ColumnButton selectedColumnButton) {
                    SwingUtilities.invokeLater( () -> {
                        guiManager.sendColumnSelection(selectedColumnButton.getColumn());
                    });
                }
            });
            gridPanel.add(columnButton);
        }
        gridPanel.setPreferredSize(new Dimension(15,15));
        add(gridPanel,BorderLayout.CENTER);
    }
}
