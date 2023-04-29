package org.projectsw.View;

import org.projectsw.Model.Board;
import org.projectsw.Model.Game;
import org.projectsw.Model.GameView;
import org.projectsw.Model.Message;
import org.projectsw.Util.Observable;

public class TextualUI extends Observable<UIEvent> implements Runnable{

    @Override
    public void run() {

    }

    public void update(GameView model, Game.Event arg){
        switch(arg){
            case UPDATED_BOARD -> showBoard(model);
            case UPDATED_SHELF -> showShelf(model);
            case UPDATED_CURRENT_PLAYER -> showCurrentPlayer(model);
            case UPDATED_CHAT -> showChat(model);
        }
    }

    private void showBoard(GameView model){
        Board board = model.getGameBoard();
        if(board == null)
            return;
        System.out.println("\n");
        board.printBoard();
    }

    private void showShelf(GameView model){
        System.out.println("\n--- "+model.getCurrentPlayerName()+" ---\n");
        model.getCurrentPlayerShelf().printShelf();
    }

    private void showCurrentPlayer(GameView model){
        System.out.println("\n"+model.getCurrentPlayerName());
    }

    private void showChat(GameView model){
        for(Message message : model.getChat().getChat())
            System.out.println("\n"+message.getSender().getNickname()+": "+message.getContent());
    }
}
