package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Entrenador {

	private String nombre;
	private List<Pokemon> equipoPrincipal;
	private List<Pokemon> equipoCaja;
	private int pokedollars;
	private int id;
	private List<Objeto> objetos;
	public int pokeballs = 100;

	public Entrenador(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.equipoPrincipal = new ArrayList<>();
		this.equipoCaja = new ArrayList<>();
		this.pokedollars = 0;
		this.objetos = new ArrayList<>();
		this.pokeballs = 100;
	}

	public void añadirPokemon(Pokemon p) {
		if (equipoPrincipal.size() < 6) {
			equipoPrincipal.add(p);
		} else {
			equipoCaja.add(p);
		}
	}

	public void usarObjeto(Pokemon p, Objeto o) {
		if (objetos.contains(o)) {
			o.aplicar(p);
			objetos.remove(o);
		}
	}

	public void añadirObjeto(Objeto o) {
		objetos.add(o);
	}

	public void ganarDinero(int cantidad) {
		pokedollars += cantidad;
	}

	public void gastarDinero(int cantidad) {
		if (pokedollars >= cantidad) {
			pokedollars -= cantidad;
		} else {
			System.out.println("No tienes suficiente dinero.");
		}
	}

	public void mostrarEquipo() {
		System.out.println("Equipo Principal:");
		for (Pokemon p : equipoPrincipal) {
			System.out.println("- " + p.getNombre());
		}

		System.out.println("Caja:");
		for (Pokemon p : equipoCaja) {
			System.out.println("- " + p.getNombre());
		}
	}

	public void cargarPokemonsDesdeBD(Connection conexion) {
		equipoPrincipal.clear();
		equipoCaja.clear();

		try {
			String sql = "SELECT * FROM pokemon WHERE id_entrenador = ?";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, this.id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString("nombre");
				int nivel = rs.getInt("nivel");
				int numPokedex = rs.getInt("num_pokedex");
				int equipo = rs.getInt("equipo");
				int idPokemon = rs.getInt("id_pokemon");

				String sexoBD = rs.getString("sexo");
				Sexo sexo = sexoBD.equals("H") ? Sexo.MACHO : Sexo.HEMBRA;

				String tipo1BD = rs.getString("tipo1");
				String tipo2BD = rs.getString("tipo2");

				Tipo tipo1 = Tipo.valueOf(quitarTildes(tipo1BD.toUpperCase()));
				Tipo tipo2 = (tipo2BD != null && !tipo2BD.isEmpty()) ? Tipo.valueOf(quitarTildes(tipo2BD.toUpperCase()))
						: Tipo.NORMAL;

				// Crear instancia
				Pokemon p = new Pokemon(nombre, sexo, tipo1, tipo2);
				p.setNivel(nivel);
				p.setNumPokedex(numPokedex);
				p.setIdPokemon(idPokemon);

				// Cargar estadísticas
				p.setVitalidad(rs.getInt("vitalidad"));
				p.setEstamina(rs.getInt("estamina"));
				p.setAtaque(rs.getInt("ataque"));
				p.setDefensa(rs.getInt("defensa"));
				p.setAtaqueEspecial(rs.getInt("ataque_especial"));
				p.setDefensaEspecial(rs.getInt("defensa_especial"));
				p.setVelocidad(rs.getInt("velocidad"));
				p.recalcularPS(); // ✅ Calcula ps y psMax usando vitalidad

				if (equipo == 1) {
					equipoPrincipal.add(p);
				} else if (equipo == 2) {
					equipoCaja.add(p);
				}
			}

			while (equipoPrincipal.size() < 6 && !equipoCaja.isEmpty()) {
				Pokemon p = equipoCaja.remove(0);
				equipoPrincipal.add(p);
			}

			System.out.println("Equipo: " + equipoPrincipal.size() + " | Caja: " + equipoCaja.size());

		} catch (SQLException | IllegalArgumentException e) {
			System.err.println("Error al cargar Pokémon desde la BD:");
			e.printStackTrace();
		}
	}

	public void actualizarEquipoEnBD(Connection conexion) {
		try {
			// Primero, marcar todos los Pokémon del entrenador como sin equipo (0 opcional,
			// lo eliminamos)
			// Luego actualizar uno a uno lo correcto
			// Marcar como equipo = 1 (equipo principal)
			String updateEquipo = "UPDATE pokemon SET equipo = 1 WHERE id_pokemon = ?";
			PreparedStatement stmtEquipo = conexion.prepareStatement(updateEquipo);
			for (Pokemon p : equipoPrincipal) {
				stmtEquipo.setInt(1, p.getIdPokemon());
				stmtEquipo.executeUpdate();
			}

			String updateCaja = "UPDATE pokemon SET equipo = 2 WHERE id_pokemon = ?";
			PreparedStatement stmtCaja = conexion.prepareStatement(updateCaja);
			for (Pokemon p : equipoCaja) {
				stmtCaja.setInt(1, p.getIdPokemon());
				stmtCaja.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void añadirPokeballs(int cantidad) {
		this.pokeballs += cantidad;
	}

	/*
	 * Metodos guardar...EnBD: Estos metodso guardan todos los datos del Entrenador
	 * en la base de datos cuando este cierra la aplicacion. Al iniciar sesion se
	 * cargan desde la base de datos para recuperar la partida
	 */
	public void guardarPokeballsEnBD(Connection conexion) {
		try {
			String sql = "UPDATE Entrenadores SET pokeballs = ? WHERE id_entrenador = ?";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, this.pokeballs);
			stmt.setInt(2, this.id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarPokedollarsEnBD(Connection conexion) {
		try {
			String sql = "UPDATE entrenadores SET pokedolares = ? WHERE id_entrenador = ?";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, this.pokedollars);
			stmt.setInt(2, this.id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void guardarObjetosEnBD(Connection conexion) {
		try {
			// Borrar todos los objetos actuales del entrenador
			String deleteSQL = "DELETE FROM entrenador_objeto WHERE id_entrenador = ?";
			PreparedStatement deleteStmt = conexion.prepareStatement(deleteSQL);
			deleteStmt.setInt(1, this.id);
			deleteStmt.executeUpdate();

			// Insertar los objetos actuales
			String insertSQL = "INSERT INTO entrenador_objeto (id_entrenador, id_objeto, cantidad) VALUES (?, ?, ?)";
			PreparedStatement insertStmt = conexion.prepareStatement(insertSQL);

			Map<Integer, Integer> conteo = new HashMap<>();
			for (Objeto obj : objetos) {
				conteo.put(obj.getIdObjeto(), conteo.getOrDefault(obj.getIdObjeto(), 0) + 1);
			}

			for (Map.Entry<Integer, Integer> entry : conteo.entrySet()) {
				insertStmt.setInt(1, this.id);
				insertStmt.setInt(2, entry.getKey());
				insertStmt.setInt(3, entry.getValue());
				insertStmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cargarObjetosDesdeBD(Connection conexion) {
		objetos.clear();
		try {
			String sql = """
					    SELECT o.id_objeto, o.nombre, o.tipo, o.valor, eo.cantidad
					    FROM objeto o
					    JOIN entrenador_objeto eo ON o.id_objeto = eo.id_objeto
					    WHERE eo.id_entrenador = ?
					""";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, this.id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString("nombre");
				int precio = rs.getInt("precio");
				int idObjeto = rs.getInt("id_objeto");
				int cantidad = rs.getInt("cantidad");

				Objeto obj = new Objeto(nombre, precio, idObjeto);
				for (int i = 0; i < cantidad; i++) {
					objetos.add(obj);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String quitarTildes(String input) {
	    return input
	        .replace("á", "a").replace("é", "e").replace("í", "i")
	        .replace("ó", "o").replace("ú", "u")
	        .replace("Á", "A").replace("É", "E").replace("Í", "I")
	        .replace("Ó", "O").replace("Ú", "U")
	        .replace("ñ", "n").replace("Ñ", "N");
	}


	public void restarPokedollars(int cantidad) {
		if (cantidad > 0 && this.pokedollars >= cantidad) {
			this.pokedollars -= cantidad;
		}
	}
	
	public int getCantidadObjeto(String nombre) {
	    String normalizado = quitarTildes(nombre.toLowerCase());

	    for (Objeto o : objetos) {
	        if (quitarTildes(o.getNombre().toLowerCase()).equals(normalizado)) {
	            return o.getCantidad();
	        }
	    }
	    return 0;
	}



	// Getters & Setters
	public int getPokeballs() {
		return pokeballs;
	}

	public void setPokeballs(int pokeballs) {
		this.pokeballs = pokeballs;
	}

	public List<Pokemon> getEquipo() {
		return equipoPrincipal;
	}

	public List<Pokemon> getCaja() {
		return equipoCaja;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Pokemon> getEquipoPrincipal() {
		return equipoPrincipal;
	}

	public void setEquipoPrincipal(List<Pokemon> equipoPrincipal) {
		this.equipoPrincipal = equipoPrincipal;
	}

	public List<Pokemon> getEquipoCaja() {
		return equipoCaja;
	}

	public void setEquipoCaja(List<Pokemon> equipoCaja) {
		this.equipoCaja = equipoCaja;
	}

	public int getPokedollars() {
		return pokedollars;
	}

	public void setPokedollars(int pokedollars) {
		this.pokedollars = pokedollars;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}
}
