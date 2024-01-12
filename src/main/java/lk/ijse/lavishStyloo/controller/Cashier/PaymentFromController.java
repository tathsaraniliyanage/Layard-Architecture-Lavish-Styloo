package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PaymentFromController implements Initializable {
    public JFXRadioButton bookingDetails;
    public JFXRadioButton orderDetails;
    public JFXRadioButton order;
    public JFXRadioButton bookings;
    public AnchorPane pane;
    public JFXTextField txtSearchText;
    public JFXRadioButton payment;

    public void orderOnAction(ActionEvent actionEvent) {
        setFilter();
    }
    private void setFilter() {
        if (bookings.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Cashier/BookingFrom.fxml");
        } else if (bookingDetails.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Cashier/VewBookingFrom.fxml");
        } else if (order.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Cashier/OrderFrom.fxml");
        } else if (orderDetails.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Cashier/VewOrderFrom.fxml");
        }else if (payment.isSelected()){
            NavigationUtility.onTheTopNavigation(pane, "Cashier/PaymentPayFrom.fxml");
        }
    }

    public void bookingDetailsOnAction(ActionEvent actionEvent) {
        setFilter();
    }

    public void bookingsOnActon(ActionEvent actionEvent) {
        setFilter();
    }

    public void orderDetailsOnAction(ActionEvent actionEvent) {
        setFilter();
    }

    public void paymentOnAction(ActionEvent actionEvent) {
        setFilter();
    }

    public void productFromSearchOnKeyReleased(KeyEvent keyEvent) {
       /* if (bookingDetails.isSelected()) {

        } else if (orderDetails.isSelected()) {
            OrderViewFromController.getController().searchOrder(txtSearchText.getText());
        }*/
//        >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        if (bookings.isSelected()) {
//            NavigationUtility.onTheTopNavigation(pane, "Cashier/BookingFrom.fxml");
        } else if (bookingDetails.isSelected()) {

        } else if (order.isSelected()) {
//            NavigationUtility.onTheTopNavigation(pane, "Cashier/OrderFrom.fxml");
        } else if (orderDetails.isSelected()) {
            VewOrderFromController.getController().searchOrder(txtSearchText.getText());
//            NavigationUtility.onTheTopNavigation(pane, "Cashier/VewOrderFrom.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(pane, "Cashier/BookingFrom.fxml");
    }
    private void printBill(String cust_oid) {
        System.out.println(cust_oid+" report id");
        InputStream resource = this.getClass().getResourceAsStream("/report/Bill.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id",cust_oid);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
