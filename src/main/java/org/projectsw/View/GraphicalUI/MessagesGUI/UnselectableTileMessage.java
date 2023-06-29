package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;


/**
 * This class represents a message frame indicating that a tile is unselectable.
 * It extends the MessageFrame class.
 */
public class UnselectableTileMessage extends MessageFrame {

    /**
     * Constructs an UnselectableTileMessage object.
     * It displays a message dialog indicating that the tile cannot be selected.
     */
    public UnselectableTileMessage() {
        super();
        JOptionPane.showMessageDialog(UnselectableTileMessage.this,"You can't select this tile!");
    }
}
