package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public Bus(Bus b){
        this.id = b.getId();
        this.placa = b.getPlaca();
        this.empresaProveedora = b.getEmpresaProveedora();
        this.modelo = b.getModelo();
        this.estado = b.isEstado();
        turnos = new ArrayList<Turno>();
    }

    public Bus(){
        this.estado = false;
        this.id = 0;
        this.placa = "";
        this.empresaProveedora = "";
        this.modelo = "";
        this.estado = false;
        turnos = new ArrayList<>();
    }

    public boolean verificarValidez(LocalTime inicio, LocalTime fin) {
        for (Turno t : turnos) {
            if(t != null){
                if (!(fin.isBefore(t.getHoraInicio()) || inicio.isAfter(t.getHoraFin()))) {
                    return false; // Hay un solapamiento
                }
            }
        }
        return true; // No hay solapamientos
    }



    public boolean busesDisponibles(LocalTime inicio, LocalTime fin, String conductor) {
        if (verificarValidez(inicio, fin)) {
            turnos.add(new Turno(inicio, fin, conductor)); // Añadir el nuevo turno si es válido
            return true;
        }
        return false;
    }


    public String getPlaca(){
        return placa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEmpresaProveedora() {
        return empresaProveedora;
    }

    public void setEmpresaProveedora(String empresaProveedora) {
        this.empresaProveedora = empresaProveedora;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    /** PagosXBus:
     * Descripcion: Genera un resumen de los pagos realizados por turnos en un bus específico.
     * Este método recorre todos los turnos asociados al bus y obtiene los detalles
     * de los pagos realizados mediante el método `calcularPago()` de la clase `Turno`.
     * @return Una cadena que contiene el nombre del conductor y el monto a liquidar segun cuantos turnos
     * haya realizado.
     **/
    public String PagosXBus(){
        double pago = 0;
        for(Turno t : turnos){
            if(t != null)
                pago += t.calcularPago();
        }
        return "Conductor: " + turnos.get(1).getConductor() + " - pago: " + pago + "\n";
    }


    @Override
    public String toString() {
        return "id: " + id + ", Placa: " + placa + ", Empresa: " + empresaProveedora + ", Modelo: " + modelo + ", Estado: " + (estado ? "Asignado" : "No asignado");
    }

}
