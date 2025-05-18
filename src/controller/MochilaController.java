package controller;

import java.io.IOException;
import java.sql.Connection;

import bd.BDConnection;
import dao.ObjetoDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import model.Objeto;

public class MochilaController {
	// Variables
	private Entrenador entrenador;
	private ObjetoDAO objetoDAO;

	// Variables FXML
    @FXML
    private ImageView btnSalir;

    @FXML
    private Label lblAnillo;

    @FXML
    private Label lblBaston;

    @FXML
    private Label lblChaleco;

    @FXML
    private Label lblEter;

    @FXML
    private Label lblPesa;

    @FXML
    private Label lblPilas;

    @FXML
    private Label lblPluma;
    
    // Carga los objetos que posee el Entrenador desde la base de datos
    private void cargarObjetos() {
        entrenador.setObjetos(objetoDAO.obtenerObjetosPorEntrenador(entrenador.getId()));

        System.out.println("Objetos cargados en el entrenador:");
        for (Objeto o : entrenador.getObjetos()) {
            System.out.println("- " + o.getNombre() + " x" + o.getCantidad());
        }

        lblEter.setText("" + entrenador.getCantidadObjeto("eter"));
        lblPluma.setText("" + entrenador.getCantidadObjeto("pluma"));
        lblPesa.setText("" + entrenador.getCantidadObjeto("pesa"));
        lblChaleco.setText("" + entrenador.getCantidadObjeto("chaleco"));
        lblBaston.setText("" + entrenador.getCantidadObjeto("baston"));
        lblPilas.setText("" + entrenador.getCantidadObjeto("pilas"));
        lblAnillo.setText("" + entrenador.getCantidadObjeto("anillo único"));
    }


    @FXML
    void salir(MouseEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tienda.fxml"));
			Parent root = loader.load();

			TiendaController tiendaController = loader.getController();
			tiendaController.init(entrenador, BDConnection.getConnection());

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Tienda Pokémon");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void init(Entrenador entrenador, Connection conexion) {
	    this.entrenador = entrenador;
	    this.objetoDAO = new ObjetoDAO(conexion);
	    entrenador.setObjetos(objetoDAO.obtenerObjetosPorEntrenador(entrenador.getId()));
	    cargarObjetos(); // Actualiza los labels con el numero de objetos que tiene el Entrenador
	}

}
