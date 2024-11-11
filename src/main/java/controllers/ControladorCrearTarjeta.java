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
    private void crearTarjeta() throws TarjetaException {

        System.out.println("ASJHFASKILDJASDASJKLDSA");
        int IDAsignado = 1;

        for (Tarjeta tar: tarjetaManager.getTarjetas()) {
            if (tar.getId() == IDAsignado) {
                throw new TarjetaException("Tarjeta ya existente", Alert.AlertType.WARNING);
            }else{

            }
        }

        String montoInicialText = montoInicialField.getText();
        Tarjeta nuevaTarjeta = new Tarjeta(1, Integer.parseInt(montoInicialText));
        tarjetaManager.addTarjeta(nuevaTarjeta);
        PersoIntegrado.imprimirTarjetasCargadas();
        PersoIntegrado.saveData();
        showAlert("Tarjeta", "Tarjeta creada exitosamente con saldo: " + montoInicialText + " con ID: " + IDAsignado, Alert.AlertType.INFORMATION);



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
