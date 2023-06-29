package org.projectsw.Distributed.SocketMiddleware;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;
import org.projectsw.Distributed.Messages.ResponseMessages.ResponseMessage;
import org.projectsw.Distributed.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Represents a server Stub that acts as a remote proxy for a server.
 * Implements the Server interface and Serializable interface.
 */
public class ServerStub implements Server {
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerStub(String ip, int port){
        this.ip=ip;
        this.port=port;
    }

    /**
     * Registers a client with the server by establishing a socket connection and initializing the input and output streams.
     * @param client the client to register
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("An error while creating output stream: ", e);
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("An error while creating input stream: ", e);
            }
        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server: ", e);
        }
    }

    /**
     * Sends an input message to the server for processing.
     * @param client the client sending the input message
     * @param input  the input message to send
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void update(Client client, InputMessage input) throws RemoteException {
        try {
            oos.writeObject(input);
            oos.flush();
            oos.reset();
        } catch (IOException e) {
            throw new RemoteException("An error while sending an input message: ", e);
        }
    }

    @Override
    public void startPingThread() throws RemoteException {

    }

    /**
     * Receives a response message from the server and updates the client.
     * @param client the client receiving the response message
     * @throws RemoteException if a remote communication error occurs
     */
    public void receive(Client client) throws RemoteException{
        ResponseMessage responseMessage;
        try {
            responseMessage = (ResponseMessage) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("An error while receiving model view from client: ", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("An error while deserializing model view from client: ", e);
        }
        client.update(responseMessage);
    }

    /**
     * Closes the socket connection to the server.
     * @throws RemoteException if a remote communication error occurs
     */
    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("An error while closing socket: ", e);
        }
    }
}