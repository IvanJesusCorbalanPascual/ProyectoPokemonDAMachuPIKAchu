package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {

	private Stage primaryStage;

	private FXMLLoader loader;

	@FXML
	private Button btnCaptura;

	@FXML
	private Button btnCentroPokemon;

	@FXML
	private Button btnCombate;

	@FXML
	private Button btnCrianza;

	@FXML
	private Button btnEntrenamiento;

	@FXML
	private Button btnEquipo;

	@FXML
	private Button btnPokedex;

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnTienda;

	@FXML
	private ImageView imgFondo;

	@FXML
	private Label lblEntrenador;

	@FXML
	private Label lblPokedolares;

	@FXML
	private ImageView logoPokemon;

	@FXML
	private Text txtEntrenador;

	@FXML
	private Text txtPokedolares;

	@FXML
	void arriba(MouseEvent event) {

	}

	@FXML
	void abrirCaptura(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/captura.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Combate");
		controladorCombate controller = loader.getController();
		controller.init(primaryStage);
		controller.setPrimaryStage(primaryStage);

		primaryStage.show();
	}

	@FXML
	void abrirCentroPokemon(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CentroPokemon.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Combate");
		controladorCombate controller = loader.getController();
		controller.init(primaryStage);
		controller.setPrimaryStage(primaryStage);

		primaryStage.show();
	}

	@FXML
	void abrirCombate(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/combate.fxml"));
		Parent root = loader.load();
		
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Combate");
		controladorCombate controller = loader.getController();
		controller.init(primaryStage);
		controller.setPrimaryStage(primaryStage);

		primaryStage.show();
	}

	@FXML
	void abrirCrianza(ActionEvent event) {

	}

	@FXML
	void abrirEntrenamiento(ActionEvent event) {

	}

	@FXML
	void abrirEquipo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/equipo.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) btnEquipo.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void abrirPokedex(ActionEvent event) {

	}

	@FXML
	void abrirTienda(ActionEvent event) {

	}

	@FXML
	public void salir(ActionEvent event) {
		Platform.exit();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}

	@FXML
	public void initialize() {

	}

}
