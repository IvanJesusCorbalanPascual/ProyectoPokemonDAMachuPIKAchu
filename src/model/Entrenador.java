package model;

import java.util.*;

public class Entrenador {
    private String nombre;
    private List<Pokemon> equipoPrincipal;
    private List<Pokemon> equipoCaja;
    private int pokedollars;
    private List<Objeto> objetos;
    public int pokeballs = 100;

    public Entrenador(String nombre) {
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
            o.aplicar(p); // Método aplicar debe estar definido en la clase Objeto
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

}
