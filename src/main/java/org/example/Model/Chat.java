package org.example.Model;

import java.util.ArrayList;

public class Chat {
    private ArrayList<String> chat;

    public Chat (){
        chat = new ArrayList<String>();
    }

    public void addChatLog (String message){
        chat.add(message);
    }

    public ArrayList<String> getChat(){
        return chat;
    }
}
