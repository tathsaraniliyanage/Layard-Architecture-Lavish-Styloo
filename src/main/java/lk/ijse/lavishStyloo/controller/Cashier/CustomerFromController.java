package lk.ijse.lavishStyloo.controller.Cashier;

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
import lk.ijse.lavishStyloo.bo.custom.BookingBO;
import lk.ijse.lavishStyloo.bo.custom.CustomerBO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.tm.CustomerTm;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFromController implements Initializable {

    public static String customerId;
    private static CustomerFromController customerFromController;
    public Text txtCustomersCount;
    public Text txtBookingCount;
    public JFXTextField txtSearchText;
    public Text btnText;
    public JFXButton btnClear;

    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colMail;
    public TableColumn colContact;
    public TableColumn colAction;

    ObservableList<CustomerTm> list = FXCollections.observableArrayList();
    CustomerBO customerBo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);


    public CustomerFromController() {
        customerFromController = this;
    }

    public static CustomerFromController controller() {
        return customerFromController;
    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        if (btnText.getText().equals("NEW")) {
            NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");
        } else {
            NavigationUtility.popupNavigation("Cashier/CustomerUpdateFrom.fxml");
        }
    }

    public void customerFromTblOnClick(MouseEvent mouseEvent) {
        btnText.setText("UPDATE");
        btnClear.setVisible(true);

        CustomerTm customerTm = (CustomerTm) tblCustomer.getSelectionModel().getSelectedItem();
        customerId = customerTm.getColId();

    }

    public void CustomerFromClearOnAction(ActionEvent actionEvent) {
        tblCustomer.getSelectionModel().clearSelection();
        btnClear.setVisible(false);
        btnText.setText("NEW");
    }

    public void customerFromSearchOnKeyReleased(KeyEvent keyEvent) {
        String searchText = txtSearchText.getText();
        try {
            List<CustomerDTO> customerDTOS = customerBo.findCustomerByLike(searchText);
            setCustomerTm(customerDTOS);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadAllCustomer() {
        try {
            List<CustomerDTO> list = customerBo.findAll();
            setCustomerTm(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCustomerTm(List<CustomerDTO> list) {
        tblCustomer.getItems().clear();
        this.list.clear();

        SimpleDateFormat format = new SimpleDateFormat();
        format.format(new Date());

        for (CustomerDTO customerDTO : list) {
            CustomerTm tm = new CustomerTm();
            tm.setColId(customerDTO.getCustomer_id());
            tm.setColName(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
            tm.setColAddress(customerDTO.getLane() + ", " + customerDTO.getStreet() + ", " + customerDTO.getCity());
            tm.setColContact(customerDTO.getContact());
            tm.setColMail(customerDTO.getEmail());
            this.list.add(tm);
        }

        tblCustomer.refresh();
    }

    private void setCustomerBookingCount() {
        try {
            txtBookingCount.setText(bookingBO.countBooking());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setCustomerCount() {
        try {
            txtCustomersCount.setText(customerBo.countCustomer());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("colId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("colName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("colAddress"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("colMail"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("colContact"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("colAction"));
        tblCustomer.setItems(list);
        loadAllCustomer();
        setCustomerCount();
        setCustomerBookingCount();
    }

    /*private CustomerDTO getTextFieldToCustomerData() {
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
    }*/

}
