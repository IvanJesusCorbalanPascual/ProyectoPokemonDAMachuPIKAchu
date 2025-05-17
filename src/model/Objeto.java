package model;

public class Objeto {
	private String nombre;
	private int precio;
	private int idObjeto;
	private int cantidad;

	// Constructor con cantidad
	public Objeto(String nombre, int precio, int idObjeto, int cantidad) {
	    this.nombre = nombre;
	    this.precio = precio;
	    this.idObjeto = idObjeto;
	    this.cantidad = cantidad;
	}
	
	// Constructor sin cantidad
	public Objeto(String nombre, int precio, int idObjeto) {
	    this.nombre = nombre;
	    this.precio = precio;
	    this.idObjeto = idObjeto;
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
//	        p.restaurarMovimientos();
//	        break;
//
//		
//		 case "anillo unico": p.activarInvencibilidad(3);
//		 p.setAtaque((int) (p.getAtaque() * 10));
//		 break;
		 
		}

	}
	
	// Getters & Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}

	public int getCantidad() {
	    return cantidad;
	}

	public void setCantidad(int cantidad) {
	    this.cantidad = cantidad;
	}
	
	// toString	
	@Override
	public String toString() {
	    return nombre + " x" + cantidad;
	}

}
