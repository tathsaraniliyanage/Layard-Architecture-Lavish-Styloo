package lk.ijse.lavishStyloo.controller.Admin;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.tm.EmployeeTm;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeChildFromController implements Initializable {
    public static String nic;
    private static EmployeeChildFromController controller;
    public JFXTextField txtSearchText;
    public JFXButton btnClear;
    public TableView tbl;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colMail;
    public TableColumn colContact;
    public TableColumn colAction;
    public Text btnText;
    ObservableList<EmployeeTm> list = FXCollections.observableArrayList();
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public EmployeeChildFromController() {
        controller = this;
    }

    public static EmployeeChildFromController getController() {
        return controller;
    }

    public void employeeFromSearchOnKeyReleased(KeyEvent keyEvent) {
        String searchText = txtSearchText.getText();
        try {
            List<EmployeeDTO> list = employeeBO.findEmployeeByLike(searchText);
            setEmployeeTm(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void employeeFromClearOnAction(ActionEvent actionEvent) {
        tbl.getSelectionModel().clearSelection();
        btnClear.setVisible(false);
        btnText.setText("NEW");
    }

    public void employeeFromTblOnClick(MouseEvent mouseEvent) {
        EmployeeTm dto = (EmployeeTm) tbl.getSelectionModel().getSelectedItem();
        //System.out.println(dto.getId());
        nic = dto.getId();

        btnText.setText("UPDATE");
        btnClear.setVisible(true);


    }

    public void employeeFromAddOnAction(ActionEvent actionEvent) {
        if (btnText.getText().equals("NEW")) {
            NavigationUtility.popupNavigation("Admin/EmployeeAddFrom.fxml");
        } else {
            NavigationUtility.popupNavigation("Admin/EmployeeUpdateFrom.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
        tbl.setItems(list);

        loadAllEmployee();
    }

    public void loadAllEmployee() {
        try {
            List<EmployeeDTO> list = employeeBO.findEmployee();
            setEmployeeTm(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setEmployeeTm(List<EmployeeDTO> list) {
        this.list.clear();
        for (EmployeeDTO dto : list) {
            EmployeeTm tm = new EmployeeTm();
            tm.setId(dto.getNic());
            tm.setName(dto.getFirst_name() + " " + dto.getLast_name());
            tm.setAddress(dto.getCity() + ", " + dto.getLane() + ", " + dto.getStreet());
            tm.setEmail(dto.getEmail());
            tm.setContact(dto.getContact());
            this.list.add(tm);
        }
        tbl.refresh();
    }
}
