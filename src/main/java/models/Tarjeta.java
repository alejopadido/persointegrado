package models;

import java.util.ArrayList;
import java.util.List;

public class Tarjeta {
    private String id;
    private double saldo;
    private List<TransaccionPasaje> transacciones;

    public Tarjeta(String id, double saldoInicial) {
        this.id = id;
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public void realizarTransaccion(TransaccionPasaje transaccion) {
        if (saldo >= transaccion.getMonto()) {
            saldo -= transaccion.getMonto();
            transacciones.add(transaccion);
        } else {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    public String getId() {
        return id;
    }
}
