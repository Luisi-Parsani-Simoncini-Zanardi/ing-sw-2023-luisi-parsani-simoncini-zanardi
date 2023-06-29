package org.projectsw.View.GraphicalUI.MessagesGUI;

import javax.swing.*;

/**
 * This class represents a message frame indicating that the player needs to reinsert their nickname due to a reload of a previous game.
 * It extends the MessageFrame class.
 */
public class ReinsertNicknameMessage extends MessageFrame{

    /**
     * Constructs a ReinsertNicknameMessage object.
     * It displays a message dialog indicating that the first player has chosen to reload a previous game and the player needs to reinsert their nickname.
     * The player should make sure to enter their previous nickname.
     */
    public ReinsertNicknameMessage(){
        super();
        JOptionPane.showMessageDialog(ReinsertNicknameMessage.this, "Seems that the first player chooses to reload a previous game!\n" +
                "You have to reinsert your nick (be sure tu insert your previous nickname!)");
    }
}
