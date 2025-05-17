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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Entrenador;
import model.Pokemon;

public class CentroPokemonController {

	// Variables
	private Entrenador entrenador;
	private Stage primaryStage;

	// Variables FXML
	@FXML
	private ProgressBar bar1, bar2, bar3, bar4, bar5, bar6;
	@FXML
	private ImageView btnCurarPokemon;
	@FXML
	private Button btnSalir;
	@FXML
	private ImageView pkm1, pkm2, pkm3, pkm4, pkm5, pkm6;
	@FXML
	private Label pkmlbl1, pkmlbl2, pkmlbl3, pkmlbl4, pkmlbl5, pkmlbl6;

	// Metodos
	
	// Para que salgan los mismo pokemon del equipo en el centro pokemon
	private void actualizarVista() {
		if (entrenador == null)
			return;

		List<Pokemon> equipo = entrenador.getEquipo();
		actualizarSlot(0, equipo);
		actualizarSlot(1, equipo);
		actualizarSlot(2, equipo);
		actualizarSlot(3, equipo);
		actualizarSlot(4, equipo);
		actualizarSlot(5, equipo);
	}

	// M
	private void actualizarSlot(int index, List<Pokemon> equipo) {
		ImageView[] imagenes = { pkm1, pkm2, pkm3, pkm4, pkm5, pkm6 };
		Label[] nombres = { pkmlbl1, pkmlbl2, pkmlbl3, pkmlbl4, pkmlbl5, pkmlbl6 };
		ProgressBar[] barras = { bar1, bar2, bar3, bar4, bar5, bar6 };

		if (index < equipo.size()) {
			Pokemon p = equipo.get(index);
			String ruta = "/Imagenes/Pokemon/Front/" + p.getNumPokedex() + ".png";

			try {
				var stream = getClass().getResourceAsStream(ruta);
				if (stream != null) {
					imagenes[index].setImage(new Image(stream));
				} else {
					System.err.println("No se encontró la imagen: " + ruta);
					imagenes[index].setImage(null);
				}
			} catch (Exception e) {
				System.err.println("Error cargando imagen: " + e.getMessage());
				imagenes[index].setImage(null);
			}

			nombres[index].setText(p.getNombre());
			barras[index].setProgress((double) p.getPs() / p.getPsMax());
		} else {
			imagenes[index].setImage(null);
			nombres[index].setText("");
			barras[index].setProgress(0);
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

	@FXML
	private void curarEquipo() {
		if (entrenador == null)
			return;

		for (Pokemon p : entrenador.getEquipo()) {
			p.setPs(p.getPsMax()); // Restaurando la vida al maximo
		}

		actualizarVista(); // Actualizando la interfaz para reflejar los cambios
	}

	// Getters & Setters
	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
		actualizarVista();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
