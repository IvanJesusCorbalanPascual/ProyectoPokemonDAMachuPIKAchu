package model;

public class Movimiento {
    private int id;
    private String nombre;
    private int nivelAprendizaje;
    private int ppMax;
    private TipoMovimiento tipo;      // ATAQUE, ESTADO, MEJORA
    private Tipo tipoElemento;        // FUEGO, AGUA, PLANTA, etc.
    private int potencia;
    private String estado;
    private int turnos;
    private String mejora;
    private int num;
    private int ppActual;

    public Movimiento(int id, String nombre, int nivelAprendizaje, int ppMax, String tipoMovimientoStr,
                      String tipoElementoStr, int potencia, String estado, int turnos,
                      String mejora, int num) {
        this.id = id;
        this.nombre = nombre;
        this.nivelAprendizaje = nivelAprendizaje;
        this.ppMax = ppMax;
        this.ppActual = ppMax;

        try {
            this.tipo = TipoMovimiento.valueOf(tipoMovimientoStr.toUpperCase());
        } catch (Exception e) {
            System.err.println("Error: tipoMovimiento inválido → " + tipoMovimientoStr);
            this.tipo = TipoMovimiento.ATAQUE;
        }

        try {
            this.tipoElemento = Tipo.valueOf(tipoElementoStr.toUpperCase());
        } catch (Exception e) {
            System.err.println("Error: tipoElemento inválido → " + tipoElementoStr);
            this.tipoElemento = Tipo.NORMAL;
        }

        this.potencia = potencia;
        this.estado = estado;
        this.turnos = turnos;
        this.mejora = mejora;
        this.num = num;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getNivelAprendizaje() { return nivelAprendizaje; }
    public int getPpMax() { return ppMax; }
    public TipoMovimiento getTipo() { return tipo; }
    public Tipo getTipoElemento() { return tipoElemento; }
    public int getPotencia() { return potencia; }
    public String getEstado() { return estado; }
    public int getTurnos() { return turnos; }
    public String getMejora() { return mejora; }
    public int getNum() { return num; }
    public int getPpActual() { return ppActual; }

    // Gestión de PP
    public void gastarPP() {
        if (ppActual > 0) ppActual--;
    }

    public boolean tienePP() {
        return ppActual > 0;
    }

    public void recargarPP() {
        this.ppActual = this.ppMax;
    }
}
