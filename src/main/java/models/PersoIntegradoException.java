package models;

public class PersoIntegradoException extends Exception {

    private String detalle;

    public PersoIntegradoException(String message) {
        this.detalle = message;
    }

    @Override
    public String toString() {
        return "PersoIntegradoException{" +
                "detalle='" + detalle + '\'' +
                '}';
    }
}
