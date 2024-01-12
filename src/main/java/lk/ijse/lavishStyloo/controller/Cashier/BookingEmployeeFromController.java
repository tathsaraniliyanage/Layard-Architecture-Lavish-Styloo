package lk.ijse.lavishStyloo.controller.Cashier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.tm.AppointmentEmployeeTm;
import lk.ijse.lavishStyloo.dto.tm.AppointmentTm;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookingEmployeeFromController implements Initializable {
    public static ArrayList<String> empLis = new ArrayList<>();
    public TableView tblEmp;
    public TableColumn colEmployee;
    public TableColumn colStatus;
    public TableColumn colOption;
    ObservableList<AppointmentEmployeeTm> list = FXCollections.observableArrayList();
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public void btnApplyOnAction(ActionEvent actionEvent) {
        if (!empLis.isEmpty()) {
            for (AppointmentTm tm : BookingFromController.getInstance().list) {
                if (empLis.get(0).equals(tm.getTreatment_id())) {
                    tm.setEmployee(empLis.get(2));
                    tm.setNic(empLis.get(1));

                }
            }
        }
        BookingFromController.getInstance().tblBooking.refresh();
        empLis.clear();
        NavigationUtility.close(actionEvent);
    }

    public void btnApplyAllOnAction(ActionEvent actionEvent) {
        if (!empLis.isEmpty()) {
            for (AppointmentTm tm : BookingFromController.getInstance().list) {
                tm.setEmployee(empLis.get(2));
                tm.setNic(empLis.get(1));
            }
        }
        BookingFromController.getInstance().tblBooking.refresh();
        empLis.clear();
        NavigationUtility.close(actionEvent);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllEmployee();
        tblEmp.setItems(list);
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("choose"));
    }

    private void loadAllEmployee() {
        try {
            List<EmployeeDTO> list = employeeBO.findEmployeeAvelebel();
            for (EmployeeDTO dto : list) {
                AppointmentEmployeeTm tm = new AppointmentEmployeeTm();
                tm.setEmployee(dto.getFirst_name() + " " + dto.getLast_name());
                tm.setNic(dto.getNic());
                tm.setStatus("Available");
                this.list.add(tm);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
