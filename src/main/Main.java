package main;

import controller.MenuController;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.ConexionBD;
import model.Entrenador;
import model.Pokemon;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Connection conexion = ConexionBD.establecerConexion();
    	
        Entrenador entrenador = new Entrenador(1, "LuisRe");
        entrenador.cargarPokemonsDesdeBD(conexion);
        System.out.println("Pokémon cargados:");
        for (Pokemon p : entrenador.getEquipo()) {
            System.out.println("- " + p.getNombre());
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml")); // Creando el FXML manualmente nos permite poder acceder luego al controlador
        Parent root = loader.load();
       
        MenuController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.setEntrenador(entrenador);

        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        /*Image image = new Image(getClass().getResourceAsStream("/imagenes/pokeball.png")); // intento de cambiar el icono del programa	
        primaryStage.getIcons().add(image);*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
