package models;

import java.io.Serializable;
import java.time.LocalTime;

public class HoraDeLlegada implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public LocalTime getHora() {
        return hora;
    }
}

