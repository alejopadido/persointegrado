module org.persointegrado.persointegrado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires activation;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires com.opencsv;

    opens controllers to javafx.fxml;
    exports controllers;
    exports org.persointegrado.persointegrado;
}
