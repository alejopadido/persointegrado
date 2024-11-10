package models;

import controllers.ControladorCrearContrato;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PersoIntegrado implements Serializable {
    private static PersoIntegrado instance;
    List<EmpresaProveedora> empresas;
    List<Ruta> rutas;

    public PersoIntegrado() {
        empresas = new ArrayList<EmpresaProveedora>();
    }

    public void crearEmpresa(String nombre, int nit, String contrato) throws PersoIntegradoException, EmpresaProveedoraException, ContratoProveedorException {
        boolean flag = true;
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNit() == nit || e.getNombre().equalsIgnoreCase(nombre) || e.getContrato().getContrato() == contrato){
                    flag = false;
                }
            }
        }
        if(flag){
            empresas.add(new EmpresaProveedora(nombre, nit, contrato));
        }
    }

    public void añadirBus(String nombreEmpresa,String placa, String modelo) throws BusException, EmpresaProveedoraException {
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNombre().equalsIgnoreCase(nombreEmpresa)){
                    e.añadirBus(idNuevoBus(),placa,modelo);
                }
            }
        }
    }

    public void crearRuta(String conductor, LocalTime inicio, LocalTime fin, int horaInicio, int horaFinal, int minutoInicio, int minutoFinal, String nombre, int numeroDeRuta, boolean ciclico, int busesDisponibles) throws PersoIntegradoException {
        if(nombre.equalsIgnoreCase("") || numeroDeRuta<=0 || busesDisponibles<=0 ){
            throw new PersoIntegradoException("No pueden existir nulos o menores a 0");
        }else{
            LocalTime inicioRuta = utils.util.integersToTimes(horaInicio,minutoInicio);
            LocalTime finRuta = utils.util.integersToTimes(horaFinal,minutoFinal);

            int x = verificarValidez(inicio,fin, busesDisponibles);
            if(x >= numeroDeRuta){
                List<List<Bus>> buses = busesDisponibles(inicio,fin,busesDisponibles,conductor);
            }
            rutas.add(new Ruta(inicioRuta,finRuta,nombre,numeroDeRuta,ciclico,busesDisponibles));


        }
    }

    public int verificarValidez(LocalTime inicio, LocalTime fin, int busesDisponibles) throws PersoIntegradoException {
        int c  = 0;
        for(EmpresaProveedora e: empresas){
            if(c >= busesDisponibles){
                break;
            }
            if(e != null){
                c += e.verificarValidez(inicio,fin,busesDisponibles);
            }
        }
        return  c;
    }

    public List<List<Bus>> busesDisponibles(LocalTime inicio, LocalTime fin, int busesDisponibles, String conductor){
        List<List<Bus>> ls = new ArrayList<>(){
        };
        for(EmpresaProveedora e: empresas){
            ls.add(e.busesDisponibles(inicio,fin,busesDisponibles, conductor));
        }
        return ls;
    }

    public void añadirALaRuta(Bus b, String nombre ){

    }

    public int idNuevoBus(){
        int x = 0;{
            for(EmpresaProveedora e: empresas){
                if(e != null){
                    x += e.getBusesSize();
                }
            }
        }
        x++;
        return x;
    }

    public void imprimirEmpresas(){
        for(EmpresaProveedora e: empresas){
            System.out.println(e);
        }
    }

    /**
     * Uso del patron singleton para garantizar que todas los controladores interactuan con la misma instancia
     * de perso integrado
     * @return
     */
    public static PersoIntegrado getInstance() {
        if (instance == null) {
            synchronized (PersoIntegrado.class) {
                if (instance == null) {
                    instance = new PersoIntegrado();
                }
            }
        }
        return instance;
    }


    public List<EmpresaProveedora> getEmpresas() {
        return empresas;
    }


}
