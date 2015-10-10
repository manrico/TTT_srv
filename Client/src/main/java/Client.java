/**
 * Created by urmet on 9.10.15.
 */

import com.ttt.Message.Command;
import com.ttt.Message.Message;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Client extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Message message = new Message(Command.PING, "Hi there!");
        try {
            Socket socket = new Socket("localhost", 5843);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            // don't catch root exception!
            System.out.println("unknown host!");
        }


    }
}
