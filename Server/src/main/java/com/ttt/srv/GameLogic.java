package com.ttt.srv;

import com.ttt.Message.*;

/**
 * Game logic class, handles server messages and acts on them
 * Created by marko on 07.10.15.
 */

public class GameLogic {
    public Message handleMessage(Message message) {
        Message answer;
        switch ((ClientCommand) message.cmd) {
            case PING:
                answer = new Message(ServerCommand.PONG, null);
                break;
            case REGISTER:
                String playerName = message.payload;
                answer = new Message(ServerCommand.REGISTER_OK, "Registered " + playerName + ", will send notification when game begins.");
                break;
            default:
                answer = new Message(ServerCommand.ERROR, "No comprendo?");
        }
        return answer;
    }
}
