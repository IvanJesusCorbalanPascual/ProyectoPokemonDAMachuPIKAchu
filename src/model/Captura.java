	package model;
	
	import bd.BDConnection;
	
	import java.sql.*;
	
	public class Captura {
	
		public static Pokemon generarPokemonAleatorio() {
		    // Paso 1: Obtener un número de pokédex aleatorio
		    String sqlNum = "SELECT num_pokedex FROM Pokedex ORDER BY RAND() LIMIT 1";
	
		    try (Connection conn = BDConnection.getConnection();
		         Statement stmt = conn.createStatement();
		         ResultSet rsNum = stmt.executeQuery(sqlNum)) {
	
		        if (rsNum.next()) {
		            int num = rsNum.getInt("num_pokedex");
	
		            // Paso 2: Obtener el nombre de ese Pokémon con su número
		            String sqlNombre = "SELECT nom_pokemon FROM Pokedex WHERE num_pokedex = " + num;
		            try (ResultSet rsNombre = stmt.executeQuery(sqlNombre)) {
		                if (rsNombre.next()) {
		                    String nombre = rsNombre.getString("nom_pokemon");
		                    
		                    Pokemon p = new Pokemon(num, nombre);
		                    
		                    p.generarEstadisticasIniciales();
		                    return p;
		                }
		            }
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

	            // Paso 2: Obtener el nombre de ese Pokémon con su número
	                    //return new Pokemon(num, nombre);

