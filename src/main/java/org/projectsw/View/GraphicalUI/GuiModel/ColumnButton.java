package org.projectsw.View.GraphicalUI.GuiModel;

import javax.swing.*;

/*
 * This class represent an extension of JButton to resolve the necessity to associate a number (the column number)
 * to the button in phase of column selection.
 */
public class ColumnButton extends JButton {
    private final int column;
    public ColumnButton(String label,int column) {
        super(label);
        this.column = column;
        setContentAreaFilled(false);
    }
    public int getColumn() {
        return column;
    }
}
