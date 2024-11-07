package models;

public class ContratoProveedorException extends Exception {

    private String detalle;

    public ContratoProveedorException(String message) {
        this.detalle = message;
    }

    @Override
    public String toString() {
        return "ContratoProveedorException{" +
                "detalle='" + detalle + '\'' +
                '}';
    }
}