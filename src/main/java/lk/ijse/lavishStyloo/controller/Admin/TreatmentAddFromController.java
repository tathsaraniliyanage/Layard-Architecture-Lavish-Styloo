package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.TreatmentBO;
import lk.ijse.lavishStyloo.dto.TreatmentDTO;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.Notification;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TreatmentAddFromController implements Initializable {

    public JFXTextField txtTreatment;
    public JFXTextField txtPrice;
    public JFXTextField txtDescription;
    public JFXComboBox cmbCategory;
    public JFXButton btn;

    TreatmentBO treatmentBO = (TreatmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TREATMENT);


    public void AddOnAction(ActionEvent actionEvent) {
        if (isFinalCheck() && cmbCategory.getValue() != null) {

            TreatmentDTO treatmentDTO = new TreatmentDTO();
            treatmentDTO.setTreat_id(getNext());
            treatmentDTO.setTreatment(txtTreatment.getText());
            treatmentDTO.setCategory(String.valueOf(cmbCategory.getValue()));
            treatmentDTO.setPrice(Double.parseDouble(txtPrice.getText()));
            treatmentDTO.setDescription(txtDescription.getText());
            try {
                boolean isSave = treatmentBO.save(treatmentDTO);
                if (isSave) {
                    TreatmentFromController.getController().loadAllTreatment();
                    closeOnAction(actionEvent);
//                new Alert(Alert.AlertType.CONFIRMATION, "ok").show();
                    Notification.notification("Treatment", "Process Completely successful");
                } else {
                    //   new Alert(Alert.AlertType.CONFIRMATION, "not").show();
                    Notification.notificationWARNING("Treatment", "Process Something Wrong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            //  Notification.notificationWARNING("Treatment", "check All fields");
        }
    }

    private boolean isFinalCheck() {
        ArrayList<String> list = new ArrayList<>();
        list.add("^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        return RegexUtil.checkFinalResult(list, btn,
                txtPrice,
                txtTreatment,
                txtDescription);

    }

    private String getNext() {
        try {
            return treatmentBO.getNext();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCategory();
    }

    private void setCategory() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("HAIR TREATMENT");
        list.add("HAIR CUTS");
        list.add("HAIR STYLES");
        list.add("HAIR COLOURING");
        list.add("FULL MAKEUP");
        list.add("NAIL TREATMENTS");
        list.add("LASH LIFT & TINT");
        cmbCategory.setItems(list);
    }

    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: black");
    }

    public void treatmentOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtTreatment, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");

    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtDescription, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");

    }
}
