package main;

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

    // Getters/Setters si los necesitas
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
                int idPokemon = rs.getInt("id_pokemon"); // 🔧 CAMBIO

                String sexoBD = rs.getString("sexo");
                Sexo sexo = sexoBD.equals("H") ? Sexo.MACHO : Sexo.HEMBRA;

                String tipo1BD = rs.getString("tipo1");
                String tipo2BD = rs.getString("tipo2");

                Tipo tipo1 = Tipo.valueOf(quitarTildes(tipo1BD.toUpperCase()));
                Tipo tipo2 = (tipo2BD != null && !tipo2BD.isEmpty())
                    ? Tipo.valueOf(quitarTildes(tipo2BD.toUpperCase()))
                    : Tipo.NORMAL;

                Pokemon p = new Pokemon(nombre, sexo, tipo1, tipo2);
                p.setNivel(nivel);
                p.setNumPokedex(numPokedex);
                p.setIdPokemon(idPokemon); // 🔧 CAMBIO

                // Asignar bien según valor de BD
                if (equipo == 1) {
                    equipoPrincipal.add(p);
                } else if (equipo == 2) {
                    equipoCaja.add(p);  // <- Faltaba esto
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
            // Primero, marcar todos los Pokémon del entrenador como sin equipo (0 opcional, lo eliminamos)
            // Luego actualizamos uno a uno lo correcto

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




    private String quitarTildes(String input) {
        return input
            .replace("Á", "A")
            .replace("É", "E")
            .replace("Í", "I")
            .replace("Ó", "O")
            .replace("Ú", "U")
            .replace("Ñ", "N");
    }
}