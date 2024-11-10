package models;

import java.io.Serializable;
import java.util.List;

public class Bus implements Serializable {

    private int id;
    private String placa;
    private String empresaProveedora;
    private String modelo;
    private boolean estado;


    public Bus(int id, String placa, String empresaProveedora, String modelo) throws BusException{
        if(id<1 || placa.equalsIgnoreCase("") || empresaProveedora.equalsIgnoreCase("") || modelo.equalsIgnoreCase("")) {
            throw new BusException("Los campos no pueden estar vacios");
        }else{
            this.id = id;
            this.placa = placa;
            this.empresaProveedora = empresaProveedora;
            this.modelo = modelo;
            this.estado = false;
        }

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
