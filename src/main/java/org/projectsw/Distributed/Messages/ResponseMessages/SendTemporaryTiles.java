package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a response message indicating a temporary tiles response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class SendTemporaryTiles extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SendTemporaryTiles object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public SendTemporaryTiles(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the SendTemporaryTiles message on the specified TextualUI.
     * Displays the selected tiles from the model along with their corresponding colors.
     * @param tui the TextualUI on which to execute the action
     */
    @Override
    public void execute(TextualUI tui) {
        System.out.println("You have selected: ");
        ArrayList<Tile> tiles = model.getTemporaryTiles();
        for (int i = 0; i < tiles.size(); i++) {
            int integer = i + 1;
            switch (tiles.get(i).getTile()) {
                case CATS -> System.out.println(integer + " " + ConsoleColors.CATS);
                case TROPHIES -> System.out.println(integer + " " + ConsoleColors.TROPHIES);
                case BOOKS -> System.out.println(integer + " " + ConsoleColors.BOOKS);
                case FRAMES -> System.out.println(integer + " " + ConsoleColors.FRAMES);
                case GAMES -> System.out.println(integer + " " + ConsoleColors.GAMES);
                case PLANTS -> System.out.println(integer + " " + ConsoleColors.PLANTS);
            }
        }
    }

    @Override
    public void execute(GuiManager guiManager) {
        guiManager.updateModel(model);
    }
}
