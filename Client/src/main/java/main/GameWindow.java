package main;

import com.ttt.Message.ClientCommand;
import com.ttt.Message.Message;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

/**
 * Created by X1 on 24.11.2015.
 */
public class GameWindow {
    private Stage stage2 = new Stage();
    private Pane gameScene;
    private Scene scene2;
    private String boardBackground;
    private ObjectOutputStream oos;
    private Rectangle border;
    private Image xPic = new Image(getClass().getResourceAsStream("X.png"));
    private Image oPic = new Image(getClass().getResourceAsStream("O.png"));

    GameWindow() {
        createContent();
    }

    private Pane createContent() {
        gameScene = new Pane();
        gameScene.setPrefSize(800, 600);
        boardBackground = Game.class.getResource("Grid.png").toExternalForm();
        gameScene.setStyle("-fx-background-image: url('" + boardBackground + "'); " +
                "-fx-background-position: top left; " +
                "-fx-background-repeat: stretch;");
        scene2 = new Scene(gameScene);
        stage2.setScene(scene2);
        stage2.setResizable(false);
        stage2.show();

        // Create tiles to set Id-s
        int tileIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Game tile = new Game();
                tile.setId("" + tileIndex);
                tileIndex++;
                tile.setTranslateX(j * 150);
                tile.setTranslateY(i * 150);
                gameScene.getChildren().add(tile);
            }
        }
        return gameScene;
    }

    // TODO move this out from here.
    public static void handleMessage(Message message) {
    }

    private class Game extends StackPane {
        private Label fill = new Label();

        public Game() {
            // Tic Tac Toe board with rectangles, can be uncommented as were using background image as well.
            border = new Rectangle(150, 150);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            border.setOpacity(0);
            getChildren().addAll(border, fill);

            this.enableMouse();
        }

        private void enableMouse() {
            setOnMouseClicked(event -> {
                drawMark(2);
                Message message = new Message(ClientCommand.DECISION, this.getId());
                sendMessage(message);
            });
        }

        private void sendMessage(Message message) {
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

        private void enableTileMouseEvents() {
            for (Iterator<javafx.scene.Node> i = gameScene.getChildren().iterator(); i.hasNext(); ) {
                Game currentPane = (Game) i.next();
                if (currentPane.fill.equals("")) {
                    currentPane.enableMouse();
                }
            }
        }

        private void disableMouse() {
            for (Iterator<Node> i = gameScene.getChildren().iterator(); i.hasNext(); ) {
                i.next().setOnMouseClicked(null);
            }
        }

        private void drawMark(int mark) {
            switch (mark) {
                case 0:
                    fill.setText("");
                    break;
                case 1:
                    fill.setGraphic(new ImageView(xPic));
                    break;
                case 2:
                    fill.setGraphic(new ImageView(oPic));
                    break;
                default:
                    fill.setText(""); //TODO build exception / handling
                    break;
            }
        }
    }
}

