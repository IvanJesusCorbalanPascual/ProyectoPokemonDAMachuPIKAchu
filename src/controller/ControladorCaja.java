package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import bd.BDConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class ControladorCaja {

	// Variables 
	private Stage primaryStage;
	private Entrenador entrenador;
	private int cajaActual = 0;
	private static final int POKEMONS_POR_CAJA = 24;

	// Variables FXML
	@FXML
	private Button btnAnteriorCaja;
	@FXML
	private Button btnSiguienteCaja;
	@FXML
	private ImageView btnEquipo;
	@FXML
	private ImageView btnMover;
	@FXML
	private ImageView btnVolver;
	@FXML
	private ImageView fondoCaja;

	@FXML
	private ImageView imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6, imgPoke7, imgPoke8, imgPoke9,
			imgPoke10, imgPoke11, imgPoke12, imgPoke13, imgPoke14, imgPoke15, imgPoke16, imgPoke17, imgPoke18,
			imgPoke19, imgPoke20, imgPoke21, imgPoke22, imgPoke23, imgPoke24;

	// Metodos
	
	public void actualizarVista() {
		ImageView[] imageViews = { imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6, imgPoke7, imgPoke8,
				imgPoke9, imgPoke10, imgPoke11, imgPoke12, imgPoke13, imgPoke14, imgPoke15, imgPoke16, imgPoke17,
				imgPoke18, imgPoke19, imgPoke20, imgPoke21, imgPoke22, imgPoke23, imgPoke24 };

		List<Pokemon> cajaCompleta = entrenador.getCaja();
		int inicio = cajaActual * POKEMONS_POR_CAJA;
		int fin = Math.min(inicio + POKEMONS_POR_CAJA, cajaCompleta.size());

		for (int i = 0; i < imageViews.length; i++) {
			int pokeIndex = inicio + i;
			if (pokeIndex < fin) {
				Pokemon p = cajaCompleta.get(pokeIndex);
				int num = p.getNumPokedex();
				imageViews[i]
						.setImage(new Image(getClass().getResourceAsStream("/Imagenes/Pokemon/Front/" + num + ".png")));
			} else {
				imageViews[i].setImage(null);
			}
		}
	}

	private void moverAequipo(int index) {
		List<Pokemon> caja = entrenador.getCaja();
		List<Pokemon> equipo = entrenador.getEquipo();

		int realIndex = cajaActual * POKEMONS_POR_CAJA + index;

		if (realIndex < caja.size() && equipo.size() < 6) {
			Pokemon p = caja.remove(realIndex);
			equipo.add(p);
			actualizarVista();
			entrenador.actualizarEquipoEnBD(BDConnection.getConnection());
		}
	}

	@FXML
	void siguienteCaja(ActionEvent event) {
		if ((cajaActual + 1) * POKEMONS_POR_CAJA < entrenador.getCaja().size()) {
			cajaActual++;
			actualizarVista();
		}
	}

	@FXML
	void anteriorCaja(ActionEvent event) {
		if (cajaActual > 0) {
			cajaActual--;
			actualizarVista();
		}
	}

	private void liberarPokemon(int index) {
		System.out.println("Intentando liberar Pokémon en índice " + index);
		int realIndex = cajaActual * POKEMONS_POR_CAJA + index;

		if (realIndex >= entrenador.getCaja().size())
			return;

		Pokemon pokemon = entrenador.getCaja().get(realIndex);

		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Liberar Pokémon");
		alerta.setHeaderText("¿Liberar a " + pokemon.getNombre() + "?");
		alerta.setContentText("Esta acción no se puede deshacer.");

		Optional<ButtonType> resultado = alerta.showAndWait();
		if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
			// Eliminar de la base de datos
			entrenador.eliminarPokemonDeBD(pokemon);

			// Eliminar de la lista
			entrenador.getCaja().remove(realIndex);

			// Refrescar vista
			actualizarVista();
		}
	}

	@FXML
	void btnEquipoClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/equipo.fxml"));
			Parent root = loader.load();

			ControladorEquipo controladorEquipo = loader.getController();
			controladorEquipo.setEntrenador(this.entrenador);
			controladorEquipo.setPrimaryStage(this.primaryStage);
			controladorEquipo.actualizarVista();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Equipo Pokémon");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void btnEquipoEnter(MouseEvent event) {
		((Node) event.getSource()).setOpacity(0.9);
	}

	@FXML
	void btnEquipoExit(MouseEvent event) {
		((Node) event.getSource()).setOpacity(1.0);
	}

	@FXML
	void btnMoverClick(MouseEvent event) {
	}

	@FXML
	void btnMoverEnter(MouseEvent event) {
		((Node) event.getSource()).setOpacity(0.9);
	}

	@FXML
	void btnMoverExit(MouseEvent event) {
		((Node) event.getSource()).setOpacity(1.0);
	}

	@FXML
	void btnVolverClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
			Parent root = loader.load();

			MenuController menuController = loader.getController();
			menuController.setEntrenador(this.entrenador);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Menú Principal");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void btnVolverEnter(MouseEvent event) {
		((Node) event.getSource()).setOpacity(0.9);
	}

	@FXML
	void btnVolverExit(MouseEvent event) {
		((Node) event.getSource()).setOpacity(1.0);
	}

	// Metodos imgPoke?Click. click normal para mover el pokemon al equipo, clic
	// derecho para liberarlo
	@FXML
	void imgPoke1Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(0);
		else
			moverAequipo(0);
	}

	@FXML
	void imgPoke2Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(1);
		else
			moverAequipo(1);
	}

	@FXML
	void imgPoke3Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(2);
		else
			moverAequipo(2);
	}

	@FXML
	void imgPoke4Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(3);
		else
			moverAequipo(3);
	}

	@FXML
	void imgPoke5Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(4);
		else
			moverAequipo(4);
	}

	@FXML
	void imgPoke6Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(5);
		else
			moverAequipo(5);
	}

	@FXML
	void imgPoke7Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(6);
		else
			moverAequipo(6);
	}

	@FXML
	void imgPoke8Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(7);
		else
			moverAequipo(7);
	}

	@FXML
	void imgPoke9Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(8);
		else
			moverAequipo(8);
	}

	@FXML
	void imgPoke10Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(9);
		else
			moverAequipo(9);
	}

	@FXML
	void imgPoke11Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(10);
		else
			moverAequipo(10);
	}

	@FXML
	void imgPoke12Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(11);
		else
			moverAequipo(11);
	}

	@FXML
	void imgPoke13Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(12);
		else
			moverAequipo(12);
	}

	@FXML
	void imgPoke14Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(13);
		else
			moverAequipo(13);
	}

	@FXML
	void imgPoke15Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(14);
		else
			moverAequipo(14);
	}

	@FXML
	void imgPoke16Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(15);
		else
			moverAequipo(15);
	}

	@FXML
	void imgPoke17Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(16);
		else
			moverAequipo(16);
	}

	@FXML
	void imgPoke18Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(17);
		else
			moverAequipo(17);
	}

	@FXML
	void imgPoke19Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(18);
		else
			moverAequipo(18);
	}

	@FXML
	void imgPoke20Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(19);
		else
			moverAequipo(19);
	}

	@FXML
	void imgPoke21Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(20);
		else
			moverAequipo(20);
	}

	@FXML
	void imgPoke22Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(21);
		else
			moverAequipo(21);
	}

	@FXML
	void imgPoke23Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(22);
		else
			moverAequipo(22);
	}

	@FXML
	void imgPoke24Click(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY)
			liberarPokemon(23);
		else
			moverAequipo(23);
	}
	
	// Getters & Setters
	
	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
		actualizarVista();
	}


	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}
	
	
}
