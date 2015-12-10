package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by X1 on 24.11.2015.
 */
public class WelcomeScr {
    private Button start;
    private Label enter;
    private Text hello, empty;
    private TextField name;
    private Stage stage1 = new Stage();
    private Scene scene1;
    private VBox vbox;
    private Reflection reflection;

    WelcomeScr() {
        firstScene();
//        login();
    }

    private void firstScene() {
        vbox = new VBox(25);
        vbox.setPrefSize(800, 600);
        vbox.setPadding(new Insets(75));
        vbox.getStyleClass().addAll("vbox");
        scene1 = new Scene(vbox);
        scene1.getStylesheets().add
                (WelcomeScr.class.getResource("Design.css").toExternalForm());
        hello = new Text(50, 50, "TIC TAC TOE");
        reflection = new Reflection();
        hello.setFill(Color.WHITESMOKE);
        hello.setFont(Font.font("Lucida Handwriting", FontPosture.ITALIC, 55));
        hello.setEffect(reflection);
        empty = new Text("");
        enter = new Label("Please enter your name to begin:");
        name = new TextField();
        name.setMaxWidth(250);
        start = new Button("START GAME");
        start.setOnAction(event -> {
            stage1.close();
            new TheGame();
        });

        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(hello, empty, enter, name, start);

        stage1.setScene(scene1);
        stage1.setResizable(false);
        stage1.show();

    }

    /* private void login() {
        start.setOnAction(event -> {
            String nimi = name.getText();
            Andmebaas baas = new Andmebaas();
            boolean result = baas.login(nimi);
            baas.sulgeYhendus();
            if (result) {
                UserDetails ud = new UserDetails(nimi);
                stage1.close();
            }
        });
    }*/
}
