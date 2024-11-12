package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import models.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

public class manejadorArchivos {
    static PersoIntegrado perso = PersoIntegrado.getInstance();
    public static void reporteNominaConductores(List<String> pagosNomina){
        try {
            OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream("ReporteNominaConductores.txt"));
            for(int i = 0 ; i<pagosNomina.size() ; i++)
                if (pagosNomina.get(i) != null)
                    out.write(pagosNomina.get(i));
            out.close();

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al generar el archivo: " + e );
        } catch (Exception e) {
            System.out.println("Ha ocurrido otro error en la creación del reporte: " + e );
        }
    }

    public static void leerArchivoContratos(String archivo){
        try{
            InputStreamReader input = new InputStreamReader(new FileInputStream(archivo));
            BufferedReader br = new BufferedReader(input);
            String linea;
            int numeroEmpresas = Integer.parseInt(br.readLine()); // Lee el número de empresas
            System.out.println("Número de empresas: " + numeroEmpresas);

            for (int i = 0; i < numeroEmpresas; i++) {
                String nombreEmpresa = br.readLine();
                int nit = Integer.parseInt(br.readLine());
                String codigoContrato = br.readLine();
                int cantidadBuses = Integer.parseInt(br.readLine());
                perso.crearEmpresa(nombreEmpresa,nit,codigoContrato);
                System.out.println("Se adiciono empresa");
                for (int j = 0; j < cantidadBuses; j++) {
                    linea = br.readLine();
                    String[] datosBus = linea.split(" ");
                    String placa = datosBus[0];
                    String modelo = datosBus[1];
                    perso.añadirBus(nombreEmpresa,placa,modelo);
                    System.out.println("Bus " + (j + 1) + ": " + placa + " - Categoría: " + modelo);
                }
                utils.sendEmail.enviarCorreo(perso.getEmpresas().get(perso.getEmpresas().size()-1));
            }
        } catch (IOException e ) {
            System.out.println("Error abriendo el archivo");
        } catch (EmpresaProveedoraException | PersoIntegradoException | ContratoProveedorException | BusException e) {
            e.printStackTrace();
        }

    }

    public static void leerContratoJson(String nombre){
        // Cambia esto a la ruta de tu archivo JSON

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el archivo JSON y convertirlo en una lista de objetos EmpresaProveedora
            String jsonContent = new String(Files.readAllBytes(Paths.get(nombre)));
            List<EmpresaProveedora> empresas = objectMapper.readValue(jsonContent, objectMapper.getTypeFactory().constructCollectionType(List.class, EmpresaProveedora.class));

            // Imprimir los datos leídos en la consola
            for (EmpresaProveedora empresa : empresas) {
                System.out.println("Nombre: " + empresa.getNombre());
                System.out.println("NIT: " + empresa.getNit());
                System.out.println("Contrato: " + (empresa.getContrato()));
                System.out.println("Buses:");
                perso.crearEmpresa(empresa);
                for (Bus bus : empresa.getBuses()) {
                    perso.añadirBus(bus);
                }
                utils.sendEmail.enviarCorreo(perso.getEmpresas().get(perso.getEmpresas().size()-1));
            }
        } catch (EmpresaProveedoraException | PersoIntegradoException | ContratoProveedorException | IOException | BusException e) {
            System.err.println("Error de formato en el archivo JSON: " + e.getMessage());
        }
    }

    public static void leerContratoCsv(String name){
        try {
            CSVReader reader = new CSVReader(new FileReader(name));
            List<String[]> lineas = reader.readAll();
            reader.close();

            Map<String, EmpresaProveedora> empresasMap = new HashMap<>();

            for (int i = 1; i < lineas.size(); i++) {
                String[] linea = lineas.get(i);
                String nombre = linea[0];
                String nit = linea[1];
                String contrato = linea[2];
                String placa = linea[3];
                String modelo = linea[4];

                if (!empresasMap.containsKey(nombre)) {
                    EmpresaProveedora empresa = new EmpresaProveedora();
                    empresa.setNombre(nombre);
                    empresa.setNit(Integer.parseInt(nit));
                    empresa.setContrato(new ContratoProveedor(contrato));
                    empresa.setBuses(new ArrayList<>());
                    empresasMap.put(nombre, empresa);
                }

                Bus bus = new Bus();
                bus.setPlaca(placa);
                bus.setModelo(modelo);
                empresasMap.get(nombre).getBuses().add(bus);
            }

            for (EmpresaProveedora empresa : empresasMap.values()) {
                System.out.println("Empresa: " + empresa.getNombre());
                System.out.println("NIT: " + empresa.getNit());
                System.out.println("Contrato: " + empresa.getContrato());
                System.out.println("Vehículos:");
                perso.crearEmpresa(empresa.getNombre(),empresa.getNit(),empresa.getContrato2());
                for (Bus vehiculo : empresa.getBuses()) {
                    perso.añadirBus(vehiculo);
                    System.out.println("  Placa: " + vehiculo.getPlaca() + ", Modelo: " + vehiculo.getModelo());
                }
                utils.sendEmail.enviarCorreo(perso.getEmpresas().get(perso.getEmpresas().size()-1));
                System.out.println();
            }

        }catch (IOException | CsvException | ContratoProveedorException | EmpresaProveedoraException | PersoIntegradoException | BusException e) {
            e.printStackTrace();
        }
    }


    public static void lecturaArchivoRutas(){
        String cadena, nombreConductor, nombreRut;
        int numRut, busesDispo, HoraIn, minutosIn, HoraFin, minutosFin;
        LocalTime horaInicio, horaFin;
        boolean ciclico;
        Turno t;
        Ruta rut;


        try {
            InputStreamReader input=new InputStreamReader(new FileInputStream("D:\\ISIST\\persointegrado\\Ruta.txt"));
            BufferedReader fa=new BufferedReader(input);
            cadena=fa.readLine();
            while(!cadena.equalsIgnoreCase("#FIN"))
            {
                if(cadena.equalsIgnoreCase("#Turno"))
                {
                    fa.readLine(); //Para saltarse la fila de títulos
                    cadena = fa.readLine(); //Lee siguiente línea para obtener el nombre del código
                    Scanner separada = new Scanner(cadena).useDelimiter(", ");

                    nombreConductor = separada.next();
                    HoraIn = Integer.parseInt(separada.next());
                    minutosIn = Integer.parseInt(separada.next());
                    //Convertir de entero a LocalTime
                    horaInicio = util.integersToTimes(HoraIn, minutosIn);

                    HoraFin = Integer.parseInt(separada.next());
                    minutosFin = Integer.parseInt(separada.next());
                    horaFin = util.integersToTimes(HoraFin, minutosFin);

                    t = new Turno(horaInicio, horaFin, nombreConductor);
                    System.out.println(t);

                    cadena = fa.readLine();
                }
                else if (cadena.equalsIgnoreCase("#Turno"))
                {
                    cadena = fa.readLine();

                    Scanner separada = new Scanner(cadena).useDelimiter(", ");
                    HoraIn = Integer.parseInt(separada.next());
                    minutosIn = Integer.parseInt(separada.next());
                    //Convertir de entero a LocalTime
                    horaInicio = util.integersToTimes(HoraIn, minutosIn);

                    HoraFin = Integer.parseInt(separada.next());
                    minutosFin = Integer.parseInt(separada.next());
                    horaFin = util.integersToTimes(HoraFin, minutosFin);

                    nombreRut = separada.next();
                    numRut = Integer.parseInt(separada.next());

                    ciclico = Boolean.parseBoolean(separada.next());
                    busesDispo = Integer.parseInt(separada.next());

                    rut = new Ruta (horaInicio, horaFin, nombreRut, numRut, ciclico, busesDispo);
                    System.out.println(rut);
                }

                cadena=fa.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo al que se refiere no fue encontrado: "+e);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error:"+e);
        }
    }

    public List<String[]> leerArchivoCSVNomina(String rutaArchivo) {
        List<String[]> datos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                datos.add(valores);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }
}
