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

    public void crearEmpresa(String nombre, int nit, int contrato) throws PersoIntegradoException, EmpresaProveedoraException, ContratoProveedorException {
        boolean flag = true;
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNit() == nit || e.getNombre().equalsIgnoreCase(nombre) || e.getContrato().contrato == contrato){
                    System.out.println("Se ha cambiado la bandera a falso");
                    flag = false;
                }
            }
        }
        if(flag){
            empresas.add(new EmpresaProveedora(nombre, nit, contrato));
        }
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
