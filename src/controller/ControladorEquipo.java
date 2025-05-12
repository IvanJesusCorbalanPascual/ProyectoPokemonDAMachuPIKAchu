package controller;

import java.io.IOException;
import java.util.List;

import bd.BDConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Entrenador;
import main.Pokemon;

public class ControladorEquipo {

	private Stage primaryStage;
    private Entrenador entrenador;

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }
    
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void actualizarVista() {
        List<Pokemon> equipo = entrenador.getEquipo();
        System.out.println("Pokémon en equipo: " + equipo.size());
        actualizarPokemon(0, equipo);
        actualizarPokemon(1, equipo);
        actualizarPokemon(2, equipo);
        actualizarPokemon(3, equipo);
        actualizarPokemon(4, equipo);
        actualizarPokemon(5, equipo);
    }
    
    private void actualizarPokemon(int index, List<Pokemon> equipo) {
        ImageView[] imgs = {imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6};
        Label[] nombres = {nombrePoke1, nombrePoke2, nombrePoke3, nombrePoke4, nombrePoke5, nombrePoke6};
        ProgressBar[] barras = {vidaPoke1, vidaPoke2, vidaPoke3, vidaPoke4, vidaPoke5, vidaPoke6};
        Label[] vidas = {vidaTxtPoke1, vidaTxtPoke2, vidaTxtPoke3, vidaTxtPoke4, vidaTxtPoke5, vidaTxtPoke6};

        if (equipo.size() > index) {
            Pokemon p = equipo.get(index);
            String ruta = "/Imagenes/Pokemon/Front/" + p.getNumPokedex() + ".png";

            try {
                var stream = getClass().getResourceAsStream(ruta);
                if (stream != null) {
                    imgs[index].setImage(new Image(stream));
                } else {
                    System.err.println("No se encontró la imagen: " + ruta);
                    imgs[index].setImage(null);
                }
            } catch (Exception e) {
                System.err.println("Error cargando imagen del Pokémon: " + e.getMessage());
                imgs[index].setImage(null);
            }

            nombres[index].setText(p.getNombre());
            vidas[index].setText(p.getPs() + " / " + p.getPsMax() + " HP");
            barras[index].setProgress((double) p.getPs() / p.getPsMax());
        } else {
            imgs[index].setImage(null);
            nombres[index].setText("");
            vidas[index].setText("");
            barras[index].setProgress(0);
        }
    }


    private void moverACaja(int index) {
        if (entrenador.getEquipo().size() > index) {
            Pokemon p = entrenador.getEquipo().remove(index);
            entrenador.getCaja().add(p);
            actualizarVista();

            // Guardar el cambio en la BD
            entrenador.actualizarEquipoEnBD(BDConnection.getConnection());
        }
    }


    
    @FXML private ImageView btnMoverCaja1, btnMoverCaja2, btnMoverCaja3, btnMoverCaja4, btnMoverCaja5, btnMoverCaja6;
    @FXML private ImageView btnVerCaja, btnVolver;
    @FXML private ImageView fondoEquipo;
    @FXML private ImageView imgPoke1, imgPoke2, imgPoke3, imgPoke4, imgPoke5, imgPoke6;
    @FXML private Label nombrePoke1, nombrePoke2, nombrePoke3, nombrePoke4, nombrePoke5, nombrePoke6;
    @FXML private ProgressBar vidaPoke1, vidaPoke2, vidaPoke3, vidaPoke4, vidaPoke5, vidaPoke6;
    @FXML private Label vidaTxtPoke1, vidaTxtPoke2, vidaTxtPoke3, vidaTxtPoke4, vidaTxtPoke5, vidaTxtPoke6;
    
    @FXML
    void btnVerCajaClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/caja.fxml"));
            Parent root = loader.load();

            ControladorCaja controladorCaja = loader.getController();
            controladorCaja.setEntrenador(this.entrenador);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controladorCaja.setPrimaryStage(stage); // ← IMPORTANTE

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Caja Pokémon");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Botones de mover
    @FXML void btnMoverCaja1Click(MouseEvent e) { moverACaja(0); }
    @FXML void btnMoverCaja2Click(MouseEvent e) { moverACaja(1); }
    @FXML void btnMoverCaja3Click(MouseEvent e) { moverACaja(2); }
    @FXML void btnMoverCaja4Click(MouseEvent e) { moverACaja(3); }
    @FXML void btnMoverCaja5Click(MouseEvent e) { moverACaja(4); }
    @FXML void btnMoverCaja6Click(MouseEvent e) { moverACaja(5); }
    
    // Efecto hover
    @FXML void btnMoverCaja1Enter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnMoverCaja2Enter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnMoverCaja3Enter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnMoverCaja4Enter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnMoverCaja5Enter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnMoverCaja6Enter(MouseEvent e) { efectoOpacidad(e, 0.8); }

    @FXML void btnMoverCaja1Exit(MouseEvent e) { efectoOpacidad(e, 1.0); }
    @FXML void btnMoverCaja2Exit(MouseEvent e) { efectoOpacidad(e, 1.0); }
    @FXML void btnMoverCaja3Exit(MouseEvent e) { efectoOpacidad(e, 1.0); }
    @FXML void btnMoverCaja4Exit(MouseEvent e) { efectoOpacidad(e, 1.0); }
    @FXML void btnMoverCaja5Exit(MouseEvent e) { efectoOpacidad(e, 1.0); }
    @FXML void btnMoverCaja6Exit(MouseEvent e) { efectoOpacidad(e, 1.0); }

    @FXML void btnVerCajaEnter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnVerCajaExit(MouseEvent e) { efectoOpacidad(e, 1.0); }

    @FXML void btnVolverEnter(MouseEvent e) { efectoOpacidad(e, 0.8); }
    @FXML void btnVolverExit(MouseEvent e) { efectoOpacidad(e, 1.0); }

    private void efectoOpacidad(MouseEvent e, double opacidad) {
        ((Node) e.getSource()).setOpacity(opacidad);
    }
    
    @FXML
    void btnVolverClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            // Pasar el entrenador al menu controller, muy importante
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




    // Métodos de eventos de botones (mismo código que ya tienes, no se repite aquí por brevedad)
    // ...
}
