package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Entrenador;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.*;

import dao.ConexionBDDAO;
import dao.ObjetoDAO;

public class ControladorLogin {
	// Variables y constantes
	public static MediaPlayer mediaPlayer;
	private Stage primaryStage;

	// Variables FXML
	@FXML
	private Button btnIniciarSesion;
	@FXML
	private Button btnRegistrar;
	@FXML
	private ImageView imgSalir;
	@FXML
	private Label lblResultado;
	@FXML
	private PasswordField txtContraseña;
	@FXML
	private TextField txtUsuario;

	// Metodos
	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
		reproducirMusica();
	}

	// Reproduce la intro de pokemon mientras el usuario esta en el login
	private void reproducirMusica() {
		try {
			String path = "src/imagenes/otros/intro.mp3";
			Media media = new Media(new File(path).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Se repite en bucle
			ControladorLogin.mediaPlayer.setVolume(0.02); // Baja el volumen de la musica
			mediaPlayer.play();
		} catch (Exception e) {
			System.out.println("Error al reproducir música: " + e.getMessage());
		}
	}

	private Connection conectar() throws SQLException {
		return ConexionBDDAO.establecerConexion();
	}

	public void init(Entrenador entrenador, Connection conexion) {
		new ObjetoDAO(conexion);
	}

	// Comprueba tanto si hay un usuario creado como si los campos estan en blanco,
	// si todo esta correcto carga la partida del Entrenador
	@FXML
	void iniciarSesion(ActionEvent event) {
		String usuario = txtUsuario.getText();
		String pass = txtContraseña.getText();

		if (usuario.isBlank() || pass.isBlank()) {
			lblResultado.setText("Por favor, completa todos los campos.");
			return;
		}

		// Conexion con la base de datos para sacar el nombre y la contraseña
		try (Connection con = conectar()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM Entrenadores WHERE usuario = ? AND contraseña = ?");
			ps.setString(1, usuario);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id_entrenador");
				String nombre = rs.getString("nombre");
				int pokedollars = rs.getInt("pokedollars");

				Entrenador entrenador = new Entrenador(id, nombre);
				entrenador.setPokedollars(pokedollars);
				entrenador.cargarPokemonsDesdeBD(con);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
				Parent root = loader.load();

				MenuController controller = loader.getController();
				controller.setEntrenador(entrenador);
				controller.setPrimaryStage(primaryStage);

				primaryStage.setScene(new Scene(root));
				primaryStage.setTitle("Menú Principal");
				primaryStage.show();

			} else {
				lblResultado.setText("Usuario o contraseña incorrectos.");
			}

		} catch (Exception e) {
			lblResultado.setText("Error al conectar.");
			e.printStackTrace();
		}
	}

	// Crea el usuario y lo guarda en la base de datos, a su vez lleva al jugador
	// directamente al menu tras registrarse
	@FXML
	void registrar(ActionEvent event) {
		String usuario = txtUsuario.getText();
		String pass = txtContraseña.getText();

		// Por si algun campo esta en blanco
		if (usuario.isBlank() || pass.isBlank()) {
			lblResultado.setText("Por favor, completa todos los campos.");
			return;
		}
		// Comprueba que no exista un usuario con los mismos datos
		try (Connection con = conectar()) {
			PreparedStatement check = con.prepareStatement("SELECT * FROM Entrenadores WHERE usuario = ?");
			check.setString(1, usuario);
			ResultSet rs = check.executeQuery();

			if (rs.next()) {
				lblResultado.setText("Ese usuario ya existe.");
				return;
			}
			// Carga el nuevo usuario a la base de datos y le asigna pokedollares y pokeballs por defecto
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Entrenadores (usuario, contraseña, nombre, pokedollars, pokeballs) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, usuario);
			ps.setString(2, pass);
			ps.setString(3, usuario);
			ps.setInt(4, 3000);
			ps.setInt(5, 10);

			ps.executeUpdate();

			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);

				Entrenador entrenador = new Entrenador(id, usuario);
				entrenador.setPokedollars(3000);
				entrenador.setPokeballs(10);

				lblResultado.setText("¡Usuario registrado con éxito!");

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
					Parent root = loader.load();

					MenuController controller = loader.getController();
					controller.setEntrenador(entrenador);
					controller.setPrimaryStage(primaryStage);

					// La musica se detiene al entrar al menu al no haber ningun objeto
					// "mediaPlayer"
					if (mediaPlayer != null) {
						mediaPlayer.stop();
					}

					primaryStage.setScene(new Scene(root));
					primaryStage.setTitle("Menú Principal");
					primaryStage.show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			lblResultado.setText("Error al registrar.");
			e.printStackTrace();
		}
	}

	@FXML
	void salir(MouseEvent event) {
		System.exit(0);
	}
}
