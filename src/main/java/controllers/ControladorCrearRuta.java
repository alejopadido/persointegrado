package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.PersoIntegrado;
import models.PersoIntegradoException;
import org.persointegrado.persointegrado.MainApplication;
import utils.util;

import java.io.IOException;
import java.time.LocalTime;

public class ControladorCrearRuta {

    private  PersoIntegrado persoIntegrado;
    private Stage stage;
    private Scene scene;
    private int conta = 1;
    private int x;
    public ControladorCrearRuta() {
        this.persoIntegrado = PersoIntegrado.getInstance();
    }

    @FXML
    private TextField tfNumeroParaderos;

    @FXML
    private TextField tfHoraFinRuta;

    @FXML
    private TextField tfMinutoFinRuta;

    @FXML
    private TextField tfMinutoFinTurno;

    @FXML
    private TextField tfHoraFinTurno;

    @FXML
    private TextField tfMinutoInicioRuta;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfBusesDisponibles;

    @FXML
    private Button btnAñadirRuta;

    @FXML
    private TextField tfNombre;

    @FXML
    private CheckBox cbCiclico;

    @FXML
    private TextField tfMinutoInicioTurno;

    @FXML
    private TextField tfNombreConductor;

    @FXML
    private TextField tfHoraInicioRuta;

    @FXML
    private Text tErrorBus;

    @FXML
    private TextField tfHoraInicioTurno;

    @FXML
    private TextField tfMinutoLlegada;

    @FXML
    private TextField tfHoraLlegada;

    @FXML
    private Button btnAñadirParada;

    @FXML
    private TextField tfNombreParadero;

    @FXML
    private TextField tfDireccionParadero;

    @FXML
    void onActionAñadirParada(ActionEvent event) throws IOException {
        x = persoIntegrado.recibirMax();
        String nomruta = persoIntegrado.getRutas().get(persoIntegrado.getRutas().size()-1).getNombre();
        if(conta<x){
            conta++;
            LocalTime llegada = util.integersToTimes(Integer.parseInt(tfHoraLlegada.getText()),Integer.parseInt(tfMinutoLlegada.getText()));
            persoIntegrado.añadirParada(nomruta,tfNombreParadero.getText(),tfDireccionParadero.getText(),llegada);

        }else{
            persoIntegrado.imprimirRutas();
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("menuAdministrador.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void onActionAñadirRuta(ActionEvent event) throws Exception {
    try{
        persoIntegrado.guardarNumeroMax(Integer.parseInt(tfNumeroParaderos.getText()));
        LocalTime inicioTurno = util.integersToTimes(Integer.parseInt(tfHoraInicioTurno.getText()), Integer.parseInt(tfMinutoInicioTurno.getText()));
        LocalTime finTurno = util.integersToTimes(Integer.parseInt(tfHoraFinTurno.getText()), Integer.parseInt(tfMinutoFinTurno.getText()));
        LocalTime inicioRuta = util.integersToTimes(Integer.parseInt(tfHoraInicioRuta.getText()), Integer.parseInt(tfMinutoInicioRuta.getText()));
        LocalTime finRuta = util.integersToTimes(Integer.parseInt(tfHoraFinRuta.getText()), Integer.parseInt(tfMinutoFinRuta.getText()));
        boolean state = cbCiclico.isSelected();
        persoIntegrado.crearRuta(tfNombreConductor.getText(),inicioTurno,finTurno,inicioRuta,finRuta, tfNombre.getText(), Integer.parseInt(tfNumero.getText()), state, Integer.parseInt(tfBusesDisponibles.getText()));
        Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("añadirParaderos.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    } catch (NumberFormatException | PersoIntegradoException | IOException e) {
        throw new Exception(e);
    }

    }

}
