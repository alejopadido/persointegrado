package org.persointegrado.persointegrado;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(MainApplication.class.getResource("login0.fxml"));
        Scene scene = new Scene(fxmlLoader);
        stage.setTitle("PersoIntegrado Salomon Avila - Sebastian Vargas - Alejandro Parrado - Gabriel Jaramillo - Sara Ocampo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}