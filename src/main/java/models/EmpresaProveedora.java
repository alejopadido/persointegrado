package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class EmpresaProveedora implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private  int nit;
    private ContratoProveedor contrato;
    private List<Bus> buses;

    public EmpresaProveedora(String nombre, int nit, String contrato) throws EmpresaProveedoraException, ContratoProveedorException {
        if(nombre == null){
            throw new EmpresaProveedoraException("Nombre del empresa nulo");
        }if(nit == 0){
            throw new EmpresaProveedoraException("Nit del empresa no puede ser 0");
        }else{
            this.nombre = nombre;
            this.nit = nit;
            this.contrato = new ContratoProveedor(contrato);
            buses = new ArrayList<Bus>();
        }

    }

    public void añadirBus(int id, String placa, String modelo) throws EmpresaProveedoraException, BusException {
        if(id<1 || placa.equalsIgnoreCase("") || modelo.equalsIgnoreCase("")) {
            throw new EmpresaProveedoraException("Los campos no pueden estar vacios");
        }else if(revisarPlacaDuplicad(placa)){
            throw new BusException("El placa ya existe");
        }else{
            buses.add(new Bus(id, placa, this.nombre, modelo));
        }
    }

    public boolean revisarPlacaDuplicad(String placa){
        boolean estado = false;
        for(Bus b: buses){
            if(placa.equalsIgnoreCase(b.getPlaca())){
                estado = true;
            }
        }
        return estado;
    }

    public int verificarValidez(LocalTime inicio, LocalTime fin) {
        int contador = 0;
        for(Bus b: buses){
            if(b.verificarValidez(inicio, fin)) {
                contador++;
            }
        }
        return contador;

    }


    public List<Bus> busesDisponibles(LocalTime inicio, LocalTime fin, String conductor) {
        List<Bus> ls = new ArrayList<>();
        for (Bus b : buses) {
            if (b != null && b.busesDisponibles(inicio, fin, conductor)) {
                ls.add(b);
            }
        }
        return ls;
    }



    public List<Bus> getBuses() {
        return buses;
    }

    public int getBusesSize(){
        return buses.size();
    }

    public int getNit(){
        return nit;
    }

    public void setNit(int nit){
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ContratoProveedor getContrato() {
        return contrato;
    }

    public void setContrato(ContratoProveedor contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString() {
        return "EmpresaProveedora{" +
                "nombre='" + nombre + '\'' +
                ", nit=" + nit +
                ", contrato=" + contrato +
                ", Buses=" + buses +
                '}';
    }


}
