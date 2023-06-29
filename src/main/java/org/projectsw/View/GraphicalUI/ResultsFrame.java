package org.projectsw.View.GraphicalUI;

import org.projectsw.View.GraphicalUI.MessagesGUI.MessageFrame;
import javax.swing.*;
import java.util.LinkedHashMap;

public class ResultsFrame extends MessageFrame {
    public ResultsFrame(LinkedHashMap<String, Integer> results) {
        super();
        StringBuilder resultsString = new StringBuilder("Results:\n");
        for (String i : results.keySet()) {
            resultsString.append(i).append(": ").append(results.get(i)).append(" points\n");
        }
        JOptionPane.showMessageDialog(ResultsFrame.this,resultsString.toString());
    }
}
