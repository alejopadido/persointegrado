package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tarjeta implements Serializable {
    private int id;
    private double saldo;
    private List<TransaccionPasaje> transacciones;

    public Tarjeta(int id, double saldoInicial) {
        this.id = id;
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setTransacciones(List<TransaccionPasaje> transacciones) {
        this.transacciones = transacciones;
    }

    public List<TransaccionPasaje> getTransacciones() {
        return transacciones;
    }

    public void realizarTransaccion(TransaccionPasaje transaccion) {
        if (saldo >= transaccion.getMonto()) {
            saldo -= transaccion.getMonto();
            transacciones.add(transaccion);
        } else {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
                "id=" + id +
                ", saldo=" + saldo +
                ", transacciones=" + transacciones +
                '}';
    }
}
