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
import java.util.ArrayList;
import java.util.List;

public class ControladorCrearTarjeta {
    @FXML
    private TextField montoInicialField;

    private PersoIntegrado persoIntegrado;
    private TarjetaManager tarjetaManager;

    public void initialize(){
        persoIntegrado = PersoIntegrado.getInstance();
        tarjetaManager = persoIntegrado.getTarjetaManager();
    }

    // Método para crear tarjeta
    @FXML
    private void crearTarjeta() {
        System.out.println("Creando tarjeta ...");

        // Obtiene el ID asignado como el siguiente número después del tamaño de la lista
        int IDAsignado = tarjetaManager.getTarjetas().size() + 1;

        // Verifica si ya existe una tarjeta con el ID asignado
        boolean tarjetaExistente = tarjetaManager.getTarjetas().stream()
                .anyMatch(tarjeta -> tarjeta.getId() == IDAsignado);

        if (tarjetaExistente) {
            showAlert("Tarjeta", "Tarjeta ya existente", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Obtiene el monto inicial ingresado
            String montoInicialText = montoInicialField.getText();
            int montoInicial = Integer.parseInt(montoInicialText);

            // Crea y agrega la nueva tarjeta
            Tarjeta nuevaTarjeta = new Tarjeta(IDAsignado, montoInicial);
            tarjetaManager.addTarjeta(nuevaTarjeta);

            // Imprime las tarjetas cargadas y guarda los datos
            PersoIntegrado.imprimirTarjetasCargadas();
            PersoIntegrado.saveData();

            // Muestra confirmación
            showAlert("Tarjeta", "Tarjeta creada exitosamente con saldo: " + montoInicialText + " e ID: " + IDAsignado, Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingrese un monto inicial válido.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlMenu(ActionEvent event) {
        try {
            Parent menuUsuarioParent = FXMLLoader.load(getClass().getResource("/org/persointegrado/persointegrado/menuAdministrador.fxml"));
            Scene menuUsuarioScene = new Scene(menuUsuarioParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(menuUsuarioScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
