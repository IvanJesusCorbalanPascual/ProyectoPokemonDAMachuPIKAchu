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
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Entrenador;


public class CentroPokemonController {

	
	@FXML
	private ProgressBar bar1;

	@FXML
	private ProgressBar bar2;

	@FXML
	private ProgressBar bar3;

	@FXML
	private ProgressBar bar4;

	@FXML
	private ProgressBar bar5;

	@FXML
	private ProgressBar bar6;

	@FXML
	private ImageView btnCurarPokemon;

	@FXML
	private Button btnSalir;

	@FXML
	private ImageView pkm1;

	@FXML
	private ImageView pkm2;

	@FXML
	private ImageView pkm3;

	@FXML
	private ImageView pkm4;

	@FXML
	private ImageView pkm5;

	@FXML
	private ImageView pkm6;

	@FXML
	private Label pkmlbl1;

	@FXML
	private Label pkmlbl2;

	@FXML
	private Label pkmlbl3;

	@FXML
	private Label pkmlbl4;

	@FXML
	private Label pkmlbl5;

	@FXML
	private Label pkmlbl6;

	private Entrenador entrenador;

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }
    
    @FXML
    void salir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            // Pasamos el entrenador antes de mostrar el menú
            MenuController menuController = loader.getController();
            menuController.setEntrenador(this.entrenador);
            menuController.setPrimaryStage((Stage) ((Node) event.getSource()).getScene().getWindow());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Principal");
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
		// Inicialización opcional
	}

}
