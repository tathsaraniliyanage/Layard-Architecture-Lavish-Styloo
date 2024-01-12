package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardFromController implements Initializable {
    @FXML
    public Pane customerDashBordPane;

    @FXML
    public Text txtPendingBookings;

    @FXML
    public Text txtToDayBokkings;

    @FXML
    public JFXTextField txtText11;

    @FXML
    public Text date;

    @FXML
    public Text txtToDayAttendance;

    @FXML
    public Text txtAvalebelEmployees;

    @FXML
    public Text txtUnComlitedBookings;

    @FXML
    public Text txtCompliteBookings;

    @FXML
    public Text timeHouer;

    @FXML
    public Text timeMin;

    @FXML
    public Text timeStatus;

    @FXML
    public Text txtLimitedItems;

    @FXML
    public Text txtTodayOrders;
    public VBox vBox;

    @FXML
    public void employeePageViewOnAction(ActionEvent event) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/EmployeeFrom.fxml");
    }

    public void onKeyReleased(KeyEvent keyEvent) {

    }

    public void homePageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/AdminHome.fxml");
    }

    public void attendancePageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/EmployeeAttendanceFrom.fxml");
    }

    public void productPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/productFrom.fxml");
    }

    public void bookingPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/PaymentViewFrom.fxml");
    }

    public void treatmentPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/TreatmentFrom.fxml");
    }

    public void chartPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/ReportFrom.fxml");
    }

    public void supperViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/SupplerViewFrom.fxml");
    }

    public void logOutOnAction(ActionEvent actionEvent) {
        NavigationUtility.switchNavigation( "LoginFrom.fxml",actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Admin/AdminHome.fxml");
    }
}
