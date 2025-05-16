package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.ConexionBD;
import model.Movimiento;

public class MovimientoDAO {

    public static List<Movimiento> obtenerMovimientosPorPokemon(int numPokedex) {
        List<Movimiento> movimientos = new ArrayList<>();

        String query = "SELECT * FROM movimiento WHERE num = ? ORDER BY nivel_aprendizaje ASC LIMIT 4";

        try (Connection conn = ConexionBD.establecerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, numPokedex);
            ResultSet rs = stmt.executeQuery();

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

        return movimientos;
    }
}
