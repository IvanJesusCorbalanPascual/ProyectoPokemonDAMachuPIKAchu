package controller;

import java.io.IOException;
import java.util.List;

import bd.BDConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Entrenador;
import main.Pokemon;

public class ControladorCaja {

	private Stage primaryStage;

	public void setPrimaryStage(Stage stage) {
	    this.primaryStage = stage;
	}
	
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

	private Entrenador entrenador;

	public void setEntrenador(Entrenador entrenador) {
	    this.entrenador = entrenador;
	    actualizarVista();
	}

	public void actualizarVista() {
	    ImageView[] imageViews = {
	        imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6,
	        imgPoke7, imgPoke8, imgPoke9, imgPoke10, imgPoke11, imgPoke12,
	        imgPoke13, imgPoke14, imgPoke15, imgPoke16, imgPoke17, imgPoke18,
	        imgPoke19, imgPoke20, imgPoke21, imgPoke22, imgPoke23, imgPoke24
	    };

	    for (int i = 0; i < imageViews.length; i++) {
	        if (entrenador.getCaja().size() > i) {
	            int num = entrenador.getCaja().get(i).getNumPokedex();
	            imageViews[i].setImage(new Image(getClass().getResourceAsStream("/Imagenes/Pokemon/Front/" + num + ".png")));
	        } else {
	            imageViews[i].setImage(null); // Si no hay Pokémon, limpiamos
	        }
	    }
	}
	
	private void moverAPequipo(int index) {
	    List<Pokemon> caja = entrenador.getCaja();
	    List<Pokemon> equipo = entrenador.getEquipo();

	    if (index < caja.size() && equipo.size() < 6) {
	        Pokemon p = caja.remove(index);
	        equipo.add(p);
	        actualizarVista();

	        // Guardar el cambio en la BD
	        entrenador.actualizarEquipoEnBD(BDConnection.getConnection());
	    }
	}



	@FXML
	void btnEquipoClick(MouseEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/equipo.fxml"));
	        Parent root = loader.load();

	        // Pasar entrenador al controlador de equipo
	        ControladorEquipo controladorEquipo = loader.getController();
	        controladorEquipo.setEntrenador(this.entrenador);
	        controladorEquipo.setPrimaryStage(this.primaryStage);
	        controladorEquipo.actualizarVista();

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Equipo Pokémon");
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
			
		     MenuController menuController = loader.getController();
		     menuController.setEntrenador(this.entrenador); 

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Menú Principal");
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
	
	@FXML void imgPoke1Click(MouseEvent e) { moverAPequipo(0); }
	@FXML void imgPoke2Click(MouseEvent e) { moverAPequipo(1); }
	@FXML void imgPoke3Click(MouseEvent e) { moverAPequipo(2); }
	@FXML void imgPoke4Click(MouseEvent e) { moverAPequipo(3); }
	@FXML void imgPoke5Click(MouseEvent e) { moverAPequipo(4); }
	@FXML void imgPoke6Click(MouseEvent e) { moverAPequipo(5); }
	@FXML void imgPoke7Click(MouseEvent e) { moverAPequipo(6); }
	@FXML void imgPoke8Click(MouseEvent e) { moverAPequipo(7); }
	@FXML void imgPoke9Click(MouseEvent e) { moverAPequipo(8); }
	@FXML void imgPoke10Click(MouseEvent e) { moverAPequipo(9); }
	@FXML void imgPoke11Click(MouseEvent e) { moverAPequipo(10); }
	@FXML void imgPoke12Click(MouseEvent e) { moverAPequipo(11); }
	@FXML void imgPoke13Click(MouseEvent e) { moverAPequipo(12); }
	@FXML void imgPoke14Click(MouseEvent e) { moverAPequipo(13); }
	@FXML void imgPoke15Click(MouseEvent e) { moverAPequipo(14); }
	@FXML void imgPoke16Click(MouseEvent e) { moverAPequipo(15); }
	@FXML void imgPoke17Click(MouseEvent e) { moverAPequipo(16); }
	@FXML void imgPoke18Click(MouseEvent e) { moverAPequipo(17); }
	@FXML void imgPoke19Click(MouseEvent e) { moverAPequipo(18); }
	@FXML void imgPoke20Click(MouseEvent e) { moverAPequipo(19); }
	@FXML void imgPoke21Click(MouseEvent e) { moverAPequipo(20); }
	@FXML void imgPoke22Click(MouseEvent e) { moverAPequipo(21); }
	@FXML void imgPoke23Click(MouseEvent e) { moverAPequipo(22); }
	@FXML void imgPoke24Click(MouseEvent e) { moverAPequipo(23); }


}
