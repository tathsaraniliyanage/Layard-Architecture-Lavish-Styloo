package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.SupplierBO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.Notification;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplyUpdateFromController implements Initializable {
    public JFXTextField txtSupperName;
    public JFXTextField txtCompany;
    public JFXTextField txtMail;
    public JFXTextField txtLocation;
    public JFXTextField txtContact;
    public Text txtId;
    public JFXButton btn;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);


    private boolean isCheckFinal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        return RegexUtil.checkFinalResult(list, btn,
                txtMail,
                txtCompany,
                txtLocation,
                txtContact,
                txtSupperName);
    }

    public void SupplyUpdateOnAction(ActionEvent actionEvent) {
        if (isCheckFinal()) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplier_id(txtId.getText());
            supplierDTO.setSupplier_name(txtSupperName.getText());
            supplierDTO.setCompany(txtCompany.getText());
            supplierDTO.setContact(txtContact.getText());
            supplierDTO.setEmail(txtMail.getText());
            supplierDTO.setLocation(txtLocation.getText());

            try {
                boolean isSave = supplierBO.update(supplierDTO);
                if (isSave) {
                    closeOnAction(actionEvent);
                    SupplerManageFromController.getController().setTableData();
                    Notification.notification("Suppler", "Process Completely successful");

                } else {
                    Notification.notificationWARNING("Suppler", "Process Something Wrong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            // Notification.notificationWARNING("Suppler","check again");
        }
    }


    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierById(SupplerManageFromController.supplier_id);
    }

    private void loadSupplierById(String id) {
        try {
            SupplierDTO dto = supplierBO.findSupplierById(id);
            txtCompany.setText(dto.getCompany());
            txtContact.setText(dto.getContact());
            txtId.setText(dto.getSupplier_id());
            txtLocation.setText(dto.getLocation());
            txtMail.setText(dto.getEmail());
            txtSupperName.setText(dto.getSupplier_name());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void mailOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtMail, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$", "-fx-text-fill: black");
    }

    public void companyOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtCompany, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");

    }

    public void locationOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtLocation, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");

    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtContact, "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}", "-fx-text-fill: black");

    }

    public void nameOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtSupperName, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");

    }
}
