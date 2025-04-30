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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class controladorCombate {

	private Stage primaryStage;

	// -- BOTONES DE ATAQUE --
	@FXML
	private Button attackBtn1;

	@FXML
	private Button attackBtn2;

	@FXML
	private Button attackBtn3;

	@FXML
	private Button attackBtn4;

	// -- FONDO DE LA ESCENA --
	@FXML
	private ImageView backgroundImg;
	
	// -- BARRAS DE VIDA Y NOMBRES --

	@FXML
	private ProgressBar enemyHealthBar;

	@FXML
	private Label enemyNameLabel;

	@FXML
	private ImageView enemySoundButton;

	@FXML
	private ProgressBar playerHealthBar;

	@FXML
	private Label playerNameLabel;
	
	// -- IMÁGENES Y BOTONES DE SONIDO
	@FXML
	private ImageView playerPokemonImage;

	@FXML
	private Button playerPokemonSoundButton1;

	@FXML
	private ImageView playerSoundButton;

	@FXML
	private ImageView rivalPokemonImage;

	@FXML
	private Button rivalpokemonSoundButton;

	// -- OTROS ELEMENTOS --

    @FXML
    private Button salirBtn;
    
	@FXML
	private Button switchCombatPokemon;

	@FXML
	private AnchorPane textCombatLog;
	
	// -- EVENTOS DE BOTONES DE ATAQUE --	
	@FXML
	void onAttack1(ActionEvent event) {

	}

	@FXML
	void onAttack2(ActionEvent event) {

	}

	@FXML
	void onAttack3(ActionEvent event) {

	}

	@FXML
	void onAttack4(ActionEvent event) {

	}
	
	// -- MÉTODO PARA VOLVER AL MENÚ PRINCIPAL --
    @FXML
    void onExit(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Menú Princiapal");
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// Método para inicializar en la ventana principal el controlador
	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}
	@FXML
	public void initialize() {
		
	}
	
	// - GETTERS Y SETTERS
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
