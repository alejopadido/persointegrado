package models;

import utils.util;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Ruta implements Serializable {
    private static final long serialVersionUID = 1L;

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
        // Obtener o crear el paradero en el ParaderoManager
        Paradero paradero = PersoIntegrado.getInstance().getParaderoManager().obtenerParadero(nombreParadero, direccion, llegada);

        // Añadir el paradero a la lista de paraderos de esta ruta
        paraderos.add(paradero);
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

    public List<Paradero> getParaderos() {
        return paraderos;
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

    public boolean tieneParadero(String nombreParadero) {
        return paraderos.stream().anyMatch(paradero -> paradero.getNombre().equalsIgnoreCase(nombreParadero));
    }

    public int contarParaderosEntre(String origen, String destino) {
        int indiceOrigen = -1;
        int indiceDestino = -1;

        for (int i = 0; i < paraderos.size(); i++) {
            if (paraderos.get(i).getNombre().equalsIgnoreCase(origen)) {
                indiceOrigen = i;
            } else if (paraderos.get(i).getNombre().equalsIgnoreCase(destino)) {
                indiceDestino = i;
            }

            if (indiceOrigen != -1 && indiceDestino != -1) {
                break;
            }
        }

        if (indiceOrigen == -1 || indiceDestino == -1) {
            return Integer.MAX_VALUE; // No existe un camino directo
        }

        return Math.abs(indiceDestino - indiceOrigen) - 1; // Retorna la cantidad de paraderos entre ambos
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ruta: \n");
        sb.append("  Nombre: ").append(nombre).append("\n");
        sb.append("  Número de Ruta: ").append(numRuta).append("\n");
        sb.append("  Ciclico: ").append(ciclico ? "Sí" : "No").append("\n");
        sb.append("  Hora de Inicio: ").append(horaInicio).append("\n");
        sb.append("  Hora de Finalización: ").append(horaFin).append("\n");
        sb.append("  Buses Disponibles: ").append(busesDisponibles).append("\n");
        sb.append("  Buses: \n");
        for (Bus bus : buses) {
            sb.append("    - ").append(bus).append("\n");
        }
        sb.append("  Paradas: \n");
        for (Paradero paradero : paraderos) {
            sb.append("    - ").append(paradero).append("\n");
        }
        return sb.toString();
    }

}
