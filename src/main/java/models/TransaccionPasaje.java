package models;

import java.time.LocalDateTime;

public class TransaccionPasaje {
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
}
