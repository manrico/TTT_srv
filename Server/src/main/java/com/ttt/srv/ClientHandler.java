package com.ttt.srv;

import com.ttt.Message.ClientCommand;
import com.ttt.Message.Message;
import com.ttt.Message.ServerCommand;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by markonou on 15.10.2015.
 */
public class ClientHandler extends Thread{
    private Socket socket;
    private GameLogic gameLogic;
    private Player player;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientHandler(Socket socket, GameLogic gameLogic) {
        super("ClientHandler");
        this.socket = socket;
        this.gameLogic = gameLogic;
        System.out.println("ClientHandler socket accepted");
    }

    public void run() {
        Message clientMessage;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            while ((clientMessage = (Message) ois.readObject()) != null) {

                System.out.println("The message from client:  " + clientMessage.toString());
                handleMessage(clientMessage);
            }
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleMessage(Message message) {
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
                this.player = new Player(playerName);
                this.player.handler = this;
                try {
                    if (this.gameLogic.addPlayerAndDecideMark(player)) {
                        gameLogic.startGame();
                    } else {
                        sendMessage(new Message(ServerCommand.STATE, "0,1,1,0,1,1,0,2,1"));
                        //sendMessage(new Message(ServerCommand.REGISTER_OK, "Registered " + playerName + ", will send notification when game begins."));
                    }
                } catch (Exception ex) {
                    sendMessage(new Message(ServerCommand.ERROR, ex.getMessage()));
                }
                break;
            default:
                sendMessage(new Message(ServerCommand.ERROR, "No comprendo?"));
        }
    }

    public void sendMessage(Message message) {
        System.out.println("Sending back to client : " + message.toString());
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Everything is broken: " + e.getMessage());
        }
    }
}
