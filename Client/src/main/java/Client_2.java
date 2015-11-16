import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by kasutaja on 10.11.2015.
 */
public class Client_2 extends Application {
    Stage      stage;
    BorderPane sceneMain;
    GridPane   sceneGame;
    VBox       startTheGame;
    Button     start;
    Label      enter, play, play2, mark;
    TextField name;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        stage = primaryStage;
        stage.setTitle("TIC TAC TOE");

        stageWindow();
        gameBoard();
        rigthMenu();
    }

    private VBox rigthMenu() {
        startTheGame = new VBox();
        startTheGame.setPadding(new Insets(10));
        startTheGame.setSpacing(8);

        enter = new Label("Please enter your name to begin:");

        name = new TextField();

        start = new Button("Start the game");
        start.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        DropShadow shadow = new DropShadow();
        start.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        start.setEffect(shadow);
                    }
                });
        start.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        start.setEffect(null);
                    }
                });

        play = new Label("You play with X-s");
        play2 = new Label("You play with O-s");

        mark = new Label("You are playing against:");

        startTheGame.getChildren().addAll(enter, name, start, play, play2, mark);
        startTheGame.setAlignment(Pos.TOP_RIGHT);
        return startTheGame;
    }

    private void gameBoard() {

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            RowConstraints row = new RowConstraints(100);
            sceneGame.getRowConstraints().add(row);
            sceneGame.getColumnConstraints().add(column);
            sceneGame.setAlignment(Pos.CENTER);
            sceneGame.setMaxSize(200, 200);
            sceneGame.setStyle("-fx-background-color: floralwhite; -fx-padding: 0; -fx-hgap: 0; -fx-vgap: 0;");
            sceneGame.setGridLinesVisible(true); // Can be uncommented to show the grid lines for debugging purposes, but not particularly useful for styling purposes.
            sceneGame.setSnapToPixel(false); // Turn layout pixel snapping off on the grid so that grid lines will be an even width.

            /* Click event
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
        sceneMain = new BorderPane();
        sceneMain.setStyle("-fx-background-color: whitesmoke; -fx-padding: 10;");
        sceneMain.setLeft(sceneGame);
        sceneMain.setRight(rigthMenu());

        Scene scene = new Scene(sceneMain, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

