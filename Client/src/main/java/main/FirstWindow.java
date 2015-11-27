package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by X1 on 24.11.2015.
 */
public class FirstWindow {
    private Button start;
    private Label enter, hello;
    private TextField name;
    private Stage stage1 = new Stage();
    private Scene scene1;
    private VBox vbox;

    FirstWindow() {
        firstScene();
//        login();
    }

    private void firstScene() {
        vbox = new VBox();
        vbox.setPrefSize(800,600);
        vbox.setPadding(new Insets(150));
        vbox.setSpacing(15);
        vbox.getStyleClass().addAll("vbox");
        scene1 = new Scene(vbox);
        scene1.getStylesheets().add
                (FirstWindow.class.getResource("Design.css").toExternalForm());
        hello = new Label("Lets play some Tic Tac Toe");
        enter = new Label("Please enter your name to begin:");
        name = new TextField();
        start = new Button("Start the game");
        start.setOnAction(event -> {
            stage1.close();
            new GameWindow();
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hello, enter, name, start);

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
