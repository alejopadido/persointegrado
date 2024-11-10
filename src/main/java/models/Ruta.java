package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ruta implements Serializable {

    private String nombre;
    private int numRuta;
    private boolean ciclico;
    private int busesDisponibles;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private List<Bus> buses;
    private List<Paradero> paraderos;


    public Ruta(LocalTime incio, LocalTime fin, String nombre, int numeroDeRuta, boolean ciclico, int busesDisponibles) {
        this.nombre = nombre;
        this.numRuta = numeroDeRuta;
        this.ciclico = ciclico;
        this.horaInicio = incio;
        this.horaFin = fin;
        this.busesDisponibles = busesDisponibles;

        buses = new ArrayList<Bus>();
        paraderos = new ArrayList<Paradero>();
    }


}
