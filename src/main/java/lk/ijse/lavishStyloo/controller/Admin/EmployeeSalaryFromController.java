package lk.ijse.lavishStyloo.controller.Admin;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.AttendanceBO;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.bo.custom.SalaryBO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.SalaryDTO;
import lk.ijse.lavishStyloo.dto.tm.SalaryTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeSalaryFromController implements Initializable {

    public JFXTextField lblSalary;
    ObservableList<SalaryTm> list = FXCollections.observableArrayList();
    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ATTENDANCE);
    SalaryBO salaryBO = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALARY);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @FXML
    private AnchorPane pane;
    @FXML
    private Text txtNetTotal;
    @FXML
    private JFXTextField LblBones;
    @FXML
    private Text txtAttendance;
    @FXML
    private Text txtAddress12;
    @FXML
    private Text txtAddress3;
    @FXML
    private Text txtAddresse;
    @FXML
    private JFXTextField LblCode;
    @FXML
    private Text txtAddress;
    @FXML
    private Text txtName;
    @FXML
    private Text txtContact;
    @FXML
    private JFXTextField txtSearchText;
    @FXML
    private TableView tbl;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colMail;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colBones;
    @FXML
    private TableColumn colSalary;
    @FXML
    private JFXButton btnClear;

    @FXML
    void btnTotalOnAction(ActionEvent event) {
        try {
            try {
                SalaryDTO salaryDTO = new SalaryDTO();
                salaryDTO.setSalary(Double.parseDouble(lblSalary.getText()));
                salaryDTO.setBonus(Double.parseDouble(LblBones.getText()));
                salaryDTO.setS_id(getNextId());
                salaryDTO.setDate(DateTimeUtil.dateNow());
                salaryDTO.setTime(DateTimeUtil.timeNow());
                salaryDTO.setNic(LblCode.getText());

                if (salaryDTO.getNic() == null && salaryDTO.getSalary() == 0 || salaryDTO.getBonus() == 0) {
                    Notification.notificationWARNING("Salary", "Check Your Information");
                } else {
                    boolean saved = salaryBO.save(salaryDTO);
                    if (saved) {
                        loadAllSalary();
                        lblSalary.clear();
                        LblBones.clear();
                        LblCode.clear();
                        txtAddress.setText("");
                        txtContact.setText("");
                        txtName.setText("");
                        txtAttendance.setText("0");
                        txtNetTotal.setText("00.00");
//                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                        Notification.notification("Salary", "Process Successful");
                    } else {
//                new Alert(Alert.AlertType.WARNING, "something wrong ").show();
                        Notification.notificationWARNING("Salary", "Process Something Wrong");
                    }
                }
            } catch (NumberFormatException e) {
                Notification.notificationWARNING("Salary", "Check Salary Or Bones and Fill all fields");
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getNextId() throws SQLException, ClassNotFoundException {
        return salaryBO.getNext();
    }

    @FXML
    void employeeFromSearchOnKeyReleased(KeyEvent event) {
        try {
            if (txtSearchText.getText().equals("")) {
                loadAllSalary();
                System.out.println(txtSearchText.getText());
            } else {
                List<SalaryTm> salaryTms = salaryBO.findSalaryByLike(txtSearchText.getText());
                toProcess(salaryTms);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void lblNicOnKeyReleased(KeyEvent event) {
        try {
            boolean isAlReadyExist = salaryBO.isExsitThisMonth(LblCode.getText());
            if (!isAlReadyExist) {
                EmployeeDTO employeeDTO = employeeBO.findById(LblCode.getText());
                txtAddress.setText(employeeDTO.getCity() + " ," + employeeDTO.getStreet() + " ," + employeeDTO.getLane());
                txtContact.setText(employeeDTO.getEmail() + " / " + employeeDTO.getContact());
                txtName.setText(employeeDTO.getFirst_name() + " " + employeeDTO.getLast_name());
                if (employeeDTO.getNic() != null) {
                    String count = attendanceBO.getEmployee(employeeDTO.getNic());
                    txtAttendance.setText(count);
                }
            } else {
                Notification.notificationWARNING("Salary", "This Employee Already Got a Salary");
//                new Alert(Alert.AlertType.WARNING, "This employee already got a salary").show();
                LblCode.clear();
                txtAddress.setText("");
                txtContact.setText("");
                txtName.setText("");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colBones.setCellValueFactory(new PropertyValueFactory<>("bones"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colId.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tbl.setItems(list);
        loadAllSalary();
    }

    private void loadAllSalary() {
        try {
            List<SalaryTm> salaryTms = salaryBO.findSalary();
            toProcess(salaryTms);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<SalaryTm> salaryTms) {
        list.clear();
        tbl.getItems().clear();
        list.addAll(salaryTms);
        tbl.refresh();
    }

    public void lblSalaryOnKeyReleased(KeyEvent keyEvent) {
        setNetTotal();
    }

    private void setNetTotal() {
        if (!txtAttendance.getText().equals("NOTHING ATTENDANCE")) {
            txtNetTotal.setText("0.0");
            int attendance = Integer.parseInt(txtAttendance.getText());
            double bones;
            try {
                bones = Double.parseDouble(LblBones.getText() == null | LblBones.getText().isEmpty() ? "0.0" : LblBones.getText());
            } catch (NumberFormatException e) {
                bones = 0.0;
            }
            double salary;
            try {
                salary = Double.parseDouble(lblSalary.getText() == null | lblSalary.getText().isEmpty() ? "0.0" : lblSalary.getText());
            } catch (NumberFormatException e) {
                salary = 0.0;
            }
            double total = 0;
            total = salary * attendance + bones;
            txtNetTotal.setText(String.valueOf(total));
        }
    }

    public void lblBonesOnKeyReleased(KeyEvent keyEvent) {
        setNetTotal();
    }
}
