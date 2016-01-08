package com.ttt.client;
/**
 * Created by urmet on 9.10.15.
 */

import com.ttt.Message.ClientCommand;
import com.ttt.Message.Message;
import com.ttt.Message.ServerCommand;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
import java.net.ConnectException;
import java.net.Socket;
import java.util.Iterator;

public class Client extends Application {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Pane gameGraphics;
    private GridPane dialogPane;
    private TextArea logBox;
    private TextField userNameField;
    private Label dialogLabel;
    private Button startButton;
    private int mark;
    final static int WIDTH = 600;
    final static int HEIGHT = 600;
    final static String HOST = "localhost";
    final static int PORT = 5843;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        // create board and add it to stage
        this.gameGraphics = this.createContent();
        this.dialogPane = this.createDialogs();
        final Group root = new Group(this.gameGraphics, this.createDebugBox(), this.dialogPane);

        primaryStage.setScene(new Scene(root,WIDTH, HEIGHT));
        primaryStage.show();
        // Disable all mouseevents on tiles.
        this.disableMouse();

        this.log("Welcome!");
        this.log("Trying to connect to : " + HOST + ":" + PORT );

        try {
            this.socket = new Socket(HOST, PORT);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());

        } catch (ConnectException e) {
            this.log("Error : " + e.getMessage());
        }

        // separate non-FX thread for server listening
        new Thread() {
            Message serverMessage;

            // runnable for that thread
            public void run() {
                try {
                    while ((serverMessage = (Message) ois.readObject()) != null) {
                        handleMessage(serverMessage);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error on handling Server communication : " + e.getMessage());
                }
            }
        }.start();

        //int[] state =  {0, 0, 0, 0, 0, 0, 0, 0, 0};
        //this.drawState(state);
    }

    private ScrollPane createDebugBox() {
        ScrollPane scrollBox = new ScrollPane();
        scrollBox.setTranslateX(10);
        scrollBox.setTranslateY(400);
        scrollBox.setFitToWidth(true);
        scrollBox.setMaxHeight(180);
        scrollBox.setMinHeight(180);
        scrollBox.setMaxWidth(580);
        scrollBox.setMinWidth(580);
        scrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.logBox = new TextArea();
        this.logBox.setEditable(false);
        scrollBox.setContent(this.logBox);
        return scrollBox;
    }

    private GridPane createDialogs() {
        GridPane dialog = new GridPane();
        dialog.setTranslateX(400);
        dialog.setTranslateY(10);
        dialog.setMaxWidth(180);
        dialog.setMaxHeight(350);
        this.startButton = new Button("Start");
        this.startButton.setOnAction(event -> {
            this.handleStartButtonAction();
        });

        this.dialogLabel = new Label("Enter Your name :");
        this.dialogLabel.setWrapText(true);
        this.userNameField = new TextField();
        dialog.add(dialogLabel, 0, 0);
        dialog.add(userNameField, 0, 1);
        dialog.add(startButton, 0, 2);
        return dialog;
    }

    /**
     * If start button is clicked, check if name is set. If it is, send register to server.
     */
    private void handleStartButtonAction() {
        String userNameFieldText = this.userNameField.getText();
        if (userNameFieldText.length() < 2) {
            this.dialogLabel.setText("Username must be longer than 2 letters.");
        } else {
            Message message = new Message(ClientCommand.REGISTER, userNameFieldText, 9);
            try {
                this.sendMessage(message);
            } catch (Exception e) {
                return;
            }
            this.dialogPane.getChildren().remove(startButton);
            this.dialogPane.getChildren().remove(userNameField);
        }
    }

    private void log(String logLine) {
       // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.logBox.appendText(">" + logLine + "\n");
    }

    private Pane createContent() {
        gameGraphics = new Pane();
        int tileIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GameSquare tile = new GameSquare();
                tile.setId("" + tileIndex);
                tileIndex++;
                tile.setTranslateX(j * 120);
                tile.setTranslateY(i * 120);
                gameGraphics.getChildren().add(tile);
            }
        }
        return gameGraphics;
    }

    private void drawState(int[] state) {
        for (Iterator<javafx.scene.Node> i = this.gameGraphics.getChildren().iterator(); i.hasNext(); ) {
            GameSquare currentPane = (GameSquare) i.next();
            int id = Integer.parseInt(currentPane.getId());
            currentPane.drawMark(state[id]);
        }
    }

    private class GameSquare extends StackPane {
        private Text fill = new Text();
        public GameSquare() {
            Rectangle border = new Rectangle(120, 120);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            fill.setFont(Font.font(50));
            getChildren().addAll(border, fill);
            this.enableMouse();
        }

        public void enableMouse() {
            setOnMouseClicked(event -> {
                System.out.println("mark : "+mark);
                drawMark(mark);
                try {
                    Message message = new Message(ClientCommand.DECISION, this.getId(), mark);
                    System.out.println(message.toString());
                    sendMessage(message);
                    disableMouse();
                } catch (Exception e) {
                    log("Error : " + e.getMessage());
                }
            });
        }

        public void drawMark(int mark) {
            switch (mark) {
                case 0:
                    fill.setText("");
                    break;
                case 1:
                    fill.setText("X");
                    break;
                case 2:
                    fill.setText("O");
                    break;
                default:
                    fill.setText(""); //TODO build exception / handling
                    break;
            }
        }
    }

    // Handles server message and sends response back, if logic dictates.
    public void handleMessage(Message message) {
        this.log("Server : " + message.toString());
        switch ((ServerCommand) message.cmd) {
            // Test connection
            case REGISTER_OK:
                Platform.runLater(() ->  this.dialogLabel.setText("Registered, waiting for other Player"));
                break;

            case STATE:
                String payload = message.payload.substring(1, message.payload.length()-1);
                String[] strArray = payload.split(",");
                int[] intArray = new int[strArray.length];
                for (int i = 0; i < strArray.length; i++) {
                    intArray[i] = Integer.parseInt(strArray[i].trim());
                }

                Platform.runLater(() -> this.drawState(intArray));
                Platform.runLater(() ->  this.dialogLabel.setText("Its Your turn!"));
                Platform.runLater(() -> this.enableTileMouseEvents());
                break;

            case GAME_START:
                Platform.runLater(() -> this.dialogLabel.setText("Game has started. Your mark is " + message.idMark + " waiting for Your turn!"));
                this.mark = message.idMark;
                break;

            case YOUR_TURN:
                Platform.runLater(() ->  this.dialogLabel.setText("Its Your turn!"));
                Platform.runLater(() -> this.enableTileMouseEvents());
                break;

            case ERROR:
                Platform.runLater(() ->  this.dialogLabel.setText("Sorry, Something went wrong :("));
                Platform.runLater(() -> this.disableMouse());
                break;
            default:
                //TODO throw Exception - we sould handle everything!
                System.out.println("Should not fall here.");
        }
    }

    private void disableMouse() {
        log("Disabling mouse.");
        for (Iterator<javafx.scene.Node> i = this.gameGraphics.getChildren().iterator(); i.hasNext(); ) {
            i.next().setOnMouseClicked(null);
        }
    }

    private void enableTileMouseEvents() {
        log("Enabling mouse.");
        for (Iterator<javafx.scene.Node> i = this.gameGraphics.getChildren().iterator(); i.hasNext(); ) {
            GameSquare currentPane = (GameSquare) i.next();
            if (currentPane.fill.getText().equals("")) {
                currentPane.enableMouse();
            }
        }
    }

    public void sendMessage(Message message) throws Exception {
        dialogLabel.setText("Waiting for your turn!");
       this.log("Sending to server : " + message.toString());
        try {
            this.oos.writeObject(message);
            this.oos.flush();
        } catch (Exception e) {
            this.log("Error : " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}



