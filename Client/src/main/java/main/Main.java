package main;

import com.ttt.Message.Message;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by X1 on 24.11.2015.
 */
public class Main extends Application {
    private ObjectInputStream ois;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        new WelcomeScr();

        // separate non-FX thread for server listening
        new Thread() {
            Message serverMessage;

            // runnable for that thread
            public void run() {
                try {
                    while ((serverMessage = (Message) ois.readObject()) != null) {
                        System.out.println("The message from server:  " + serverMessage.toString());
                        TheGame.handleMessage(serverMessage);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error on handling Server communication : " + e.getMessage());
                }
            }
        }.start();
    }
}
