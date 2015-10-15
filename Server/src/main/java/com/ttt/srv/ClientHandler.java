package com.ttt.srv;

import com.ttt.Message.ClientCommand;
import com.ttt.Message.Message;
import com.ttt.Message.ServerCommand;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by markonou on 15.10.2015.
 */
public class ClientHandler extends Thread{
    private Socket socket;
    private GameLogic gameLogic;

    public ClientHandler(Socket socket, GameLogic gameLogic) {
        super("ClientHandler");
        this.socket = socket;
        this.gameLogic = gameLogic;
        System.out.println("ClientHandler socket accepted");
    }

    public void run() {

    }

    public Message handleMessage(Message message) {
        Message answer;
        switch ((ClientCommand) message.cmd) {
            // Test connection
            case PING:
                answer = new Message(ServerCommand.PONG, null);
                break;

            // Player sends us REGISTER command with his name. We create player instance and
            // Try to add it to the game. If the game is full, we send back sorry.
            //TODO check if name is unique
            //TODO when game has 2 players, dont send REGISTER_OK, just start.
            case REGISTER:
                String playerName = message.payload;
                Player player = new Player(playerName);
                try {
                    this.gameLogic.addPlayerAndDecideMark(player);
                    answer = new Message(ServerCommand.REGISTER_OK, "Registered " + playerName + ", will send notification when game begins.");
                } catch (Exception ex) {
                    answer = new Message(ServerCommand.ERROR, ex.getMessage());
                }
                break;
            default:
                answer = new Message(ServerCommand.ERROR, "No comprendo?");
        }
        return answer;
    }
}
