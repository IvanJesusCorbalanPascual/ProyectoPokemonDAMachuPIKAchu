package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControladorCaja {

	@FXML
	private ImageView btnEquipo;

	@FXML
	private ImageView btnMover;

	@FXML
	private ImageView btnVolver;

	@FXML
	private ImageView fondoCaja;

	@FXML
	private ImageView imgPoke1;

	@FXML
	private ImageView imgPoke10;

	@FXML
	private ImageView imgPoke11;

	@FXML
	private ImageView imgPoke12;

	@FXML
	private ImageView imgPoke13;

	@FXML
	private ImageView imgPoke14;

	@FXML
	private ImageView imgPoke15;

	@FXML
	private ImageView imgPoke16;

	@FXML
	private ImageView imgPoke17;

	@FXML
	private ImageView imgPoke18;

	@FXML
	private ImageView imgPoke19;

	@FXML
	private ImageView imgPoke2;

	@FXML
	private ImageView imgPoke20;

	@FXML
	private ImageView imgPoke21;

	@FXML
	private ImageView imgPoke22;

	@FXML
	private ImageView imgPoke23;

	@FXML
	private ImageView imgPoke24;

	@FXML
	private ImageView imgPoke3;

	@FXML
	private ImageView imgPoke4;

	@FXML
	private ImageView imgPoke5;

	@FXML
	private ImageView imgPoke6;

	@FXML
	private ImageView imgPoke7;

	@FXML
	private ImageView imgPoke8;

	@FXML
	private ImageView imgPoke9;

	@FXML
	void btnEquipoClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/equipo.fxml"));
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
	void btnEquipoEnter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.9);
	}

	@FXML
	void btnEquipoExit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);
	}

	@FXML
	void btnMoverClick(MouseEvent event) {

	}

	@FXML
	void btnMoverEnter(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(0.9);
	}

	@FXML
	void btnMoverExit(MouseEvent event) {
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
		source.setOpacity(0.9);
	}

	@FXML
	void btnVolverExit(MouseEvent event) {
		Node source = (Node) event.getSource();
		source.setOpacity(1.0);
	}

}
