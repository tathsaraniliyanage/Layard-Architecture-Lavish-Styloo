package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.TreatmentBO;
import lk.ijse.lavishStyloo.dto.TreatmentDTO;
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookingTreatmentFromController implements Initializable {
    public TableView tblOrder;
    public TableColumn colId;
    public TableColumn colTreatment;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colDescription;
    public TableColumn colSelected;
    public JFXTextField txtSearchText;
    public JFXComboBox cmbCategory;

    ObservableList<TreatmentTm> list = FXCollections.observableArrayList();
    TreatmentBO treatmentBO = (TreatmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TREATMENT);

    public void SearchOnKeyReleased(KeyEvent keyEvent) {

    }

    public void cmbCatOnAction(ActionEvent actionEvent) {
        if (cmbCategory.getValue() != null) {
            try {
                List<TreatmentDTO> list = treatmentBO.findTreatmentByCategory(String.valueOf(cmbCategory.getValue()));
                toProcess(setTM(list));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else if (cmbCategory.getValue().equals("LOAD ALL")) {
            loadAllTreatment();
        }

    }

    private void toProcess(List<TreatmentTm> list) {

        this.list.clear();
        this.list.addAll(list);
        ObservableList<TreatmentTm> items = tblOrder.getItems();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSelected.setCellValueFactory(new PropertyValueFactory<>("tick"));
        tblOrder.setItems(list);

        // loadAllTreatment();
        loadCategory();
    }

    private void loadCategory() {
        try {
            List<String> list = treatmentBO.loadCategory();
            cmbCategory.getItems().add("LOAD ALL");
            cmbCategory.setValue("LOAD ALL");
            cmbCategory.getItems().addAll(list);
            loadAllTreatment();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadAllTreatment() {
        try {
            List<TreatmentDTO> list = treatmentBO.findAll();
            toProcess(setTM(list));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<TreatmentTm> setTM(List<TreatmentDTO> all) {
        List<TreatmentTm> list = new ArrayList<>();
        for (TreatmentDTO dto : all) {
            TreatmentTm tm = new TreatmentTm();
            tm.setId(dto.getTreat_id());
            tm.setPrice(String.valueOf(dto.getPrice()));
            tm.setCategory(dto.getCategory());
            tm.setTreatment(dto.getTreatment());
            tm.setDescription(dto.getDescription());
            list.add(tm);
        }
        return list;
    }
}
