package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Movimiento;

public class MovimientoDAO {
	// Lista de movimientos del pokemon
    public static List<Movimiento> obtenerMovimientosPorPokemon(int numPokedex) {
        List<Movimiento> movimientos = new ArrayList<>();
        
        // Carga de la base de datos el movimiento que tiene el pokemon con el mismo numero de pokedex (num)
        String query = "SELECT * FROM movimiento WHERE num = ? ORDER BY nivel_aprendizaje ASC LIMIT 4";

        try (Connection conn = ConexionBDDAO.establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
        	
        	// Reemplaza el simbolo "?" por la respuesta de la consulta
            stmt.setInt(1, numPokedex);
            ResultSet rs = stmt.executeQuery();

            // Bucle que lee los resultados y asigna el movimiento con sus datos
            while (rs.next()) {
                Movimiento m = new Movimiento(
                    rs.getInt("id_movimiento"),
                    rs.getString("nom_movimiento"),
                    rs.getInt("nivel_aprendizaje"),
                    rs.getInt("pp_max"),
                    rs.getString("tipo"),        // tipoMovimiento: ATAQUE, ESTADO, MEJORA
                    rs.getString("tipo_mov"),    // tipoElemento: FUEGO, AGUA, etc.
                    rs.getInt("potencia"),
                    rs.getString("estado"),
                    rs.getInt("turnos"),
                    rs.getString("mejora"),
                    rs.getInt("num")
                );
                movimientos.add(m);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos del Pokémon con num = " + numPokedex);
            e.printStackTrace();
        }
        
        // Devuelve de 1 a 4 movimientos
        return movimientos;
    }
}
