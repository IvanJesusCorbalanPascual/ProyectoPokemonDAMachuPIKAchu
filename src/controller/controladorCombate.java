package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class controladorCombate {

	private Stage primaryStage;

	@FXML
	private Button attackBtn1;

	@FXML
	private Button attackBtn2;

	@FXML
	private Button attackBtn3;

	@FXML
	private Button attackBtn4;

	@FXML
	private ImageView backgroundImg;

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

	@FXML
	private Button switchCombatPokemon;

	@FXML
	private AnchorPane textCombatLog;

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

	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}
	@FXML
	public void initialize() {
		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
