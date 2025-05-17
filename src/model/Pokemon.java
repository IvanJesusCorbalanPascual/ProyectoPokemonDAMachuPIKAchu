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
	private int turnosEstado = 0;
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
	    while (experiencia >= nivel * 10) {
	        experiencia -= nivel * 10;
	        subirNivel();
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
	    psMax = vitalidad; // Actualiza PS al máximo
	    ps = psMax;

	    System.out.println(nombre + " subió a nivel " + nivel + "!");

	    // Permite aprender movimiento en los niveles clave
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
	    if (estado == Estado.DORMIDO) {
	        return false;
	    } else if (estado == Estado.PARALIZADO) {
	        boolean ataca = Math.random() > 0.5;
	        if (!ataca) {
	        	System.out.println(nombre + " está paralizado y no se puede mover.");
	        }
	        return ataca;
	    }
	    return true;
	}

	public String getResumenEstado() {
		return estaDebilitado() ? " (DEBILITADO)"
			 : estado != Estado.NINGUNO ? " [" + estado.toString() + "]"
			 : "";
	}
	
	public Pokemon copiar() {
		Pokemon copia = new Pokemon(numPokedex, nombre);
		copia.setMote(mote);
		copia.setNivel(nivel);
		copia.setExperiencia(experiencia);
		copia.setSexo(sexo);
		copia.setTipo1(tipo1);
		copia.setTipo2(tipo2);
		copia.setVitalidad(vitalidad);
		copia.setEstado(estado);
		copia.setTurnosEstado(turnosEstado);
		copia.setPs(ps);
		copia.setPsMax(psMax);
		copia.setAtaque(ataque);
		copia.setDefensa(defensa);
		copia.setAtaqueEspecial(ataqueEspecial);
		copia.setDefensaEspecial(defensaEspecial);
		copia.setVelocidad(velocidad);
		copia.setEstamina(estamina);
		copia.setObjeto(objeto);
		copia.setMovimientosDisponibles(new ArrayList<>(movimientosDisponibles));
		return copia;
	}
    
	public void aplicarEfectoEstado() {
	    if (estado == null || estado == Estado.NINGUNO) return;

	    switch (estado) {
	        case DORMIDO -> {
	            turnosEstado--;
	            System.out.println(nombre + " está dormido. Turnos restantes: " + turnosEstado);
	            if (turnosEstado <= 0) {
	                estado = Estado.NINGUNO;
	                turnosEstado = 0;
	            }
	        }
	        case ENVENENADO, QUEMADO -> {
	            int daño = Math.max(1, (int) (psMax * 0.1));
	            ps -= daño;
	            if (ps < 0) ps = 0;
	            System.out.println(nombre + " sufre " + daño + " de daño por estar " + estado.toString().toLowerCase() + ".");
	        }
	        case PARALIZADO -> {
	            //Lo controlamos en puedeAtacar()
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
        if (estado == Estado.DORMIDO) {
            this.turnosEstado = 2 + new Random().nextInt(3); // Se dormira entre 2 y 4 turnos
        }
    }

	
	public void setTurnosEstado(int turnos) {
	    this.turnosEstado = turnos;
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
	
	public boolean recibirDanio(int danio) {
	    this.ps -= danio;
	    if (this.ps < 0) this.ps = 0;
	    return estaDebilitado();
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

	public void setMovimientosPosibles(List<Movimiento> movimientosPosibles) {
		this.movimientosPosibles = movimientosPosibles;
	}
	
	@Override
	public String toString() {
	    return getMote() + " (" + sexo + ", Nv. " + nivel + ")";
	}

	public void setPs(int ps) {
	    this.ps = Math.max(0, Math.min(ps, this.psMax));
	}

	public void setPsMax(int psMax) {
	    this.psMax = psMax;
	    if (this.ps > psMax) this.ps = psMax;
	}
}
