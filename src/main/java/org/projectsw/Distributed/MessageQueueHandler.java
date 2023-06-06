package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueueHandler implements Runnable {
    private final ConcurrentLinkedQueue<InputMessage> messages = new ConcurrentLinkedQueue<>();
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
    public void add(InputMessage input){
        messages.offer(input);
    }
}