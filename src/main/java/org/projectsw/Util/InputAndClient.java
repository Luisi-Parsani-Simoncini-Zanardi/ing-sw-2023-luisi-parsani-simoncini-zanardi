package org.projectsw.Util;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;

public class InputAndClient{
    Client client;
    InputMessage input;
    public InputAndClient(Client client, InputMessage input){
        this.client=client;
        this.input=input;
    }
    public Client getClient() {
        return client;
    }
    public InputMessage getInputMessage() {
        return input;
    }

}