package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

public class ReinsertNicknameMessage extends MessageFrame{
    public ReinsertNicknameMessage(){
        super();
        JOptionPane.showMessageDialog(ReinsertNicknameMessage.this, "Seems that the first player chooses to reload a previous game!\n" +
                "You have to reinsert your nick (be sure tu insert your previous nickname!)");
    }
}
