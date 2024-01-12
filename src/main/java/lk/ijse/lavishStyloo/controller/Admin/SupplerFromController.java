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

public class SupplerFromController implements Initializable {
    public JFXTextField txtSearchText;
    public AnchorPane pane;
    public JFXRadioButton supplerOrder;
    public JFXRadioButton supper;
    public JFXRadioButton supplerOrderView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(pane, "Admin/SupplerManageFrom.fxml");
        supper.setSelected(true);
    }

    public void supplerOrderViewOnAction(ActionEvent actionEvent) {
        setAction();
    }

    private void setAction() {
        if (supper.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Admin/SupplerManageFrom.fxml");
        } else if (supplerOrder.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Admin/SupplierOrderFrom.fxml");

        } else if (supplerOrderView.isSelected()) {
            NavigationUtility.onTheTopNavigation(pane, "Admin/VewSupplerOrderFrom.fxml");

        }
    }

    public void supperOnAction(ActionEvent actionEvent) {
        setAction();
    }

    public void supplerOrderOnAction(ActionEvent actionEvent) {
        setAction();
    }

    public void productFromSearchOnKeyReleased(KeyEvent keyEvent) {

    }
}
