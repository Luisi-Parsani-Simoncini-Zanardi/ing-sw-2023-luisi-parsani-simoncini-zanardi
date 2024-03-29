package org.projectsw.View.GraphicalUI;

import org.projectsw.Util.Config;
import javax.swing.*;

/*
 * This class is the frame where the player can insert his nickname.
 */
public class NicknameFrame extends StartingMenuFrame {

    /**
     * Creates the nickname frame with a JTextField where the user can insert his nickname.
     * It also adds to the frame an option pane, if the user press OK it sends the nickname, if press Cancel the process closes.
     * @param guiManager The GuiManager object used to send back the messages.
      */
    public NicknameFrame(GuiManager guiManager) {
        super(guiManager);

        boolean nicknameInsert = false;
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Nickname:"));
        JTextField nicknameField = new JTextField(20);
        panel.add(nicknameField);
        do {
            int option = JOptionPane.showConfirmDialog(null, panel, "Nickname Input", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nickname = nicknameField.getText();
                if (nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(NicknameFrame.this, "Please insert a nickname before clicking \"OK\"");
                } else {
                    if(nickname.equals(Config.broadcastID)){
                        JOptionPane.showMessageDialog(NicknameFrame.this, "You can't choose the broadcast nickname");
                    } else {
                        nicknameInsert = true;
                        guiManager.sendNickname(nickname);
                    }
                }
            }
            if (option == JOptionPane.CANCEL_OPTION){
                nicknameInsert = true;
                guiManager.kill(1);
            }
        } while(!nicknameInsert);
    }
}
