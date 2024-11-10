package models;

import java.io.Serializable;
import java.time.LocalTime;

public class Turno implements Serializable {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String conductor;

    public Turno(LocalTime horaInicio, LocalTime horaFin, String conductor) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.conductor = conductor;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }


}
