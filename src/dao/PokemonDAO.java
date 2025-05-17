package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ConexionBD;
import model.Pokemon;

public class PokemonDAO {
	public static void actualizarEstadisticas(Pokemon p) {
	    String sql = "UPDATE pokemon SET nivel = ?, ps = ?, ataque = ?, defensa = ?, velocidad = ? WHERE id_pokemon = ?";

	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = ConexionBD.establecerConexion();
	        stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, p.getNivel());
	        stmt.setInt(2, p.getPs());
	        stmt.setInt(3, p.getAtaque());
	        stmt.setInt(4, p.getDefensa());
	        stmt.setInt(5, p.getVelocidad());
	        stmt.setInt(6, p.getIdPokemon());

	        stmt.executeUpdate();
	        System.out.println("Estadísticas del Pokémon actualizadas correctamente.");

	    } catch (SQLException e) {
	        System.err.println("Error al actualizar estadísticas del Pokémon:");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException ex) {
	            System.err.println("Error al cerrar la conexión:");
	            ex.printStackTrace();
	        }
	    }
	}

}
