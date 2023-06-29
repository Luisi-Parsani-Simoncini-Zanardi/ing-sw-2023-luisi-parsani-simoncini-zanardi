package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.GraphicalUI.GuiManager;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a response message indicating a results notify response.
 * Extends the ResponseMessage class and implements the Serializable interface.
 */
public class ResultsNotify extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ResultsNotify object with the specified SerializableGame.
     * @param model the SerializableGame object representing the response message
     */
    public ResultsNotify(SerializableGame model) {
        super(model);
    }

    /**
     * Executes the ResultsNotify message on the specified TextualUI.
     * Retrieves the game results from the model, sorts them in descending order,
     * and displays the results along with the position of the current player.
     * Updates the TextualUI to indicate that it is no longer waiting for results.
     * @param tui the TextualUI on which to execute the action
     */
    public void execute(TextualUI tui){
        LinkedHashMap<String, Integer> results = model.getResults().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println("\n\n-----RESULTS-----");
        for (String i : results.keySet()) {
            System.out.println(i + ": " + results.get(i) + " points");
        }
        int position = (new ArrayList<>(results.keySet()).indexOf(tui.getNickname())) +1;
        switch (position) {
            case 1 -> tui.printMedal(ConsoleColors.GOLD, "1st");
            case 2 -> tui.printMedal(ConsoleColors.SILVER, "2nd");
            case 3 -> tui.printMedal(ConsoleColors.BRONZE, "3rd");
            case 4 -> tui.printNoMedal();
        }
        tui.setWaitResult(false);
    }

    public void execute(GuiManager gui){
        LinkedHashMap<String, Integer> results = model.getResults().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        gui.setResults(results);
    }
}
