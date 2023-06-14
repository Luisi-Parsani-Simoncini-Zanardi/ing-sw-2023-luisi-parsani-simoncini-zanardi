package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class LoadGameNickDeniedMessageFrame extends JFrame {
    public LoadGameNickDeniedMessageFrame(){
        JOptionPane.showMessageDialog(LoadGameNickDeniedMessageFrame.this, "This nickname was not present in the game you're trying to reload");
    }
}
