package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));//Cambiar por login.fxml cuando esté creado
        primaryStage.setTitle("Hello World"); // El titulo de la ventana
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}