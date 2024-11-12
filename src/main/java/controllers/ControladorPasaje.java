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
import models.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorPasaje {

    @FXML
    private ComboBox<String> rutasComboBox;

    @FXML
    private ComboBox<Bus> busesComboBox;

    @FXML
    private ComboBox<String> tipoUsuarioComboBox;

    @FXML
    private TextField tarjetaField;

    @FXML
    private TextField horaField;

    @FXML
    private Label montoLabel;

    private PersoIntegrado persoIntegrado;
    private TarjetaManager tarjetaManager;

    @FXML
    public void initialize() {
        persoIntegrado = PersoIntegrado.getInstance();
        tarjetaManager = persoIntegrado.getTarjetaManager(); // Obtiene la instancia de TarjetaManager

        // Inicializa rutasComboBox con los nombres de rutas disponibles
        ObservableList<String> rutasNombres = FXCollections.observableArrayList(
                persoIntegrado.getRutas().stream()
                        .map(Ruta::getNombre)
                        .collect(Collectors.toList())
        );
        rutasComboBox.setItems(rutasNombres);

        // Configura busesComboBox para mostrar solo ID y placa
        busesComboBox.setConverter(new StringConverter<Bus>() {
            @Override
            public String toString(Bus bus) {
                return bus == null ? "" : "ID: " + bus.getId() + " - Placa: " + bus.getPlaca();
            }

            @Override
            public Bus fromString(String string) {
                return null;
            }
        });

        // Poblar el ComboBox de tipo de usuario usando TIPOS_USUARIO_TARIFAS de TarjetaManager
        tipoUsuarioComboBox.getItems().addAll(tarjetaManager.getTIPOS_USUARIO_TARIFAS().keySet());
        tipoUsuarioComboBox.setOnAction(event -> calcularMonto());
        horaField.setOnKeyReleased(event -> calcularMonto());

        // Configurar el evento de selección de ruta
        rutasComboBox.setOnAction(event -> onRutaSeleccionada());
    }

    private void onRutaSeleccionada() {
        String nombreRutaSeleccionada = rutasComboBox.getValue();
        if (nombreRutaSeleccionada != null) {
            Optional<Ruta> rutaSeleccionada = persoIntegrado.getRutas().stream()
                    .filter(ruta -> ruta.getNombre().equals(nombreRutaSeleccionada))
                    .findFirst();

            rutaSeleccionada.ifPresent(this::actualizarBuses);
        }
    }

    private void actualizarBuses(Ruta ruta) {
        busesComboBox.getItems().clear();
        busesComboBox.getItems().addAll(ruta.getBuses());
    }

    @FXML
    private void pagarPasaje() {
        String nombreRutaSeleccionada = rutasComboBox.getValue();
        Bus busSeleccionado = busesComboBox.getValue();
        String tipoUsuario = tipoUsuarioComboBox.getValue();
        Tarjeta tarjetaSelecc = tarjetaManager.obtenerTarjetaConID(Integer.parseInt(tarjetaField.getText()));

        if (nombreRutaSeleccionada == null) {
            showAlert("Error", "Por favor seleccione una ruta.");
            return;
        }

        // Retrieve the selected Ruta object
        Optional<Ruta> rutaSeleccionada = persoIntegrado.getRutas().stream()
                .filter(ruta -> ruta.getNombre().equals(nombreRutaSeleccionada))
                .findFirst();

        if (rutaSeleccionada.isEmpty()) {
            showAlert("Error", "Ruta no encontrada.");
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

        if (tarjetaSelecc == null){
            showAlert("Error", "Tarjeta no encontrada");
            return;
        }

        double monto = calcularMonto();
        String horaText = horaField.getText();
        // Parse the time from the user's input
        LocalTime hora = LocalTime.parse(horaText, DateTimeFormatter.ofPattern("HH:mm"));

        // Get the current date from the system
        LocalDate currentDate = LocalDate.now();

        // Combine the current date with the parsed time
        LocalDateTime fechaHora = LocalDateTime.of(currentDate, hora);

        if ((tarjetaSelecc.getSaldo() - monto) < 0) {
            showAlert("Warning", "Saldo Insuficiente");
            return;
        } else {
            // Pass the actual Ruta object instead of just its name
            TransaccionPasaje trans = new TransaccionPasaje(monto, busSeleccionado, rutaSeleccionada.get(), fechaHora);
            tarjetaSelecc.realizarTransaccion(trans);
        }

        showAlert("Pago Exitoso", "El pasaje ha sido cobrado con éxito en la ruta " + nombreRutaSeleccionada +
                " usando el bus " + busSeleccionado.getId() + " para un usuario de tipo " + tipoUsuario + " monto cobrado: " + monto + " Saldo restante: " + tarjetaSelecc.getSaldo());
    }


    private double calcularMonto() {
        String tipoUsuario = tipoUsuarioComboBox.getValue();
        String horaText = horaField.getText();

        if (tipoUsuario == null || horaText.isEmpty()) {
            montoLabel.setText("Seleccione tipo de usuario y hora");
            montoLabel.setStyle("-fx-text-fill: #ff9900;");
            return 0;
        }

        try {
            LocalTime hora = LocalTime.parse(horaText, DateTimeFormatter.ofPattern("HH:mm"));

            // Definimos las horas valle de acuerdo a los criterios mencionados.
            boolean esHoraValle = (hora.isBefore(LocalTime.of(5, 30)) ||
                    (hora.isAfter(LocalTime.of(8, 29)) && hora.isBefore(LocalTime.of(16, 30))) ||
                    hora.isAfter(LocalTime.of(19, 30)));

            // Usar TarjetaManager para obtener la tarifa según el tipo de usuario y si es hora valle
            double monto = tarjetaManager.getTarifa(tipoUsuario);
            if (esHoraValle && tipoUsuario.equalsIgnoreCase("Corriente")){
                monto -= 300;
            }
            montoLabel.setText("$" + monto);
            montoLabel.setStyle("-fx-text-fill: #02ff24;");
            return monto;
        } catch (DateTimeParseException e) {
            montoLabel.setText("Hora inválida");
            montoLabel.setStyle("-fx-text-fill: red;");
        }
        return 0;
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
