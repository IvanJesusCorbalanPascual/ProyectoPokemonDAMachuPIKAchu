package model;

import bd.BDConnection;
import java.sql.*;

public class Captura {

	// Genera un pokemon aleatorio a ser capturado
	public static Pokemon generarPokemonAleatorio() {
		// Paso 1: Obtener un número de pokédex aleatorio
		String sql = "SELECT * FROM pokemon ORDER BY RAND() LIMIT 1";

		try (Connection conn = BDConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				int num = rs.getInt("num_pokedex");
				String nombre = rs.getString("nombre");
				String tipo1BD = rs.getString("tipo1");
				String tipo2BD = rs.getString("tipo2");
				String sexoBD = rs.getString("sexo");

				int nivel = rs.getInt("nivel");
				int estamina = rs.getInt("estamina");
				int vitalidad = rs.getInt("vitalidad");
				int ataque = rs.getInt("ataque");
				int defensa = rs.getInt("defensa");
				int ataqueEsp = rs.getInt("ataque_especial");
				int defensaEsp = rs.getInt("defensa_especial");
				int velocidad = rs.getInt("velocidad");
				
				// Asigna el sexo desde la base de datos ignorando si es mayuscula o minuscula
				Sexo sexo = sexoBD.equalsIgnoreCase("H") ? Sexo.MACHO : Sexo.HEMBRA;
 
				// Recibe el tipo de la BD y aplicamos el metodo quitarTildes para evitar errores
				Tipo tipo1 = Tipo.valueOf(quitarTildes(tipo1BD.toUpperCase()));
				Tipo tipo2 = (tipo2BD != null && !tipo2BD.isEmpty()) ? Tipo.valueOf(quitarTildes(tipo2BD.toUpperCase()))
						: Tipo.NORMAL;

				// Creando el con los valores 
				Pokemon p = new Pokemon(nombre, sexo, tipo1, tipo2);
				p.setNumPokedex(num);
				p.setNivel(nivel);
				p.setEstamina(estamina);

				// Asignando las estadisticas al pokemon
				p.setVitalidad(vitalidad);
				p.setAtaque(ataque);
				p.setDefensa(defensa);
				p.setAtaqueEspecial(ataqueEsp);
				p.setDefensaEspecial(defensaEsp);
				p.setVelocidad(velocidad);
				
				return p;
			}
			// Atrapando el posible error de BD
		} catch (SQLException | IllegalArgumentException e) {
			System.err.println("Error al generar Pokémon aleatorio:");
			e.printStackTrace();
		}

		return null;
	}

	public static boolean intentarCaptura() {
		return Math.random() < 0.5; // Metodo con 50% de probabilidad de capturar el Pokemon
	}

	// Metodo quitarTiles para evitar errores
	private static String quitarTildes(String input) {
		return input.replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U")
				.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
				.replace("Ñ", "N").replace("ñ", "n");
	}
}
