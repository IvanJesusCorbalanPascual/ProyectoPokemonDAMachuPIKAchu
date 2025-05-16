package controller;

import java.io.IOException;
import java.sql.Connection;

import dao.ObjetoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Entrenador;
import model.Objeto;

public class TiendaController {

	private Entrenador entrenador;
	private ObjetoDAO objetoDAO;

	public void init(Entrenador entrenador, Connection conexion) {
	    this.entrenador = entrenador;
	    this.objetoDAO = new ObjetoDAO(conexion);
	    
	    // Mostrar los pokedollars al entrar a la Tienda
	    actualizarPokedollars();
	}


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

	// Metodo comprarObjeto reutilizable para todos los objetos

	private void comprarObjeto(ActionEvent event, int idObjeto) {
		Objeto objeto = objetoDAO.obtenerObjetoPorId(idObjeto);
		if (objeto == null) {
			lblResultado.setText("Error al buscar el objeto.");
			return;
		}

		int precio = objeto.getPrecio();
		if (entrenador.getPokedollars() >= precio) {
			entrenador.restarPokédolares(precio);
			entrenador.guardarPokédolaresEnBD(objetoDAO.getConexion());
			objetoDAO.añadirObjetoAMochila(entrenador.getId(), idObjeto);
			lblResultado.setText("¡Has comprado " + objeto.getNombre() + " por " + precio + "₽!");
		} else {
			lblResultado.setText("No tienes suficiente dinero para comprar " + objeto.getNombre() + ".");
		}

		actualizarPokedollars();
	}

	@FXML
	void comprarAnillo(ActionEvent event) {
		comprarObjeto(event, 7);
	}

	@FXML
	void comprarBaston(ActionEvent event) {
		comprarObjeto(event, 4);
	}

	@FXML
	void comprarChaleco(ActionEvent event) {
		comprarObjeto(event, 3);
	}

	@FXML
	void comprarEter(ActionEvent event) {
		comprarObjeto(event, 6);
	}

	@FXML
	void comprarPesa(ActionEvent event) {
		comprarObjeto(event, 5);
	}

	@FXML
	void comprarPilas(ActionEvent event) {
		comprarObjeto(event, 8);
	}

	@FXML
	void comprarPluma(ActionEvent event) {
		comprarObjeto(event, 1);
	}

	@FXML
	void comprarPokeball(ActionEvent event) {
		int precio = 200;
		if (entrenador.getPokedollars() >= precio) {
			entrenador.restarPokédolares(precio);
			entrenador.añadirPokeballs(5); // +5 Pokeballs
			entrenador.guardarPokeballsEnBD(objetoDAO.getConexion());
			entrenador.guardarPokédolaresEnBD(objetoDAO.getConexion());
			lblResultado.setText("¡Has comprado 5 Pokéballs por " + precio + "₽!");
		} else {
			lblResultado.setText("No tienes suficiente dinero para comprar Pokéballs.");
		}
		actualizarPokedollars();
	}

	private void actualizarPokedollars() {
		lblPokedollars.setText(entrenador.getPokedollars() + "");
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
}
