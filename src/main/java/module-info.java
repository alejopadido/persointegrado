module org.persointegrado.persointegrado {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.persointegrado.persointegrado to javafx.fxml;
    exports org.persointegrado.persointegrado;
}