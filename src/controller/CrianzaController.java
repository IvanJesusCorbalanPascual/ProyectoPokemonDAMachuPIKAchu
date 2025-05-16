package controller;

import java.io.IOException;
import java.sql.Connection;

import bd.BDConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import model.Entrenador;
import model.Pokemon;
import model.Sexo;
import model.Tipo;

public class CrianzaController {

    @FXML private ComboBox<Pokemon> boxMadre;
    @FXML private ComboBox<Pokemon> boxPadre;
    @FXML private Button btnAbrirHuevo;
    @FXML private Button btnCriar;
    @FXML private ImageView btnSalir;
    @FXML private ImageView imgHuevo;
    @FXML private ImageView pkm1;
    @FXML private ImageView pkm2;

    private Entrenador entrenador;
    private Connection conexion;
    private Pokemon huevo;

    // Para inicializar desde otra vista
    public void init(Entrenador entrenador, Connection conexion) {
        this.entrenador = entrenador;
        this.conexion = conexion;

        boxPadre.getItems().addAll(entrenador.getEquipo());
        boxMadre.getItems().addAll(entrenador.getEquipo());

        boxPadre.setOnAction(this::elegirPadre);
        boxMadre.setOnAction(this::elegirMadre);
    }

    @FXML
    void elegirPadre(ActionEvent event) {
        Pokemon padre = boxPadre.getValue();
        mostrarImagen(pkm1, padre);
    }

    @FXML
    void elegirMadre(ActionEvent event) {
        Pokemon madre = boxMadre.getValue();
        mostrarImagen(pkm2, madre);
    }

    private void mostrarImagen(ImageView imageView, Pokemon pokemon) {
        if (pokemon != null) {
            String ruta = "/imagenes/Pokemon/Front/" + pokemon.getNumPokedex() +".png";
            imageView.setImage(new Image(getClass().getResourceAsStream(ruta)));
        }
    }

    @FXML
    void criar(ActionEvent event) {
        Pokemon padre = boxPadre.getValue();
        Pokemon madre = boxMadre.getValue();

        if (padre == null || madre == null) {
            System.out.println("Selecciona dos Pokémon.");
            return;
        }

        if (!sonCompatibles(padre, madre)) {
            System.out.println("Los Pokémon no son compatibles.");
            return;
        }

        if (padre.getFertilidad() == 0 || madre.getFertilidad() == 0) {
            System.out.println("❌ Uno de los Pokémon no tiene más fertilidad.");
            return;
        }

        // Generar el hijo (pero aún no mostrarlo)
        huevo = generarHijo(padre, madre);

        try {
            // Mostrar imagen de huevo
            Image huevoImg = new Image(getClass().getResourceAsStream("/imagenes/otros/huevo.png"));
            imgHuevo.setImage(huevoImg);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen del huevo.");
            e.printStackTrace();
        }

        // Reducir fertilidad correctamente
        padre.reducirFertilidad();
        madre.reducirFertilidad();

        System.out.println("Huevo generado: " + huevo.getNombre());
        System.out.println("Fertilidad restante → Padre: " + padre.getFertilidad() + " | Madre: " + madre.getFertilidad());
    }



    @FXML
    void abrirHuevo(ActionEvent event) {
        if (huevo == null) {
            System.out.println("No hay huevo para abrir.");
            return;
        }

        try {
            // Mostrar imagen del Pokémon hijo en el mismo lugar
            String ruta = "/imagenes/Pokemon/Front/" + huevo.getNumPokedex() + ".png";
            var is = getClass().getResourceAsStream(ruta);
            if (is != null) {
                imgHuevo.setImage(new Image(is));
            } else {
                imgHuevo.setImage(new Image(getClass().getResourceAsStream("")));
            }

            System.out.println("Huevo abierto: " + huevo.getNombre());

            // Aniadiendo el pokemon al equipo
            entrenador.añadirPokemon(huevo);
//            entrenador.guardarEquipoEnBD(BDConnection.getConnection());
            // Tras la crianza los padres y el huevo se resetean para evitar bugs
            boxPadre.setValue(null);
            boxMadre.setValue(null);
            huevo = null;

        } catch (Exception e) {
            System.out.println("No se pudo mostrar el Pokémon.");
            e.printStackTrace();
        }
    }


    @FXML
    void salir(MouseEvent event) {
    	System.out.println("Saliendo del menu...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            MenuController controller = loader.getController();
            controller.setEntrenador(entrenador);
            controller.setPrimaryStage((Stage) ((Node) event.getSource()).getScene().getWindow());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Principal");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // comprobante de sexo
    private boolean sonCompatibles(Pokemon p1, Pokemon p2) {
        if (p1 == null || p2 == null) return false;

        if (p1.getSexo() == null || p2.getSexo() == null) {
            System.out.println("⚠ Uno de los Pokémon no tiene sexo asignado: "
                + p1.getNombre() + " (" + p1.getSexo() + "), "
                + p2.getNombre() + " (" + p2.getSexo() + ")");
            return false;
        }

        boolean compatibles = !p1.getSexo().equals(p2.getSexo()) &&
                              p1.getNombre().equalsIgnoreCase(p2.getNombre());

        if (!compatibles) {
            System.out.println("❌ No son compatibles: "
                + p1.getNombre() + " (" + p1.getSexo() + ") + "
                + p2.getNombre() + " (" + p2.getSexo() + ")");
        }

        return compatibles;
    }



    private Pokemon generarHijo(Pokemon padre, Pokemon madre) {
        // Nombre y tipos heredados del padre
        String nombre = padre.getNombre();
        Tipo tipo1 = padre.getTipo1();
        Tipo tipo2 = padre.getTipo2();

        // Sexo aleatorio
        Sexo sexo = Math.random() < 0.5 ? Sexo.MACHO : Sexo.HEMBRA;

        Pokemon hijo = new Pokemon(nombre, sexo, tipo1, tipo2);

        // Estadísticas promedio
        hijo.setNivel(1);
        hijo.setNumPokedex(padre.getNumPokedex());
        hijo.setVitalidad((padre.getVitalidad() + madre.getVitalidad()) / 2);
        hijo.setAtaque((padre.getAtaque() + madre.getAtaque()) / 2);
        hijo.setDefensa((padre.getDefensa() + madre.getDefensa()) / 2);
        hijo.setAtaqueEspecial((padre.getAtaqueEspecial() + madre.getAtaqueEspecial()) / 2);
        hijo.setDefensaEspecial((padre.getDefensaEspecial() + madre.getDefensaEspecial()) / 2);
        hijo.setVelocidad((padre.getVelocidad() + madre.getVelocidad()) / 2);
        hijo.setEstamina((padre.getEstamina() + madre.getEstamina()) / 2);

        // Recalcular PS y PSMax
        hijo.setPsMax(hijo.getVitalidad() * 10);
        hijo.setPs(hijo.getPsMax());

        return hijo;
    }


}
