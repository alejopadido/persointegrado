package models;

public class BusException extends Exception {

  private String detalle;

  public BusException(String message) {
    this.detalle = message;
  }

  @Override
  public String toString() {
    return "BusException{" +
            "detalle='" + detalle + '\'' +
            '}';
  }
}