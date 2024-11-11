package models;

import java.io.Serializable;
import java.time.LocalTime;

public class Paradero implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String direccion;
    private HoraDeLlegada horaDeLlegada;


    public Paradero(String nombre, String direccion, LocalTime horaDeLlegada) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.horaDeLlegada = new HoraDeLlegada(horaDeLlegada);
    }

    public HoraDeLlegada getHoraDeLlegada() {
        return horaDeLlegada;
    }

    @Override
    public String toString() {
        return "{nombre: " + nombre
                + ", direccion: " + direccion
                + ", horaDeLlegada: " + horaDeLlegada + "}";
    }
}
