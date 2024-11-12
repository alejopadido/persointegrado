package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.PersoIntegrado;
import models.Tarjeta;
import models.TarjetaException;
import models.TarjetaManager;

import java.io.IOException;

public class RecargarTarjetaController {

    @FXML
    private TextField idTarjetaField;

    @FXML
    private TextField montoRecargaField;

    private TarjetaManager tarjetaManager;

    public void initialize() {
        // Obtain the instance of TarjetaManager from PersoIntegrado
        tarjetaManager = PersoIntegrado.getInstance().getTarjetaManager();
    }

    // Method to handle recharge
    @FXML
    private void recargarTarjeta() {
        try {
            String idTarjetaText = idTarjetaField.getText();
            String montoRecargaText = montoRecargaField.getText();

            // Validate the ID and recharge amount
            if (idTarjetaText.isEmpty() || montoRecargaText.isEmpty()) {
                showAlert("Error", "Por favor complete todos los campos.", Alert.AlertType.WARNING);
                return;
            }

            int idTarjeta = Integer.parseInt(idTarjetaText);
            double montoRecarga = Double.parseDouble(montoRecargaText);

            if (montoRecarga <= 0) {
                showAlert("Error", "El monto de la recarga debe ser mayor a 0", Alert.AlertType.WARNING);
                return;
            }

            // Find the tarjeta and recharge it
            Tarjeta tarjeta = tarjetaManager.obtenerTarjetaConID(idTarjeta);
            if (tarjeta == null) {
                showAlert("Error", "La tarjeta con ID " + idTarjeta + " no existe.", Alert.AlertType.ERROR);
                return;
            }

            tarjeta.setSaldo(tarjeta.getSaldo() + montoRecarga);
            showAlert("Recarga Exitosa", "La tarjeta con ID " + idTarjeta + " ha sido recargada con éxito. Nuevo saldo: $" + tarjeta.getSaldo(), Alert.AlertType.INFORMATION);

            // Save data after recharging
            PersoIntegrado.saveData();

        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingrese un ID de tarjeta y un monto válidos.", Alert.AlertType.ERROR);
        }
    }

    // Method to return to the main menu
    @FXML
    private void volverAlMenu(ActionEvent event) {
        try {
            Parent menuUsuarioParent = FXMLLoader.load(getClass().getResource("/org/persointegrado/persointegrado/menuUsuario.fxml"));
            Scene menuUsuarioScene = new Scene(menuUsuarioParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(menuUsuarioScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
