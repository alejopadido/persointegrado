module org.persointegrado.persointegrado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;
    requires javafx.graphics;

    opens controllers to javafx.fxml;
    exports controllers;
    exports org.persointegrado.persointegrado;
}
