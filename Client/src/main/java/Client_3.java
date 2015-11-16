import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by kasutaja on 16.11.2015.
 */
public class Client_3 extends Application {

    Stage       stage;
    Scene       firstScene, secondScene;
    GridPane    sceneGame;
    VBox        first;
    Button      start;
    Label       enter, hello;
    TextField   name;

    @Override
    public void start(Stage primaryStage) throws Exception {

        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        stage = primaryStage;

        stageWindow();
        firstWindow();
        gameBoard();
    }

    private void firstWindow() {

        first = new VBox(5);
        first.setPadding(new Insets(150));
        first.setSpacing(15);

        hello = new Label("Lets play some Tic Tac Toe");
        enter = new Label("Please enter your name to begin:");
        name = new TextField();
        start = new Button("Start the game");
        start.setOnAction(event -> stage.setScene(secondScene));

        /* DROP SHADOW EFFECT FOR BUTTONS

        start.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        start.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        start.setEffect(shadow);
                    }
                });
        //Removing the shadow when the mouse cursor is off
        start.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        start.setEffect(null);
                    }
                });*/

        first.getChildren().addAll(hello, enter, name, start);
        first.setAlignment(Pos.CENTER);
        firstScene = new Scene(first, 600, 400);
        stage.setScene(firstScene);
        stage.show();
    }

    private void gameBoard() {

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            RowConstraints row = new RowConstraints(100);
            sceneGame.getRowConstraints().add(row);
            sceneGame.getColumnConstraints().add(column);
            sceneGame.setAlignment(Pos.CENTER);
            sceneGame.setMaxSize(200, 200);
            //sceneGame.setStyle("-fx-background-color: floralwhite; -fx-padding: 0; -fx-hgap: 0; -fx-vgap: 0;");
            sceneGame.setGridLinesVisible(true); // Can be uncommented to show the grid lines for debugging purposes, but not particularly useful for styling purposes.
            //sceneGame.setSnapToPixel(false); // Turn layout pixel snapping off on the grid so that grid lines will be an even width.

            /* CLICK EVENT

            for (int j = 0; j < 3; j++) {
                Text XandO = new Text();
                XandO.setFont(Font.font("Arial", FontWeight.BLACK, 12));
                *//*XandO.setStyle();*//*
                XandO.setOnMouseClicked(event -> clickHandler(XandO));
                sceneGame.add(XandO, i, j);
            }*/
        }
    }

    private void stageWindow() {

        sceneGame = new GridPane();
        secondScene = new Scene(sceneGame, 600, 400);
        /* CSS
        File  f     = new File("src/mystylesheet.css");
        secondScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));*/
        stage.setScene(secondScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}