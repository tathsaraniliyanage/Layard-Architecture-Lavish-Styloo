package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.util.ResourceBundle;


public class CashierDashboardFromController implements Initializable {
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

    @FXML
    public void customerPageViewOnAction(ActionEvent event) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/CustomerFrom.fxml");
    }

    public void onKeyReleased(KeyEvent keyEvent) {

    }

    public void homePageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/CashierHome.fxml");
    }

    public void attendancePageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/EmployeeAttendanceFrom.fxml");
    }

    public void productPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/productFrom.fxml");
    }

    public void bookingPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/PaymentFrom.fxml");
    }

    public void treatmentPageViewOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/TreatmentViewFrom.fxml");
    }

    public void appointmentOnAction(MouseEvent mouseEvent) {
        NavigationUtility.popupNavigation("Cashier/BookingFrom.fxml");
    }

    public void customerAddOnAction(MouseEvent mouseEvent) {
        NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(customerDashBordPane, "Cashier/CashierHome.fxml");
    }

    public void logOutOnAction(ActionEvent actionEvent) {
        NavigationUtility.switchNavigation( "LoginFrom.fxml",actionEvent);
    }
}
