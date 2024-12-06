/* Lab - 5
Course Code and section: COMP 228 - Section 405
Professor: Shaharm Jalaliniya
Group Number:
Member's Name: Isabel Lorrelyn Lag-ang, Mauli Gandhi
Student Number: 301385246 and 301486344
*/
package com.example.mauligandhi_comp228lab5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.parseInt;

public class HelloController
{
    //all the GUI Items
    @FXML
    private TableColumn o_owner_id_column;
    @FXML
    private TableColumn name_column;
    @FXML
    private TableColumn address_column;
    @FXML
    private TableColumn phone_column;
    @FXML
    private TableColumn email_column;
    @FXML
    private TableView ownerTable;
    @FXML
    private TableView carTable;
    @FXML
    private TableColumn c_car_id_column;
    @FXML
    private TableColumn brand_column;
    @FXML
    private TableColumn vin_column;
    @FXML
    private TableColumn year_column;
    @FXML
    private TableColumn type_column;
    @FXML
    private TableColumn model_column;
    @FXML
    private TableView repairTable;
    @FXML
    private TableColumn r_repair_id_column;
    @FXML
    private TableColumn r_owner_id_column;
    @FXML
    private TableColumn r_car_id_column;
    @FXML
    private TableColumn r_repairDate;
    @FXML
    private TableColumn r_description_column;
    @FXML
    private TableColumn r_cost_column;
    @FXML
    private DatePicker startDate;
    @FXML
    private TableColumn rs_repair_id_column;
    @FXML
    private TableColumn rs_owner_id_column;
    @FXML
    private TableColumn rs_car_id_column;
    @FXML
    private TableColumn rs_repairDate;
    @FXML
    private TableColumn rs_description_column;
    @FXML
    private TableColumn rs_cost_column;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView rsTable;
    @FXML
    private TextField rsOwnerId;
    @FXML
    private TextField rsCarId;
    @FXML
    private TextField o_ownerIdTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField c_carIdTextField;
    @FXML
    private TextField brandTextField;
    @FXML
    private TextField modelTextField;
    @FXML
    private TextField vinTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField repairIdTextfield;
    @FXML
    private TextField r_ownerIdTextField;
    @FXML
    private TextField r_carIdTextField;
    @FXML
    private DatePicker serviceDate;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField costTextField;
    @FXML
    private ComboBox typeComboBox;

    //GUI function for adding the owner items
    public void addOwnerButtonAction(ActionEvent actionEvent) throws SQLException
    {
        DBUtil.insertData("INSERT INTO Owner VALUES (' " + o_ownerIdTextField.getText() + " ' , '" + nameTextField.getText() + "' , ' " + addressTextField.getText() + "', '" + phoneTextField.getText() + "', '" +
                emailTextField.getText() + "')");
        populateData();
    }
    //GUI function for updating the owner items
    public void updateOwnerButtonAction(ActionEvent actionEvent) throws SQLException
    {
        DBUtil.updateOwner(parseInt(o_ownerIdTextField.getText()), nameTextField.getText(), addressTextField.getText(), phoneTextField.getText(), emailTextField.getText());
        populateData();
    }
    //GUI function for deleting owner items in the sql
    public void deleteOwnerButtonAction(ActionEvent actionEvent) throws SQLException
    {
        DBUtil.deleteRecord("Owner", "ownerID = " + o_ownerIdTextField.getText());
        populateData();

    }
    //making options for the combo box
    public void makeComboBox()
        {
            String[] carTypes = { "Van", "SUV", "Sudan", "Truck", "Electric"};
            typeComboBox.getItems().addAll(carTypes);
        }
    //GUI function for adding the car items
    public void addCarButtonAction(ActionEvent actionEvent) throws SQLException
    {
        DBUtil.insertData("INSERT INTO Car VALUES (' " + c_carIdTextField.getText() + " ' , '" + brandTextField.getText() + "' , ' " + modelTextField.getText() + "', '" + vinTextField.getText() + "', '" +
                yearTextField.getText() + "', '" + typeComboBox.getValue() + "')");
        populateData();
    }
    //GUI function for updating the car items
    public void updateCarButtonAction(ActionEvent actionEvent) throws SQLException {
        DBUtil.updateCar(parseInt(c_carIdTextField.getText()), brandTextField.getText(), modelTextField.getText(), parseInt(vinTextField.getText()), parseInt(yearTextField.getText()), String.valueOf(typeComboBox.getValue()));
        populateData();
    }
    //GUI function for deleting car items in the sql
    public void deleteCarButtonAction(ActionEvent actionEvent) throws SQLException {
        DBUtil.deleteRecord("Car", "carID = " + c_carIdTextField.getText());
        populateData();
    }
    //GUI function for adding the repair items
    public void addRepairButtonAction(ActionEvent actionEvent) throws SQLException {
        DBUtil.insertData("INSERT INTO Repair VALUES (' " + repairIdTextfield.getText() + " ' , '" + r_ownerIdTextField.getText() + "' , ' " + r_carIdTextField.getText()  + "', '" + String.valueOf(serviceDate.getValue())  + "', '" +
                descriptionTextField.getText() + "', '" + costTextField.getText() + "')");
        populateData();
    }
    //GUI function for updating the repair items
    public void updateRepairButtonAction(ActionEvent actionEvent) throws SQLException
    {
        DBUtil.updateRepair(parseInt(repairIdTextfield.getText()),parseInt(r_ownerIdTextField.getText()), parseInt(r_carIdTextField.getText()), String.valueOf(serviceDate.getValue()), descriptionTextField.getText(), parseInt(costTextField.getText()));
        populateData();
    }
    //GUI function for deleting repair items in the sql
    public void deleteRepairButtonAction(ActionEvent actionEvent) throws SQLException
    {
        DBUtil.deleteRecord("Repair", "repairID = " + repairIdTextfield.getText());
        populateData();
    }
    //GUI function for searches the repair information by inputing two dates
    public void srdButtonAction(ActionEvent actionEvent) throws SQLException
    {

        DBUtil.displayRepairsBetweenDates(String.valueOf(startDate.getValue()), String.valueOf(endDate.getValue()));

        ObservableList<Repair> repairListByDates = DBUtil.displayRepairsBetweenDates(String.valueOf(startDate.getValue()), String.valueOf(endDate.getValue()));
        rs_repair_id_column.setCellValueFactory(new PropertyValueFactory("repairID"));
        rs_owner_id_column.setCellValueFactory(new PropertyValueFactory("ownerID"));
        rs_car_id_column.setCellValueFactory(new PropertyValueFactory("carID"));
        rs_repairDate.setCellValueFactory(new PropertyValueFactory("serviceDate"));
        rs_description_column.setCellValueFactory(new PropertyValueFactory("description"));
        rs_cost_column.setCellValueFactory(new PropertyValueFactory("cost"));
        rsTable.getItems().clear();
        rsTable.getItems().addAll(repairListByDates);
        rs_repair_id_column.setSortType(TableColumn.SortType.ASCENDING);
        rsTable.sort();




    }
    //GUI function for searching the repair informatoin by the owner ID
    public void sroButtonAction(ActionEvent actionEvent) throws SQLException {

        DBUtil.getOwnerById(parseInt(rsOwnerId.getText()));

        ObservableList<Repair> repairListByOwnerID = DBUtil.getRepairsByOwnerId(parseInt(rsOwnerId.getText()));
        rs_repair_id_column.setCellValueFactory(new PropertyValueFactory("repairID"));
        rs_owner_id_column.setCellValueFactory(new PropertyValueFactory("ownerID"));
        rs_car_id_column.setCellValueFactory(new PropertyValueFactory("carID"));
        rs_repairDate.setCellValueFactory(new PropertyValueFactory("serviceDate"));
        rs_description_column.setCellValueFactory(new PropertyValueFactory("description"));
        rs_cost_column.setCellValueFactory(new PropertyValueFactory("cost"));
        rsTable.getItems().clear();
        rsTable.getItems().addAll(repairListByOwnerID);
        rs_repair_id_column.setSortType(TableColumn.SortType.ASCENDING);
        rsTable.sort();
    }
    //GUI function for Searches the repair information by the car id
    public void srcButtonAction(ActionEvent actionEvent) throws SQLException {

        DBUtil.getCarById(parseInt(rsCarId.getText()));

        ObservableList<Repair> repairListByCarID = DBUtil.getRepairsByCarId(parseInt(rsCarId.getText()));
        rs_repair_id_column.setCellValueFactory(new PropertyValueFactory("repairID"));
        rs_owner_id_column.setCellValueFactory(new PropertyValueFactory("ownerID"));
        rs_car_id_column.setCellValueFactory(new PropertyValueFactory("carID"));
        rs_repairDate.setCellValueFactory(new PropertyValueFactory("serviceDate"));
        rs_description_column.setCellValueFactory(new PropertyValueFactory("description"));
        rs_cost_column.setCellValueFactory(new PropertyValueFactory("cost"));
        rsTable.getItems().clear();
        rsTable.getItems().addAll(repairListByCarID);
        rs_repair_id_column.setSortType(TableColumn.SortType.ASCENDING);
        rsTable.sort();
    }
    //GUI function for Reloads the Data in the table
    public void queryButtonAction(ActionEvent actionEvent)throws SQLException
    {
        populateData();
    }
    //GUI function for populates the data in the GUI tables
    public void populateData () throws SQLException
    {
        ObservableList<Owner> ownerList = DBUtil.getAllOwners();

        o_owner_id_column.setCellValueFactory(new PropertyValueFactory("ownerID"));
        name_column.setCellValueFactory(new PropertyValueFactory("Name"));
        address_column.setCellValueFactory(new PropertyValueFactory("Address"));
        phone_column.setCellValueFactory(new PropertyValueFactory("Phone"));
        email_column.setCellValueFactory(new PropertyValueFactory("Email"));
        ownerTable.getItems().clear();
        ownerTable.getItems().addAll(ownerList);
        o_owner_id_column.setSortType(TableColumn.SortType.ASCENDING);
        ownerTable.sort();

        ObservableList<Car> carList = DBUtil.getAllCars();
        c_car_id_column.setCellValueFactory(new PropertyValueFactory("carID"));
        brand_column.setCellValueFactory(new PropertyValueFactory("make"));
        model_column.setCellValueFactory(new PropertyValueFactory("model"));
        vin_column.setCellValueFactory(new PropertyValueFactory("VIN"));
        year_column.setCellValueFactory(new PropertyValueFactory("buildYear"));
        type_column.setCellValueFactory(new PropertyValueFactory("type"));
        carTable.getItems().clear();
        carTable.getItems().addAll(carList);
        c_car_id_column.setSortType(TableColumn.SortType.ASCENDING);
        carTable.sort();

        ObservableList<Repair> repairList = DBUtil.getAllRepairs();
        r_repair_id_column.setCellValueFactory(new PropertyValueFactory("repairID"));
        r_owner_id_column.setCellValueFactory(new PropertyValueFactory("ownerID"));
        r_car_id_column.setCellValueFactory(new PropertyValueFactory("carID"));
        r_repairDate.setCellValueFactory(new PropertyValueFactory("serviceDate"));
        r_description_column.setCellValueFactory(new PropertyValueFactory("description"));
        r_cost_column.setCellValueFactory(new PropertyValueFactory("cost"));
        repairTable.getItems().clear();
        repairTable.getItems().addAll(repairList);
        r_repair_id_column.setSortType(TableColumn.SortType.ASCENDING);
        repairTable.sort();
    }
    //shows data when the page first loads up
    public void initialize() throws SQLException
    {
        populateData();
        makeComboBox();
    }













}