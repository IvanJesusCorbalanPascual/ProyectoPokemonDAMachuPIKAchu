package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
