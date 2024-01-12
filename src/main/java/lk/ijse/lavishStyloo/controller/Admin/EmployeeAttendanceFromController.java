package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.AttendanceBO;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.dto.AttendanceDTO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.tm.AttendanceTm;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeeAttendanceFromController implements Initializable {

    public Text txtDetailsFistName;
    public Text txtDetailsNIC;
    public Text txtDetailsInTime;
    public Text txtDetailsOutIme;
    public Text txtDetailsDate;

    public TableView tblAttendance;
    public TableColumn colNic;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDate;
    public TableColumn colInTime;
    public TableColumn colOutTime;

    public Text txtAllTodayAttendance;

    public JFXTextField txtSearchText;

    public JFXTextField txtEmployeeNic;
    public Text txtName;
    public Text txtNIC;
    public DatePicker txtDate;
    public JFXRadioButton rBtnToDayAttendance;
    public JFXRadioButton rBtnSelectedAttendance;
    public JFXButton btnAdd;

    ObservableList<AttendanceTm> list = FXCollections.observableArrayList();

    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ATTENDANCE);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public void employeeNicOnKeyReleased(KeyEvent keyEvent) {
        EmployeeDTO employeeDTO = null;
        try {
            employeeDTO = employeeBO.findById(txtEmployeeNic.getText());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (employeeDTO.getNic() != null) {
            btnAdd.setDisable(false);
            txtNIC.setText(employeeDTO.getNic());
            txtName.setText(employeeDTO.getFirst_name() + " " + employeeDTO.getLast_name());
        } else {
            txtNIC.setText("");
            txtName.setText("");
            btnAdd.setDisable(true);
        }
    }

    public void addOnAction(ActionEvent actionEvent) {

        btnAdd.setDisable(true);

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setNic(txtNIC.getText());
        attendanceDTO.setDate(DateTimeUtil.dateNow());
        attendanceDTO.setIn_time(DateTimeUtil.timeNow());
        attendanceDTO.setOut_time("00:00:00");

        try {
            AttendanceDTO dto = attendanceBO.findAttendanceByDateAndNic(txtNIC.getText(), DateTimeUtil.dateNow());
            if (dto.getNic() == null) {
                boolean attendanceSave = attendanceBO.save(attendanceDTO);
                if (attendanceSave) {
                    txtName.setText("");
                    txtNIC.setText("");
                    txtEmployeeNic.clear();
                    loadTodayAttendance();
                    Notification.notification("Attendance", "Successes");

                }
            } else {
                txtEmployeeNic.clear();
//                new Alert(Alert.AlertType.WARNING, "Employee is Exist ! ").show();
                Notification.notificationWARNING("Attendance", "Employee is Exist ! ");
                txtName.setText("");
                txtNIC.setText("");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void attendanceFromTblOnClick(MouseEvent mouseEvent) {
        try {

            AttendanceTm tm = (AttendanceTm) tblAttendance.getSelectionModel().getSelectedItem();
            txtDetailsFistName.setText(tm.getName());
            txtDetailsNIC.setText(tm.getNic());
            txtDetailsDate.setText(tm.getDate());
            txtDetailsInTime.setText(tm.getInTime());
            txtDetailsOutIme.setText(tm.getOutTime());

            if (attendanceBO.isOutTimeUpdated(tm.getNic())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are Your Sure ? Mark Out Time", ButtonType.NO, ButtonType.YES);
                alert.showAndWait();
                if (alert.getResult().equals(ButtonType.YES)) {
                    boolean outTime = attendanceBO.setOutTime(tm.getNic());
                    if (outTime) {
                        Notification.notification("Attendance", "Out Time is Reported");
                        loadTodayAttendance();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void attendanceFromSearchOnKeyReleased(KeyEvent keyEvent) {
        try {
            String date = String.valueOf(txtDate.getValue());
            if (txtDate.getValue() == null)
                date = DateTimeUtil.dateNow();
            List<AttendanceTm> list = attendanceBO.findByDateAndNameAndNic(date, txtSearchText.getText());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadTodayAttendance() {
        setAttendanceCount(DateTimeUtil.dateNow());
        try {
            List<AttendanceTm> list = attendanceBO.findByDate(DateTimeUtil.dateNow());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<AttendanceTm> list) {
        /**
         * Set Details to employee attendance details box
         * */
        for (AttendanceTm tm : list) {
            txtDetailsFistName.setText(tm.getName());
            txtDetailsNIC.setText(tm.getNic());
            txtDetailsDate.setText(tm.getDate());
            txtDetailsInTime.setText(tm.getInTime());
            txtDetailsOutIme.setText(tm.getOutTime());
        }
        /**
         * clear table and Observable list
         * */
        tblAttendance.getItems().clear();
        this.list.clear();
        /**
         * set data to Observable list
         * */
        tblAttendance.getItems().addAll(list);
        tblAttendance.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        tblAttendance.setItems(list);

        loadTodayAttendance();
        setAttendanceCount(DateTimeUtil.dateNow());
    }

    private void setAttendanceCount(String date) {
        try {
            txtAllTodayAttendance.setText(attendanceBO.countAttendanceByDate(date));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dateOnAction(ActionEvent actionEvent) {
        setSelectedDateAttendance();
    }

    private void setSelectedDateAttendance() {
        String date = String.valueOf(txtDate.getValue());
        if (txtDate.getValue() == null)
            date = DateTimeUtil.dateNow();
        setAttendanceCount(date);
        try {
            List<AttendanceTm> list = attendanceBO.findByDate(date);
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void rBtnToDayAttendanceOnAction(ActionEvent actionEvent) {
        setDate();
    }

    private void setDate() {
        if (rBtnSelectedAttendance.isSelected()) {
            txtDate.setDisable(false);
            setSelectedDateAttendance();
        }
        if (rBtnToDayAttendance.isSelected()) {
            txtDate.setDisable(true);
            loadTodayAttendance();
        }

    }

    public void rBtnSelectedAttendanceOnAction(ActionEvent actionEvent) {
        setDate();
    }
}
