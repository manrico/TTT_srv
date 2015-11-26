package main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by X1 on 24.11.2015.
 */
public class GameWindow {
    private Stage stage2 = new Stage();
    private Pane gameScene;
    private Scene scene2;
    private String boardBackground;

    GameWindow() {
        createContent();
    }

    private Pane createContent() {
        gameScene = new Pane();
        gameScene.setPrefSize(475, 475);
        boardBackground = Game.class.getResource("Grid.png").toExternalForm();
        gameScene.setStyle("-fx-background-image: url('" + boardBackground + "'); " +
                "-fx-background-position: top left; " +
                "-fx-background-repeat: stretch;");
        scene2 = new Scene(gameScene);
        stage2.setScene(scene2);
        stage2.setResizable(true);
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
}
