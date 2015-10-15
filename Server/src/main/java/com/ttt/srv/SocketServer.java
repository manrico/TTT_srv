package com.ttt.srv;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ttt.Message.*;

public class SocketServer {
    public static Message main(String args[]) throws Exception {
        ServerSocket serverSocket;
        Socket fromClientSocket;
        int cTosPortNumber = 5843;
        Message clientMessage;
        GameLogic logic = new GameLogic();

        serverSocket = new ServerSocket(cTosPortNumber);
        System.out.println("Waiting for a connection on " + cTosPortNumber);
        fromClientSocket = serverSocket.accept();
        System.out.println("fromClientSocket accepted");

        ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

        ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());

        while ((clientMessage = (Message) ois.readObject()) != null) {
            System.out.println("The message from client:  " + clientMessage.toString());
            Message serverAnswer = logic.handleMessage(clientMessage);
            System.out.println("Sending back to client : " + serverAnswer.toString());
            oos.writeObject(serverAnswer);
            oos.flush();
        }
        oos.close();
        fromClientSocket.close();
        return null;
    }
}
