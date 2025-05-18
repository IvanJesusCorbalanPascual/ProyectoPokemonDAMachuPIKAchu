package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Objeto;

public class ObjetoDAO {
    private Connection conexion;

    public ObjetoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection getConexion() {
        return this.conexion;
    }

    // Añade 1 unidad del objeto a la mochila del Entrenador
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

    // Devuelve un objeto por su ID cargandolo desde la BD, usando el constructor
    public Objeto obtenerObjetoPorId(int idObjeto) {
        String sql = "SELECT nombre, precio FROM objetos WHERE id_objeto = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idObjeto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int precio = rs.getInt("precio");
                return new Objeto(nombre, precio, idObjeto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Carga los objetos que el Entrenador tenga en su mochila
    public List<Objeto> obtenerObjetosPorEntrenador(int idEntrenador) {
        List<Objeto> objetos = new ArrayList<>();

        // Juntando las tablas Objetos y Mochila con INNER JOIN
        String sql = """
            SELECT o.id_objeto, o.nombre, o.precio, m.cantidad
            FROM Objetos o
            JOIN Mochila m ON o.id_objeto = m.id_objeto
            WHERE m.id_entrenador = ?
        """;

        // Prepara y ejectuta la consulta
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_objeto");
                String nombre = rs.getString("nombre");
                int precio = rs.getInt("precio");
                int cantidad = rs.getInt("cantidad");

                Objeto objeto = new Objeto(nombre, precio, id, cantidad);
                objetos.add(objeto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objetos;
    }



}
