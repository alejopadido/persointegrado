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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.PersoIntegrado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ControladorReporteNomina {
    private PersoIntegrado persoIntegrado;

    @FXML
    private Button idBGenerarReporte;

    @FXML
    private Button volverButton;

    @FXML
    private TableView<TurnoReporte> tablaReporte;

    @FXML
    private TableColumn<TurnoReporte, String> colBusId;

    @FXML
    private TableColumn<TurnoReporte, String> colNombreConductor;

    @FXML
    private TableColumn<TurnoReporte, Double> colPago;

    public ControladorReporteNomina() {
        this.persoIntegrado = PersoIntegrado.getInstance();
    }

    @FXML
    public void initialize() {
        colBusId.setCellValueFactory(new PropertyValueFactory<>("busId"));
        colNombreConductor.setCellValueFactory(new PropertyValueFactory<>("nombreConductor"));
        colPago.setCellValueFactory(new PropertyValueFactory<>("pago"));

        cargarDatosEnTabla();
    }

    @FXML
    private void onGenerarReporte() {
        List<String[]> datosReporte = persoIntegrado.obtenerTurnosConPagos();
        try (PrintWriter writer = new PrintWriter(new File("reporte_nomina.csv"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bus ID,Nombre Conductor,Pago\n");

            for (String[] fila : datosReporte) {
                sb.append(fila[0]).append(",").append(fila[1]).append(",").append(fila[2]).append("\n");
            }

            writer.write(sb.toString());
            System.out.println("Reporte CSV generado exitosamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
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

    private void cargarDatosEnTabla() {
        List<String[]> datosReporte = persoIntegrado.obtenerTurnosConPagos();
        ObservableList<TurnoReporte> listaReporte = FXCollections.observableArrayList();

        for (String[] fila : datosReporte) {
            listaReporte.add(new TurnoReporte(fila[0], fila[1], Double.parseDouble(fila[2])));
        }

        tablaReporte.setItems(listaReporte);
    }

    public static class TurnoReporte {
        private String busId;
        private String nombreConductor;
        private Double pago;

        public TurnoReporte(String busId, String nombreConductor, Double pago) {
            this.busId = busId;
            this.nombreConductor = nombreConductor;
            this.pago = pago;
        }

        public String getBusId() {
            return busId;
        }

        public void setBusId(String busId) {
            this.busId = busId;
        }

        public String getNombreConductor() {
            return nombreConductor;
        }

        public void setNombreConductor(String nombreConductor) {
            this.nombreConductor = nombreConductor;
        }

        public Double getPago() {
            return pago;
        }

        public void setPago(Double pago) {
            this.pago = pago;
        }
    }
}
