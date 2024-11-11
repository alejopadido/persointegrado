package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorMenuUsuario {

    @FXML
    private ImageView imgConsultarUnaRuta;

    @FXML
    private ImageView imgPagarPasaje;

    @FXML
    private ImageView imgConsultarRutas;

    @FXML
    void onMouseClickedConsultarRutas(MouseEvent event) {
        try {
            // Cargar la nueva pantalla "ConsultarRutasOptimas.fxml"
            Parent consultarRutasOptimasParent = FXMLLoader.load(getClass().getResource("/org/persointegrado/persointegrado/ConsultarRutasOptimas.fxml"));

            // Obtener el Stage actual desde el evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear una nueva escena y configurarla en el Stage
            Scene scene = new Scene(consultarRutasOptimasParent);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onMouseClickedConsultarUnaRuta(MouseEvent event) {
        try {
            // Cargar la nueva pantalla "ConsultarRuta.fxml" desde la ubicación correcta
            Parent consultarRutaParent = FXMLLoader.load(getClass().getResource("/org/persointegrado/persointegrado/ConsultarRuta.fxml"));

            // Obtener el Stage actual desde el evento
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear una nueva escena y configurarla en el stage
            Scene scene = new Scene(consultarRutaParent);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMouseClickedPagarPasaje(MouseEvent event) {

    }

}
