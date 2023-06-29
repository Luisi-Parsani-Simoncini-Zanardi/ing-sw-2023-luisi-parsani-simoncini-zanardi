package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that the nickname has been denied.
 * It extends the MessageFrame class.
 */
public class NicknameDeniedFrame extends MessageFrame {

    /**
     * Constructs a NicknameDeniedFrame object.
     * It displays a message dialog indicating that the nickname has been denied.
     */
    public NicknameDeniedFrame(){
        super();
        JOptionPane.showMessageDialog(NicknameDeniedFrame.this,"Nickname denied");
    }
}
