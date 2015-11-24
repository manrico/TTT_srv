package main;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by X1 on 24.11.2015.
 */
public class GameWindow {
    private Stage stage2 = new Stage();
    private Pane gameScene;
    private Scene scene2;

    GameWindow(){
        createContent();
    }

    private Pane createContent() {
        gameScene = new Pane();
        gameScene.setPrefSize(600, 400);
        scene2 = new Scene(gameScene);
        stage2.setScene(scene2);
        stage2.setResizable(false);
        stage2.show();

        int tileIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Game tile = new Game();
                tile.setId("" + tileIndex);
                tileIndex++;
                tile.setTranslateX(j * 120);
                tile.setTranslateY(i * 120);
                gameScene.getChildren().add(tile);
            }
        }
        return gameScene;
    }
}
