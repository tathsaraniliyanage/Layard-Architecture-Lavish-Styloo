package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.SupplierBO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.dto.tm.SupplerTm;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class SupplerManageFromController implements Initializable {
    public static String supplier_id;
    private static SupplerManageFromController controller;
    public TableColumn colCompany;
    public TableColumn colDealer;
    public TableColumn colId;
    public TableColumn colLocation;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colOption;
    public TableView tbl;
    public Text txtAllSuppliers;
    public Text btnText;
    public JFXButton btnClear;
    ObservableList<SupplerTm> list = FXCollections.observableArrayList();
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public SupplerManageFromController() {
        controller = this;
    }



    /*public void searchOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<SupplerTm> list = TreatmentModel.loadAllTreatmentByLike(txtSearchText.getText());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public static SupplerManageFromController getController() {
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colDealer.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        tbl.setItems(list);

        setTableData();

    }

    public void setTableData() {
        setCount();
        this.list.clear();
        tbl.getItems().clear();
        try {
            List<SupplierDTO> all = supplierBO.findAll();
            for (SupplierDTO dto : all) {
                SupplerTm supplerTm = new SupplerTm();
                supplerTm.setSupplier_id(dto.getSupplier_id());
                supplerTm.setSupplier_name(dto.getSupplier_name());
                supplerTm.setCompany(dto.getCompany());
                supplerTm.setEmail(dto.getEmail());
                supplerTm.setContact(dto.getContact());
                supplerTm.setLocation(dto.getLocation());
                this.list.add(supplerTm);
            }
            toProcess(this.list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCount() {
        try {
            txtAllSuppliers.setText(supplierBO.countSupper());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(ObservableList<SupplerTm> list) {
        tbl.setItems(list);
        tbl.refresh();

    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        if (btnText.getText().equals("NEW")) {
            NavigationUtility.popupNavigation("Admin/SupplyAddFrom.fxml");
        } else {
            NavigationUtility.popupNavigation("Admin/SupplyUpdateFrom.fxml");
        }
    }

    public void CustomerFromClearOnAction(ActionEvent actionEvent) {
        tbl.getSelectionModel().clearSelection();
        btnClear.setVisible(false);
        btnText.setText("NEW");
    }

    public void tblOnMouseClick(MouseEvent mouseEvent) {
        btnText.setText("UPDATE");
        btnClear.setVisible(true);

        SupplerTm supplerTm = (SupplerTm) tbl.getSelectionModel().getSelectedItem();
        supplier_id = supplerTm.getSupplier_id();
    }
}
