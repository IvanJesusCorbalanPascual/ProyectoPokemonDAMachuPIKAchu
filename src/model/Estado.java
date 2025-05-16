package model;

public enum Estado { // Enumerador del estado del pokemon

	NINGUNO("Ninguno"), PARALIZADO("Paralizado"), DORMIDO("Dormido"), QUEMADO("Quemado"), ENVENENADO("Envenenado"),
	CONGELADO("Congelado");

	private final String nombre;

	Estado(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean estaEstadoAlterado() {
		return this != NINGUNO;
	}

	// Lógica específica de cada estado
	public String getDescripcionEfectoCombate() {
		return switch (this) {
		case PARALIZADO -> "Puede fallar el turno (25%).";
		case DORMIDO -> "No permite atacar durante varios turnos.";
		case QUEMADO -> "Piede PS por cada turno y su ataque se reduce.";
		case ENVENENADO -> "Pierde PS cada turno.";
		case CONGELADO -> "No puede atacar hasta que desaparezca el efecto.";
		default -> "Sin efecto.";
		};
	}
}
