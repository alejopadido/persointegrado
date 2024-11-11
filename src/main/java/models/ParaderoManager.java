package models;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ParaderoManager {
    private static final Map<String, Paradero> paraderos = new HashMap<>();

    // Obtener un paradero (lo crea si no existe)
    public static Paradero obtenerParadero(String nombre, String direccion, LocalTime horaDeLlegada) {
        return paraderos.computeIfAbsent(nombre, key -> new Paradero(nombre, direccion, horaDeLlegada));
    }

    // Obtener todos los paraderos
    public static Map<String, Paradero> obtenerTodosLosParaderos() {
        return paraderos;
    }
}
