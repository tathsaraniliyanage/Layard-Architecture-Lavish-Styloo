package lk.ijse.lavishStyloo.controller.Admin.bar;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.CustomerOrderBO;
import lk.ijse.lavishStyloo.dto.tm.HomeBookingTm;
import lk.ijse.lavishStyloo.util.Notification;

import java.sql.SQLException;

public class BookingBarController {
    public Text txtTime;
    public Text txtCustomerName;
    CustomerOrderBO customerOrderBO = (CustomerOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_ORDER);
    private String id;

    public void cancelOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {

            System.out.println(this.id);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Are your Sure ?", ButtonType.NO, ButtonType.YES);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.YES)) {
                if (customerOrderBO.cancel(this.id)) {
                    Notification.notification("Booking", "Canceled");
                } else {
                    Notification.notificationWARNING("Booking ", "Something Wong");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setData(HomeBookingTm tm) {
        txtCustomerName.setText(tm.getName());
        txtTime.setText(tm.getTime());
        this.id = tm.getId();
    }
}
