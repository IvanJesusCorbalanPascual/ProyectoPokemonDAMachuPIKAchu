package controller;

import java.io.IOException;
import java.util.ArrayList;
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
import model.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import model.Entrenador;
import model.Estado;
import model.Movimiento;
import model.Pokemon;
import model.Tipo;
import model.TipoMovimiento;
import dao.MovimientoDAO;
import dao.PokemonDAO;

public class controladorCombate {

	// Variables
	private Stage primaryStage;
	private Entrenador entrenador;
	private Pokemon pokemonJugador;
	private Pokemon pokemonEnemigo;
	private int psActualJugador;
	private int psActualEnemigo;
	private List<Pokemon> equipoEnemigo;
	private int indiceEnemigo = 0;
	private int pokemonsDerrotadosJugador = 0;
	private int pokemonsDerrotadosEnemigo = 0;
	private boolean turnoJugadorPrimero;
	private int turno = 0;
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
	private Button salirBtn;
	@FXML
	private Button switchCombatPokemon;
	@FXML
	private AnchorPane textCombatLog;

	@FXML
	private javafx.scene.control.TextArea combatLogArea;

	private void logCombate(String mensaje) {
		combatLogArea.appendText(mensaje + "\n");
		// Esto baja automáticamente nuestro log
		combatLogArea.setScrollTop(Double.MAX_VALUE);
	}

	@FXML
	void onAttack1(ActionEvent event) {
		System.out.println("Botón de ataque 1 pulsado.");
		ejecutarAtaque(0);
	}

	@FXML
	void onAttack2(ActionEvent event) {
		System.out.println("Botón de ataque 1 pulsado.");
		ejecutarAtaque(1);
	}

	@FXML
	void onAttack3(ActionEvent event) {
		System.out.println("Botón de ataque 1 pulsado.");
		ejecutarAtaque(2);
	}

	@FXML
	void onAttack4(ActionEvent event) {
		System.out.println("Botón de ataque 1 pulsado.");
		ejecutarAtaque(3);
	}

	@FXML
	void cambiarPokemon(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seleccionPokemon.fxml"));
			Parent root = loader.load();

			// Pasar entrenador o el Pokémon actual si hace falta
			SeleccionarPokemonController controller = loader.getController();
			controller.setContextoCompleto(entrenador, pokemonJugador, pokemonEnemigo, psActualJugador, psActualEnemigo,
					primaryStage, equipoEnemigo, indiceEnemigo);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void mostrarPantallaVictoria() {
		logCombate("\n¡Has ganado el combate contra todos los Pokémon enemigos!");
		desactivarBotonesAtaque();
		switchCombatPokemon.setDisable(true);

		// Recompensa de dinero
		int recompensa = 300 + (pokemonEnemigo.getNivel() * 10);
		entrenador.setPokedollars(entrenador.getPokedollars() + recompensa);
		logCombate("Has ganado " + recompensa + " Pokédólares.");

		// Guardar estado del equipo y dinero en la base de datos
		try (Connection conn = ConexionBD.establecerConexion()) {
			entrenador.actualizarEquipoEnBD(conn);
			entrenador.actualizarPokedollarsEnBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void mostrarPantallaDerrota() {
		logCombate("\nTodos tus Pokémon han sido derrotados. Has perdido el combate.");
		desactivarBotonesAtaque();

		// Guarda el estado del equipo y dinero también en la derrota
		try (Connection conn = ConexionBD.establecerConexion()) {
			entrenador.actualizarEquipoEnBD(conn);
			entrenador.actualizarPokedollarsEnBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ejecutarAtaque(int index) {
		logCombate("Turno actual: " + turno);
		boolean esTurnoJugador = (turnoJugadorPrimero && turno % 2 == 1) || (!turnoJugadorPrimero && turno % 2 == 0);
		if (!esTurnoJugador) {
			logCombate("No es tu turno.");
			return;
		}

		pokemonJugador.aplicarEfectoEstado();
		actualizarVistaCombate();
		if (pokemonJugador.estaDebilitado()) {
			playerNameLabel.setText(pokemonJugador.getNombre() + " ha sido derrotado");
			desactivarBotonesAtaque();
			return;
		}

		if (!pokemonJugador.puedeAtacar()) {
			logCombate(pokemonJugador.getNombre() + " está " + pokemonJugador.getEstado().getNombre()
					+ " y no puede atacar.");
			turno++;
			ataqueEnemigo();
			return;
		}

		List<Movimiento> movimientos = pokemonJugador.getMovimientosDisponibles();
		if (index >= movimientos.size())
			return;

		Movimiento movimiento = movimientos.get(index);
		if (!movimiento.tienePP()) {
			logCombate("¡" + movimiento.getNombre() + " no tiene PP!");
			return;
		}

		movimiento.gastarPP();
		switch (movimiento.getTipo()) {
		case ATAQUE -> {
			int danio = calcularDanio(pokemonJugador, pokemonEnemigo, movimiento);
			psActualEnemigo -= danio;
			if (psActualEnemigo < 0)
				psActualEnemigo = 0;
			logCombate(
					pokemonJugador.getNombre() + " usó " + movimiento.getNombre() + " e hizo " + danio + " de daño.");
		}
		case ESTADO -> {
			String estadoMovimiento = movimiento.getEstado();
			if (estadoMovimiento != null && !estadoMovimiento.equalsIgnoreCase("ninguno")
					&& !pokemonEnemigo.getEstado().estaEstadoAlterado()) {
				try {
					pokemonEnemigo.setEstado(Enum.valueOf(model.Estado.class, estadoMovimiento.toUpperCase()));
					logCombate(pokemonEnemigo.getNombre() + " fue afectado por " + estadoMovimiento + ".");
				} catch (IllegalArgumentException e) {
					logCombate("Estado no válido: " + estadoMovimiento);
				}
			}
		}
		case MEJORA -> {
			String mejora = movimiento.getMejora();
			if (mejora != null) {
				switch (mejora.toLowerCase()) {
				case "ataque" -> pokemonJugador.setAtaque(pokemonJugador.getAtaque() + 2);
				case "defensa" -> pokemonJugador.setDefensa(pokemonJugador.getDefensa() + 2);
				case "velocidad" -> pokemonJugador.setVelocidad(pokemonJugador.getVelocidad() + 2);
				default -> logCombate("⚠️ Mejora desconocida: " + mejora);
				}
				logCombate(pokemonJugador.getNombre() + " mejoró su " + mejora + " con " + movimiento.getNombre());
			}
		}
		}

		actualizarVistaCombate();
		if (psActualEnemigo <= 0) {
			logCombate(pokemonEnemigo.getNombre() + " ha sido derrotado.");
			pokemonsDerrotadosEnemigo++;

			// Otorga experiencia y comprueba si sube de nivel
			int expGanada = 10 + pokemonEnemigo.getNivel() * 2;
			logCombate(pokemonJugador.getNombre() + " ganó " + expGanada + " puntos de experiencia.");
			int nivelAnterior = pokemonJugador.getNivel();
			pokemonJugador.subirExperiencia(expGanada);
			if (pokemonJugador.getNivel() > nivelAnterior) {
				logCombate(pokemonJugador.getNombre() + " subió al nivel " + pokemonJugador.getNivel() + "!");
				pokemonJugador.setPs(pokemonJugador.getPs() + 5);
				pokemonJugador.setAtaque(pokemonJugador.getAtaque() + 2);
				pokemonJugador.setDefensa(pokemonJugador.getDefensa() + 2);
				pokemonJugador.setVelocidad(pokemonJugador.getVelocidad() + 1);
				logCombate(pokemonJugador.getNombre() + " mejoró sus estadísticas al subir de nivel.");
				PokemonDAO.actualizarEstadisticas(pokemonJugador); // Lo guarda en la base de datos
			}

			if (pokemonsDerrotadosEnemigo >= 6) {
				mostrarPantallaVictoria();
				return;
			}

			indiceEnemigo++;
			if (equipoEnemigo != null && indiceEnemigo < equipoEnemigo.size()) {
				pokemonEnemigo = equipoEnemigo.get(indiceEnemigo);
				psActualEnemigo = pokemonEnemigo.getPs();
				logCombate("¡El enemigo envía a " + pokemonEnemigo.getNombre() + "!");
			} else {
				mostrarPantallaVictoria(); // <- Esto marca correctamente que ganaste
				return;
			}

			actualizarVistaCombate();
		}

		turno++;
		pokemonJugador.setPs(psActualJugador);
		ataqueEnemigo();
	}

	@FXML
	void onExit(ActionEvent event) {
		try {
			// Guardar el estado del equipo y dinero al salir del combate
			try (Connection conn = ConexionBD.establecerConexion()) {
				if (conn != null) {
					entrenador.actualizarEquipoEnBD(conn);
					entrenador.actualizarPokedollarsEnBD();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Cargar la vista del menú principal
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

	public void init(Stage Stage) {
		this.primaryStage = Stage;
	}

	@FXML
	public void initialize() {
	}
	
	public void iniciarCombate() {
		List<Pokemon> equipo = entrenador.getEquipo();
		if (equipo.isEmpty())
			return;

		pokemonJugador = entrenador.obtenerPrimerPokemonConVida();
		if (pokemonJugador == null) {
		    logCombate("No tienes Pokémon con vida.");
		    mostrarPantallaDerrota();
		    return;
		}
		pokemonJugador.setMovimientosDisponibles(MovimientoDAO.obtenerMovimientosPorPokemon(pokemonJugador.getNumPokedex()));
		psActualJugador = pokemonJugador.getPs();

		// CAMBIO: Generamos un equipo enemigo completo
		equipoEnemigo = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Pokemon enemigo = Captura.generarPokemonAleatorio();
			enemigo.setMovimientosDisponibles(MovimientoDAO.obtenerMovimientosPorPokemon(enemigo.getNumPokedex()));
			equipoEnemigo.add(enemigo);
		}
		pokemonEnemigo = equipoEnemigo.get(0);
		psActualJugador = pokemonJugador.getPs();
		psActualEnemigo = pokemonEnemigo.getPs();

		turnoJugadorPrimero = pokemonJugador.getVelocidad() >= pokemonEnemigo.getVelocidad();
		turno = 1;
		actualizarVistaCombate();
		if (!turnoJugadorPrimero)
			ataqueEnemigo();
	}

	public void actualizarVistaCombate() {
		playerNameLabel.setText(pokemonJugador.getNombre() + " (HP: " + psActualJugador + ")");
		enemyNameLabel.setText(pokemonEnemigo.getNombre() + " (HP: " + psActualEnemigo + ")");
		playerHealthBar.setProgress((double) psActualJugador / pokemonJugador.getPsMax());
		enemyHealthBar.setProgress((double) psActualEnemigo / pokemonEnemigo.getPs());
		try {
			playerPokemonImage.setImage(new javafx.scene.image.Image(
					getClass().getResource("/imagenes/Pokemon/Back/" + pokemonJugador.getNumPokedex() + ".png")
							.toExternalForm()));
			rivalPokemonImage.setImage(new javafx.scene.image.Image(
					getClass().getResource("/imagenes/Pokemon/Front/" + pokemonEnemigo.getNumPokedex() + ".png")
							.toExternalForm()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Movimiento> movimientos = pokemonJugador.getMovimientosDisponibles();
		attackBtn1.setText(movimientos.size() > 0 ? movimientos.get(0).getNombre() + " ("
				+ movimientos.get(0).getPpActual() + "/" + movimientos.get(0).getPpMax() + ")" : "Ataque 1");
		attackBtn2.setText(movimientos.size() > 1 ? movimientos.get(1).getNombre() + " ("
				+ movimientos.get(1).getPpActual() + "/" + movimientos.get(1).getPpMax() + ")" : "Ataque 2");
		attackBtn3.setText(movimientos.size() > 2 ? movimientos.get(2).getNombre() + " ("
				+ movimientos.get(2).getPpActual() + "/" + movimientos.get(2).getPpMax() + ")" : "Ataque 3");
		attackBtn4.setText(movimientos.size() > 3 ? movimientos.get(3).getNombre() + " ("
				+ movimientos.get(3).getPpActual() + "/" + movimientos.get(3).getPpMax() + ")" : "Ataque 4");
	}

	private void ataqueEnemigo() {
		pokemonEnemigo.aplicarEfectoEstado();

		if (pokemonEnemigo.estaDebilitado())
			return;

		List<Movimiento> movimientos = pokemonEnemigo.getMovimientosDisponibles();
		Movimiento movimiento = movimientos.stream().filter(Movimiento::tienePP).findAny().orElse(null);
		if (movimiento == null)
			return;
		movimiento.gastarPP();
		if (movimiento.getTipo() == null)
			return;

		switch (movimiento.getTipo()) {
		case ATAQUE -> {
			int danio = calcularDanio(pokemonEnemigo, pokemonJugador, movimiento);
			psActualJugador -= danio;
			if (psActualJugador < 0)
				psActualJugador = 0;
			logCombate(
					pokemonEnemigo.getNombre() + " usó " + movimiento.getNombre() + " e hizo " + danio + " de daño.");
		}
		case ESTADO -> {
			String estadoMovimiento = movimiento.getEstado();
			if (estadoMovimiento != null && !estadoMovimiento.equalsIgnoreCase("ninguno")
					&& !pokemonJugador.getEstado().estaEstadoAlterado()) {
				try {
					pokemonJugador.setEstado(Enum.valueOf(Estado.class, estadoMovimiento.toUpperCase()));
					logCombate(pokemonJugador.getNombre() + " fue afectado por " + estadoMovimiento);
				} catch (IllegalArgumentException ignored) {
				}
			}
		}
		case MEJORA -> {
			String mejora = movimiento.getMejora();
			if (mejora != null) {
				switch (mejora.toLowerCase()) {
				case "ataque" -> pokemonEnemigo.setAtaque(pokemonEnemigo.getAtaque() + 2);
				case "defensa" -> pokemonEnemigo.setDefensa(pokemonEnemigo.getDefensa() + 2);
				case "velocidad" -> pokemonEnemigo.setVelocidad(pokemonEnemigo.getVelocidad() + 2);
				}
				logCombate(pokemonEnemigo.getNombre() + " mejoró su " + mejora);
			}
		}
		}
		if (psActualJugador <= 0) {
			psActualJugador = 0; // Asegura que sea 0 exacto
			pokemonJugador.setPs(0); //Esto es clave en el caso de 0 ps
			logCombate(pokemonJugador.getNombre() + " ha sido derrotado.");
			pokemonsDerrotadosJugador++;
			
			actualizarVistaCombate();
			
			if (pokemonsDerrotadosJugador >= 6 || !hayPokemonDisponibles()) {
				mostrarPantallaDerrota();
				return;
			}
			desactivarBotonesAtaque();
			forzarCambioDePokemon();
			return;
		}
		pokemonJugador.setPs(psActualJugador); // Esto guarda la vida actual si sigue vivo

		turno++;
		actualizarVistaCombate();
	}

	private void desactivarBotonesAtaque() {
		attackBtn1.setDisable(true);
		attackBtn2.setDisable(true);
		attackBtn3.setDisable(true);
		attackBtn4.setDisable(true);
	}

	private void activarBotonesAtaque() {
		attackBtn1.setDisable(false);
		attackBtn2.setDisable(false);
		attackBtn3.setDisable(false);
		attackBtn4.setDisable(false);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPokemonEnemigo(Pokemon enemigo, int ps) {
		this.pokemonEnemigo = enemigo;
		this.psActualEnemigo = ps;
	}

	public void setPsActualJugador(int ps) {
		this.psActualJugador = ps;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setEntrenador(Entrenador entrenador) {
		setEntrenador(entrenador, true); // <- Versión con solo 1 parámetro, que llama a la otra
	}

	public void setEntrenador(Entrenador entrenador, boolean iniciarCombate) {
		this.entrenador = entrenador;
		if (iniciarCombate) {
			iniciarCombate();
		}
	}

	public void setPokemonJugador(Pokemon pokemon) {
		this.pokemonJugador = pokemon;
		this.psActualJugador = pokemon.getPs();
		if (pokemonEnemigo != null) {
			actualizarVistaCombate();
		}
	}

	public void setPokemonJugador(Pokemon pokemon, int ps) {
		this.pokemonJugador = pokemon;
		this.psActualJugador = ps;

		if (pokemonEnemigo != null) {
			actualizarVistaCombate();

			// Si el nuevo Pokémon no está debilitado activara los botones
			if (!pokemon.estaDebilitado()) {
				activarBotonesAtaque();
			}
		}
	}


	public void setEquipoEnemigo(List<Pokemon> equipoEnemigo, int indice, int psActual) {
		this.equipoEnemigo = equipoEnemigo;
		this.indiceEnemigo = indice;
		this.pokemonEnemigo = equipoEnemigo.get(indice);
		this.psActualEnemigo = psActual;
	}

	private int calcularDanio(Pokemon atacante, Pokemon defensor, Movimiento movimiento) {
		double multiplicador = Tipo.calcularMultiplicador(movimiento.getTipoElemento(), defensor.getTipo1(),
				defensor.getTipo2());

		int nivel = 50; // puedes cambiarlo si tienes una lógica de niveles

		double base = (((2.0 * nivel) / 5 + 2) * movimiento.getPotencia() * atacante.getAtaque())
				/ (double) defensor.getDefensa();
		int danio = (int) ((base / 50 + 2) * multiplicador);

		// Bonus de objeto (como "pesa")
		if (atacante.getObjeto() != null && atacante.getObjeto().getNombre().equalsIgnoreCase("pesa")) {
			danio *= 1.3;
		}

		return Math.max(1, danio);
	}

	private boolean hayPokemonDisponibles() {
		return entrenador.getEquipo().stream().anyMatch(p -> !p.estaDebilitado());
	}

	private void forzarCambioDePokemon() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seleccionPokemon.fxml"));
			Parent root = loader.load();

			SeleccionarPokemonController controller = loader.getController();
			controller.setContextoCompleto(entrenador, pokemonJugador, pokemonEnemigo, psActualJugador, psActualEnemigo,
					primaryStage, equipoEnemigo, indiceEnemigo);

			// Mostrar automáticamente mensaje
			controller.setMensaje("¡Tu Pokémon se ha debilitado!");

			primaryStage.setScene(new Scene(root));
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
