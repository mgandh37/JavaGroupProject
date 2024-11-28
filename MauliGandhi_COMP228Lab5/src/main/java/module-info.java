module com.example.mauligandhi_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens com.example.mauligandhi_comp228lab5 to javafx.fxml;
    exports com.example.mauligandhi_comp228lab5;
}