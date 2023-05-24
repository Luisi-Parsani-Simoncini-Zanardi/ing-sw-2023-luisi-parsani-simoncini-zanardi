package org.projectsw.Distributed.Messages.ResponseMessages;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.ConsoleColors;
import org.projectsw.View.GraphicalUI;
import org.projectsw.View.TextualUI;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultsNotify extends ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResultsNotify(SerializableGame model) {
        super(model);
    }
    public void execute(TextualUI tui){
        LinkedHashMap<String, Integer> results = model.getResults().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println("-----RESULTS-----");
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
    public void execute(GraphicalUI gui){}
}
