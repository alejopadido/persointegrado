package utils;

import java.time.LocalTime;

public class util {

    public static LocalTime integersToTimes(int hora, int minutos) {
        LocalTime horaFormateada = LocalTime.of(hora,minutos);
        return horaFormateada;
    }

}
