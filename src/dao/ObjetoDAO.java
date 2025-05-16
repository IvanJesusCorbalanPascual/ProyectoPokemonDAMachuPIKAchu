package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Objeto;

public class ObjetoDAO {
	private Connection conexion;

	public ObjetoDAO(Connection conexion) {
		this.conexion = conexion;
	}

	public Connection getConexion() {
		return this.conexion;
	}

	public void añadirObjetoAMochila(int idEntrenador, int idObjeto) {
		try {
			String sql = """
					    INSERT INTO mochila (id_entrenador, id_objeto, cantidad)
					    VALUES (?, ?, 1)
					    ON DUPLICATE KEY UPDATE cantidad = cantidad + 1
					""";

			try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
				stmt.setInt(1, idEntrenador);
				stmt.setInt(2, idObjeto);
				stmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Objeto obtenerObjetoPorId(int id) {
	    String sql = "SELECT nombre, precio FROM Objetos WHERE id_objeto = ?";
	    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            int precio = rs.getInt("precio");
	            return new Objeto(nombre, precio);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
