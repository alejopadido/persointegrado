package models;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import controllers.ControladorCrearContrato;
import utils.manejadorArchivos;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersoIntegrado implements Serializable {
    private static final long serialVersionUID = 1L; // Version de serializacion

    private static PersoIntegrado instance;
    List<EmpresaProveedora> empresas;
    List<Ruta> rutas;
    private int max;
    private transient ParaderoManager paraderoManager;

    public PersoIntegrado() {
        empresas = new ArrayList<EmpresaProveedora>();
        rutas = new ArrayList<Ruta>();
        paraderoManager = ParaderoManager.getInstance();

    }

    /**
     * Uso del patron singleton para garantizar que todas los controladores interactuan con la misma instancia
     * de perso integrado
     * @return
     */
    public static PersoIntegrado getInstance() {
        if (instance == null) {
            // Intentar cargar datos si existen
            instance = loadData();
            if (instance == null) {
                // Si no hay datos guardados, crear una nueva instancia
                instance = new PersoIntegrado();
                System.out.println("No se encontraron datos guardados. Se creó una nueva instancia.");
            } else {
                instance.initializeTransientFields();
                System.out.println("Datos cargados exitosamente.");
                imprimirRutasCargadas();
            }
        }
        return instance;
    }

    private void initializeTransientFields() {
        if (paraderoManager == null) {
            paraderoManager = ParaderoManager.getInstance();
        }

        // Sincronizar todos los paraderos de las rutas con el ParaderoManager
        for (Ruta ruta : rutas) {
            System.out.println("A continuacion los paraderos de la ruta:" + ruta.getParaderos());
            for (Paradero paradero : ruta.getParaderos()) {
                paraderoManager.obtenerParadero(paradero.getNombre(), paradero.getDireccion(), paradero.getHoraDeLlegada().getHora());
            }
        }
    }

    // Metodo para obtener el ParaderoManager
    public ParaderoManager getParaderoManager() {
        if (paraderoManager == null) {
            paraderoManager = ParaderoManager.getInstance();
        }
        return paraderoManager;
    }

    // Método para imprimir las rutas que se han cargado
    private static void imprimirRutasCargadas() {
        if (instance != null && !instance.getRutas().isEmpty()) {
            System.out.println("Las siguientes rutas han sido cargadas:");
            for (Ruta ruta : instance.getRutas()) {
                System.out.println(ruta);
            }
        } else {
            System.out.println("No se han encontrado rutas guardadas.");
        }
    }


    // Método para guardar los datos en un archivo
    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("persoIntegradoData.ser"))) {
            oos.writeObject(instance);
            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar los datos desde un archivo
    public static PersoIntegrado loadData() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("persoIntegradoData.ser"))) {
            return (PersoIntegrado) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró un archivo de datos guardados. Creando nueva instancia...");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarContratoTXT(){
        utils.manejadorArchivos.leerArchivoContratos("contratos.txt");
    }

    public void cargarContratoJSON(){
        utils.manejadorArchivos.leerContratoJson("contratos.json");
    }

    public void cargarContratoCSV(){
        utils.manejadorArchivos.leerContratoCsv("contratos.csv");
    }

    public void leerRutas(){
        utils.manejadorArchivos.lecturaArchivoRutas();
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
    public void crearEmpresa(EmpresaProveedora em) throws PersoIntegradoException, EmpresaProveedoraException, ContratoProveedorException {
        boolean flag = true;
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNit() == em.getNit() || e.getNombre().equalsIgnoreCase(em.getNombre()) || Objects.equals(e.getContrato().getContrato(), em.getContrato().getContrato())){
                    flag = false;
                }
            }
        }
        if(flag){
            empresas.add(new EmpresaProveedora(em));
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

    public void añadirBus(Bus bus) throws BusException, EmpresaProveedoraException {
        for(EmpresaProveedora e: empresas){
            if(e != null){
                if(e.getNombre().equalsIgnoreCase(bus.getEmpresaProveedora())){
                    e.añadirBus(bus);
                }
            }
        }
    }


    public void crearRuta(List<String> conductor, LocalTime inicio, LocalTime fin, LocalTime inicioRuta, LocalTime finRuta, String nombre, int numeroDeRuta, boolean ciclico, int busesDisponibles) throws PersoIntegradoException {

        if (nombre.equalsIgnoreCase("") || numeroDeRuta <= 0 || busesDisponibles <= 0) {
            throw new PersoIntegradoException("No pueden existir nulos o menores a 0");
        } else {
            System.out.println("Verificando validez de buses...");
            int x = verificarValidez(inicio, fin, busesDisponibles);
            System.out.println("Buses válidos: " + x);
            if (x >= busesDisponibles) {
                System.out.println("Obteniendo buses disponibles...");
                List<List<Bus>> buses = busesDisponibles(inicio, fin, conductor, busesDisponibles);
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
        saveData();
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


    public List<List<Bus>> busesDisponibles(LocalTime inicio, LocalTime fin, List<String> conductor, int limite) {
        List<List<Bus>> ls = new ArrayList<>();
        int conta = 0;
        for (EmpresaProveedora e : empresas) {
            if (e != null) {
                if(conta<limite){
                    ls.add(e.busesDisponibles(inicio, fin,conductor.get(conta)));
                    conta++;
                }else{
                    ls.add(e.busesDisponibles(inicio, fin,conductor.get(conta)));
                }

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


    public List<EmpresaProveedora> getEmpresas() {
        return empresas;
    }
    public List<Ruta> getRutas(){
        return rutas;
    }


    /** pagosNominaConductores:
     * Descripcion: Genera un resumen de los pagos realizados por las rutas.
     * Este método recorre todos las rutas asociados y obtiene los detalles
     * de los pagos realizados por cada ruta y sus respectivos autobuses mediante el método
     * `PagosXRuta()` de la clase `Ruta`.
     * @return Una lista de String que contiene el nombre de la ruta, el número y una lista
     * de los pagos realizados por bus asociado de cada una de las rutas en la lista.
     **/
    public List<String> pagosNominaConductores(){
        List<String> pagosNomina = new ArrayList<String>();
        for(Ruta r : rutas){
            if( r != null)
                pagosNomina.add(r.PagosXRuta());
        }
        return pagosNomina;
    }


}
