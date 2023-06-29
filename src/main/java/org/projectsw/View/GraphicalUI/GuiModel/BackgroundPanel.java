package org.projectsw.View.GraphicalUI.GuiModel;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    private int width;

    private int height;

    public BackgroundPanel(Image backgroundImage, int width, int height) {
        this.backgroundImage = backgroundImage;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, this);
        }
    }
}