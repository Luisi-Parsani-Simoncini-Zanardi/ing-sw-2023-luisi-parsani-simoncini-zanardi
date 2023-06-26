package org.projectsw.Distributed;

import org.projectsw.Controller.Engine;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The MessageQueueHandler class is responsible for handling input messages from clients.
 * It implements the Runnable interface to run in a separate thread.
 * The class maintains a concurrent linked queue to store input messages.
 * It executes the input messages sequentially by invoking their execute() method on the provided Engine.
 */
public class MessageQueueHandler implements Runnable {
    private final ConcurrentLinkedQueue<InputMessage> messages = new ConcurrentLinkedQueue<>();
    private final Engine engine;

    /**
     * Constructs a new MessageQueueHandler with the specified Engine.
     * @param engine the Engine to process the input messages
     */
    public MessageQueueHandler(Engine engine) {
        this.engine = engine;
    }

    /**
     * Runs the MessageQueueHandler in a separate thread.
     * It continuously checks for input messages in the queue and processes them by invoking their execute() method on the Engine.
     */
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

    /**
     * Adds an InputMessage to the message queue.
     * @param input the InputMessage to be added to the queue
     */
    public void add(InputMessage input){
        messages.offer(input);
    }
}