package models;

import controllers.ControladorCrearContrato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersoIntegrado implements Serializable {
    private static PersoIntegrado instance;
    List<EmpresaProveedora> empresas;
    ControladorCrearContrato controladorCrearContrato;


    public PersoIntegrado() {
        empresas = new ArrayList<EmpresaProveedora>();
    }

    public void crearEmpresa(String nombre, int nit, String contrato) throws PersoIntegradoException, EmpresaProveedoraException, ContratoProveedorException {
        boolean flag = true;
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNit() == nit || e.getNombre().equalsIgnoreCase(nombre) || e.getContrato().getContrato() == contrato){
                    System.out.println("Se ha cambiado la bandera a falso");
                    flag = false;
                }
            }
        }
        if(flag){
            empresas.add(new EmpresaProveedora(nombre, nit, contrato));
        }
    }

    public void añadirBus(String nombreEmpresa,String placa, String modelo) throws BusException, EmpresaProveedoraException {
        System.out.println("El nombre de la empresa es: "+ nombreEmpresa);
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNombre().equalsIgnoreCase(nombreEmpresa)){
                    e.añadirBus(idNuevoBus(),placa,modelo);
                    System.out.println("Se encontro y añadio");
                }
            }
        }
        imprimirEmpresas();
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
