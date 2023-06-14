package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class NicknameDeniedFrame extends JFrame {
    public NicknameDeniedFrame(){
        JOptionPane.showMessageDialog(NicknameDeniedFrame.this,"Nickname denied, it's already used by another player");
    }
}
