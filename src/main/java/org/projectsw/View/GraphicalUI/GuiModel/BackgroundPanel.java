package org.projectsw.View.GraphicalUI.GuiModel;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a panel with a background image.
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private int width;
    private int height;

    /**
     * Constructs a BackgroundPanel object with the specified background image, width, and height.
     * @param backgroundImage the background image to be displayed on the panel.
     * @param width the width of the panel.
     * @param height the height of the panel.
     */
    public BackgroundPanel(Image backgroundImage, int width, int height) {
        this.backgroundImage = backgroundImage;
        this.width = width;
        this.height = height;
    }

    /**
     * Paints the background image on the panel.
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, this);
        }
    }
}