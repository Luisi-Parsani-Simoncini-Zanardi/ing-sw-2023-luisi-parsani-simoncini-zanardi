package org.projectsw.View.GraphicalUI;

import org.projectsw.View.GraphicalUI.MessagesGUI.MessageFrame;
import javax.swing.*;
import java.util.LinkedHashMap;

/**
 * This class represents a frame that displays the results of the game.
 * It extends the MessageFrame class.
 */
public class ResultsFrame extends MessageFrame {


    /**
     * Constructs a ResultsFrame with the provided results.
     * @param results A LinkedHashMap containing the results, where the keys represent the player names
     *                and the values represent their respective points.
     */
    public ResultsFrame(LinkedHashMap<String, Integer> results) {
        super();
        StringBuilder resultsString = new StringBuilder("Results:\n");
        for (String i : results.keySet()) {
            resultsString.append(i).append(": ").append(results.get(i)).append(" points\n");
        }
        JOptionPane.showMessageDialog(ResultsFrame.this,resultsString.toString());
    }
}
