package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Bus;
import models.PersoIntegrado;
import models.Ruta;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Collectors;

public class PasajeController {

    @FXML
    private ComboBox<String> rutasComboBox; // ComboBox para seleccionar el nombre de la ruta

    @FXML
    private ComboBox<Bus> busesComboBox; // ComboBox para seleccionar el bus

    @FXML
    private ComboBox<String> tipoUsuarioComboBox; // ComboBox para seleccionar el tipo de usuario

    @FXML
    private TextField tarjetaField;

    @FXML
    private TextField horaField;

    @FXML
    private Label montoLabel;

    private PersoIntegrado persoIntegrado;

    @FXML
    public void initialize() {
        persoIntegrado = PersoIntegrado.getInstance();

        // Inicializar ComboBox con los nombres de las rutas disponibles
        ObservableList<String> rutasNombres = FXCollections.observableArrayList(
                persoIntegrado.getRutas().stream()
                        .map(Ruta::getNombre)
                        .collect(Collectors.toList())
        );
        rutasComboBox.setItems(rutasNombres);


        // Configurar el ComboBox de buses para mostrar solo ID y placa
        busesComboBox.setConverter(new StringConverter<Bus>() {
            @Override
            public String toString(Bus bus) {
                return bus == null ? "" : "ID: " + bus.getId() + " - Placa: " + bus.getPlaca();
            }

            @Override
            public Bus fromString(String string) {
                return null; // No se necesita conversión inversa
            }
        });

        // Poblar el ComboBox de tipo de usuario con las opciones
        tipoUsuarioComboBox.getItems().addAll("Mayor de edad", "Discapacitado", "Estudiante", "Usuario Normal");
        tipoUsuarioComboBox.setOnAction(event -> calcularMonto());
        horaField.setOnKeyReleased(event -> calcularMonto());

        // Configurar el evento de selección de ruta
        rutasComboBox.setOnAction(event -> onRutaSeleccionada());
    }

    private void onRutaSeleccionada(){
        String nombreRutaSeleccionada = rutasComboBox.getValue();
        if (nombreRutaSeleccionada != null) {
            Optional<Ruta> rutaSeleccionada = persoIntegrado.getRutas().stream()
                    .filter(ruta -> ruta.getNombre().equals(nombreRutaSeleccionada))
                    .findFirst();

            rutaSeleccionada.ifPresent(this::actualizarBuses);
        }
    }

    /**
     * Método para actualizar el ComboBox de buses en función de la ruta seleccionada.
     */
    private void actualizarBuses(Ruta ruta) {
        busesComboBox.getItems().clear(); // Limpiar los buses existentes en el ComboBox
        busesComboBox.getItems().addAll(ruta.getBuses()); // Añadir los buses de la ruta seleccionada
    }

    @FXML
    private void pagarPasaje() {
        // Obtener el nombre de la ruta seleccionada, el bus seleccionado, y el tipo de usuario
        String nombreRutaSeleccionada = rutasComboBox.getValue();
        Bus busSeleccionado = busesComboBox.getValue();
        String tipoUsuario = tipoUsuarioComboBox.getValue();

        if (nombreRutaSeleccionada == null) {
            showAlert("Error", "Por favor seleccione una ruta.");
            return;
        }

        if (busSeleccionado == null) {
            showAlert("Error", "Por favor seleccione un bus.");
            return;
        }

        if (tipoUsuario == null) {
            showAlert("Error", "Por favor seleccione el tipo de usuario.");
            return;
        }

        // Lógica para procesar el pago del pasaje, puedes incluir descuentos según el tipo de usuario
        showAlert("Pago Exitoso", "El pasaje ha sido cobrado con éxito en la ruta " + nombreRutaSeleccionada +
                " usando el bus " + busSeleccionado.getId() + " para un usuario de tipo " + tipoUsuario);
    }

    private void calcularMonto() {
        String tipoUsuario = tipoUsuarioComboBox.getValue();
        String horaText = horaField.getText();
        int monto = 0;

        if (tipoUsuario == null || horaText.isEmpty()) {
            montoLabel.setText("Seleccione tipo de usuario y hora");
            return;
        }

        try {
            LocalTime hora = LocalTime.parse(horaText, DateTimeFormatter.ofPattern("HH:mm"));
            boolean esHoraValle = (hora.isBefore(LocalTime.of(5, 30)) ||
                    (hora.isAfter(LocalTime.of(8, 29)) && hora.isBefore(LocalTime.of(16, 30))) ||
                    hora.isAfter(LocalTime.of(19, 30)));

            switch (tipoUsuario) {
                case "Estudiante": monto = 1400; break;
                case "Mayor de edad": monto = 1250; break;
                case "Discapacitado": monto = 1300; break;
                case "Usuario Normal": monto = esHoraValle ? 1400 : 1700; break;
            }
            montoLabel.setText("$" + monto);
        } catch (DateTimeParseException e) {
            montoLabel.setText("Hora inválida");
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
