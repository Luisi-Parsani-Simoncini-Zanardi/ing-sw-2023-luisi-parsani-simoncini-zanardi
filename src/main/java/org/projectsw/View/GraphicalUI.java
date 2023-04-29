package org.projectsw.View;

import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Util.Observable;

public class GraphicalUI extends Observable<UIEvent> implements Runnable{

    public void update(GameView model, Game.Event arg){}

    @Override
    public void run() {

    }
}
