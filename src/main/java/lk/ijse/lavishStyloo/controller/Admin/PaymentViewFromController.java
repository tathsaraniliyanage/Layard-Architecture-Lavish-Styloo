package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentViewFromController implements Initializable {
    public JFXRadioButton bookingDetails;
    public JFXRadioButton orderDetails;
    public JFXRadioButton order;
    public AnchorPane pane;
    public JFXTextField txtSearchText;

    private void setFilter() {
       if (bookingDetails.isSelected()) {
           NavigationUtility.onTheTopNavigation(pane, "Admin/VewBookingFrom.fxml");
       } else if (orderDetails.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Admin/VewOrderFrom.fxml");
        }
    }

    public void bookingDetailsOnAction(ActionEvent actionEvent) {
        setFilter();
    }


    public void orderDetailsOnAction(ActionEvent actionEvent) {
        setFilter();
    }

    public void productFromSearchOnKeyReleased(KeyEvent keyEvent) {
        if (bookingDetails.isSelected()) {

        } else if (orderDetails.isSelected()) {
            OrderViewFromController.getController().searchOrder(txtSearchText.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(pane, "Admin/VewOrderFrom.fxml");

    }
}
