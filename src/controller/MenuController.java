package controller;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import bd.BDConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConexionBD;
import model.Entrenador;

public class MenuController {
	// Variables
	private Stage primaryStage;
	private Entrenador entrenador;

	// Variables FXML
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

	// Esta clase es todo metodos FXML, simplemente cambiar el Stage a las demas
	// vistas

	// Metodos FXML
	@FXML
	void arriba(MouseEvent event) {

	}

	@FXML
	void abrirCaptura(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/captura.fxml"));
		Parent root = loader.load();

		CapturaController controller = loader.getController();
		controller.setEntrenador(this.entrenador); // ← AÑADIDO
		controller.init(primaryStage);
		controller.setPrimaryStage(primaryStage);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setTitle("Captura");
		stage.show();
	}

	@FXML
	void abrirCentroPokemon(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CentroPokemon.fxml"));
		Parent root = loader.load();

		CentroPokemonController controller = loader.getController();
		controller.setEntrenador(this.entrenador); // ← AÑADIDO
		controller.setPrimaryStage(primaryStage);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setTitle("Centro Pokémon");
		stage.show();
	}

	@FXML
	void abrirCombate(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/combate.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		controladorCombate controller = loader.getController();
		controller.setPrimaryStage(stage);
		controller.setEntrenador(this.entrenador); // ← Ahora sí se puede llamar con seguridad

		stage.setScene(new Scene(root));
		stage.setTitle("Combate");
		stage.show();
	}

	@FXML
	void abrirCrianza(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crianza.fxml"));
			Parent root = loader.load();

			CrianzaController controller = loader.getController();
			controller.init(entrenador, BDConnection.getConnection()); // ✅ CORRECTO

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Crianza Pokémon");
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void abrirEntrenamiento(ActionEvent event) {

	}

	@FXML
	void abrirEquipo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/equipo.fxml"));
			Parent root = loader.load();

			ControladorEquipo controladorEquipo = loader.getController();
			controladorEquipo.setEntrenador(this.entrenador);
			controladorEquipo.actualizarVista();

			Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Equipo Pokémon");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void abrirTienda(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tienda.fxml"));
			Parent root = loader.load();

			TiendaController tiendaController = loader.getController();
			tiendaController.init(entrenador, BDConnection.getConnection());

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Tienda Pokémon");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void salir(ActionEvent event) {
		guardarProgreso();
	
	public void guardarProgreso() {
	    try (Connection conn = ConexionBD.establecerConexion()) {
	        if (conn != null) {
	            entrenador.actualizarEquipoEnBD(conn);
	            entrenador.guardarObjetosEnBD(conn);
	            entrenador.guardarPokeballsEnBD(conn);
	            entrenador.guardarPokedollarsEnBD(conn);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;

		// Actualizar los labels con el nombre y los pokedólares
		if (lblEntrenador != null && lblPokedolares != null) {
			lblEntrenador.setText(entrenador.getNombre());
			lblPokedolares.setText(entrenador.getPokedollars() + " ₽");
		}
	}

	// Permite iniciar menu desde otras pantallas
	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}

	@FXML
	public void initialize() {
		// Detiene y deshabilita la musica del login al entra al menu
		if (ControladorLogin.mediaPlayer != null) {
			ControladorLogin.mediaPlayer.stop();
			ControladorLogin.mediaPlayer.dispose();
			ControladorLogin.mediaPlayer = null;
		}
	}

	// Getters & Setters
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;

		// Actualizar los labels con el nombre y los pokedollars
		if (lblEntrenador != null && lblPokedolares != null) {
			lblEntrenador.setText(entrenador.getNombre());
			lblPokedolares.setText(entrenador.getPokedollars() + " ₽");
		}
	}
}
