package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MenuController {

    @FXML
    private Button btnCaptura;

    @FXML
    private Button btnCentroPokemon;

    @FXML
    private Button btnCombate;

    @FXML
    private Button btnCrianza;

    @FXML
    private Button btnEntrenamiento;

    @FXML
    private Button btnEquipo;

    @FXML
    private Button btnPokedex;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnTienda;

    @FXML
    private ImageView imgFondo;

    @FXML
    private Label lblEntrenador;

    @FXML
    private Label lblPokedolares;

    @FXML
    private ImageView logoPokemon;

    @FXML
    private Text txtEntrenador;

    @FXML
    private Text txtPokedolares;

    @FXML
    void arriba(MouseEvent event) {

    }

    @FXML
    public void salir(ActionEvent event) {
    	//MenuController.show(); (LuisRe lo hizo)
    	// this.stage.close(); (LuisRe lo hizo)
    }

}
