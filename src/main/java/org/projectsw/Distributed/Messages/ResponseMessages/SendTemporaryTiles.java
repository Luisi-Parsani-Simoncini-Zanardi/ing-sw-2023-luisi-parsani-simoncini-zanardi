package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.Model.Tile;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.TextualUI;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class SendTemporaryTiles extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public SendTemporaryTiles(SerializableGame model) {
        super(model);
    }
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
}
