package controller;

import java.io.IOException;
import java.sql.Connection;

import bd.BDConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import model.Entrenador;
import model.Pokemon;
import model.Sexo;
import model.Tipo;

public class CrianzaController {

	@FXML
	private ComboBox<Pokemon> boxMadre;
	@FXML
	private ComboBox<Pokemon> boxPadre;
	@FXML
	private Button btnAbrirHuevo;
	@FXML
	private Button btnCriar;
	@FXML
	private ImageView btnSalir;
	@FXML
	private ImageView imgHuevo;
	@FXML
	private ImageView pkm1;
	@FXML
	private ImageView pkm2;
	@FXML
	private Label lblResultado;

	private Entrenador entrenador;
	private Pokemon huevo;

	// Para inicializar desde otra vista
	public void init(Entrenador entrenador, Connection conexion) {
		this.entrenador = entrenador;
		boxPadre.getItems().addAll(entrenador.getEquipo());
		boxMadre.getItems().addAll(entrenador.getEquipo());

		boxPadre.setOnAction(this::elegirPadre);
		boxMadre.setOnAction(this::elegirMadre);
	}

	@FXML
	void elegirPadre(ActionEvent event) {
		Pokemon padre = boxPadre.getValue();
		mostrarImagen(pkm1, padre);
	}

	@FXML
	void elegirMadre(ActionEvent event) {
		Pokemon madre = boxMadre.getValue();
		mostrarImagen(pkm2, madre);
	}

	private void mostrarImagen(ImageView imageView, Pokemon pokemon) {
		if (pokemon != null) {
			String ruta = "/imagenes/Pokemon/Front/" + pokemon.getNumPokedex() + ".png";
			imageView.setImage(new Image(getClass().getResourceAsStream(ruta)));
		}
	}

	@FXML
	void criar(ActionEvent event) {
		Pokemon padre = boxPadre.getValue();
		Pokemon madre = boxMadre.getValue();

		if (padre == null || madre == null) {
			System.out.println("Selecciona dos Pokémon");
			lblResultado.setText("Selecciona dos Pokémon");
			return;
		}

		if (!sonCompatibles(padre, madre)) {
			System.out.println("Los Pokémon no son compatibles");
			lblResultado.setText("Los Pokémon no son compatibles");
			return;
		}

		if (padre.getFertilidad() == 0 || madre.getFertilidad() == 0) {
			System.out.println("❌ Uno de los Pokémon no tiene más fertilidad.");
			lblResultado.setText("❌ Uno de los Pokémon no tiene más fertilidad.");
			return;
		}

		// Generar el hijo (pero aún no mostrarlo)
		huevo = generarHijo(padre, madre);

		try {
			// Mostrar imagen de huevo
			Image huevoImg = new Image(getClass().getResourceAsStream("/imagenes/otros/huevo.png"));
			imgHuevo.setImage(huevoImg);
		} catch (Exception e) {
			System.out.println("No se pudo cargar la imagen del huevo.");
			e.printStackTrace();
		}

		// Reducir fertilidad correctamente
		padre.reducirFertilidad();
		madre.reducirFertilidad();

		System.out.println("Huevo generado: " + huevo.getNombre());
		System.out.println(
				"Fertilidad restante → Padre: " + padre.getFertilidad() + " | Madre: " + madre.getFertilidad());
	}

	@FXML
	void abrirHuevo(ActionEvent event) {
		if (huevo == null) {
			System.out.println("No hay huevo para abrir");
			lblResultado.setText("No hay huevo para abrir");
			return;
		}

		try {
			// Mostrar imagen del Pokémon hijo en el mismo lugar
			String ruta = "/imagenes/Pokemon/Front/" + huevo.getNumPokedex() + ".png";
			var is = getClass().getResourceAsStream(ruta);
			if (is != null) {
				imgHuevo.setImage(new Image(is));
			} else {
				imgHuevo.setImage(new Image(getClass().getResourceAsStream("")));
			}

			System.out.println("Huevo abierto: " + huevo.getNombre());

			// Aniadiendo el pokemon al equipo
			entrenador.añadirPokemon(huevo);
//            entrenador.guardarEquipoEnBD(BDConnection.getConnection());
			// Tras la crianza los padres y el huevo se resetean para evitar bugs
			boxPadre.setValue(null);
			boxMadre.setValue(null);
			huevo = null;

		} catch (Exception e) {
			System.out.println("No se pudo mostrar el Pokémon.");
			e.printStackTrace();
		}
	}

	@FXML
	void salir(MouseEvent event) {
		System.out.println("Saliendo del menu...");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
			Parent root = loader.load();

			MenuController controller = loader.getController();
			controller.setEntrenador(entrenador);
			controller.setPrimaryStage((Stage) ((Node) event.getSource()).getScene().getWindow());

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Menú Principal");
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Comprobante de sexo
	private boolean sonCompatibles(Pokemon p1, Pokemon p2) {
		if (p1 == null || p2 == null)
			return false;

		if (p1.getSexo() == null || p2.getSexo() == null) {
			System.out.println("⚠ Uno de los Pokémon no tiene sexo asignado: " + p1.getNombre() + " (" + p1.getSexo()
					+ "), " + p2.getNombre() + " (" + p2.getSexo() + ")");
			return false;
		}

		boolean compatibles = !p1.getSexo().equals(p2.getSexo()) && p1.getNombre().equalsIgnoreCase(p2.getNombre());

		if (!compatibles) {
			System.out.println("❌ No son compatibles: " + p1.getNombre() + " (" + p1.getSexo() + ") + " + p2.getNombre()
					+ " (" + p2.getSexo() + ")");
		}

		return compatibles;
	}

	private Pokemon generarHijo(Pokemon padre, Pokemon madre) {
		// Nombre y tipos heredados del padre
		String nombre = padre.getNombre();
		Tipo tipo1 = padre.getTipo1();
		Tipo tipo2 = padre.getTipo2();

		// Estableciendo sexo aleatorio
		Sexo sexo = Math.random() < 0.5 ? Sexo.MACHO : Sexo.HEMBRA;

		Pokemon hijo = new Pokemon(nombre, sexo, tipo1, tipo2);

		// Estadísticas heredadas con el máximo entre padre y madre
		hijo.setNivel(1);
		hijo.setNumPokedex(padre.getNumPokedex());

		// El metodo Math.max escoge el valor mayor de los 2, por lo tanto este metodo
		// establece las mejores estadisticas de ambos pokemon padre y madre al pokemon
		// hijo
		hijo.setVitalidad(Math.max(padre.getVitalidad(), madre.getVitalidad()));
		hijo.setAtaque(Math.max(padre.getAtaque(), madre.getAtaque()));
		hijo.setDefensa(Math.max(padre.getDefensa(), madre.getDefensa()));
		hijo.setAtaqueEspecial(Math.max(padre.getAtaqueEspecial(), madre.getAtaqueEspecial()));
		hijo.setDefensaEspecial(Math.max(padre.getDefensaEspecial(), madre.getDefensaEspecial()));
		hijo.setVelocidad(Math.max(padre.getVelocidad(), madre.getVelocidad()));
		hijo.setEstamina(Math.max(padre.getEstamina(), madre.getEstamina()));

		// Asignar vida (PS y PSMax) correctamente
		hijo.setPsMax(hijo.getVitalidad());
		hijo.setPs(hijo.getPsMax());

		return hijo;
	}

}
