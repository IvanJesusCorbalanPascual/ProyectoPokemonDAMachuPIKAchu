package model;

import bd.BDConnection;
import java.sql.*;

public class Captura {

    public static Pokemon generarPokemonAleatorio() {
        String sql = "SELECT num_pokedex, nom_pokemon FROM Pokedex ORDER BY RAND() LIMIT 1";

        try (Connection conn = BDConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int num = rs.getInt("num_pokedex");
                String nombre = rs.getString("nom_pokemon");
                return new Pokemon(num, nombre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean intentarCaptura() {
        return Math.random() < 0.5; // Metodo con 50% de probabilidad de capturar el Pokemon
    }
}
