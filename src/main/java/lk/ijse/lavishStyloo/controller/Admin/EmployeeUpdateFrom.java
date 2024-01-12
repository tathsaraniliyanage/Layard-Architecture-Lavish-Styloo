package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.dao.DAOFactory;
import lk.ijse.lavishStyloo.dao.custom.UserDAO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.UserDTO;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.Notification;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeUpdateFrom implements Initializable {
    public JFXButton btn;

    public DatePicker txtDate;

    public JFXComboBox cmbGender;

    public JFXTextField txtNic;
    public Text txtWarningNic;
    public JFXComboBox cmbRole;
    public JFXTextField txtUseName;
    public JFXTextField txtPassword;

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    EmployeeBO employeeBO= (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);



    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtMail;

    @FXML
    private JFXTextField txtStreet;

    @FXML
    private JFXTextField txtLane;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtContact;


    public void employeeUpdateOnAction(ActionEvent actionEvent) {
        if (checkIsFinal()) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setNic(txtNic.getText());
            employeeDTO.setFirst_name(txtFirstName.getText());
            employeeDTO.setLast_name(txtLastName.getText());
            employeeDTO.setEmail(txtMail.getText());
            employeeDTO.setCity(txtCity.getText());
            employeeDTO.setLane(txtLane.getText());
            employeeDTO.setStreet(txtStreet.getText());
            employeeDTO.setContact(txtContact.getText());
            employeeDTO.setDateOfBirth(txtDate.getValue());
            employeeDTO.setGender(String.valueOf(cmbGender.getValue()));
            employeeDTO.setRole(String.valueOf(cmbRole.getValue()));

            try {
                if (cmbRole.getValue().equals("ADMIN") || cmbRole.getValue().equals("CASHIER")) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setNic(txtNic.getText());
                    userDTO.setUserName(txtUseName.getText());
                    userDTO.setPassword(txtPassword.getText());

                    if (userDAO.isExUser(txtNic.getText())) {
                        if (employeeBO.update(employeeDTO, userDTO)) {
                            EmployeeFromController.getController().setCount();
                            EmployeeChildFromController.getController().loadAllEmployee();
                            NavigationUtility.close(actionEvent);
                            Notification.notification("Update", "Process Successful");
                        } else {
                            Notification.notificationWARNING("Update", "Something Wong");
                        }
                    } else {
                        if (employeeBO.updateAndUserSave(employeeDTO, userDTO)) {
                            EmployeeFromController.getController().setCount();
                            EmployeeChildFromController.getController().loadAllEmployee();
                            NavigationUtility.close(actionEvent);
                            Notification.notification("Update", "Process Successful");
                        } else {
                            Notification.notificationWARNING("Update", "Something Wong");
                        }
                    }

                } else {
                    if (userDAO.isExUser(txtNic.getText())) {
                        if (employeeBO.updateAndRemoveUser(employeeDTO)) {
                            EmployeeFromController.getController().setCount();
                            EmployeeChildFromController.getController().loadAllEmployee();
                            NavigationUtility.close(actionEvent);
                            Notification.notification("Update", "Process Successful");
                        } else {
                            Notification.notificationWARNING("Update", "Something Wong");
                        }
                    } else {
                        boolean isSave = employeeBO.update(employeeDTO);
                        if (isSave) {
                            NavigationUtility.close(actionEvent);
                            EmployeeFromController.getController().setCount();
                            EmployeeChildFromController.getController().loadAllEmployee();
//                           new Alert(Alert.AlertType.CONFIRMATION, "Employee updated ! ").show();
                            Notification.notification("Update", "employee Update Successfully");
                        } else {
                            Notification.notificationWARNING("Update", "something wrong");
                        }
                    }
                }
               /* boolean isSave = EmployeeModel.update(employeeDTO);
                if (isSave) {
                    EmployeeFromController.getController().setCount();
                    EmployeeChildFromController.getController().loadAllEmployee();
//                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated ! ").show();
                    Notification.notification("Update", "employee Update Successfully");
                } else {
                    Notification.notificationWARNING("Update", "something wrong");
                }*/
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    private boolean checkIsFinal() {
        ArrayList<String> list = new ArrayList<>();

        list.add("^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}");

        return RegexUtil.checkFinalResult(list, btn,
                txtNic,
                txtFirstName,
                txtLastName,
                txtMail,
                txtStreet,
                txtLane,
                txtCity,
                txtContact
        );
    }


    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }


    public void fNameOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtFirstName, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void lastOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtLastName, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void mailOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtMail, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$", "-fx-text-fill: black");
    }

    public void streetOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtStreet, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void laneOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtLane, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void cityOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtCity, "\\b([a-z]|[A-Z]|[\\s])+", "-fx-text-fill: black");
    }

    public void contactOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtContact, "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}", "-fx-text-fill: black");
    }

    public void nicOnKeyRelease(KeyEvent keyEvent) {
        boolean isMatch = RegexUtil.regex(btn, txtNic, "^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$", "-fx-text-fill: black");
        try {
            EmployeeDTO employeeDTO = employeeBO.findById(txtNic.getText());
            boolean isNicNotExists = employeeDTO.getNic() == null;
            if (!isNicNotExists && !isMatch) {
                txtWarningNic.setText("This Nic Already Exists");
            } else {
                txtWarningNic.setText("");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGender();
        setRole();
        setEmployee(EmployeeChildFromController.nic);
    }

    private void setRole() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("ADMIN");
        list.add("CASHIER");
        list.add("WORKER");
        cmbRole.setItems(list);
    }

    private void setEmployee(String nic) {
        try {
            EmployeeDTO employeeDTO = employeeBO.findById(nic);
            txtNic.setText(employeeDTO.getNic());
            txtFirstName.setText(employeeDTO.getFirst_name());
            txtLastName.setText(employeeDTO.getLast_name());
            txtMail.setText(employeeDTO.getEmail());
            txtCity.setText(employeeDTO.getCity());
            txtLane.setText(employeeDTO.getLane());
            txtStreet.setText(employeeDTO.getStreet());
            txtContact.setText(employeeDTO.getContact());
            txtDate.setValue(employeeDTO.getDateOfBirth());
            cmbGender.setValue(employeeDTO.getGender());
            cmbRole.setValue(employeeDTO.getRole());
            if (employeeDTO.getRole().equals("ADMIN") | employeeDTO.getRole().equals("CASHIER")) {
                UserDTO userDTO = employeeBO.getUser(employeeDTO.getNic());
                txtUseName.setVisible(true);
                txtPassword.setVisible(true);

                txtUseName.setText(userDTO.getUserName());
                txtPassword.setText(userDTO.getPassword());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setGender() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("MALE");
        list.add("FEMALE");
        list.add("OTHER");
        cmbGender.setItems(list);
    }
}
