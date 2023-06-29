package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Model.Tile;
import org.projectsw.Util.Config;
import org.projectsw.Util.PathSolverGui;

import javax.swing.*;
import java.awt.*;

/*
 * This class represents the JPanel equivalent to the Shelf, with, at the bottom some buttons to select the column.
 */
public class NoSelectableShelf extends JPanel {

    /**
     * Constructs the JPanel equivalent to the Shelf.
     * @param shelf the shelf to copy.
     */
    public NoSelectableShelf(Tile[][] shelf) {
        super();

        ImageIcon backgroundImage = new ImageIcon(PathSolverGui.shelfPath());

        // Crea il pannello con sfondo
        BackgroundPanel gridPanel = new BackgroundPanel(backgroundImage.getImage(), 1200, 600);

        setLayout(new BorderLayout());
        gridPanel.setLayout(new GridLayout(7,5,55,10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 0, 150));
        for(int i=Config.shelfHeight-1; i>=0; i--) {
            for(int j=0; j<Config.shelfLength; j++) {
                Tile tile = shelf[i][j];
                NoSelectableTile noSelectableTile = new NoSelectableTile(tile);
                gridPanel.add(noSelectableTile);
            }
        }

        gridPanel.setPreferredSize(new Dimension(10,15));
        add(gridPanel,BorderLayout.CENTER);
    }
}
