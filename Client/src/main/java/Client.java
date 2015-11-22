/**
 * Created by urmet on 9.10.15.
 */

import com.ttt.Message.ClientCommand;
import com.ttt.Message.Message;
import com.ttt.Message.ServerCommand;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import java.util.Iterator;

public class Client extends Application {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Pane gameStage, stage;


    public void start(Stage primaryStage) throws Exception {
      /*  this.socket = new Socket("localhost", 5843);
        try {
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }*/

        // create board and add it to stage.
        this.gameStage = this.createContent();
        primaryStage.setScene(new Scene(this.gameStage));

        //TODO move this out from here.


        // separate non-FX thread for server listening
        new Thread() {
            Message serverMessage;

            // runnable for that thread
            public void run() {
                try {
                    while ((serverMessage = (Message) ois.readObject()) != null) {
                        System.out.println("The message from server:  " + serverMessage.toString());
                        handleMessage(serverMessage);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error on handling Server communication : " + e.getMessage());
                }
            }
        }.start();

        primaryStage.show();
        Message message = new Message(ClientCommand.REGISTER, "Marko");
        /*this.sendMessage(message);
        int[] state =  { 0, 1, 2, 0, 0, 1, 0, 1, 2 };
        this.drawState(state);*/

    }

    private Pane createContent() {
        stage = new Pane();
        stage.setPrefSize(600, 400);            // creates the application window

        int tileIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GameBoard tile = new GameBoard();
                tile.setId("" + tileIndex);
                tileIndex++;
                tile.setTranslateX(j * 120);
                tile.setTranslateY(i * 120);
                stage.getChildren().add(tile);
            }
        }
        return stage;
    }

    private void drawState(int[] state) {

        for (Iterator<javafx.scene.Node> i = this.stage.getChildren().iterator(); i.hasNext(); ) {
            GameBoard currentPane = (GameBoard) i.next();
            int id = Integer.parseInt(currentPane.getId());
            currentPane.drawMark(state[id]);
        }
    }

    private class GameBoard extends StackPane {
        private Text filltext = new Text();                             // sets GameBoard text to default (empty)

        public GameBoard() {
            Rectangle border = new Rectangle(120, 120);            // draws TicTacToe board
            border.setFill(null);
            border.setStroke(Color.BLACK);
            //border.setOpacity(0);

            filltext.setFont(Font.font(50));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, filltext);

            this.enableMouse();

        }

        public void enableMouse() {

            setOnMouseClicked(event -> {
                drawMark(1);
                Message message = new Message(ClientCommand.DECISION, this.getId());
                sendMessage(message);
            });
        }

        public void drawMark(int mark) {
            switch (mark) {
                case 0:
                    filltext.setText("");
                    break;
                case 1:
                    filltext.setText("X");
                    break;
                case 2:
                    filltext.setText("O");
                    break;
                default:
                    filltext.setText(""); //TODO build exception / handling
                    break;
            }
        }

    }

    public static void main(String[] args) {
        launch(args);

    }

    /*
    Handles server message and sends response back, if logic dictates.
     */
    public void handleMessage(Message message) {
        switch ((ServerCommand) message.cmd) {
            // Test connection
            case REGISTER_OK:
                System.out.println("Got register OK");
                break;
            case STATE:
                String[] strArray = message.payload.split(",");
                int[] intArray = new int[strArray.length];
                for (int i = 0; i < strArray.length; i++) {
                    intArray[i] = Integer.parseInt(strArray[i]);
                }
                this.drawState(intArray);
            default:
                //TODO throw Exception - we sould handle everything!
                System.out.println("Should not fall here.");
        }
    }

    private void disableMouse() {
        for (Iterator<javafx.scene.Node> i = this.stage.getChildren().iterator(); i.hasNext(); ) {
            i.next().setOnMouseClicked(null);
        }
    }

    private void enableTileMouseEvents() {

        for (Iterator<javafx.scene.Node> i = this.stage.getChildren().iterator(); i.hasNext(); ) {
            GameBoard currentPane = (GameBoard) i.next();
            if (currentPane.filltext.equals("")) {
                currentPane.enableMouse();
            }

        }

    }

    public void sendMessage(Message message) {

        this.disableMouse();
        this.enableTileMouseEvents();

        System.out.println("Sending to server : " + message.toString());
        try {
            oos.writeObject(message);
            oos.flush();

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());

        }
    }

    private void sendDecision(int decision) {
        Message decisionMessage = new Message(ClientCommand.DECISION, Integer.toString(decision));
        this.sendMessage(decisionMessage);
    }
}



