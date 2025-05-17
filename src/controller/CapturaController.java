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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Captura;
import model.Entrenador;
import model.Pokemon;
import model.Sexo;
import bd.BDConnection;

public class CapturaController {

	// Variables
	private Pokemon pokemonActual;
	private Entrenador entrenador;
	private Stage primaryStage;

	// Variables FXML
	@FXML
	private Button btnSalir;
	@FXML
	private ImageView imgCapturar;
	@FXML
	private ImageView imgGenerar;
	@FXML
	private ImageView imgPokemonGenerado;
	@FXML
	private Label lblResultado;
	@FXML
	private Label lblPokemonGenerado;
	@FXML
	private Label numPokeballs;
	@FXML
	private ImageView pokeball;

	// Metodos
	public void init(Stage stage) {
		this.primaryStage = stage;
	}

	// Actualizando las pokeballs restantes
	private void actualizarContadorPokeballs() {
		numPokeballs.setText("Pokéballs: " + entrenador.getPokeballs());
	}
	
	// Metodo capturar, solo funciona si el entrenador tiene pokeballs
	@FXML
	void capturar(MouseEvent event) {
		if (pokemonActual == null)
			return;

		if (entrenador.getPokeballs() <= 0) {
			lblResultado.setText("¡No te quedan Pokéballs!");
			return;
		}

		// Gasta una Pokeball
		entrenador.setPokeballs(entrenador.getPokeballs() - 1);
		actualizarContadorPokeballs();

		boolean exito = Captura.intentarCaptura();

		if (exito) {
			lblResultado.setText("¡Has capturado a " + pokemonActual.getNombre() + "!");
			// Aquí puedes añadir el Pokémon al equipo/caja si quieres
			entrenador.añadirPokemon(pokemonActual);
			generar(event);
		} else {
			lblResultado.setText("¡" + pokemonActual.getNombre() + " ha escapado!");
		}

		// Guardar pokéballs actualizadas
		entrenador.guardarPokeballsEnBD(BDConnection.getConnection());
	}

	// Metodo generar pokemon asignando un sexo aleatorio en el proceso
	@FXML
	void generar(MouseEvent event) {
		pokemonActual = Captura.generarPokemonAleatorio();

		if (pokemonActual != null) {
			// Asignar sexo aleatorio
			if (pokemonActual.getSexo() == null) {
				pokemonActual.setSexo(Math.random() < 0.5 ? Sexo.MACHO : Sexo.HEMBRA);
			}

			// Mostrar nombre y sexo
			lblPokemonGenerado.setText(pokemonActual.getNombre() + " (" + pokemonActual.getSexo() + ")");

			try {
				String ruta = "/imagenes/Pokemon/Front/" + pokemonActual.getNumPokedex() + ".png";
				var is = getClass().getResourceAsStream(ruta);
				if (is != null) {
					Image imagen = new Image(is);
					imgPokemonGenerado.setImage(imagen);
				} else {
					System.out.println("Imagen no encontrada para " + pokemonActual.getNombre() + " ("
							+ pokemonActual.getNumPokedex() + ")");
					imgPokemonGenerado.setImage(new Image(getClass().getResourceAsStream("")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Generado: " + pokemonActual.getNombre() + " (" + pokemonActual.getNumPokedex()
					+ ") | Sexo: " + pokemonActual.getSexo());
		}
	}

	@FXML
	void salir(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
			Parent root = loader.load();

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

	// Getters & Setters
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
		actualizarContadorPokeballs();
	}
}
