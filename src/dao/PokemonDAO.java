package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ConexionBD;
import model.Pokemon;
import model.Sexo;

public class PokemonDAO {
	private Connection conexion;
	
	public PokemonDAO(Connection conexion) {
		this.conexion = conexion;
	}
	
	public void insertarPokemon(Pokemon pokemon, int idEntrenador) {
		try {
			String sql = """
				INSERT INTO pokemon (id_entrenador, num_pokedex, nombre, nivel, sexo, tipo1, tipo2,
									 vitalidad, estamina, ataque, defensa, ataque_especial, defensa_especial,
									 velocidad, equipo)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";

			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, idEntrenador);
			stmt.setInt(2, pokemon.getNumPokedex());
			stmt.setString(3, pokemon.getNombre());
			stmt.setInt(4, pokemon.getNivel());
			stmt.setString(5, pokemon.getSexo() == Sexo.MACHO ? "H" : "M");
			stmt.setString(6, pokemon.getTipo1().name());
			stmt.setString(7, pokemon.getTipo2().name());
			stmt.setInt(8, pokemon.getVitalidad());
			stmt.setInt(9, pokemon.getEstamina());
			stmt.setInt(10, pokemon.getAtaque());
			stmt.setInt(11, pokemon.getDefensa());
			stmt.setInt(12, pokemon.getAtaqueEspecial());
			stmt.setInt(13, pokemon.getDefensaEspecial());
			stmt.setInt(14, pokemon.getVelocidad());
			stmt.setInt(15, 1); // Lo metemos al equipo principal por defecto

			stmt.executeUpdate();
			stmt.close();

			System.out.println("Pokémon insertado correctamente en la base de datos.");

		} catch (Exception e) {
			System.err.println("Error al insertar el Pokémon:");
			e.printStackTrace();
		}
	}
	
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
