package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class circuloController {

    @FXML
    private Circle Circulo;

    @FXML
    private Button btnAbajo;

    @FXML
    private Button btnArriba;

    @FXML
    private Button btnDerecha;

    @FXML
    private Button btnIzquierda;

    // Movimiento hacia ABAJO (eje Y aumenta)
    @FXML
    void abajo(ActionEvent event) {
        Circulo.setCenterY(Circulo.getCenterY() + 10);
    }

    // Movimiento hacia ARRIBA (eje Y disminuye)
    @FXML
    void arriba(ActionEvent event) {
        Circulo.setCenterY(Circulo.getCenterY() - 10);
    }

    // Movimiento hacia la DERECHA (eje X aumenta)
    @FXML
    void derecha(ActionEvent event) {
        Circulo.setCenterX(Circulo.getCenterX() + 10);
    }

    // Movimiento hacia la IZQUIERDA (eje X disminuye)
    @FXML
    void izquierda(ActionEvent event) {
        Circulo.setCenterX(Circulo.getCenterX() - 10);
    }
}
