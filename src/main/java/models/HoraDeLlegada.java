package models;

import java.io.Serializable;
import java.time.LocalTime;

public class HoraDeLlegada implements Serializable {
    LocalTime hora;

    public  HoraDeLlegada(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "HoraDeLlegada{" +
                "hora=" + hora +
                '}';
    }
}

