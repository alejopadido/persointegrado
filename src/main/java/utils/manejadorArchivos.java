package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<EmpresaProveedora> empresas = objectMapper.readValue(
                    new File(nombre), new TypeReference<List<EmpresaProveedora>>() {}
            );

            // Imprimir los datos leídos en la consola
            for (EmpresaProveedora empresa : empresas) {
                System.out.println("Nombre: " + empresa.getNombre());
                System.out.println("NIT: " + empresa.getNit());
                System.out.println("Contrato: " + (empresa.getContrato() != null ? "Presente" : "No presente"));
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
}
