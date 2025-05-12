package controller;

import java.io.IOException;
import java.util.List;

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
import model.Captura;
import model.Entrenador;
import model.Pokemon;

public class controladorCombate {

	private Stage primaryStage;
	private Entrenador entrenador;
	private Pokemon pokemonJugador;
	private Pokemon pokemonEnemigo;
	private int turno = 0;

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

			// ← ¡IMPORTANTE! Pasamos el entrenador al menú
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
	
	// Método para inicializar en la ventana principal el controlador
	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}
	@FXML
	public void initialize() {
		
	}
	
	public void iniciarCombate() {
	    List<Pokemon> equipo = entrenador.getEquipo();
	    if (equipo.isEmpty()) return;

	    pokemonJugador = equipo.get(0);
	    pokemonEnemigo = Captura.generarPokemonAleatorio();

	    actualizarVistaCombate();
	}

	private void actualizarVistaCombate() {
		playerNameLabel.setText(pokemonJugador.getNombre() + " (HP: " + pokemonJugador.getPs() + ")");
		enemyNameLabel.setText(pokemonEnemigo.getNombre() + " (HP: " + pokemonEnemigo.getPs() + ")");
	    playerHealthBar.setProgress(1.0);
	    enemyHealthBar.setProgress(1.0);

	    try {
	        String rutaJugador = getClass().getResource("/imagenes/Pokemon/Front/" + pokemonJugador.getNumPokedex() + ".png").toExternalForm();
	        playerPokemonImage.setImage(new javafx.scene.image.Image(rutaJugador));

	        String rutaEnemigo = getClass().getResource("/imagenes/Pokemon/Front/" + pokemonEnemigo.getNumPokedex() + ".png").toExternalForm();
	        rivalPokemonImage.setImage(new javafx.scene.image.Image(rutaEnemigo));
	    } catch (Exception e) {
	        System.out.println("Error al cargar imágenes de los Pokémon.");
	        e.printStackTrace();
	    }
	}

	
	// - GETTERS Y SETTERS
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void setEntrenador(Entrenador entrenador) {
	    this.entrenador = entrenador;
	    iniciarCombate();
	}

}
