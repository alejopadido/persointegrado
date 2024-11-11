package models;

import controllers.ControladorCrearContrato;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PersoIntegrado implements Serializable {
    private static PersoIntegrado instance;
    List<EmpresaProveedora> empresas;
    List<Ruta> rutas;
    private int max;

    public PersoIntegrado() {
        empresas = new ArrayList<EmpresaProveedora>();
        rutas = new ArrayList<Ruta>();
    }

    public void guardarNumeroMax(int max){
        this.max = max;
    }

    public int recibirMax(){
        return max;
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

    public void crearRuta(String conductor, LocalTime inicio, LocalTime fin, LocalTime inicioRuta, LocalTime finRuta, String nombre, int numeroDeRuta, boolean ciclico, int busesDisponibles) throws PersoIntegradoException {

        if (nombre.equalsIgnoreCase("") || numeroDeRuta <= 0 || busesDisponibles <= 0) {
            throw new PersoIntegradoException("No pueden existir nulos o menores a 0");
        } else {
            System.out.println("Verificando validez de buses...");
            int x = verificarValidez(inicio, fin, busesDisponibles);
            System.out.println("Buses válidos: " + x);
            if (x >= busesDisponibles) {
                System.out.println("Obteniendo buses disponibles...");
                List<List<Bus>> buses = busesDisponibles(inicio, fin, conductor);
                Ruta nuevaRuta = new Ruta(inicioRuta, finRuta, nombre, numeroDeRuta, ciclico, busesDisponibles);
                rutas.add(nuevaRuta);
                System.out.println("Añadiendo buses a la ruta...");
                añadirALaRuta(buses, nombre, busesDisponibles);
            } else {
                throw new PersoIntegradoException("No hay suficientes buses disponibles para esta ruta.");
            }
        }

        for (Ruta e : rutas) {
            System.out.println(e.toString());
        }
    }

    public void imprimirRutas(){
        for(Ruta r: rutas){
            System.out.println(r.toString());
        }
    }


    public void añadirParada(String nombreRuta, String nombreParadero, String direccion, LocalTime llegada) {
        for (Ruta r : rutas) {
            if (r != null && r.getNombre().equalsIgnoreCase(nombreRuta)) {
                r.añadirParadero(nombreParadero, direccion, llegada);
            }
        }

    }


    public int verificarValidez(LocalTime inicio, LocalTime fin, int busesDisponibles) throws PersoIntegradoException {
        int x = 0;
        for(EmpresaProveedora e: empresas){
            if(e != null){
                x += e.verificarValidez(inicio, fin);
            }
        }
        return x;
    }


    public List<List<Bus>> busesDisponibles(LocalTime inicio, LocalTime fin, String conductor) {
        List<List<Bus>> ls = new ArrayList<>();
        for (EmpresaProveedora e : empresas) {
            if (e != null) {
                ls.add(e.busesDisponibles(inicio, fin, conductor));
            }
        }
        return ls;
    }

    public void imprimitListaDeListas(List<List<Bus>> busesAInsertar){
        for(List<Bus> l: busesAInsertar){
            for(Bus b: l){
                System.out.println(b.toString());
            }
        }
    }

    public void añadirALaRuta(List<List<Bus>> busesAInsertar, String nombre, int conta) {
        imprimitListaDeListas(busesAInsertar);
        int x = 0;
        for (List<Bus> l : busesAInsertar) {
            for (Bus b : l) {
                for (Ruta r : rutas) {
                    if (r != null && r.getNombre().equalsIgnoreCase(nombre)) {
                        if(x<conta){
                            r.añadirBus(b);
                            x++;
                        }
                    }
                }
            }
        }
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
    public List<Ruta> getRutas(){
        return rutas;
    }


}
