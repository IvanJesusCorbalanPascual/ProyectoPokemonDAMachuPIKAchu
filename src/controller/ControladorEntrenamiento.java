package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.MovimientoDAO;
import dao.PokemonDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Captura;
import model.Entrenador;
import model.Estado;
import model.Movimiento;
import model.Pokemon;
import model.Tipo;

public class ControladorEntrenamiento {

	private Stage primaryStage;
	private Entrenador entrenador;
	private Pokemon pokemonJugador;
	private Pokemon pokemonEnemigo;
	private int psActualJugador;
	private int psActualEnemigo;
	private List<Pokemon> equipoEnemigo;
	private int indiceEnemigo = 0;
	private boolean turnoJugadorPrimero;
	private int turno = 0;

	@FXML private Button attackBtn1;
	@FXML private Button attackBtn2;
	@FXML private Button attackBtn3;
	@FXML private Button attackBtn4;
	@FXML private ProgressBar enemyHealthBar;
	@FXML private Label enemyNameLabel;
	@FXML private ProgressBar playerHealthBar;
	@FXML private Label playerNameLabel;
	@FXML private ImageView playerPokemonImage;
	@FXML private ImageView rivalPokemonImage;
	@FXML private Button salirBtn;
	@FXML private AnchorPane textCombatLog;
	@FXML private TextArea combatLogArea;
	@FXML private Button switchCombatPokemon;

	@FXML
	public void initialize() {
	}

	public void init(Stage stage) {
		this.primaryStage = stage;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
		iniciarEntrenamiento();
	}

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	private void iniciarEntrenamiento() {
		pokemonJugador = entrenador.obtenerPrimerPokemonConVida();
		if (pokemonJugador == null) {
			log("No tienes Pokémon con vida.");
			return;
		}
		pokemonJugador.setMovimientosDisponibles(MovimientoDAO.obtenerMovimientosPorPokemon(pokemonJugador.getNumPokedex()));
		psActualJugador = pokemonJugador.getPs();

		equipoEnemigo = new ArrayList<>();
		int nivelReferencia = pokemonJugador.getNivel();
		for (int i = 0; i < 3; i++) {
			Pokemon enemigo = Captura.generarPokemonAleatorioNivel(nivelReferencia);
			enemigo.setMovimientosDisponibles(MovimientoDAO.obtenerMovimientosPorPokemon(enemigo.getNumPokedex()));
			equipoEnemigo.add(enemigo);
		}

		pokemonEnemigo = equipoEnemigo.get(0);
		psActualEnemigo = pokemonEnemigo.getPs();
		turnoJugadorPrimero = pokemonJugador.getVelocidad() >= pokemonEnemigo.getVelocidad();
		turno = 1;
		actualizarVista();
		if (!turnoJugadorPrimero) ataqueEnemigo();
	}

	private void actualizarVista() {
		playerNameLabel.setText(pokemonJugador.getNombre() + " (HP: " + psActualJugador + ")");
		enemyNameLabel.setText(pokemonEnemigo.getNombre() + " (HP: " + psActualEnemigo + ")");
		playerHealthBar.setProgress((double) psActualJugador / pokemonJugador.getPsMax());
		enemyHealthBar.setProgress((double) psActualEnemigo / pokemonEnemigo.getPs());

		try {
			playerPokemonImage.setImage(new Image(getClass().getResource("/imagenes/Pokemon/Back/" + pokemonJugador.getNumPokedex() + ".png").toExternalForm()));
			rivalPokemonImage.setImage(new Image(getClass().getResource("/imagenes/Pokemon/Front/" + pokemonEnemigo.getNumPokedex() + ".png").toExternalForm()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Movimiento> movimientos = pokemonJugador.getMovimientosDisponibles();
		attackBtn1.setText(movimientos.size() > 0 ? movimientos.get(0).getNombre() : "Ataque 1");
		attackBtn2.setText(movimientos.size() > 1 ? movimientos.get(1).getNombre() : "Ataque 2");
		attackBtn3.setText(movimientos.size() > 2 ? movimientos.get(2).getNombre() : "Ataque 3");
		attackBtn4.setText(movimientos.size() > 3 ? movimientos.get(3).getNombre() : "Ataque 4");
	}

	private void ejecutarAtaque(int index) {
		if (pokemonJugador.estaDebilitado()) return;

		List<Movimiento> movimientos = pokemonJugador.getMovimientosDisponibles();
		if (index >= movimientos.size()) return;
		Movimiento movimiento = movimientos.get(index);
		if (!movimiento.tienePP()) return;
		movimiento.gastarPP();

		switch (movimiento.getTipo()) {
			case ATAQUE -> {
				int danio = calcularDanio(pokemonJugador, pokemonEnemigo, movimiento);
				psActualEnemigo -= danio;
				log(pokemonJugador.getNombre() + " usó " + movimiento.getNombre() + " e hizo " + danio + " de daño.");
			}
			case ESTADO -> {
				String estado = movimiento.getEstado();
				if (estado != null && !estado.equalsIgnoreCase("ninguno")) {
					pokemonEnemigo.setEstado(Estado.valueOf(estado.toUpperCase()));
					log(pokemonEnemigo.getNombre() + " fue afectado por " + estado);
				}
			}
			case MEJORA -> {
				String mejora = movimiento.getMejora();
				if (mejora != null) {
					log(pokemonJugador.getNombre() + " mejoró su " + mejora);
				}
			}
		}

		if (psActualEnemigo <= 0) {
			log(pokemonEnemigo.getNombre() + " ha sido derrotado.");
			int expGanada = 10 + pokemonEnemigo.getNivel() * 2;
			log(pokemonJugador.getNombre() + " ganó " + expGanada + " puntos de experiencia.");
			int nivelAnterior = pokemonJugador.getNivel();
			pokemonJugador.subirExperiencia(expGanada);
			if (pokemonJugador.getNivel() > nivelAnterior) {
				log(pokemonJugador.getNombre() + " subió a nivel " + pokemonJugador.getNivel() + "!");
				PokemonDAO.actualizarEstadisticas(pokemonJugador);
			}

			indiceEnemigo++;
			if (indiceEnemigo < equipoEnemigo.size()) {
				pokemonEnemigo = equipoEnemigo.get(indiceEnemigo);
				psActualEnemigo = pokemonEnemigo.getPs();
				log("¡Nuevo enemigo: " + pokemonEnemigo.getNombre() + "!");
			} else {
				log("¡Has terminado el entrenamiento!");
				desactivarBotonesAtaque();
				return;
			}
		}

		turno++;
		actualizarVista();
		ataqueEnemigo();
	}

	private void ataqueEnemigo() {
		if (pokemonEnemigo.estaDebilitado()) return;
		List<Movimiento> movimientos = pokemonEnemigo.getMovimientosDisponibles();
		Movimiento movimiento = movimientos.stream().filter(Movimiento::tienePP).findAny().orElse(null);
		if (movimiento == null) return;
		movimiento.gastarPP();

		switch (movimiento.getTipo()) {
			case ATAQUE -> {
				int danio = calcularDanio(pokemonEnemigo, pokemonJugador, movimiento);
				psActualJugador -= danio;
				log(pokemonEnemigo.getNombre() + " usó " + movimiento.getNombre() + " e hizo " + danio + " de daño.");
			}
			case ESTADO -> {
				String estado = movimiento.getEstado();
				if (estado != null && !estado.equalsIgnoreCase("ninguno")) {
					pokemonJugador.setEstado(Estado.valueOf(estado.toUpperCase()));
					log(pokemonJugador.getNombre() + " fue afectado por " + estado);
				}
			}
			case MEJORA -> {
				String mejora = movimiento.getMejora();
				if (mejora != null) {
					log(pokemonEnemigo.getNombre() + " mejoró su " + mejora);
				}
			}
		}
		actualizarVista();
	}

	private int calcularDanio(Pokemon atacante, Pokemon defensor, Movimiento movimiento) {
		double mult = Tipo.calcularMultiplicador(movimiento.getTipoElemento(), defensor.getTipo1(), defensor.getTipo2());
		double base = ((2 * atacante.getNivel() / 5.0 + 2) * movimiento.getPotencia() * atacante.getAtaque()) / defensor.getDefensa();
		return Math.max(1, (int)((base / 50 + 2) * mult));
	}

	private void log(String msg) {
		combatLogArea.appendText(msg + "\n");
	}

	private void desactivarBotonesAtaque() {
		attackBtn1.setDisable(true);
		attackBtn2.setDisable(true);
		attackBtn3.setDisable(true);
		attackBtn4.setDisable(true);
	}

	@FXML
	void cambiarPokemon(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seleccionPokemon.fxml"));
			Parent root = loader.load();

			SeleccionarPokemonController controller = loader.getController();
			controller.setContextoCompleto(entrenador, pokemonJugador, pokemonEnemigo, psActualJugador, psActualEnemigo, primaryStage, equipoEnemigo, indiceEnemigo);

			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onAttack1(ActionEvent event) { ejecutarAtaque(0); }
	@FXML
	void onAttack2(ActionEvent event) { ejecutarAtaque(1); }
	@FXML
	void onAttack3(ActionEvent event) { ejecutarAtaque(2); }
	@FXML
	void onAttack4(ActionEvent event) { ejecutarAtaque(3); }

	@FXML
	void onExit(ActionEvent event) {
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
}
