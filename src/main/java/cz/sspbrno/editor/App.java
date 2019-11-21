package cz.sspbrno.editor;
    
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
        public static void run(String[] args) {
            launch(args);
        }
    
        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = new FXMLLoader(getClass().getClassLoader().getResource("editor.fxml")).load();
            primaryStage.setScene(new Scene(root, 800, 700));
            primaryStage.setMinWidth(780);
            primaryStage.setMinHeight(650);
            primaryStage.show();
        }
}
