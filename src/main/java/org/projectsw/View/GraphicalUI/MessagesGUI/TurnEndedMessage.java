package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class TurnEndedMessage extends MessageFrame {
    public TurnEndedMessage(){
        super();
        JOptionPane.showMessageDialog(TurnEndedMessage.this, "Your turn has ended!");
    }
}
