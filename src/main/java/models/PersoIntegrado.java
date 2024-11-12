package models;

import controllers.ControladorCrearContrato;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PersoIntegrado implements Serializable {
    private static final long serialVersionUID = 1L; // Version de serializacion

    private static PersoIntegrado instance;
    List<EmpresaProveedora> empresas;
    List<Ruta> rutas;
    protected List<Tarjeta> tarjetas;
    private int max;
    private transient ParaderoManager paraderoManager;
    private transient TarjetaManager tarjetaManager;

    public PersoIntegrado() {
        empresas = new ArrayList<EmpresaProveedora>();
        rutas = new ArrayList<Ruta>();
        paraderoManager = ParaderoManager.getInstance();
        tarjetaManager = TarjetaManager.getInstance();
        tarjetas = new ArrayList<Tarjeta>();
    }

    public static void actualizarListaTarjetas(){
        instance.tarjetas = instance.tarjetaManager.getTarjetas();
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
                imprimirTarjetasCargadas();
            }
        }
        return instance;
    }

    private void initializeTransientFields() {
        if (paraderoManager == null) {
            paraderoManager = ParaderoManager.getInstance();
        }

        if (tarjetaManager == null) {
            tarjetaManager = TarjetaManager.getInstance();
        }

        // Sincronizar todos los paraderos de las rutas con el ParaderoManager
        for (Ruta ruta : rutas) {
            System.out.println("A continuacion los paraderos de la ruta:" + ruta.getParaderos());
            for (Paradero paradero : ruta.getParaderos()) {
                paraderoManager.obtenerParadero(paradero.getNombre(), paradero.getDireccion(), paradero.getHoraDeLlegada().getHora());
            }
        }

        if (instance.tarjetas != null){
            tarjetaManager.setTarjetas(instance.tarjetas);
        }
    }

    // Método para obtener el ParaderoManager
    public ParaderoManager getParaderoManager() {
        if (paraderoManager == null) {
            paraderoManager = ParaderoManager.getInstance();
        }
        return paraderoManager;
    }

    public TarjetaManager getTarjetaManager() {
        if (tarjetaManager == null) {
            tarjetaManager = TarjetaManager.getInstance();
        }
        return tarjetaManager;
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

    public static void imprimirTarjetasCargadas() {
        if (instance != null){
            List<Tarjeta> tarjetas = instance.getTarjetaManager().getTarjetas();
            if (tarjetas.isEmpty()) {
                System.out.println("No hay tarjetas registradas.");
            } else {
                System.out.println("Tarjetas registradas:");
                for (Tarjeta tarjeta : tarjetas) {
                   // System.out.println("ID: " + tarjeta.getId() + ", Saldo: $" + tarjeta.getSaldo());
                    System.out.println(tarjeta);
                }
            }
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
