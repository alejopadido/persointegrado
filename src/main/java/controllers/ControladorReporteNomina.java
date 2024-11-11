package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.PersoIntegrado;
import utils.manejadorArchivos;

import java.util.List;

public class ControladorReporteNomina {
    private PersoIntegrado persoIntegrado;
    private List<String> reporteNomina;
    public ControladorReporteNomina() {
        this.persoIntegrado = PersoIntegrado.getInstance();
    }

    @FXML
    private Button idBGenerarReporte;

    @FXML
    private TextArea idReporte;

    @FXML
    void onGenerarReporte(ActionEvent event) {
        try {
            reporteNomina = persoIntegrado.pagosNominaConductores();

            manejadorArchivos.reporteNominaConductores(reporteNomina);

            StringBuilder reporteBuilder = new StringBuilder();
            for (String r : reporteNomina) {
                reporteBuilder.append(r).append("\n");
            }
            idReporte.setText(reporteBuilder.toString());

        }catch (Exception e){
            System.out.println(e);
        }

    }

}
