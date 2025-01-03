package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Paradero;
import models.PersoIntegrado;
import models.Ruta;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultarRutaController {

    @FXML
    private ComboBox<String> rutaComboBox;
    @FXML
    private Button consultarParaderosButton;
    @FXML
    private ListView<String> paraderosListView;
    @FXML
    private TextField horaInicioField;
    @FXML
    private TextField horaFinField;
    @FXML
    private Button consultarHorariosButton;
    @FXML
    private ListView<String> horariosListView;
    @FXML
    private Button volverButton;

    private PersoIntegrado persoIntegrado;

    public void initialize(){
        persoIntegrado = PersoIntegrado.getInstance();

        // Inicializar ComboBox con las rutas disponibles
        ObservableList<String> rutasNombres = FXCollections.observableArrayList(
                persoIntegrado.getRutas().stream()
                        .map(Ruta::getNombre)
                        .collect(Collectors.toList())
        );
        rutaComboBox.setItems(rutasNombres);

        // Configurar eventos para los botones
        consultarParaderosButton.setOnAction(event -> consultarParaderos());
        consultarHorariosButton.setOnAction(event -> consultarHorarios());
        volverButton.setOnAction(event -> volverAlMenu(event));
    }

    private void consultarParaderos() {
        String nombreRuta = rutaComboBox.getValue();
        Ruta rutaSeleccionada = persoIntegrado.getRutas().stream()
                .filter(ruta -> ruta.getNombre().equals(nombreRuta))
                .findFirst()
                .orElse(null);

        if (rutaSeleccionada != null) {
            List<String> paraderos = rutaSeleccionada.getParaderos().stream()
                    .map(Paradero::toString)
                    .collect(Collectors.toList());
            paraderosListView.setItems(FXCollections.observableArrayList(paraderos));
        } else {
            showAlert("Error", "Ruta no encontrada");
        }
    }

    private void consultarHorarios() {
        String nombreRuta = rutaComboBox.getValue();
        Ruta rutaSeleccionada = persoIntegrado.getRutas().stream()
                .filter(ruta -> ruta.getNombre().equals(nombreRuta))
                .findFirst()
                .orElse(null);

        if (rutaSeleccionada != null) {
            try {
                LocalTime horaInicio = LocalTime.parse(horaInicioField.getText());
                LocalTime horaFin = LocalTime.parse(horaFinField.getText());

                List<String> horarios = rutaSeleccionada.getParaderos().stream()
                        .filter(paradero -> paradero.getHoraDeLlegada().getHora().isAfter(horaInicio) &&
                                paradero.getHoraDeLlegada().getHora().isBefore(horaFin))
                        .map(Paradero::toString)
                        .collect(Collectors.toList());

                horariosListView.setItems(FXCollections.observableArrayList(horarios));
            } catch (Exception e) {
                showAlert("Error", "Formato de hora incorrecto. Use HH:mm");
            }
        } else {
            showAlert("Error", "Ruta no encontrada");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
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
