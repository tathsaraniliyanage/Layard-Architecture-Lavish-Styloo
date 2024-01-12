package lk.ijse.lavishStyloo.controller.Cashier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.tm.AppointmentEmployeeTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookingAvailableEmployeeFromController implements Initializable {
    public static ArrayList<String> empLis = new ArrayList<>();
    public TableView tblEmp;
    public TableColumn colEmployee;
    public TableColumn colStatus;
    ObservableList<AppointmentEmployeeTm> list = FXCollections.observableArrayList();
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllEmployee();
        tblEmp.setItems(list);
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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
