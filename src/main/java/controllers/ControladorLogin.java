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
import org.persointegrado.persointegrado.MainApplication;
import java.io.IOException;

public class ControladorLogin {

    private Stage stage;
    private Scene scene;

    @FXML
    private Text tError;
    @FXML
    private Button btnUsuario;

    @FXML
    private Button btnAdministrador;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfNumeroDeCedula;

    @FXML
    private Button btnIngresar;

    @FXML
    void onActionNumeroDeCedula(ActionEvent event) {
        
    }

    @FXML
    void onActionContrasena(ActionEvent event) {

    }

    public void onActionUsuario(ActionEvent actionEvent) {
        try{
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("menuUsuario.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        };
    }

    public void onActionAdministrador(ActionEvent actionEvent) {
        try{
            Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("loginAdmin.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        };

    }

    public void onActionIngresar(ActionEvent actionEvent) {
        if(tfNumeroDeCedula.getText().equalsIgnoreCase("1") && tfContrasena.getText().equalsIgnoreCase("j")){
            try{
                Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("menuAdministrador.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader);
                stage.setScene(scene);
                stage.show();
            }catch(IOException e){
                e.printStackTrace();
            };
        }else{
            tError.setText("Datos invalidos");
        }

    }
}
