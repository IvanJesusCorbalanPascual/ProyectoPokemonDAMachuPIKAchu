package main;

public class Objeto {
    private String nombre;
    private String descripcion;
    private String efecto;
    private int precio;

    public Objeto(String nombre, String descripcion, String efecto, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.efecto = efecto;
        this.precio = precio;
    }

    public void aplicar(Pokemon p) {
        switch (efecto.toLowerCase()) {
            case "cura":
                p.setEstado(Estado.SALUDABLE);
                System.out.println(p.getNombre() + " ha sido curado.");
                break;

            case "cura vida":
                p.setVitalidad(p.getVitalidad() + 20); // ejemplo, cura 20 HP
                System.out.println(p.getNombre() + " ha recuperado vitalidad.");
                break;

            case "ataque+":
                p.setAtaque(p.getAtaque() + 5);
                System.out.println(p.getNombre()+"aumentó su ataque.");
                break;

            // Puedes añadir más efectos según necesites

            default:
                System.out.println("Efecto desconocido.");
                break;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEfecto() {
        return efecto;
    }

    public int getPrecio() {
        return precio;
    }
}
