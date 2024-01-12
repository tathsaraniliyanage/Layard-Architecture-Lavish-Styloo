package lk.ijse.lavishStyloo.controller.Admin;


import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.EmployeeBO;
import lk.ijse.lavishStyloo.bo.custom.SalaryBO;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFromController implements Initializable {
    private static EmployeeFromController controller;
    public JFXRadioButton employeeSalary;
    public AnchorPane pane;
    public JFXRadioButton employee;
    public Text txtSalaryCount;
    public Text txtEmployeesCount;
    SalaryBO salaryBO = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALARY);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public EmployeeFromController() {
        controller = this;
    }

    public static EmployeeFromController getController() {
        return controller;
    }

    public void employeeSalaryOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(pane, "/Admin/EmployeeSalaryFrom.fxml");
    }

    public void employeeOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(pane, "/Admin/EmployeeChaildFrom.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(pane, "/Admin/EmployeeChaildFrom.fxml");
        setCount();
    }

    public void setCount() {
        try {
            txtEmployeesCount.setText(employeeBO.CountEmployee() + "+");
            txtSalaryCount.setText(salaryBO.getCount() + "+");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
