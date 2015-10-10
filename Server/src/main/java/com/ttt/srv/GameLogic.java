package com.ttt.srv;

import com.ttt.Message.*;

/**
 * Game logic class, handles server messages and acts on them
 * Created by marko on 07.10.15.
 */

public class GameLogic {
    public Message handleMessage(Message message) {
        Message answer;
        switch (message.cmd) {
            case PING:
                answer = new Message(Command.PONG, null);
                break;
            case ERROR:
                answer = new Message(Command.BYE, "Error recieved, bye for now.");
                break;
            default:
                answer = new Message(Command.HELLO, "Hi?");
        }
        return answer;
    }
}
