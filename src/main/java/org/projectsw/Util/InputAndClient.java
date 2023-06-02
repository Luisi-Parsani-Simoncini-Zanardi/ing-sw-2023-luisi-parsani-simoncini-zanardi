package org.projectsw.Util;

import org.projectsw.Distributed.Client;
import org.projectsw.Distributed.Messages.InputMessages.InputMessage;

public class InputAndClient implements Comparable<InputAndClient>{
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

    @Override
    public int compareTo(InputAndClient inputAndClient) {
       // return compare(this.value, other.getValue());
        return 0;
    }
}