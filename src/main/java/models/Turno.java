package models;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

public class Turno implements Serializable {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String conductor;
    private double PAGOxHora = 3500;


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

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public double getPAGOxHora() {
        return PAGOxHora;
    }

    public void setPAGOxHora(double PAGOxHora) {
        this.PAGOxHora = PAGOxHora;
    }

    public int calcularDuracionTurno(LocalTime i, LocalTime f){
        Duration duracion = Duration.between(i, f);
        return (int) duracion.toHours();
    }

    public double calcularPago(){
        return calcularDuracionTurno(horaInicio, horaFin) * 3500;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", conductor='" + conductor + '\'' +
                ", PAGOxHora=" + PAGOxHora +
                '}';
    }
}
