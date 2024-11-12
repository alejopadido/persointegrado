package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Tarjeta;
import models.TarjetaManager;
import models.TransaccionPasaje;

import java.io.IOException;

public class ConsultarTarjetaController {

    @FXML
    private TextField idTarjetaField;

    @FXML
    private Label saldoLabel;

    @FXML
    private ListView<String> transaccionesListView;

    private TarjetaManager tarjetaManager;

    public void initialize() {
        tarjetaManager = TarjetaManager.getInstance();
    }

    // Método para consultar tarjeta
    @FXML
    private void consultarTarjeta() {
        String idTarjetaText = idTarjetaField.getText();

        if (idTarjetaText.isEmpty()) {
            showAlert("Error", "Por favor ingrese un ID de tarjeta.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int idTarjeta = Integer.parseInt(idTarjetaText);
            Tarjeta tarjeta = tarjetaManager.obtenerTarjetaConID(idTarjeta);

            if (tarjeta == null) {
                showAlert("Error", "No se encontró una tarjeta con el ID proporcionado.", Alert.AlertType.ERROR);
            } else {
                saldoLabel.setText("$" + tarjeta.getSaldo());
                cargarTransacciones(tarjeta);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "El ID de tarjeta debe ser un número válido.", Alert.AlertType.ERROR);
        }
    }

    // Método para cargar las transacciones en la lista
    private void cargarTransacciones(Tarjeta tarjeta) {
        transaccionesListView.getItems().clear();
        transaccionesListView.setItems(FXCollections.observableArrayList(
                tarjeta.getTransacciones().stream()
                        .map(TransaccionPasaje::toString) // Ajusta si tienes un método adecuado para mostrar
                        .toList()
        ));
    }

    // Método para mostrar alertas
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
}
