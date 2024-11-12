package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransaccionPasaje implements Serializable {
    private double monto;
    private Bus bus;
    private Ruta ruta;
    private LocalDateTime fechaHora;

    public TransaccionPasaje(double monto, Bus bus, Ruta ruta, LocalDateTime fechaHora) {
        this.monto = monto;
        this.bus = bus;
        this.ruta = ruta;
        this.fechaHora = fechaHora;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "TransaccionPasaje{" +
                "monto=" + monto +
                ", busPlaca=" + bus.getPlaca() +
                ", rutaNombre=" + ruta.getNombre() +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
