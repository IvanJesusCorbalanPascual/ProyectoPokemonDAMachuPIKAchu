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
import main.Entrenador;

public class TiendaController {

	private Entrenador entrenador;
	private ObjetoDAO objetoDAO;

	public void init(Entrenador entrenador, Connection conexion) {
	    this.entrenador = entrenador;
	    this.objetoDAO = new ObjetoDAO(conexion);
	}
	
    @FXML private Button btnAnillo;
    @FXML private Button btnBaston;
    @FXML private Button btnChaleco;
    @FXML private Button btnEter;
    @FXML private Button btnPesa;
    @FXML private Button btnPilas;
    @FXML private Button btnPluma;
    @FXML private Button btnPokeball;
    @FXML private Label lblResultado;

    @FXML
    void comprarAnillo(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 7);
        lblResultado.setText("¡Has comprado el anillo único!");
    }

    @FXML
    void comprarBaston(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 4);
        lblResultado.setText("¡Has comprado un bastón!");
    }

    @FXML
    void comprarChaleco(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 3);
        lblResultado.setText("¡Has comprado un chaleco!");
    }

    @FXML
    void comprarEter(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 6);
        lblResultado.setText("¡Has comprado un éter!");
    }

    @FXML
    void comprarPesa(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 5);
        lblResultado.setText("¡Has comprado una pesa!");
    }

    @FXML
    void comprarPilas(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 8);
        lblResultado.setText("¡Has comprado pilas!");
    }

    @FXML
    void comprarPluma(ActionEvent event) {
        objetoDAO.añadirObjetoAMochila(entrenador.getId(), 1);
        lblResultado.setText("¡Has comprado una pluma!");
    }
    
    @FXML
    void comprarPokeball(ActionEvent event) {
        entrenador.añadirPokeballs(5); // Se añaden 5 pokeballs
        entrenador.guardarPokeballsEnBD(objetoDAO.getConexion());
        lblResultado.setText("¡Has comprado 5 Pokéballs!");
        
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
