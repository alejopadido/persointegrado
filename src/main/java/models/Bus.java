package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Bus implements Serializable {

    private int id;
    private String placa;
    private String empresaProveedora;
    private String modelo;
    private boolean estado;
    List<Turno> turnos;

    public Bus(int id, String placa, String empresaProveedora, String modelo) throws BusException{
        if(id<1 || placa.equalsIgnoreCase("") || empresaProveedora.equalsIgnoreCase("") || modelo.equalsIgnoreCase("")) {
            throw new BusException("Los campos no pueden estar vacios");
        }else{
            this.id = id;
            this.placa = placa;
            this.empresaProveedora = empresaProveedora;
            this.modelo = modelo;
            this.estado = false;
            turnos = new ArrayList<Turno>();
        }

    }

    public boolean verificarValidez(LocalTime inicio, LocalTime fin, int busesDisponibles){
        boolean posible = true;
        for(Turno t: turnos){
            if(fin.isBefore(t.getHoraFin()) && inicio.isAfter(t.getHoraInicio())){
                posible = true;
            }else{
                posible = false;
                return posible;
            }
        }
        return posible;
    }

    public boolean busesDisponibles(LocalTime inicio, LocalTime fin, int busesDisponibles, String conductor){
        boolean posible = true;
        for(Turno t: turnos){
            if(fin.isBefore(t.getHoraFin()) && inicio.isAfter(t.getHoraInicio())){
                posible = true;
            }else{
                posible = false;
                return posible;
            }
        }
        if(posible){
            turnos.add(new Turno(inicio, fin, conductor));
        }
        return posible;
    }

    public String getPlaca(){
        return placa;
    }

    @Override
    public String toString() {
        String s;
        if(!this.estado){
            s = "No está asignado actualmente";
        }else{
            s = "Ya se encuentra asignado";
        }
        return "{id: " + id +
                "Placa: " + placa +
                "Nombre de la empresa proveedora: " + empresaProveedora +
                "Modelo: " + modelo +
                "Estado: " + s +
                "} ";
    }
}
