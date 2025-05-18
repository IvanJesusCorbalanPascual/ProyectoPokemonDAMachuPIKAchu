package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.ControladorLogin;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); // Vista de login
        Parent root = loader.load();

        ControladorLogin controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Iniciar sesión");

        // Cambiando el icono del programa
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/otros/pokeball.png")));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
