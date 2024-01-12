package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.bo.BOFactory;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.Notification;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CustomerUpdateFromController implements Initializable {
    public Text txtCustomerId;
    @FXML
    public JFXTextField txtFirstName;
    @FXML
    public JFXTextField txtLastName;
    @FXML
    public JFXTextField txtMail;
    @FXML
    public JFXTextField txtStreet;
    @FXML
    public JFXTextField txtLane;
    @FXML
    public JFXTextField txtCity;
    @FXML
    public JFXTextField txtContact;
    public JFXButton btn;

    CustomerBO customerBo= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    private boolean checkFinal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("\\b([a-z]|[A-Z]|[\\s])+");
        list.add("0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}");

        return RegexUtil.checkFinalResult(list, btn,
                txtFirstName,
                txtLastName,
                txtMail,
                txtStreet,
                txtLane,
                txtCity,
                txtContact);
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    public void customerUpdateOnAction(ActionEvent actionEvent) {


        if (checkFinal()) {
            CustomerDTO customerDTO = getCustomer();
            String customer_id = txtCustomerId.getText();
            customerDTO.setCustomer_id(customer_id);
            try {
                boolean customerUpdate = customerBo.update(customerDTO);
                if (customerUpdate) {
                    Notification.notification("Customer Update", "successful Customer Update");
                    NavigationUtility.close(actionEvent);
                    CustomerFromController.controller().loadAllCustomer();
                    CustomerFromController.controller().setCustomerCount();
                } else {
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void getCustomerById(String customerId) {
        CustomerDTO customerById = null;
        try {
            customerById = customerBo.findCustomerById(customerId);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        setCustomer(customerById);
    }

    private CustomerDTO getCustomer() {
        String first_name = txtFirstName.getText();
        String last_name = txtLastName.getText();
        String email = txtMail.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String street = txtStreet.getText();
        String contact = txtContact.getText();

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setFirst_name(first_name);
        customerDTO.setLast_name(last_name);
        customerDTO.setEmail(email);
        customerDTO.setCity(city);
        customerDTO.setLane(lane);
        customerDTO.setStreet(street);
        customerDTO.setContact(contact);

        return customerDTO;
    }

    private void setCustomer(CustomerDTO customerDTO) {
        txtCustomerId.setText(customerDTO.getCustomer_id());
        txtFirstName.setText(customerDTO.getFirst_name());
        txtLastName.setText(customerDTO.getLast_name());
        txtMail.setText(customerDTO.getEmail());
        txtCity.setText(customerDTO.getCity());
        txtLane.setText(customerDTO.getLane());
        txtStreet.setText(customerDTO.getStreet());
        txtContact.setText(customerDTO.getContact());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomerById(CustomerFromController.customerId);
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
}
