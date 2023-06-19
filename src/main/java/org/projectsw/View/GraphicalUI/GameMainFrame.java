package org.projectsw.View.GraphicalUI;

import org.projectsw.Model.SerializableGame;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableBoard;

import javax.swing.*;
import java.awt.*;

public class GameMainFrame extends JFrame {

    private GuiManager guiManager;
    private final Object lock = new Object();
    private final Object finalLock = new Object();

    public GameMainFrame(GuiManager guiManager){

        this.guiManager = guiManager;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(bounds.width, bounds.height);

        Panel panelLeft = new Panel();
        panelLeft.setPreferredSize(new Dimension(200,200));
        panelLeft.setBackground(Color.blue);
        add(panelLeft,BorderLayout.WEST);

        Panel panelRight = new Panel();
        panelRight.setPreferredSize(new Dimension(200,200));
        panelRight.setBackground(Color.yellow);
        add(panelRight,BorderLayout.EAST);

        Panel panelUp = new Panel();
        panelUp.setPreferredSize(new Dimension(200,100));
        panelUp.setBackground(Color.green);
        add(panelUp,BorderLayout.NORTH);

        Panel panelDown = new Panel();
        panelDown.setPreferredSize(new Dimension(200,200));
        panelDown.setBackground(Color.red);
        add(panelDown,BorderLayout.SOUTH);

        SelectableBoard selectableBoard = new SelectableBoard();
        add(selectableBoard,BorderLayout.CENTER);

        //askForBoard();

        setVisible(true);
        waitFinalLock();
    }

    public SelectableBoard updateBoard(SerializableGame game){
        return new SelectableBoard(game,guiManager);
    }

    private void askForBoard() {
        guiManager.askBoard(this);
    }

    private void waitResponse(){
        synchronized (lock){
            try{
                lock.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyResponse(){
        synchronized (lock){
            lock.notify();
        }
    }

    private void waitFinalLock(){
        synchronized (finalLock){
            try{
                finalLock.wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void notifyFinalLock(){
        synchronized (finalLock){
            finalLock.notify();
        }
    }
}
