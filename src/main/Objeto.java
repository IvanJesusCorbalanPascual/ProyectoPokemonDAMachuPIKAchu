package main;

public class Objeto {
	private String nombre;
	private int precio;

	public Objeto(String nombre, int precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPrecio() {
		return precio;
	}

	// Aplica el efecto dependiendo del objeto
	public void aplicar(Pokemon p) {
		switch (nombre.toLowerCase()) {
		case "pesa":
			p.setAtaque((int) (p.getAtaque() * 1.2));
			p.setDefensa((int) (p.getDefensa() * 1.2));
			p.setVelocidad((int) (p.getVelocidad() * 0.8));
			break;

		case "pluma":
			p.setVelocidad((int) (p.getVelocidad() * 1.3));
			p.setDefensa((int) (p.getDefensa() * 0.8));
			p.setDefensaEspecial((int) (p.getDefensaEspecial() * 0.8));
			break;

		case "chaleco":
			p.setDefensa((int) (p.getDefensa() * 1.2));
			p.setDefensaEspecial((int) (p.getDefensaEspecial() * 1.2));
			p.setVelocidad((int) (p.getVelocidad() * 0.85));
			p.setAtaque((int) (p.getAtaque() * 0.85));
			break;

		case "baston":
			p.setEstamina((int) (p.getEstamina() * 1.2));
			p.setVelocidad((int) (p.getVelocidad() * 0.85));
			break;

//	    case "pilas":
//	        p.setRecuperacionEstamina((int) (p.getRecuperacionEstamina() * 1.5));
//	        p.setDefensaEspecial((int) (p.getDefensaEspecial() * 0.7));
//	        break;
//
//	    case "eter":
//	        p.restaurarMovimientos(); // Asegúrate de tener este método en la clase Pokémon
//	        break;

		/*
		 * case "anillo unico": p.activarInvencibilidad(3); // Método que debes
		 * implementar para durar 3 turnos p.setAtaque((int) (p.getAtaque() * 10));
		 * break;
		 */
		}

	}
}
