package model;

import java.util.*;

public class Pokemon {
	private int numPokedex; // IMPORTANTE, Creada para la clase Captura, puede dar problemas
	private String nombre;
	private String mote;
	private int vitalidad;
	private int ataque;
	private int defensa;
	private int ataqueEspecial;
	private int defensaEspecial;
	private int velocidad;
	private int estamina;
	private int nivel;
	private int experiencia;
	private int fertilidad = 5;
	private Sexo sexo;
	private Tipo tipo1;
	private Tipo tipo2;
	private Estado estado;
	private Objeto objeto;
	private List<Movimiento> movimientosDisponibles;
	private List<Movimiento> movimientosPosibles;
	

	public Pokemon(String nombre, Sexo sexo, Tipo tipo1, Tipo tipo2) {
		this.nombre = nombre;
		this.mote = null;
		this.nivel = 1;
		this.experiencia = 0;
		this.sexo = sexo;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.estado = Estado.SALUDABLE;
		this.movimientosDisponibles = new ArrayList<>();
		this.movimientosPosibles = new ArrayList<>();
		generarEstadisticasIniciales();
	}

	 public Pokemon(int numPokedex, String nombre) {
	        this.numPokedex = numPokedex;
	        this.nombre = nombre;
	    }
	 
	private void generarEstadisticasIniciales() { // Genera estadistias aleatorias para el pokemon recien creado
		Random rand = new Random();
		this.vitalidad = rand.nextInt(10) + 1;
		this.ataque = rand.nextInt(10) + 1;
		this.defensa = rand.nextInt(10) + 1;
		this.ataqueEspecial = rand.nextInt(10) + 1;
		this.defensaEspecial = rand.nextInt(10) + 1;
		this.velocidad = rand.nextInt(10) + 1;
		this.estamina = rand.nextInt(10) + 1;
	}

	public void subirExperiencia(int expGanada) {
		experiencia += expGanada;
		if (experiencia >= nivel * 10) {
			subirNivel();
			experiencia = 0;
		}
	}

	private void subirNivel() {
		nivel++;
		Random rand = new Random();
		vitalidad += rand.nextInt(5) + 1;
		ataque += rand.nextInt(5) + 1;
		defensa += rand.nextInt(5) + 1;
		ataqueEspecial += rand.nextInt(5) + 1;
		defensaEspecial += rand.nextInt(5) + 1;
		velocidad += rand.nextInt(5) + 1;
		estamina += rand.nextInt(5) + 1;
		if (nivel == 8 || nivel == 16 ||nivel == 32 ) {
			aprenderMovimiento();
		}
	}

	private void aprenderMovimiento() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * public void aprenderMovimiento(Movimiento nuevo) { if
	 * (movimientosDisponibles.size() < 4) { movimientosDisponibles.add(nuevo); }
	 * else { // lógica para intercambiar movimiento, según decisión del usuario } }
	 */
	public void setEstado(Estado saludable) {
		// TODO Auto-generated method stub

	}

	public String getNombre() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getVitalidad() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setVitalidad(int i) {
		// TODO Auto-generated method stub

	}

	public int getAtaque() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAtaque(int i) {
		// TODO Auto-generated method stub

	}

	public int getNumPokedex() {
		return numPokedex;
	}

	// Métodos get/set y otros métodos como atacar, etc., vendrán después
}
