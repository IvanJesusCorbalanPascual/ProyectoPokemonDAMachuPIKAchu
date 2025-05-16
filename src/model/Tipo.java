package model;

import java.util.EnumMap;
import java.util.Map;

public enum Tipo {
    NORMAL, FUEGO, AGUA, PLANTA, ELECTRICO, HIELO, LUCHA,
    VENENO, TIERRA, VOLADOR, PSIQUICO, BICHO, ROCA, FANTASMA, DRAGON, HADA;

    private Map<Tipo, Double> efectividades;

    public double getMultiplicadorContra(Tipo enemigo) {
        return efectividades.getOrDefault(enemigo, 1.0);
    }

    public static double calcularMultiplicador(Tipo tipoAtaque, Tipo tipoDef1, Tipo tipoDef2) {
        double mult1 = tipoAtaque.getMultiplicadorContra(tipoDef1);
        double mult2 = tipoDef2 != null ? tipoAtaque.getMultiplicadorContra(tipoDef2) : 1.0;
        return mult1 * mult2;
    }

    static {
        for (Tipo tipo : Tipo.values()) {
            tipo.efectividades = new EnumMap<>(Tipo.class);
            for (Tipo t : Tipo.values()) {
                tipo.efectividades.put(t, 1.0); // por defecto
            }

            switch (tipo) {
                case NORMAL -> {
                    tipo.efectividades.put(FANTASMA, 0.0);
                    tipo.efectividades.put(ROCA, 0.5);
                }
                case FUEGO -> {
                    tipo.efectividades.put(PLANTA, 2.0);
                    tipo.efectividades.put(HIELO, 2.0);
                    tipo.efectividades.put(BICHO, 2.0);
                    tipo.efectividades.put(FUEGO, 0.5);
                    tipo.efectividades.put(AGUA, 0.5);
                    tipo.efectividades.put(ROCA, 0.5);
                    tipo.efectividades.put(DRAGON, 0.5);
                }
                case AGUA -> {
                    tipo.efectividades.put(FUEGO, 2.0);
                    tipo.efectividades.put(TIERRA, 2.0);
                    tipo.efectividades.put(ROCA, 2.0);
                    tipo.efectividades.put(AGUA, 0.5);
                    tipo.efectividades.put(PLANTA, 0.5);
                    tipo.efectividades.put(DRAGON, 0.5);
                }
                case PLANTA -> {
                    tipo.efectividades.put(AGUA, 2.0);
                    tipo.efectividades.put(ROCA, 2.0);
                    tipo.efectividades.put(TIERRA, 2.0);
                    tipo.efectividades.put(FUEGO, 0.5);
                    tipo.efectividades.put(PLANTA, 0.5);
                    tipo.efectividades.put(VENENO, 0.5);
                    tipo.efectividades.put(VOLADOR, 0.5);
                    tipo.efectividades.put(BICHO, 0.5);
                    tipo.efectividades.put(DRAGON, 0.5);
                }
                case ELECTRICO -> {
                    tipo.efectividades.put(AGUA, 2.0);
                    tipo.efectividades.put(VOLADOR, 2.0);
                    tipo.efectividades.put(PLANTA, 0.5);
                    tipo.efectividades.put(ELECTRICO, 0.5);
                    tipo.efectividades.put(DRAGON, 0.5);
                    tipo.efectividades.put(TIERRA, 0.0);
                }
                case HIELO -> {
                    tipo.efectividades.put(PLANTA, 2.0);
                    tipo.efectividades.put(TIERRA, 2.0);
                    tipo.efectividades.put(VOLADOR, 2.0);
                    tipo.efectividades.put(DRAGON, 2.0);
                    tipo.efectividades.put(FUEGO, 0.5);
                    tipo.efectividades.put(AGUA, 0.5);
                    tipo.efectividades.put(HIELO, 0.5);
                }
                case LUCHA -> {
                    tipo.efectividades.put(NORMAL, 2.0);
                    tipo.efectividades.put(ROCA, 2.0);
                    tipo.efectividades.put(HIELO, 2.0);
                    tipo.efectividades.put(FANTASMA, 0.0);
                    tipo.efectividades.put(VENENO, 0.5);
                    tipo.efectividades.put(VOLADOR, 0.5);
                }
                case VENENO -> {
                    tipo.efectividades.put(PLANTA, 2.0);
                    tipo.efectividades.put(FANTASMA, 0.0);
                    tipo.efectividades.put(ROCA, 0.5);
                    tipo.efectividades.put(TIERRA, 0.5);
                }
                case TIERRA -> {
                    tipo.efectividades.put(FUEGO, 2.0);
                    tipo.efectividades.put(ELECTRICO, 2.0);
                    tipo.efectividades.put(ROCA, 2.0);
                    tipo.efectividades.put(BICHO, 0.5);
                    tipo.efectividades.put(PLANTA, 0.5);
                    tipo.efectividades.put(VOLADOR, 0.0);
                }
                case VOLADOR -> {
                    tipo.efectividades.put(PLANTA, 2.0);
                    tipo.efectividades.put(BICHO, 2.0);
                    tipo.efectividades.put(LUCHA, 2.0);
                    tipo.efectividades.put(ELECTRICO, 0.5);
                    tipo.efectividades.put(ROCA, 0.5);
                }
                case PSIQUICO -> {
                    tipo.efectividades.put(LUCHA, 2.0);
                    tipo.efectividades.put(VENENO, 2.0);
                    tipo.efectividades.put(PSIQUICO, 0.5);
                    tipo.efectividades.put(FANTASMA, 0.0);
                }
                case BICHO -> {
                    tipo.efectividades.put(PLANTA, 2.0);
                    tipo.efectividades.put(PSIQUICO, 2.0);
                    tipo.efectividades.put(FUEGO, 0.5);
                    tipo.efectividades.put(LUCHA, 0.5);
                    tipo.efectividades.put(FANTASMA, 0.5);
                }
                case ROCA -> {
                    tipo.efectividades.put(FUEGO, 2.0);
                    tipo.efectividades.put(HIELO, 2.0);
                    tipo.efectividades.put(VOLADOR, 2.0);
                    tipo.efectividades.put(BICHO, 2.0);
                    tipo.efectividades.put(LUCHA, 0.5);
                    tipo.efectividades.put(TIERRA, 0.5);
                }
                case FANTASMA -> {
                    tipo.efectividades.put(FANTASMA, 2.0);
                    tipo.efectividades.put(NORMAL, 0.0);
                    tipo.efectividades.put(PSIQUICO, 0.0);
                }
                case DRAGON -> {
                    tipo.efectividades.put(DRAGON, 2.0);
                }
                default -> {}
            }
        }
    }
}
