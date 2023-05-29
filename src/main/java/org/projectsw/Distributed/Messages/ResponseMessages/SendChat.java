package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.Message;
import org.projectsw.Model.SerializableGame;
import org.projectsw.Util.Config;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;

public class SendChat extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public SendChat(SerializableGame model) {
        super(model);
    }
    @Override
    public void execute(TextualUI tui) {
        int counter = 0;
        if (model.getPlayerName().equals(Config.everyone)){
            System.out.println("---GLOBAL CHAT---");
            for (Message message : model.getChat()) {
                if (message.getScope().equals(Config.everyone)) {
                    System.out.println(tui.getNameColors().get(message.getSender()) + message.getSender() + ": " + ConsoleColors.RESET + message.getPayload());
                    counter++;
                }
            }
        }
        else {
            System.out.println("---CHAT WITH "+model.getPlayerName()+"---");
            for (Message message : model.getChat()) {
                if ((message.getScope().equals(tui.getNickname()) && message.getSender().equals(model.getPlayerName())) ||
                        (message.getScope().equals(model.getPlayerName()) && message.getSender().equals(tui.getNickname()))) {
                    System.out.println(tui.getNameColors().get(message.getSender()) + message.getSender() + ": " + ConsoleColors.RESET + message.getPayload());
                    counter++;
                }
            }
        }
        if(counter == 0)
            System.err.println("This chat is empty...");
        System.out.print("\n");
    }
}
