package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class UnselectableTileMessage extends MessageFrame {
    public UnselectableTileMessage() {
        super();
        JOptionPane.showMessageDialog(UnselectableTileMessage.this,"You can't select this tile!");
    }
}
