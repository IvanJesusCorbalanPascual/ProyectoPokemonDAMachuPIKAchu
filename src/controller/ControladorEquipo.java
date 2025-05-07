package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControladorEquipo {

	@FXML
	private ImageView btnMoverCaja1;

	@FXML
	private ImageView btnMoverCaja2;

	@FXML
	private ImageView btnMoverCaja3;

	@FXML
	private ImageView btnMoverCaja4;

	@FXML
	private ImageView btnMoverCaja5;

	@FXML
	private ImageView btnMoverCaja6;

	@FXML
	private ImageView btnVerCaja;

	@FXML
	private ImageView btnVolver;

	@FXML
	private ProgressBar enemyHealthBar121;

	@FXML
	private ImageView fondoEquipo;

	@FXML
	private ImageView imgPoke1;

	@FXML
	private ImageView imgPoke2;

	@FXML
	private ImageView imgPoke3;

	@FXML
	private ImageView imgPoke4;

	@FXML
	private ImageView imgPoke5;

	@FXML
	private ImageView imgPoke6;

	@FXML
	private Label nombrePoke1;

	@FXML
	private Label nombrePoke2;

	@FXML
	private Label nombrePoke3;

	@FXML
	private Label nombrePoke4;

	@FXML
	private Label nombrePoke5;

	@FXML
	private ProgressBar vidaPoke1;

	@FXML
	private ProgressBar vidaPoke2;

	@FXML
	private ProgressBar vidaPoke3;

	@FXML
	private ProgressBar vidaPoke4;

	@FXML
	private ProgressBar vidaPoke5;

	@FXML
	private Label vidaPoke6;

	@FXML
	private Label vidaTxtPoke1;

	@FXML
	private Label vidaTxtPoke2;

	@FXML
	private Label vidaTxtPoke3;

	@FXML
	private Label vidaTxtPoke4;

	@FXML
	private Label vidaTxtPoke5;

	@FXML
	private Label vidaTxtPoke6;

	private Stage primaryStage;

	@FXML
	void btnMoverCaja1Click(MouseEvent event) {

	}

	@FXML
	void btnMoverCaja1Enter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	

	}

	@FXML
	void btnMoverCaja1Exit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

	@FXML
	void btnMoverCaja2Click(MouseEvent event) {

	}

	@FXML
	void btnMoverCaja2Enter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	

	}

	@FXML
	void btnMoverCaja2Exit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	

	}

	@FXML
	void btnMoverCaja3Click(MouseEvent event) {

	}

	@FXML
	void btnMoverCaja3Enter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	
	}

	@FXML
	void btnMoverCaja3Exit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

	@FXML
	void btnMoverCaja4Click(MouseEvent event) {

	}

	@FXML
	void btnMoverCaja4Enter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	
	}

	@FXML
	void btnMoverCaja4Exit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

	@FXML
	void btnMoverCaja5Click(MouseEvent event) {

	}

	@FXML
	void btnMoverCaja5Enter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	
	}

	@FXML
	void btnMoverCaja5Exit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

	@FXML
	void btnMoverCaja6Click(MouseEvent event) {

	}

	@FXML
	void btnMoverCaja6Enter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	
	}

	@FXML
	void btnMoverCaja6Exit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

	@FXML
	void btnVerCajaClick(MouseEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/caja.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void btnVerCajaEnter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	
	}

	@FXML
	void btnVerCajaExit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

	@FXML
	void btnVolverClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void btnVolverEnter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.8);	
	}

	@FXML
	void btnVolverExit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);	
	}

}
