package org.projectsw.View.GraphicalUI.GuiModel;

import javax.swing.*;

/*
 * This class represent an extension of JButton to resolve the necessity to associate a number (the column number)
 * to the button in phase of column selection.
 */
public class ColumnButton extends JButton {
    private final int column;

    /**
     * Constructs the column button.
     * @param label the label to put on the button.
     * @param column the number to associate at the button.
     */
    public ColumnButton(String label,int column) {
        super(label);
        this.column = column;
        setContentAreaFilled(false);
    }

    /**
     * Returns the number associated to the button
     * @return the int corresponding to the index of the selected column.
     */
    public int getColumn() {
        return column;
    }
}
