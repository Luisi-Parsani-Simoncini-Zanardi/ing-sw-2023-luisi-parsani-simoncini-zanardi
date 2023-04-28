package org.projectsw.View;

import org.projectsw.Util.Observable;



public class TextualUI extends Observable<TextualUI.Event>{
    public enum Event{
        TILE_SELECTION, COLUMN_SELECTION, TILE_INSERTION
    }
}
