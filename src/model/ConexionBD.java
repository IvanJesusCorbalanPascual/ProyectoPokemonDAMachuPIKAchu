package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	public static Connection establecerConexion() {
        try {
            // Asegúrate de que estas rutas y credenciales coincidan con tu configuración XAMPP
            String url = "jdbc:mysql://localhost:3306/proyectopokemon";
            String usuario = "root";
            String contrasena = "";

            Connection conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión establecida correctamente.");
            return conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
            return null;
        }
    }
}
