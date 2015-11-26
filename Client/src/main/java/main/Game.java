package main;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static javafx.scene.layout.Background.EMPTY;

/**
 * Created by X1 on 24.11.2015.
 */
public class Game extends StackPane {
    private Label fill = new Label();
    private Rectangle border;
    private Image xPic = new Image(getClass().getResourceAsStream("X.png"));
    private Image oPic = new Image(getClass().getResourceAsStream("O.png"));

    public Game() {
        border = new Rectangle(120, 120);            // draws TicTacToe board
        border.setFill(null);
        border.setStroke(Color.BLACK);
//        border.setStyle("-fx-image:url('Grid.png');");
//            border.setOpacity(0);
        getChildren().addAll(border, fill);

        this.enableMouse();

    }

    public void enableMouse() {

        setOnMouseClicked(event -> {
            drawMark(1);
//            Message message = new Message(ClientCommand.DECISION, this.getId());
//            sendMessage(message);
        });
    }

    public void drawMark(int mark) {
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
