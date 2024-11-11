package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ParaderoManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private static ParaderoManager instance;
    private final Map<String, Paradero> paraderos;

    // Constructor privado para el Singleton
    private ParaderoManager() {
        paraderos = new HashMap<>();
    }

    // Obtener la instancia de ParaderoManager
    public static ParaderoManager getInstance() {
        if (instance == null) {
            instance = new ParaderoManager();
        }
        return instance;
    }

    // Método para obtener un paradero único, o crear uno nuevo si no existe
    public Paradero obtenerParadero(String nombre, String direccion, LocalTime horaDeLlegada) {
        return paraderos.computeIfAbsent(nombre, key -> new Paradero(nombre, direccion, horaDeLlegada));
    }

    // Obtener todos los paraderos
    public Map<String, Paradero> obtenerTodosLosParaderos() {
        return paraderos;
    }
}
