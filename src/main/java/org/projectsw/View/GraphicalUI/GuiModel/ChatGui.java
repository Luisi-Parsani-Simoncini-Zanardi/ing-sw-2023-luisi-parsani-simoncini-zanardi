package org.projectsw.View.GraphicalUI.GuiModel;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.ChatMessage;
import org.projectsw.Model.Chat;
import org.projectsw.Model.Message;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.SerializableInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * This class represents the Chet, putting it on a panel.
 */
public class ChatGui extends JPanel {
    private JTextArea chatLogTextArea;
    private JTextField inputTextField;

    /**
     * Constructs a ChatGui object.
     * @param guiManager the GuiManager instance.
     * @param chat the ArrayList of Message objects representing the chat.
     * @param client the Client object associated with the chat.
     * @param alphanumericKey the alphanumeric key used for serialization.
     * @param nickname the nickname of the user.
     */
    public ChatGui(GuiManager guiManager, ArrayList<Message> chat, Client client, String alphanumericKey, String nickname) {
        setLayout(new BorderLayout());

        // Creazione del log della chat
        chatLogTextArea = new JTextArea();
        chatLogTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatLogTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Creazione dell'input di testo per la chat
        inputTextField = new JTextField();
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputTextField.getText();
                try {
                    guiManager.setChangedAndNotifyObservers(new ChatMessage(new SerializableInput(alphanumericKey, nickname,message, client)));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                inputTextField.setText("");
            }
        });
        add(inputTextField, BorderLayout.SOUTH);

        appendToChatLog(chat);
    }

    /**
     * Appends the messages from the chat to the chat log.
     * @param chat The ArrayList of Message objects representing the chat.
     */
    public void appendToChatLog(ArrayList<Message> chat) {
        for(Message message : chat) {
            chatLogTextArea.append(message.getSender() + ": " + message.getPayload() + "\n");
            chatLogTextArea.setCaretPosition(chatLogTextArea.getDocument().getLength());
        }
    }
}
