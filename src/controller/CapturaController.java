package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CapturaController {

    @FXML
    private Button btnSalir;

    @FXML
    private ImageView imgCapturar;

    @FXML
    private ImageView imgGenerar;

    @FXML
    private ImageView imgPokemonGenerado;

    @FXML
    private Label lblPokemonGenerado;

    @FXML
    private Label numPokeballs;

    @FXML
    private ImageView pokeball;

    @FXML
    void btnSalirEntered(MouseDragEvent event) {

    }

    @FXML
    void btnSalirSalir(MouseDragEvent event) {

    }

    @FXML
    void capturar(MouseEvent event) {

    }

    @FXML
    void generar(MouseEvent event) {

    }

    @FXML
    void imgCapturarEntered(MouseEvent event) {

    }

    @FXML
    void imgCapturarExit(MouseEvent event) {

    }

    @FXML
    void imgGenerarEntered(MouseEvent event) {

    }

    @FXML
    void imgGenerarExit(MouseEvent event) {

    }

    @FXML
    void salir(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private Stage primaryStage;

    public void init(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        // Aquí puedes inicializar cosas si lo necesitas
    }
}

