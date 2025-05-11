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
        try {
            String sql = "SELECT * FROM pokemon WHERE id_entrenador = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, this.id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int nivel = rs.getInt("nivel");
                int numPokedex = rs.getInt("num_pokedex");
                
                String sexoBD = rs.getString("sexo");
                Sexo sexo = sexoBD.equals("H") ? Sexo.MACHO : Sexo.HEMBRA;

                String tipo1BD = rs.getString("tipo1");
                String tipo2BD = rs.getString("tipo2");

                System.out.println("Tipo1 leido: '" + tipo1BD + "'");
                System.out.println("Tipo2 leido: '" + tipo2BD + "'");

                Tipo tipo1 = Tipo.valueOf(quitarTildes(tipo1BD.toUpperCase()));
                Tipo tipo2 = (tipo2BD != null && !tipo2BD.isEmpty())
                    ? Tipo.valueOf(quitarTildes(tipo2BD.toUpperCase()))
                    : Tipo.NORMAL;

                Pokemon p = new Pokemon(nombre, sexo, tipo1, tipo2);
                p.setNivel(nivel);
                p.setNumPokedex(numPokedex);

                this.añadirPokemon(p);
            }

            System.out.println("Pokémon cargados: " + equipoPrincipal.size());

        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error al cargar Pokémon desde la BD:");
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
