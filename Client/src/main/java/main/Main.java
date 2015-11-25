package main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by X1 on 24.11.2015.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        new FirstWindow();
    }
}
