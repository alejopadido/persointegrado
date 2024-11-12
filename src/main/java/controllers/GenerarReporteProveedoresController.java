package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea; // Changed to javafx.scene.control.TextArea
import javafx.stage.Stage;
import models.Bus;
import models.ContratoProveedor;
import models.EmpresaProveedora;
import models.PersoIntegrado;
import utils.manejadorArchivos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerarReporteProveedoresController {

    @FXML
    private TextArea reporteTextArea;

    private PersoIntegrado persoIntegrado;
    private manejadorArchivos ManejadorArchivos;

    public void initialize() {
        persoIntegrado = PersoIntegrado.getInstance();
        ManejadorArchivos = new manejadorArchivos();
    }

    @FXML
    private void generarReporte() {
        List<String[]> datosCSV = ManejadorArchivos.leerArchivoCSVNomina("contratos.csv");
        Map<String, EmpresaInfo> reporte = new HashMap<>();

        for (String[] fila : datosCSV) {
            String busID = fila[0];
            String nombreConductor = fila[1];
            double pago = Double.parseDouble(fila[2]);

            Bus bus = persoIntegrado.getBusPorId(Integer.parseInt(busID));
            if (bus != null) {
                String empresaName = bus.getEmpresaProveedora();
                EmpresaProveedora empresa = persoIntegrado.getEmpresaProveedoraByName(empresaName);
                ContratoProveedor contrato = empresa.getContrato();

                EmpresaInfo empresaInfo = reporte.computeIfAbsent(empresa.getNombre(), k -> new EmpresaInfo(empresa.getNombre(), contrato.getContrato()));
                empresaInfo.sumarPagoBus(busID, pago);
            }
        }

        StringBuilder reporteTexto = new StringBuilder();
        for (EmpresaInfo empresaInfo : reporte.values()) {
            reporteTexto.append("Empresa: ").append(empresaInfo.nombreEmpresa).append("\n");
            reporteTexto.append("Número de Contrato: ").append(empresaInfo.numeroContrato).append("\n");
            reporteTexto.append("Total pagado: $").append(empresaInfo.getTotalPagado()).append("\n");

            for (Map.Entry<String, Double> busPago : empresaInfo.pagosPorBus.entrySet()) {
                reporteTexto.append(" - Bus ID: ").append(busPago.getKey()).append(", Pago: $").append(busPago.getValue()).append("\n");
            }

            reporteTexto.append("\n");
        }

        reporteTextArea.setText(reporteTexto.toString());
    }

    private static class EmpresaInfo {
        private String nombreEmpresa;
        private String numeroContrato;
        private Map<String, Double> pagosPorBus;
        private double totalPagado;

        public EmpresaInfo(String nombreEmpresa, String numeroContrato) {
            this.nombreEmpresa = nombreEmpresa;
            this.numeroContrato = numeroContrato;
            this.pagosPorBus = new HashMap<>();
            this.totalPagado = 0;
        }

        public void sumarPagoBus(String busID, double pago) {
            pagosPorBus.put(busID, pagosPorBus.getOrDefault(busID, 0.0) + pago);
            totalPagado += pago;
        }

        public double getTotalPagado() {
            return totalPagado;
        }
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
}
