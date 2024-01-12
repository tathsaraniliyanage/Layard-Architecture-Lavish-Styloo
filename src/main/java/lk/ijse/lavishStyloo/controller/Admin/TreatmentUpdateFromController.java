package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
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

public class TreatmentUpdateFromController implements Initializable {
    public JFXTextField txtTreatment;
    public JFXTextField txtPrice;
    public JFXTextField txtDescription;
    public JFXComboBox cmbCategory;
    public Text txtId;
    public JFXButton btn;

    TreatmentBO treatmentBO = (TreatmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TREATMENT);


    public void updateOnAction(ActionEvent actionEvent) {
        if (isFinalCheck()) {
            TreatmentDTO treatmentDTO = new TreatmentDTO();
            treatmentDTO.setTreat_id(txtId.getText());
            treatmentDTO.setTreatment(txtTreatment.getText());
            treatmentDTO.setCategory(String.valueOf(cmbCategory.getValue()));
            treatmentDTO.setPrice(Double.parseDouble(txtPrice.getText()));
            treatmentDTO.setDescription(txtDescription.getText());
            try {
                boolean isSave = treatmentBO.update(treatmentDTO);
                if (isSave) {
                    closeOnAction(actionEvent);
                    TreatmentFromController.getController().loadAllTreatment();
                    // new Alert(Alert.AlertType.CONFIRMATION,"ok").show();
                    Notification.notification("Treatment", "Process Completely successful");
                } else {
                    //  new Alert(Alert.AlertType.CONFIRMATION,"not").show();
                    Notification.notificationWARNING("Treatment", "Process Something Wrong");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            //Notification.notificationWARNING("Treatment", "check All fields");
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

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTreatment(lk.ijse.lavishStyloo.controller.Admin.TreatmentFromController.treatmentId);
    }

    private void setTreatment(String treatmentId) {
        try {
            TreatmentDTO dto = treatmentBO.findTreatmentById(treatmentId);
            txtDescription.setText(dto.getDescription());
            txtId.setText(dto.getTreat_id());
            txtTreatment.setText(dto.getTreatment());
            txtPrice.setText(String.valueOf(dto.getPrice()));
            cmbCategory.setValue(dto.getCategory());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: black");
    }

    public void treatmentOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtTreatment, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");

    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtDescription, "\\b([a-z]|[A-Z]|[\\s]|[([+-]?[0-9]+(?:\\.[0-9]{0,4})?)])+", "-fx-text-fill: black");

    }
}
