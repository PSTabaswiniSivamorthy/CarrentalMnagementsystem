package com.example.admin;

import javafx.fxml.Initializable;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import java.io.File;
import java.util.*;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.collections.transformation.FilteredList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import java.net.URL;

public class dashboardcontroller implements Initializable {

        @FXML
        private TextField avaliableCars_brand;

        @FXML
        private Button avaliableCars_btn;

        @FXML
        private TextField avaliableCars_carid;

        @FXML
        private Button avaliableCars_clearBtn;

        @FXML
        private TableColumn <carData,String> avaliableCars_co_model;

        @FXML
        private TableColumn<carData,String> avaliableCars_col_brand;

        @FXML
        private TableColumn<carData,String> avaliableCars_col_carid;

        @FXML
        private TableColumn<carData,String> avaliableCars_col_price;

        @FXML
        private TableColumn<carData,String> avaliableCars_col_status;

        @FXML
        private Button avaliableCars_deleteBtn;

        @FXML
        private AnchorPane avaliableCars_form;

        @FXML
        private ImageView avaliableCars_imageView;

        @FXML
        private Button avaliableCars_importBtn;

        @FXML
        private Button avaliableCars_insertBtn;

        @FXML
        private TextField avaliableCars_model;

        @FXML
        private TextField avaliableCars_price;

        @FXML
        private TextField avaliableCars_search;

        @FXML
        private ComboBox<?> avaliableCars_status;

        @FXML
        private TableView<carData> avaliableCars_tableView;

        @FXML
        private Button avaliableCars_updateBtn;

        @FXML
        private Button close;

        @FXML
        private Label home_avaliableCars;

        @FXML
        private Button home_btn;

        @FXML
        private LineChart<?, ?> home_customerChart;

        @FXML
        private BarChart<?, ?> home_incomeChart;

        @FXML
        private Label home_totalCustomers;

        @FXML
        private Label home_totalIncome;

        @FXML
        private Button logoutBtn;

        @FXML
        private AnchorPane main_form;

        @FXML
        private Button minimize;

        @FXML
        private Button rentBtn;

        @FXML
        private Button rentCar_btn;

        @FXML
        private TextField rent_amount;

        @FXML
        private Label rent_balance;

        @FXML
        private ComboBox<?> rent_brand;

        @FXML
        private ComboBox<?> rent_carid;

        @FXML
        private TableColumn<?, ?> rent_col_brand;

        @FXML
        private AnchorPane home_form;

        @FXML
        private TableColumn<?, ?> rent_col_carid;

        @FXML
        private TableColumn<?, ?> rent_col_model;

        @FXML
        private TableColumn<?, ?> rent_col_price;

        @FXML
        private TableColumn<?, ?> rent_col_status;

        @FXML
        private DatePicker rent_dateRented;

        @FXML
        private DatePicker rent_dateReturned;

        @FXML
        private TextField rent_firstName;

        @FXML
        private AnchorPane rent_form;

        @FXML
        private ComboBox<?> rent_gender;

        @FXML
        private TextField rent_lastName;

        @FXML
        private ComboBox<?> rent_model;

        @FXML
        private TableView<carData> rent_tableView;

        @FXML
        private Label rent_total;

        @FXML
        private Label username;

        @FXML
        void close(ActionEvent event) {

        }

        @FXML
        void minimize(ActionEvent event) {

        }
        private Connection connect;
        private PreparedStatement prepare;
        private ResultSet result;
        private Statement statement;

        private Image image;




        private double x = 0;
        private double y = 0;

        public void logout() {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            try {
                if (option.get().equals(ButtonType.OK)) {
                    // HIDE YOUR DASHBOARD FORM
                    logoutBtn.getScene().getWindow().hide();

                    // LINK YOUR LOGIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                        stage.setOpacity(.8);
                    });

                    root.setOnMouseReleased((MouseEvent event) -> {
                        stage.setOpacity(1);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public void availableCarAdd() {



        String sql = "INSERT INTO car (car_id, brand, model, price, status, image, date) "
                + "VALUES(?,?,?,?,?,?,?)";
        availableCarImportImage();

        connect = Database.connectDb();

        try {
            Alert alert;

            if (avaliableCars_carid.getText().isEmpty()
                    || avaliableCars_brand.getText().isEmpty()
                    || avaliableCars_model.getText().isEmpty()
                    || avaliableCars_status.getSelectionModel().getSelectedItem() == null
                    || avaliableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, avaliableCars_carid.getText());
                prepare.setString(2, avaliableCars_brand.getText());
                prepare.setString(3, avaliableCars_model.getText());
                prepare.setString(4, avaliableCars_price.getText());
                prepare.setString(5, (String) avaliableCars_status.getSelectionModel().getSelectedItem());

                String uri = getData.path;
                uri = uri.replace("\\", "\\\\");

                prepare.setString(6, uri);

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(7, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableCarImportImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 116, 153, false, true);
            avaliableCars_imageView.setImage(image);

        }

    }
        public void switchForm(ActionEvent event){

            if (event.getSource() == home_btn) {
                home_form.setVisible(true);
                avaliableCars_form.setVisible(false);
                rent_form.setVisible(false);

                home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
                avaliableCars_btn.setStyle("-fx-background-color:transparent");
                rentCar_btn.setStyle("-fx-background-color:transparent");


            } else if (event.getSource() == avaliableCars_btn) {
                home_form.setVisible(false);
                avaliableCars_form.setVisible(true);
                rent_form.setVisible(false);

                avaliableCars_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
                home_btn.setStyle("-fx-background-color:transparent");
                rentCar_btn.setStyle("-fx-background-color:transparent");
                availableCarAdd();



            } else if (event.getSource() == rentCar_btn) {
                home_form.setVisible(false);
                avaliableCars_form.setVisible(false);
                rent_form.setVisible(true);

                rentCar_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
                home_btn.setStyle("-fx-background-color:transparent");
                avaliableCars_btn.setStyle("-fx-background-color:transparent");



            }

        }


        public void close() {
            System.exit(0);
        }

        public void minimize() {
            Stage stage = (Stage)main_form.getScene().getWindow();
            stage.setIconified(true);
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }


}

