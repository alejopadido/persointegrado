package utils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class util {

    public static LocalTime integersToTimes(int hora, int minutos) {
        LocalTime horaFormateada = LocalTime.of(hora,minutos);
        return horaFormateada;
    }

    public static List<String> listaConductores(String s){
        String[] nombres = s.split(",");
        return new ArrayList<String>(Arrays.asList(nombres));
    }
}
