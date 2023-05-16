package org.projectsw.View;

import org.projectsw.Model.Enums.GameEvent;
import org.projectsw.Model.GameView;
import org.projectsw.Util.Observable;
import org.projectsw.View.Enums.UIEvent;
import org.projectsw.View.Enums.UITurnState;

public class GraphicalUI extends Observable<UIEvent> implements Runnable{

    private UITurnState state;

    public void setState(UITurnState state){this.state = state;}
    public void update(GameView model, GameEvent arg){}

    @Override
    public void run() {

    }
}
