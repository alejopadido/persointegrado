module com.example.persointegrado {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.persointegrado to javafx.fxml;
    exports com.example.persointegrado;
}