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

        ImageIcon backgroundImage = new ImageIcon("src/main/resources/ImagesGui/Boards/Shelf.png");

        // Crea il pannello con sfondo
        BackgroundPanel gridPanel = new BackgroundPanel(backgroundImage.getImage(), 1200, 600);

        gridPanel.setLayout(new GridLayout(7,5,50,10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 0, 150));
        for(int i=Config.shelfHeight-1; i>=0; i--) {
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
