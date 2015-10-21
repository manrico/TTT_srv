/**
 * Created by urmet on 9.10.15.
 */

import com.ttt.Message.ClientCommand;
import com.ttt.Message.Message;
import com.ttt.Message.ServerCommand;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Application {

    private boolean turnX = true;
    private Socket             socket;
    private ObjectOutputStream oos;
    private ObjectInputStream  ois;

    private Parent createContent() {

        Pane board = new Pane();
        board.setPrefSize(800, 600);            // Creates Window Pane

        // Game board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 120);
                tile.setTranslateY(i * 120);
                board.getChildren().add(tile);
            }
        }
        return board;
    }


    public void start(Stage primaryStage) throws Exception {
        this.socket = new Socket("localhost", 5843);
        try {
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }

        Message message = new Message(ClientCommand.REGISTER, "Marko");

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

        Message serverMessage;

        /*while ((serverMessage = (Message) ois.readObject()) != null) {

            System.out.println("The message from client:  " + serverMessage.toString());
            Message output = handleMessage(serverMessage);
            sendMessage(output);
        }*/
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(120, 120);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(50));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    drawX();
                    Message message = new Message(ClientCommand.REGISTER, "Marko");
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    draw0();
                    Message message = new Message(ClientCommand.REGISTER, "Marko");
                }
            });
        }

        private void drawX() {
            text.setText("X");
        }

        private void draw0() {
            text.setText("0");
        }
    }

    public static void main(String[] args) {
        launch(args);

    }

    public Message handleMessage(Message message) {
        Message answer;
        switch ((ServerCommand) message.cmd) {
            // Test connection
            case REGISTER_OK:
                System.out.println("Got register OK");
                answer = new Message(ServerCommand.PONG, null);
                break;

            default:
                answer = new Message(ServerCommand.ERROR, "No comprendo?");
        }
        return answer;
    }

    public void sendMessage(Message message) {

        System.out.println("Sending to server : " + message.toString());
        try {
            oos.writeObject(message);
            oos.flush();

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());

        }
    }
}



