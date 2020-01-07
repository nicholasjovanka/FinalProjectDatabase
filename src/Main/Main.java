package Main;

import Controller.MainMenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
        primaryStage.setTitle("Database Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
            try {
                MainMenuController.db.conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
