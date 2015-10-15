package com.ttt.srv;

import java.net.ServerSocket;
import com.ttt.Message.*;

public class SocketServer {

    public static void main(String args[]) throws Exception {
        int port = 5843;
        int maxClients = 2;
        boolean acceptingPlayers = true;
        ServerSocket serverSocket = new ServerSocket(port, maxClients);
        GameLogic gameLogic = new GameLogic();

        System.out.println("Waiting for connections on port " + port);
        while (acceptingPlayers) {
                new ClientHandler(serverSocket.accept(), gameLogic).start();
        }
    }
}
