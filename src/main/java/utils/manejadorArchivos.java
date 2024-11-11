package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class manejadorArchivos {
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
}
