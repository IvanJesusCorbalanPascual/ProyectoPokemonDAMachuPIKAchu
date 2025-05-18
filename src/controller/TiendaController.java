package controller;

import java.io.IOException;
import java.sql.Connection;

import bd.BDConnection;
import dao.ObjetoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import model.Objeto;

public class TiendaController {
	// Variables
	private Entrenador entrenador;
	private ObjetoDAO objetoDAO;

	// Variables FXML
	@FXML
	private Button btnAnillo;
	@FXML
	private Button btnBaston;
	@FXML
	private Button btnChaleco;
	@FXML
	private Button btnEter;
	@FXML
	private Button btnPesa;
	@FXML
	private Button btnPilas;
	@FXML
	private Button btnPluma;
	@FXML
	private Button btnPokeball;
	@FXML
	private Label lblPokedollars;
	@FXML
	private Label lblResultado;

	// Metodo reutilizable para comprar cada objeto
	private void comprarObjeto(ActionEvent event, int idObjeto) {
		Objeto objeto = objetoDAO.obtenerObjetoPorId(idObjeto);
		if (objeto == null) {
			lblResultado.setText("Error al buscar el objeto.");
			return;
		}

		int precio = objeto.getPrecio();
		if (entrenador.getPokedollars() >= precio) {
			entrenador.restarPokedollars(precio);
			entrenador.guardarPokedollarsEnBD(objetoDAO.getConexion());
			objetoDAO.añadirObjetoAMochila(entrenador.getId(), idObjeto);
			lblResultado.setText("¡Has comprado " + objeto.getNombre() + " por " + precio + "₽!");
		} else {
			lblResultado.setText("No tienes suficiente dinero para comprar " + objeto.getNombre() + ".");
		}

		actualizarPokedollars();
	}

	@FXML
	void comprarPesa(ActionEvent event) {
		comprarObjeto(event, 1);
	}

	@FXML
	void comprarPluma(ActionEvent event) {
		comprarObjeto(event, 2);
	}

	@FXML
	void comprarChaleco(ActionEvent event) {
		comprarObjeto(event, 3);
	}

	@FXML
	void comprarBaston(ActionEvent event) {
		comprarObjeto(event, 4);
	}

	@FXML
	void comprarPilas(ActionEvent event) {
		comprarObjeto(event, 5);
	}

	@FXML
	void comprarEter(ActionEvent event) {
		comprarObjeto(event, 6);
	}

	@FXML
	void comprarAnillo(ActionEvent event) {
		comprarObjeto(event, 7);
	}

	@FXML
	void comprarPokeball(ActionEvent event) {
		comprarObjeto(event, 8);
		entrenador.añadirPokeball(); // Suma una pokeball al inventario de captura
	}


	private void actualizarPokedollars() {
		lblPokedollars.setText(entrenador.getPokedollars() + "");
	}

	@FXML
	void abrirMochila(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mochila.fxml"));
			Parent root = loader.load();

			MochilaController mochilaController = loader.getController();
			mochilaController.init(entrenador, BDConnection.getConnection());

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Mochila");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
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

	public void init(Entrenador entrenador, Connection conexion) {
		this.entrenador = entrenador;
		this.objetoDAO = new ObjetoDAO(conexion);

		// Actualizando y mostrando los pokedollars al entrar a la Tienda
		actualizarPokedollars();
	}
}
