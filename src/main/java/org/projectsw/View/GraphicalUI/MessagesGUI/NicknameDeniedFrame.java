package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class NicknameDeniedFrame extends MessageFrame {
    public NicknameDeniedFrame(){
        super();
        JOptionPane.showMessageDialog(NicknameDeniedFrame.this,"Nickname denied, it's already used by another player");
    }
}
