package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.ParaderoManager;
import models.PersoIntegrado;
import models.Ruta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultarRutaOptimaController {

    @FXML
    private ComboBox<String> origenComboBox;

    @FXML
    private ComboBox<String> destinoComboBox;

    @FXML
    private Button buscarRutaButton;

    @FXML
    private ListView<String> rutasListView;

    @FXML
    private Button volverButton;

    private PersoIntegrado persoIntegrado;

    public void initialize() {
        // Obtener la instancia de PersoIntegrado
        persoIntegrado = PersoIntegrado.getInstance();

        // Obtener el ParaderoManager a través de PersoIntegrado
        ParaderoManager paraderoManager = persoIntegrado.getParaderoManager();

        // Depuración: Verificar el contenido de los paraderos
        if (paraderoManager.obtenerTodosLosParaderos().isEmpty()) {
            System.out.println("No se encontraron paraderos al inicializar el controlador.");
        } else {
            System.out.println("Paraderos disponibles:");
            paraderoManager.obtenerTodosLosParaderos().forEach((nombre, paradero) -> {
                System.out.println("Paradero: " + nombre + ", Dirección: " + paradero.getDireccion());
            });
        }

        // Inicializar los ComboBox con todos los nombres de paraderos disponibles
        List<String> paraderosNombres = new ArrayList<>(paraderoManager.obtenerTodosLosParaderos().keySet());
        ObservableList<String> paraderosList = FXCollections.observableArrayList(paraderosNombres);
        origenComboBox.setItems(paraderosList);
        destinoComboBox.setItems(paraderosList);

        // Configurar el evento del botón
        buscarRutaButton.setOnAction(event -> buscarRutas());
        volverButton.setOnAction(event -> volverAlMenu(event));
    }

    private void buscarRutas() {
        String origenNombre = origenComboBox.getValue();
        String destinoNombre = destinoComboBox.getValue();

        if (origenNombre == null || destinoNombre == null || origenNombre.equals(destinoNombre)) {
            rutasListView.setItems(FXCollections.observableArrayList("Seleccione origen y destino válidos."));
            return;
        }

        List<Ruta> rutasConAmbosParaderos = persoIntegrado.getRutas().stream()
                .filter(ruta -> ruta.tieneParadero(origenNombre) && ruta.tieneParadero(destinoNombre))
                .sorted(Comparator.comparingInt(ruta -> ruta.contarParaderosEntre(origenNombre, destinoNombre)))
                .collect(Collectors.toList());

        if (rutasConAmbosParaderos.isEmpty()) {
            rutasListView.setItems(FXCollections.observableArrayList("No se encontraron rutas que conecten esos paraderos."));
        } else {
            List<String> rutasInfo = rutasConAmbosParaderos.stream()
                    .map(Ruta::toString)
                    .collect(Collectors.toList());
            rutasListView.setItems(FXCollections.observableArrayList(rutasInfo));
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
}
