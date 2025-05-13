package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Captura;
import model.Entrenador;
import model.Pokemon;
import bd.BDConnection;

public class CapturaController {

    private Pokemon pokemonActual;
    private Entrenador entrenador;

    @FXML private Button btnSalir;
    @FXML private ImageView imgCapturar;
    @FXML private ImageView imgGenerar;
    @FXML private ImageView imgPokemonGenerado;
    @FXML private Label lblResultado;
    @FXML private Label lblPokemonGenerado;
    @FXML private Label numPokeballs;
    @FXML private ImageView pokeball;

    private Stage primaryStage;

    public void init(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
        actualizarContadorPokeballs();
    }

    private void actualizarContadorPokeballs() {
        numPokeballs.setText("Pokéballs: " + entrenador.getPokeballs());
    }

    @FXML
    void capturar(MouseEvent event) {
        if (pokemonActual == null) return;

        if (entrenador.getPokeballs() <= 0) {
            lblResultado.setText("¡No te quedan Pokéballs!");
            return;
        }

        // Gasta una Pokéball
        entrenador.setPokeballs(entrenador.getPokeballs() - 1);
        actualizarContadorPokeballs();

        boolean exito = Captura.intentarCaptura();

        if (exito) {
            lblResultado.setText("¡Has capturado a " + pokemonActual.getNombre() + "!");
            // Aquí puedes añadir el Pokémon al equipo/caja si quieres
            entrenador.añadirPokemon(pokemonActual);
            generar(event);
        } else {
            lblResultado.setText("¡" + pokemonActual.getNombre() + " ha escapado!");
        }

        // Guardar pokéballs actualizadas
        entrenador.guardarPokeballsEnBD(BDConnection.getConnection());
    }

    @FXML
    void generar(MouseEvent event) {
        pokemonActual = Captura.generarPokemonAleatorio();

        if (pokemonActual != null) {
            lblPokemonGenerado.setText(pokemonActual.getNombre());
            try {
                String ruta = getClass()
                    .getResource("/imagenes/Pokemon/Front/" + pokemonActual.getNumPokedex() + ".png")
                    .toExternalForm();
                Image imagen = new Image(ruta);
                imgPokemonGenerado.setImage(imagen);
            } catch (Exception e) {
                System.out.println("Imagen no encontrada para el Pokémon número " + pokemonActual.getNumPokedex());
                e.printStackTrace();
            }

            System.out.println("Generado: " + pokemonActual.getNombre() + " (" + pokemonActual.getNumPokedex() + ")");
        } else {
            lblPokemonGenerado.setText("No se pudo generar");
            imgPokemonGenerado.setImage(null);
            System.out.println("Error: pokemonActual es null");
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

    @FXML
    public void initialize() {
        // Si necesitas lógica adicional al cargar la vista
    }
}
