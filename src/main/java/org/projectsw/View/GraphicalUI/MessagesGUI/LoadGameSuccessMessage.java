package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class LoadGameSuccessMessage extends MessageFrame{
    public LoadGameSuccessMessage(){
        super();
        JOptionPane.showMessageDialog(LoadGameSuccessMessage.this,"Game successfully loaded!");
    }
}
