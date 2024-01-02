module com.example.admin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.admin to javafx.fxml;
    exports com.example.admin;
}
