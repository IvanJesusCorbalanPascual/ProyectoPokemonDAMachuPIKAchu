package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

import java.io.IOException;
import java.util.List;

import dao.MovimientoDAO;

public class SeleccionarPokemonController {

	private List<Pokemon> equipo;
	private Entrenador entrenador;
	private Pokemon pokemonActual;
	private Pokemon enemigo;
	private int psJugador;
	private int psEnemigo;
	private List<Pokemon> equipoEnemigo;
	private int indiceEnemigo;
	private Stage primaryStage;

	@FXML
	private ImageView btnUsar1, btnUsar2, btnUsar3, btnUsar4, btnUsar5, btnUsar6;
	@FXML
	private ImageView btnVolver;

	@FXML
	private Label eligePokeLbl;

	@FXML
	private ImageView imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6;
	@FXML
	private Label nombrePoke1, nombrePoke2, nombrePoke3, nombrePoke4, nombrePoke5, nombrePoke6;
	@FXML
	private Label nivelPoke1, nivelPoke2, nivelPoke3, nivelPoke4, nivelPoke5, nivelPoke6;
	@FXML
	private ProgressBar vidaPoke1, vidaPoke2, vidaPoke3, vidaPoke4, vidaPoke5, vidaPoke6;
	@FXML
	private Label vidaTxtPoke1, vidaTxtPoke2, vidaTxtPoke3, vidaTxtPoke4, vidaTxtPoke5, vidaTxtPoke6;

	public void setEquipo(List<Pokemon> equipo) {
		this.equipo = equipo;
		cargarEquipo();
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
		mostrarEquipo(); // O cualquier método para rellenar los datos de la vista
	}

	public void setContextoCompleto(Entrenador entrenador, Pokemon actual, Pokemon enemigo, int psJugador,
			int psEnemigo, Stage stage, List<Pokemon> equipoEnemigo, int indiceEnemigo) {
		this.entrenador = entrenador;
		this.pokemonActual = actual;
		this.enemigo = enemigo;
		this.psJugador = psJugador;
		this.psEnemigo = psEnemigo;
		this.primaryStage = stage;
		this.equipo = entrenador.getEquipo();
		this.equipoEnemigo = equipoEnemigo;
		this.indiceEnemigo = indiceEnemigo;
		mostrarEquipo();
	}

	public void setMensaje(String mensaje) {
		if (eligePokeLbl != null) {
			eligePokeLbl.setText(mensaje);
		} else {
			System.err.println("eligePokeLbl es null.");
		}
	}

	private void cargarEquipo() {
		ImageView[] imgs = { imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6 };
		Label[] nombres = { nombrePoke1, nombrePoke2, nombrePoke3, nombrePoke4, nombrePoke5, nombrePoke6 };
		Label[] niveles = { nivelPoke1, nivelPoke2, nivelPoke3, nivelPoke4, nivelPoke5, nivelPoke6 };
		ProgressBar[] barras = { vidaPoke1, vidaPoke2, vidaPoke3, vidaPoke4, vidaPoke5, vidaPoke6 };
		Label[] vidaTxt = { vidaTxtPoke1, vidaTxtPoke2, vidaTxtPoke3, vidaTxtPoke4, vidaTxtPoke5, vidaTxtPoke6 };

		for (int i = 0; i < equipo.size(); i++) {
			Pokemon p = equipo.get(i);
			nombres[i].setText(p.getMote());
			niveles[i].setText("Nv: " + p.getNivel());
			barras[i].setProgress((double) p.getPs() / p.getPsMax());
			vidaTxt[i].setText(p.getPs() + " / " + p.getPsMax());

			try {
				imgs[i].setImage(new javafx.scene.image.Image(getClass()
						.getResource("/imagenes/Pokemon/Front/" + p.getNumPokedex() + ".png").toExternalForm()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void usarPokemon(int index) {
		Pokemon seleccionado = equipo.get(index);
		if (seleccionado.estaDebilitado()) {
			eligePokeLbl.setText("Ese Pokémon está debilitado.");
			return;
		}

		if (equipoEnemigo == null || indiceEnemigo >= equipoEnemigo.size()) {
			eligePokeLbl.setText("¡Ya no quedan Pokémon enemigos!");
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/combate.fxml"));
			Parent root = loader.load();

			controladorCombate combateController = loader.getController();

			seleccionado.setMovimientosDisponibles(
					MovimientoDAO.obtenerMovimientosPorPokemon(seleccionado.getNumPokedex()));

			// Orden importante
			combateController.setPrimaryStage(primaryStage);
			combateController.setEquipoEnemigo(equipoEnemigo, indiceEnemigo, psEnemigo);
			combateController.setPokemonJugador(seleccionado, seleccionado.getPs());
			combateController.setEntrenador(entrenador, false); // No reinicia el combate

			primaryStage.setScene(new Scene(root));
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void mostrarEquipo() {
		List<Pokemon> equipo = entrenador.getEquipo();

		ImageView[] botonesUsar = { btnUsar1, btnUsar2, btnUsar3, btnUsar4, btnUsar5, btnUsar6 };
		ImageView[] imagenes = { imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6 };
		Label[] nombres = { nombrePoke1, nombrePoke2, nombrePoke3, nombrePoke4, nombrePoke5, nombrePoke6 };
		Label[] niveles = { nivelPoke1, nivelPoke2, nivelPoke3, nivelPoke4, nivelPoke5, nivelPoke6 };
		ProgressBar[] barrasVida = { vidaPoke1, vidaPoke2, vidaPoke3, vidaPoke4, vidaPoke5, vidaPoke6 };
		Label[] textosVida = { vidaTxtPoke1, vidaTxtPoke2, vidaTxtPoke3, vidaTxtPoke4, vidaTxtPoke5, vidaTxtPoke6 };

		for (int i = 0; i < 6; i++) {
			if (i < equipo.size()) {
				Pokemon p = equipo.get(i);
				nombres[i].setText(p.getNombre());
				niveles[i].setText("Nv: " + p.getNivel());

				if (p.estaDebilitado()) {
				    textosVida[i].setText("DEBILITADO");
				    barrasVida[i].setProgress(0);
				    barrasVida[i].setStyle("-fx-accent: grey;");

				    botonesUsar[i].setOpacity(0.3); // Se ve desactivado
				    botonesUsar[i].setOnMouseClicked(null); // Elimina el evento
				    botonesUsar[i].setMouseTransparent(true); // Evita que se le pueda hacer clic
				} else {
				    textosVida[i].setText(p.getPs() + " / " + p.getPsMax() + " HP");
				    double porcentajeVida = (double) p.getPs() / p.getPsMax();
				    barrasVida[i].setProgress(porcentajeVida);
				    barrasVida[i].setStyle("");

				    int index = i;
				    botonesUsar[i].setOpacity(1.0);
				    botonesUsar[i].setMouseTransparent(false); // << Habilita clic
				    botonesUsar[i].setOnMouseClicked(event -> usarPokemon(index));
				}


				try {
					String path = "/imagenes/Pokemon/Front/" + p.getNumPokedex() + ".png";
					imagenes[i].setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(path)));
				} catch (Exception e) {
					System.err.println("No se pudo cargar imagen del Pokémon con ID: " + p.getNumPokedex());
				}

			} else {
				nombres[i].setText("");
				niveles[i].setText("");
				textosVida[i].setText("");
				barrasVida[i].setProgress(0);
				imagenes[i].setImage(null);
			}
		}
	}

	// Botones Usar
	@FXML
	void btnUsar1Click(MouseEvent e) {
		usarPokemon(0);
	}

	@FXML
	void btnUsar2Click(MouseEvent e) {
		usarPokemon(1);
	}

	@FXML
	void btnUsar3Click(MouseEvent e) {
		usarPokemon(2);
	}

	@FXML
	void btnUsar4Click(MouseEvent e) {
		usarPokemon(3);
	}

	@FXML
	void btnUsar5Click(MouseEvent e) {
		usarPokemon(4);
	}

	@FXML
	void btnUsar6Click(MouseEvent e) {
		usarPokemon(5);
	}

	// Botón Volver
	@FXML
	void btnVolverClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/combate.fxml"));
			Parent root = loader.load();

			controladorCombate combateController = loader.getController();

			// Primero establecer todos los datos antes de actualizar vista
			combateController.setPrimaryStage(primaryStage);
			combateController.setEquipoEnemigo(equipoEnemigo, indiceEnemigo, psEnemigo); // Enemigo primero
			combateController.setPokemonJugador(pokemonActual, psJugador); // Despues jugador
			combateController.setEntrenador(entrenador, false); // No reinicia el combate

			combateController.actualizarVistaCombate();

			primaryStage.setScene(new Scene(root));
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Eventos de hover (por si deseas efectos en el futuro)
	@FXML
	void btnUsar1Enter(MouseEvent e) {
	}

	@FXML
	void btnUsar1Exit(MouseEvent e) {
	}

	@FXML
	void btnUsar2Enter(MouseEvent e) {
	}

	@FXML
	void btnUsar2Exit(MouseEvent e) {
	}

	@FXML
	void btnUsar3Enter(MouseEvent e) {
	}

	@FXML
	void btnUsar3Exit(MouseEvent e) {
	}

	@FXML
	void btnUsar4Enter(MouseEvent e) {
	}

	@FXML
	void btnUsar4Exit(MouseEvent e) {
	}

	@FXML
	void btnUsar5Enter(MouseEvent e) {
	}

	@FXML
	void btnUsar5Exit(MouseEvent e) {
	}

	@FXML
	void btnUsar6Enter(MouseEvent e) {
	}

	@FXML
	void btnUsar6Exit(MouseEvent e) {
	}

	@FXML
	void btnVolverEnter(MouseEvent e) {
	}

	@FXML
	void btnVolverExit(MouseEvent e) {
	}
}
