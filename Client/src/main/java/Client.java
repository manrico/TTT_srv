/**
 * Created by urmet on 9.10.15.
 */

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

public class Client extends Application {

    private boolean turnX = true;

    private Parent createContent() {

        Pane board = new Pane();
        board.setPrefSize(800, 600);            // Akna loomine

        // Mänguruudustik
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

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(120, 120);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(50));        // X-de ja 0-de suurus
            setAlignment(Pos.CENTER);           // x-de 0-de paiknevus "kastis"
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    drawX();
                } else if (event.getButton() == MouseButton.SECONDARY){
                    draw0();
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
}



    /*@Override
    public void start(Stage primaryStage) {
        Message message = new Message(ClientCommand.REGISTER, "Marko");



    }
    public void sendMessage(Message message) {
        try {
            Socket socket = new Socket("localhost", 5843);
            System.out.println("Sending to server : " + message.toString());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            // don't catch root exception!
            System.out.println("unknown host!");
        }
    }
}
*/
