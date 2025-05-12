package controller;

import java.io.IOException;

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
import model.Entrenador;

public class MenuController {

	private Stage primaryStage;

	private FXMLLoader loader;
	
	private Entrenador entrenador;

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
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/combate.fxml"));
	    Parent root = loader.load();

	    controladorCombate controller = loader.getController();
	    controller.setEntrenador(this.entrenador); //  Muy importante
	    controller.setPrimaryStage((Stage) ((Node) event.getSource()).getScene().getWindow());

	    controller.iniciarCombate(); 
	    
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Combate");
	    stage.show(); 
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
	void abrirPokedex(ActionEvent event) {

	}

	@FXML
	void abrirTienda(ActionEvent event) {

	}

	@FXML
	public void salir(ActionEvent event) {
		this.entrenador.actualizarEquipoEnBD(BDConnection.getConnection());
		Platform.exit();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}

	@FXML
	public void initialize() {
	    if (this.entrenador == null) {
	        this.entrenador = new Entrenador(1, "LuisRe");
	        this.entrenador.cargarPokemonsDesdeBD(BDConnection.getConnection());
	    }
	}
}
