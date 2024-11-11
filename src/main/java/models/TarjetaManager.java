package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TarjetaManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static TarjetaManager instance;

    // Mapa constante para tipos de usuario y tarifas
    private static final Map<String, Integer> TIPOS_USUARIO_TARIFAS;

    // Lista de tarjetas
    private List<Tarjeta> tarjetas;

    // Inicialización estática del mapa de tarifas
    static {
        Map<String, Integer> tiposUsuario = new HashMap<>();
        tiposUsuario.put("Estudiante", 1400);
        tiposUsuario.put("Mayor de edad", 1250);
        tiposUsuario.put("Discapacitado", 1300);
        tiposUsuario.put("Corriente", 1700);
        TIPOS_USUARIO_TARIFAS = Collections.unmodifiableMap(tiposUsuario);
    }

    private TarjetaManager() {
        this.tarjetas = new ArrayList<>();
    }

    // Método singleton para obtener la instancia
    public static TarjetaManager getInstance() {
        if (instance == null) {
            instance = new TarjetaManager();
        }
        return instance;
    }

    // Método para añadir una tarjeta a la lista
    public void addTarjeta(Tarjeta tarjeta) {
        this.tarjetas.add(tarjeta);
    }

    // Método para obtener la lista de tarjetas
    public List<Tarjeta> getTarjetas() {
        return this.tarjetas;
    }

    public Map<String, Integer> getTIPOS_USUARIO_TARIFAS(){
        return TIPOS_USUARIO_TARIFAS;
    }

    // Método para obtener la tarifa según el tipo de usuario y si es hora valle
    public int getTarifa(String tipoUsuario) {
        return TIPOS_USUARIO_TARIFAS.getOrDefault(tipoUsuario, 0);
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
