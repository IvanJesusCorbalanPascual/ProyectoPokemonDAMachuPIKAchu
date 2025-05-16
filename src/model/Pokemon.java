package model;

import java.util.*;

public class Pokemon {
	private int idPokemon;
	private int numPokedex;
	private String nombre;
	private String mote;
	private int vitalidad;
	private int ps;
	private int psMax;
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
	private Estado estado = Estado.NINGUNO;
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
		this.estado = Estado.NINGUNO;
		this.movimientosDisponibles = new ArrayList<>();
		this.movimientosPosibles = new ArrayList<>();
		generarEstadisticasIniciales();
	}

	public Pokemon(int numPokedex, String nombre) {
	    this.numPokedex = numPokedex;
	    this.nombre = nombre;
	}
	 
	public void generarEstadisticasIniciales() {
		Random rand = new Random();
		this.vitalidad = rand.nextInt(10) + 1;
		this.ataque = rand.nextInt(10) + 1;
		this.defensa = rand.nextInt(10) + 1;
		this.ataqueEspecial = rand.nextInt(10) + 1;
		this.defensaEspecial = rand.nextInt(10) + 1;
		this.velocidad = rand.nextInt(10) + 1;
		this.estamina = rand.nextInt(10) + 1;
		this.psMax = this.vitalidad; 
		this.ps = this.psMax;
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
		if ((nivel == 8 || nivel == 16 || nivel == 32) && !movimientosPosibles.isEmpty()) {
			Movimiento nuevo = movimientosPosibles.get(0);
			aprenderMovimiento(nuevo);
		}
	}

	public void aprenderMovimiento(Movimiento nuevo) {
	    if (movimientosDisponibles.size() < 4) {
	        movimientosDisponibles.add(nuevo);
	    } else {
	        movimientosDisponibles.set(0, nuevo); 
	    }
	}
	
	public void recalcularPS() {
	    this.psMax = this.vitalidad; 
	    this.ps = this.psMax;
	}
	
    public boolean puedeAtacar() {
        return switch (estado) {
            case DORMIDO, CONGELADO -> false;
            case PARALIZADO -> Math.random() > 0.25;
            default -> true;
        };
    }
    
    public void aplicarEfectoEstado() {
        if (estado == null || estado == Estado.NINGUNO) return;

        switch (estado) {
            case ENVENENADO, QUEMADO -> {
                int danio = Math.max(1, psMax / 8); // 1/8 de PS max como daño
                recibirDanio(danio);
                System.out.println(nombre + " sufre " + danio + " de daño por el estado " + estado.getNombre());
            }
            case DORMIDO, CONGELADO -> {
                
                System.out.println(nombre + " está " + estado.getNombre() + " y no puede atacar.");
            }
            default -> {}
        }
    }


	/*
	 * public void aprenderMovimiento(Movimiento nuevo) { if
	 * (movimientosDisponibles.size() < 4) { movimientosDisponibles.add(nuevo); }
	 * else { // lógica para intercambiar movimiento, según decisión del usuario } }
	 */
	
	
	// Getters & Setters
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Estado getEstado() {
	    return estado;
	}
	
	public String getNombre() {
		return nombre;
	}

	public int getVitalidad() {
		return vitalidad;
	}

	public void setVitalidad(int vitalidad) {
	    this.vitalidad = vitalidad;
	    this.psMax = vitalidad;
	    this.ps = psMax;
	}

	public void setPs(int ps) {
		this.ps = ps;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int i) {
		this.ataque = i;
	}

	public int getNumPokedex() {
	    return numPokedex;
	}

	public void setNumPokedex(int numPokedex) {
	    this.numPokedex = numPokedex;
	}

	public int getPs() {
		return ps;
	}

	public int getPsMax() {
	    return psMax;
	}
	
	public String getMote() {
	    return mote != null ? mote : nombre;
	}
	
	public void setMote(String mote) {
	    this.mote = mote;
	}
	
	public int getNivel() {
	    return nivel;
	}
	
	public int getExperiencia() {
	    return experiencia;
	}
	
	public Sexo getSexo() {
	    return sexo;
	}
	
	public Tipo getTipo1() {
	    return tipo1;
	}
	
	public Tipo getTipo2() {
	    return tipo2;
	}
	
	public int getDefensa() {
	    return defensa;
	}

	public int getDefensaEspecial() {
	    return defensaEspecial;
	}

	public int getVelocidad() {
	    return velocidad;
	}

	public int getEstamina() {
	    return estamina;
	}
	
	public int getFertilidad() {
	    return fertilidad;
	}

	public void reducirFertilidad() {
	    if (fertilidad > 0) fertilidad--;
	}
	
	public boolean estaDebilitado() {
	    return ps <= 0;
	}

	public void curar() {
	    this.ps = this.psMax;
	    this.estado = Estado.NINGUNO;
	}
	
	public void recibirDanio(int danio) {
	    this.ps -= danio;
	    if (this.ps < 0) this.ps = 0;
	}

	public boolean estaDisponible() {
	    return !estaDebilitado() && estado == Estado.NINGUNO;
	}
	
	public List<Movimiento> getMovimientosDisponibles() {
	    return movimientosDisponibles;
	}

	public List<Movimiento> getMovimientosPosibles() {
	    return movimientosPosibles;
	}
	
	public void setObjeto(Objeto objeto) {
	    this.objeto = objeto;
	}
	
	public void setMovimientosDisponibles(List<Movimiento> movimientos) {
	    this.movimientosDisponibles = movimientos;
	}
	
	public void setDefensa(int defensa) {
	    this.defensa = defensa;
	}
	
	public void setNivel(int nivel) {
	    this.nivel = nivel;
	}

	public void setAtaqueEspecial(int ataqueEspecial) {
	    this.ataqueEspecial = ataqueEspecial;
	}

	public void setDefensaEspecial(int defensaEspecial) {
	    this.defensaEspecial = defensaEspecial;
	}

	public void setVelocidad(int velocidad) {
	    this.velocidad = velocidad;
	}

	public void setEstamina(int estamina) {
	    this.estamina = estamina;
	}

	public Objeto getObjeto() {
	    return objeto;
	}
	
	public int getIdPokemon() {
		return idPokemon;
	}

	public void setIdPokemon(int idPokemon) {
		this.idPokemon = idPokemon;
	}

	public void setFertilidad(int fertilidad) {
		this.fertilidad = fertilidad;
	}

	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}

	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPsMax(int psMax) {
		this.psMax = psMax;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public void setTipo1(Tipo tipo1) {
		this.tipo1 = tipo1;
	}

	public void setTipo2(Tipo tipo2) {
		this.tipo2 = tipo2;
	}

	public void setMovimientosDisponibles(List<Movimiento> movimientosDisponibles) {
		this.movimientosDisponibles = movimientosDisponibles;
	}

	public void setMovimientosPosibles(List<Movimiento> movimientosPosibles) {
		this.movimientosPosibles = movimientosPosibles;
	}
	
	@Override
	public String toString() {
	    return nombre + " (" + sexo + ", Nv. " + nivel + ")";
	}

	public void setPs(int ps) {
	    this.ps = Math.min(ps, this.psMax);
	}

	public void setPsMax(int psMax) {
	    this.psMax = psMax;
	    if (this.ps > psMax) this.ps = psMax;
	}
}
