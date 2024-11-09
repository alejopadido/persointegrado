package models;

public class EmpresaProveedoraException extends Exception {

    private String detalle;

    public EmpresaProveedoraException(String message) {
        this.detalle = message;
    }

    @Override
    public String toString() {
        return "EmpresaProveedoraException{" +
                "detalle='" + detalle + '\'' +
                '}';
    }
}