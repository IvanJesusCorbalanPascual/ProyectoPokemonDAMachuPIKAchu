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
import model.Estado;
import model.Movimiento;
import model.Pokemon;
import model.Tipo;
import model.TipoMovimiento;
import dao.MovimientoDAO;

public class controladorCombate {

	// Variables
	private Stage primaryStage;
	private Entrenador entrenador;
	private Pokemon pokemonJugador;
	private Pokemon pokemonEnemigo;
	private int psActualJugador;
	private int psActualEnemigo;
	private int turno = 0;

	// Variables FXML
	@FXML private Button attackBtn1;
	@FXML private Button attackBtn2;
	@FXML private Button attackBtn3;
	@FXML private Button attackBtn4;
	@FXML private ImageView backgroundImg;
	@FXML private ProgressBar enemyHealthBar;
	@FXML private Label enemyNameLabel;
	@FXML private ImageView enemySoundButton;
	@FXML private ProgressBar playerHealthBar;
	@FXML private Label playerNameLabel;
	@FXML private ImageView playerPokemonImage;
	@FXML private Button playerPokemonSoundButton1;
	@FXML private ImageView playerSoundButton;
	@FXML private ImageView rivalPokemonImage;
	@FXML private Button rivalpokemonSoundButton;
	@FXML private Button salirBtn;
	@FXML private Button switchCombatPokemon;
	@FXML private AnchorPane textCombatLog;

	// Metodos FXML
	@FXML void onAttack1(ActionEvent event) { ejecutarAtaque(0); }
	@FXML void onAttack2(ActionEvent event) { ejecutarAtaque(1); }
	@FXML void onAttack3(ActionEvent event) { ejecutarAtaque(2); }
	@FXML void onAttack4(ActionEvent event) { ejecutarAtaque(3); }

	// Metodos
	private void ejecutarAtaque(int index) {
	    if (turno % 2 != 0) return;

	    if (!pokemonJugador.puedeAtacar()) {
	        System.out.println(pokemonJugador.getNombre() + " está " + pokemonJugador.getEstado().getNombre() + " y no puede atacar.");
	        pokemonJugador.aplicarEfectoEstado();
	        turno++;
	        ataqueEnemigo();
	        return;
	    }

	    List<Movimiento> movimientos = pokemonJugador.getMovimientosDisponibles();
	    if (index >= movimientos.size()) return;

	    Movimiento movimiento = movimientos.get(index);
	    if (!movimiento.tienePP()) {
	        System.out.println("¡" + movimiento.getNombre() + " no tiene PP!");
	        return;
	    }

	    movimiento.gastarPP();
	    actualizarVistaCombate();

	    // Manejo de tipo especial de movimiento
	    if (movimiento.getTipo() == TipoMovimiento.ESTADO) {
	        if (!movimiento.getEstado().equalsIgnoreCase("NINGUNO")) {
	            if (Math.random() < 0.3) {
	                pokemonEnemigo.setEstado(Estado.valueOf(movimiento.getEstado().toUpperCase()));
	                System.out.println(pokemonEnemigo.getNombre() + " ha sido afectado por " + movimiento.getEstado());
	            }
	        }
	        turno++;
	        ataqueEnemigo();
	        return;
	    } else if (movimiento.getTipo() == TipoMovimiento.MEJORA) {
	        if (movimiento.getMejora().equalsIgnoreCase("ataque")) {
	            pokemonJugador.setAtaque(pokemonJugador.getAtaque() + 3);
	            System.out.println(pokemonJugador.getNombre() + " aumentó su ataque");
	        }
	        turno++;
	        ataqueEnemigo();
	        return;
	    }

	    int potencia = movimiento.getPotencia();
	    int ataque = pokemonJugador.getAtaque();
	    int defensaEnemigo = pokemonEnemigo.getDefensa();

	    double multiplicador = Tipo.calcularMultiplicador(movimiento.getTipoElemento(), pokemonEnemigo.getTipo1(), pokemonEnemigo.getTipo2());
	    int danio = Math.max(1, (int) ((ataque * potencia * multiplicador) / (defensaEnemigo + 10)));

	    if (pokemonJugador.getObjeto() != null && pokemonJugador.getObjeto().getNombre().equalsIgnoreCase("pesa")) {
	        danio *= 1.3;
	    }

	    psActualEnemigo -= danio;
	    if (psActualEnemigo < 0) psActualEnemigo = 0;

	    enemyHealthBar.setProgress((double) psActualEnemigo / pokemonEnemigo.getPs());
	    enemyNameLabel.setText(pokemonEnemigo.getNombre() + " (HP: " + psActualEnemigo + ")");
	    System.out.println(pokemonJugador.getNombre() + " usó " + movimiento.getNombre() + " e hizo " + danio + " de daño.");

	    if (psActualEnemigo <= 0) {
	        enemyNameLabel.setText(pokemonEnemigo.getNombre() + " ha sido derrotado");
	        desactivarBotonesAtaque();
	        return;
	    }

	    turno++;
	    ataqueEnemigo();
	}

	// Metodo para salir al menu
	@FXML void onExit(ActionEvent event) {
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

	public void init(Stage Stage) { this.primaryStage = Stage; }
	@FXML public void initialize() {}

	public void iniciarCombate() {
		List<Pokemon> equipo = entrenador.getEquipo();
		if (equipo.isEmpty()) return;

		pokemonJugador = equipo.get(0);
		pokemonJugador.setMovimientosDisponibles(MovimientoDAO.obtenerMovimientosPorPokemon(pokemonJugador.getNumPokedex()));
		pokemonEnemigo = Captura.generarPokemonAleatorio();
		pokemonEnemigo.setMovimientosDisponibles(MovimientoDAO.obtenerMovimientosPorPokemon(pokemonEnemigo.getNumPokedex()));
		psActualJugador = pokemonJugador.getPs();
		psActualEnemigo = pokemonEnemigo.getPs();
		actualizarVistaCombate();
	}

	private void actualizarVistaCombate() {
		playerNameLabel.setText(pokemonJugador.getNombre() + " (HP: " + psActualJugador + ")");
		enemyNameLabel.setText(pokemonEnemigo.getNombre() + " (HP: " + psActualEnemigo + ")");
		playerHealthBar.setProgress((double) psActualJugador / pokemonJugador.getPs());
		enemyHealthBar.setProgress((double) psActualEnemigo / pokemonEnemigo.getPs());
		try {
			playerPokemonImage.setImage(new javafx.scene.image.Image(getClass().getResource("/imagenes/Pokemon/Front/" + pokemonJugador.getNumPokedex() + ".png").toExternalForm()));
			rivalPokemonImage.setImage(new javafx.scene.image.Image(getClass().getResource("/imagenes/Pokemon/Front/" + pokemonEnemigo.getNumPokedex() + ".png").toExternalForm()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Movimiento> movimientos = pokemonJugador.getMovimientosDisponibles();
		attackBtn1.setText(movimientos.size() > 0 ? movimientos.get(0).getNombre() + " (" + movimientos.get(0).getPpActual() + "/" + movimientos.get(0).getPpMax() + ")" : "Ataque 1");
		attackBtn2.setText(movimientos.size() > 1 ? movimientos.get(1).getNombre() + " (" + movimientos.get(1).getPpActual() + "/" + movimientos.get(1).getPpMax() + ")" : "Ataque 2");
		attackBtn3.setText(movimientos.size() > 2 ? movimientos.get(2).getNombre() + " (" + movimientos.get(2).getPpActual() + "/" + movimientos.get(2).getPpMax() + ")" : "Ataque 3");
		attackBtn4.setText(movimientos.size() > 3 ? movimientos.get(3).getNombre() + " (" + movimientos.get(3).getPpActual() + "/" + movimientos.get(3).getPpMax() + ")" : "Ataque 4");
	}

	private void ataqueEnemigo() {
		pokemonJugador.aplicarEfectoEstado();
		if (pokemonJugador.estaDebilitado()) {
			playerNameLabel.setText(pokemonJugador.getNombre() + " ha sido derrotado por el efecto del estado");
			desactivarBotonesAtaque();
			return;
		}

		List<Movimiento> movimientos = pokemonEnemigo.getMovimientosDisponibles();
		if (movimientos.isEmpty()) return;

		Movimiento movimiento = null;
		for (int i = 0; i < movimientos.size(); i++) {
			Movimiento posible = movimientos.get((int) (Math.random() * movimientos.size()));
			if (posible.tienePP()) {
				movimiento = posible;
				break;
			}
		}
		if (movimiento == null) {
			System.out.println(pokemonEnemigo.getNombre() + " no tiene movimientos con PP.");
			return;
		}
		movimiento.gastarPP();

		if (movimiento.getTipo() == TipoMovimiento.ESTADO) {
			if (!movimiento.getEstado().equalsIgnoreCase("NINGUNO")) {
				if (Math.random() < 0.3) {
					pokemonJugador.setEstado(Estado.valueOf(movimiento.getEstado().toUpperCase()));
					System.out.println(pokemonJugador.getNombre() + " ha sido afectado por " + movimiento.getEstado());
				}
			}
			turno++;
			return;
		} else if (movimiento.getTipo() == TipoMovimiento.MEJORA) {
			if (movimiento.getMejora().equalsIgnoreCase("ataque")) {
				pokemonEnemigo.setAtaque(pokemonEnemigo.getAtaque() + 3);
				System.out.println(pokemonEnemigo.getNombre() + " aumentó su ataque");
			}
			turno++;
			return;
		}

		int potencia = movimiento.getPotencia();
		int ataque = pokemonEnemigo.getAtaque();
		int defensaJugador = pokemonJugador.getDefensa();
		double multiplicador = Tipo.calcularMultiplicador(movimiento.getTipoElemento(), pokemonJugador.getTipo1(), pokemonJugador.getTipo2());
		int danio = Math.max(1, (int) ((ataque * potencia * multiplicador) / (defensaJugador + 10)));

		psActualJugador -= danio;
		if (psActualJugador < 0) psActualJugador = 0;

		playerHealthBar.setProgress((double) psActualJugador / pokemonJugador.getPs());
		playerNameLabel.setText(pokemonJugador.getNombre() + " (HP: " + psActualJugador + ")");

		System.out.println(pokemonEnemigo.getNombre() + " usó " + movimiento.getNombre() + " e hizo " + danio + " de daño.");

		if (psActualJugador <= 0) {
			playerNameLabel.setText(pokemonJugador.getNombre() + " ha sido derrotado");
			desactivarBotonesAtaque();
			return;
		}

		turno++;
	}

	private void desactivarBotonesAtaque() {
		attackBtn1.setDisable(true);
		attackBtn2.setDisable(true);
		attackBtn3.setDisable(true);
		attackBtn4.setDisable(true);
	}

	public Stage getPrimaryStage() { return primaryStage; }
	public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }
	public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; iniciarCombate(); }
}
