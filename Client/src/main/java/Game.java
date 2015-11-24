package main;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by X1 on 24.11.2015.
 */
public class Game extends StackPane {
    private Text fill = new Text();
    private Rectangle border;

    public Game() {
        border = new Rectangle(120, 120);            // draws TicTacToe board
        border.setFill(null);
        border.setStroke(Color.BLACK);
//            border.setOpacity(0);
        fill.setFont(Font.font(50));
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
