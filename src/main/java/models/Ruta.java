package models;

import utils.util;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public void añadirBus(Bus b){
        buses.add(b);

        for (Bus bus : buses) {
            System.out.println(bus.getPlaca());
        }
    }

    public void añadirParadero(String nombreParadero, String direccion, LocalTime llegada){
        paraderos.add(new Paradero(nombreParadero,direccion, llegada));
    }

    public String getNombre() {
        return nombre;
    }


    public LocalTime getInicio() {
        return horaInicio;
    }

    public LocalTime getFin() {
        return horaFin;
    }



    /** PagosXRuta:
     * Descripcion: Genera un resumen de los pagos realizados por los autobuses en una ruta específica.
     * Este método recorre todos los autobuses asociados a la ruta y obtiene los detalles
     * de los pagos realizados por cada autobús mediante el método `PagosXBus()` de la clase `Bus`.
     * @return Una cadena que contiene el nombre de la ruta, el número y una lista
     * de los pagos realizados por los autobuses en esa ruta.
     **/
    public String PagosXRuta(){
        List<String> pagosXBus = new ArrayList<String>();
        for(Bus b : buses)
            if(b != null)
                pagosXBus.add(b.PagosXBus());

        return "Pagos para la Ruta " + nombre + '\'' + numRuta +
                "{ " + pagosXBus +" } \n";
    }
    @Override
    public String toString() {
        return "{nombre: " + nombre +
                "numero de ruta: " + numRuta +
                "ciclico: " + ciclico +
                "hora de inicio: " + horaInicio +
                "hora de finalizacion: " + horaFin +
                "buses disponibles:" + busesDisponibles +
                " y estos son: " + buses +
                " y las paradas son: " + paraderos +
                "}\n";

    }
}
