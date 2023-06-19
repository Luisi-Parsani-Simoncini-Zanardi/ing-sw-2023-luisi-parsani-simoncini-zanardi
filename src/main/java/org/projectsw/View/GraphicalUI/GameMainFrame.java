package org.projectsw.View.GraphicalUI;

import org.projectsw.Distributed.Messages.InputMessages.AskForCurrentPlayer;
import org.projectsw.View.Enums.UIEndState;
import org.projectsw.View.GraphicalUI.GuiModel.NoSelectableShelf;
import org.projectsw.View.GraphicalUI.GuiModel.SelectableBoard;
import org.projectsw.View.GraphicalUI.MessagesGUI.UnselectableTileMessage;
import org.projectsw.View.SerializableInput;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class GameMainFrame extends JFrame {

    private final GuiManager guiManager;
    private final Object lock = new Object();
    private final Object finalLock = new Object();
    private boolean selectionAccepted = true;

    public GameMainFrame(GuiManager guiManager){

        this.guiManager = guiManager;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(bounds.width, bounds.height);

        Panel panelLeft = new Panel();
        panelLeft.setBackground(Color.blue);
        panelLeft.setPreferredSize(new Dimension(200,200));
        add(panelLeft,BorderLayout.WEST);

        Panel panelRight = new Panel();
        panelRight.setBackground(Color.yellow);
        panelRight.setPreferredSize(new Dimension(200,200));
        add(panelRight,BorderLayout.EAST);

        Panel centralPanel = new Panel();
        centralPanel.setLayout(new BorderLayout());
        add(centralPanel,BorderLayout.CENTER);

        Panel panelUp = new Panel();
        JLabel currentPlayerLabel = new JLabel(guiManager.askForCurrentPlayerString());
        currentPlayerLabel.setHorizontalTextPosition(JLabel.CENTER);
        currentPlayerLabel.setVerticalTextPosition(JLabel.CENTER);
        panelUp.add(currentPlayerLabel);
        panelUp.setPreferredSize(new Dimension(200,200));
        centralPanel.add(panelUp,BorderLayout.NORTH);

        Panel panelDown = new Panel();
        panelDown.setLayout(new FlowLayout());
        panelDown.setBackground(Color.red);
        panelDown.setPreferredSize(new Dimension(200,200));
        centralPanel.add(panelDown,BorderLayout.SOUTH);

        JTabbedPane boardAndShelfTabbedPane = new JTabbedPane();
        centralPanel.add(boardAndShelfTabbedPane,BorderLayout.CENTER);
        SelectableBoard selectableBoard = askForBoard();
        boardAndShelfTabbedPane.add("Board",selectableBoard);
        NoSelectableShelf noSelectableShelf = askForNsShelf();
        boardAndShelfTabbedPane.add("Your Shelf",noSelectableShelf);
        setVisible(true);

        do{
            waitResponse();
            if (selectionAccepted) {
                boardAndShelfTabbedPane.remove(selectableBoard);
                boardAndShelfTabbedPane.remove(noSelectableShelf);
                selectableBoard = askForBoard();
                boardAndShelfTabbedPane.add("Board",selectableBoard);
                noSelectableShelf = askForNsShelf();
                boardAndShelfTabbedPane.add("Your Shelf",noSelectableShelf);
                revalidate();
                repaint();
            } else {
                new UnselectableTileMessage();
            }

        } while(!guiManager.getEndState().equals(UIEndState.ENDING));

        waitFinalLock();
    }

    public void setSelectionAccepted(boolean selectionAccepted) {
        this.selectionAccepted = selectionAccepted;
    }

    private SelectableBoard askForBoard() {
        return guiManager.askBoard(this);
    }

    private  NoSelectableShelf askForNsShelf() {
        return guiManager.askNsShelf();
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
