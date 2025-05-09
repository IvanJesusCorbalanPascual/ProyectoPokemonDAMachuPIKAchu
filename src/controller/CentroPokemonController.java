package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

	@FXML
	void salir(ActionEvent event) {

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
