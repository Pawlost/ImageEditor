package cz.sspbrno.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new FXMLLoader(getClass().getClassLoader().getResource("Main.fxml")).load();
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Image Editor");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
