package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.persointegrado.persointegrado.MainApplication;

import java.io.IOException;

public class ControladorMenuAdministrador {

    @FXML
    private ImageView imgConsultarUnaRuta;

    @FXML
    private ImageView imgPagarNomina;

    @FXML
    private ImageView imgPagarPasaje;

    @FXML
    private ImageView imgReporteAProveedores;

    @FXML
    private ImageView imgCrearUnaRuta;

    @FXML
    private ImageView imgConsultarRutas;

    @FXML
    private ImageView imgCrearContratoConEmpresa;

    @FXML
    void onMouseClickedConsultarRutas(MouseEvent event) {

    }

    @FXML
    void onMouseClickedConsultarUnaRuta(MouseEvent event) {

    }

    @FXML
    void onMouseClickedPagarPasaje(MouseEvent event) {

    }

    @FXML
    void onMouseClickedCrearRuta(MouseEvent event) {
        try{
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("crearRuta.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        };
    }

    @FXML
    void onMouseClickedCrearContratoConEmpresa(MouseEvent event) {
        try{
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("crearContrato.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        };
    }

    @FXML
    void onMouseClickedPagarNomina(MouseEvent event) {
        try{
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("reporteNomina.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        };
    }

    @FXML
    void onMouseClickedReporteAProveedores(MouseEvent event) {

    }

}