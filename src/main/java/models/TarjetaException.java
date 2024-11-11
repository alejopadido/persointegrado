package models;

import javafx.scene.control.Alert;

public class TarjetaException extends Exception{

    private String detalle;
    public TarjetaException(String msg, Alert.AlertType tipo){
        detalle = msg;
        showAlert("Tarjeta Exception", msg, tipo);
    }

    private void showAlert(String title, String message, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public String toString() {
        return "TarjetaException{" +
                "detalle='" + detalle + '\'' +
                '}';
    }
}
