package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ContratoProveedorException;
import models.EmpresaProveedoraException;
import models.PersoIntegradoException;
import org.persointegrado.persointegrado.MainApplication;
import models.PersoIntegrado;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ControladorCrearContrato {

    private  PersoIntegrado persoIntegrado;
    private int contadorDeBuses = 0;
    private Stage stage;
    private Scene scene;


    public ControladorCrearContrato() {
        this.persoIntegrado = PersoIntegrado.getInstance();
    }

    @FXML
    private TextField tfNumeroContrato;

    @FXML
    private Text tErrorContrato;

    @FXML
    private TextField tfNit;

    @FXML
    private TextField tfNumeroDeBusesAOfrecer;

    @FXML
    private TextField tfNombreDeLaEmpresa;

    @FXML
    private Button btnIngresarBuses;

    @FXML
    private Button btnSiguienteBus;

    @FXML
    private TextField tfPlaca;

    @FXML
    private TextField tfModelo;

    @FXML
    private Text tErrorBus;

    @FXML
    private Text tBusActual;
    @FXML
    void onActionSiguienteBus(ActionEvent event) {

    }
    @FXML
    void onActionIngresarBuses(ActionEvent event) {
        try{
            persoIntegrado.crearEmpresa(tfNombreDeLaEmpresa.getText(),Integer.parseInt(tfNit.getText().trim()),Integer.parseInt(tfNumeroContrato.getText().trim()));

            System.out.println("Se ha creado con exito la empresa");
            persoIntegrado.imprimirEmpresas();
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("menuAdministrador.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch(PersoIntegradoException | EmpresaProveedoraException | IOException | ContratoProveedorException e){
            e.getCause();
        }
    }

}