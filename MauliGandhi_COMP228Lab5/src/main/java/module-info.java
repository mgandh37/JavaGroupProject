/* Lab - 5
Course Code and section: COMP 228 - Section 405
Professor: Shaharm Jalaliniya
Group Number:
Member's Name: Isabel Lorrelyn Lag-ang, Mauli Gandhi
Student Number: 301385246 and 301486344
*/
module com.example.mauligandhi_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens com.example.mauligandhi_comp228lab5 to javafx.fxml;
    exports com.example.mauligandhi_comp228lab5;
}