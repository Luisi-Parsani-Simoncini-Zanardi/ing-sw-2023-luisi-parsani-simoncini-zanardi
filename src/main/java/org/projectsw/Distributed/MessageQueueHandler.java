package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Util.InputAndClient;

import java.rmi.RemoteException;
import java.util.PriorityQueue;

public class MessageQueueHandler extends Thread {
    private final PriorityQueue<InputMessage> messages = new PriorityQueue<>();
    private final Engine engine;

    public MessageQueueHandler(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void run() {
        while (true) {
            if (messages.size() > 0) {
               InputMessage message = messages.poll();
                try {
                    message.execute(engine);
                } catch (RemoteException e) {
                    System.err.println("Unable to process request from client: "+message.getInput().getAlphanumericID()+"\nError: "+e.getMessage());
                }
            }
        }
    }

    public PriorityQueue<InputMessage> getMessages() {
        return messages;
    }
}