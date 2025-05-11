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
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Captura;
import model.Entrenador;
import model.Pokemon;

public class CapturaController {
	
	private Pokemon pokemonActual;
	private Entrenador entrenador;

    @FXML
    private Button btnSalir;

    @FXML
    private ImageView imgCapturar;

    @FXML
    private ImageView imgGenerar;

    @FXML
    private ImageView imgPokemonGenerado;

    @FXML
    private Label lblResultado;
    
    @FXML
    private Label lblPokemonGenerado;

    @FXML
    private Label numPokeballs;

    @FXML
    private ImageView pokeball;

    @FXML
    void btnSalirEntered(MouseDragEvent event) {

    }

    @FXML
    void btnSalirSalir(MouseDragEvent event) {

    }
    int pokeballs = 100;
    @FXML
    void capturar(MouseEvent event) {

        if (pokemonActual == null) return;

        /*if (entrenador.getPokeballs() <= 0) {
            lblResultado.setText("¡No te quedan Pokéballs!");
            return;
        }*/

        // Gasta una Pokéball
        numPokeballs.setText(""+pokeballs);
        pokeballs--;

        boolean exito = Captura.intentarCaptura();

        if (exito) {
            lblResultado.setText("¡Has capturado a " + pokemonActual.getNombre() + "!");
            generar(event);
            // Aquí puedes guardar el Pokémon capturado
        } else {
            lblResultado.setText("¡" + pokemonActual.getNombre() + " ha escapado!");
        }
    }


    @FXML
    void generar(MouseEvent event) {
        pokemonActual = Captura.generarPokemonAleatorio();

        if (pokemonActual != null) {
            lblPokemonGenerado.setText(pokemonActual.getNombre());
            try {
                String ruta = getClass().getResource("/imagenes/Pokemon/Front/" + pokemonActual.getNumPokedex() + ".png").toExternalForm();
                Image imagen = new Image(ruta);
                imgPokemonGenerado.setImage(imagen);
            } catch (Exception e) {
                System.out.println("Imagen no encontrada para el Pokémon número " + pokemonActual.getNumPokedex());
                e.printStackTrace();
            }
        }

        System.out.println("Generado: " + pokemonActual.getNombre() + " (" + pokemonActual.getNumPokedex() + ")");
    }



    @FXML
    void imgCapturarEntered(MouseEvent event) {

    }

    @FXML
    void imgCapturarExit(MouseEvent event) {

    }

    @FXML
    void imgGenerarEntered(MouseEvent event) {

    }

    @FXML
    void imgGenerarExit(MouseEvent event) {

    }

    @FXML
    void salir(ActionEvent event) {
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

    @FXML
    public void initialize() {
    	
    }
    
    // Metodo para definir las pokeballs que tiene el entrenador
    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
        numPokeballs.setText("Pokéballs: " + entrenador.getPokeballs());
    }


}