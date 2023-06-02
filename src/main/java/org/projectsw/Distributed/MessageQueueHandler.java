package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Util.InputAndClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class MessageQueueHandler extends Thread {
    private final ArrayList<InputMessage> messages = new ArrayList<>();
    private final Engine engine;

    public MessageQueueHandler(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void run() {
        while (true) {
            if (messages.size() > 0) {
               InputMessage message = messages.remove(0);
                try {
                    message.execute(engine);
                    try {
                        waitForPreviousMethod();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (RemoteException e) {
                    System.err.println("Unable to process request from client: "+message.getInput().getAlphanumericID()+"\nError: "+e.getMessage());
                }
            }
        }
    }
    private void waitForPreviousMethod() throws InterruptedException {
        while(!engine.isMethodComplete())
            wait();
        engine.setMethodComplete(false);
    }
    public ArrayList<InputMessage> getMessages() {
        return messages;
    }
}