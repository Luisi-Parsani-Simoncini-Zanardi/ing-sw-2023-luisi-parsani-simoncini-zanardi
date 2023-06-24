package org.projectsw.View.GraphicalUI.GuiModel;

import javax.swing.*;

public class ColumnButton extends JButton {
    private final int column;
    public ColumnButton(String label,int column) {
        super(label);
        this.column = column;
    }
    public int getColumn() {
        return column;
    }
}
