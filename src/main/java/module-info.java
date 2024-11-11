module org.persointegrado.persointegrado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;
    requires activation;


    opens org.persointegrado.persointegrado to javafx.fxml;
    exports org.persointegrado.persointegrado;
}